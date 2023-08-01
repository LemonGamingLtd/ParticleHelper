package com.owen1212055.particlehelper.nms.v1_19_R1;

import com.owen1212055.particlehelper.api.particle.compiled.CompiledParticle;
import com.owen1212055.particlehelper.api.particle.compiled.simple.SimpleCompiledParticle;
import com.owen1212055.particlehelper.nms.ParticleHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_19_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftNamespacedKey;
import org.bukkit.entity.Player;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;

public class ParticleHelperImpl extends ParticleHelper {

	// Iterate over the Minecraft particles to map it to the bukkit api equivalents.
	static final Map<NamespacedKey, Particle> BUKKIT_MAP = new HashMap<>();

	static {
		Registry<net.minecraft.core.particles.ParticleType<?>> particleTypes = MinecraftServer.getServer().registryAccess().registryOrThrow(Registry.PARTICLE_TYPE_REGISTRY);
		for (Map.Entry<ResourceKey<net.minecraft.core.particles.ParticleType<?>>, net.minecraft.core.particles.ParticleType<?>> particleType : particleTypes.entrySet()) {
			BUKKIT_MAP.put(CraftNamespacedKey.fromMinecraft(particleType.getKey().location()), CraftParticle.toBukkit(particleType.getValue()));
		}
	}

	@Override
	public org.bukkit.Particle getBukkitParticle(NamespacedKey key) {
		return BUKKIT_MAP.get(key);
	}

	@Override
	public CompiledParticle getGroupedSender(CompiledParticle... simpleCompiledParticles) {
		// Since 1.19 doesn't support grouped sending, we just do it manually
		return new CompiledParticle() {
			@Override
			public void send(Player player, Location location) {
				getParticleSender(this).accept(player, location);
			}
		};
	}

	@Override
	public BiConsumer<Player, Location> getParticleSender(CompiledParticle compiledParticle) {
		SimpleCompiledParticle compiled;
		if (!(compiledParticle instanceof SimpleCompiledParticle)) {
			throw new UnsupportedOperationException();
		} else {
			compiled = (SimpleCompiledParticle) compiledParticle;
		}

		NamespacedKey key = compiled.particle.getKey();
		Particle bukkitParticle = BUKKIT_MAP.get(key);
		if (bukkitParticle == null) {
			throw new UnsupportedOperationException("Could not find particle %s, please report this.".formatted(key.toString()));
		}

		ParticleOptions nmsParticle = CraftParticle.toNMS(bukkitParticle, compiled.data);

		return new BiConsumer<>() {

			// Cache last sent
			private ClientboundLevelParticlesPacket cached;

			@Override
			public void accept(Player player, Location location) {
				ClientboundLevelParticlesPacket packet;

				// Locations same? Use same packet!
				if (cached != null && cached.getX() == location.getX() && cached.getY() == location.getY() && cached.getZ() == location.getZ()) {
					packet = cached;
				} else {
					packet = new ClientboundLevelParticlesPacket(nmsParticle,
							compiled.longDistance, location.getX(), location.getY(), location.getZ(),
							compiled.offsetX, compiled.offsetY, compiled.offsetZ, compiled.speed, compiled.count);
					cached = packet;
				}

				((CraftPlayer) player).getHandle().connection.send(packet);
			}
		};
	}

	public static ClientboundLevelParticlesPacket createPacket(ParticleOptions nmsParticle, Location location, SimpleCompiledParticle compiled) {
		return new ClientboundLevelParticlesPacket(nmsParticle,
				compiled.longDistance, location.getX(), location.getY(), location.getZ(),
				compiled.offsetX, compiled.offsetY, compiled.offsetZ, compiled.speed, compiled.count);
	}


}
