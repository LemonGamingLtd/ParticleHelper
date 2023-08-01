package com.owen1212055.particlehelper.nms;

import com.owen1212055.particlehelper.api.particle.compiled.CompiledParticle;
import com.owen1212055.particlehelper.api.particle.compiled.simple.ParticleChannel;
import com.owen1212055.particlehelper.api.particle.compiled.simple.SimpleCompiledParticle;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class ParticleHelper implements ParticleChannel {

	private static final @NotNull ParticleHelper instance = getParticleHelperNms();

	public static @NotNull ParticleHelper getInstance() {
		return instance;
	}

	public abstract Particle getBukkitParticle(NamespacedKey key);

	public abstract BiConsumer<Player, Location> getSender(CompiledParticle compiledParticle);

	public abstract CompiledParticle getGroupedSender(CompiledParticle... simpleCompiledParticles);

	@Override
	public final BiConsumer<Player, Location> getSender(SimpleCompiledParticle compiledParticle) {
		return getSender((CompiledParticle) compiledParticle);
	}

	public static ParticleHelper getParticleHelperNms() {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("[.]")[3];
		String implementationClassName = "com.owen1212055.particlehelper." + version + ".ParticleHelperImpl";

		try {
			Class<ParticleHelper> implementationClass = (Class<ParticleHelper>) Class.forName(implementationClassName);
			return implementationClass.getConstructor().newInstance();
		} catch (ClassNotFoundException var4) {
			throw new UnsupportedOperationException("No NMS implementation of interface \"ParticleHelper\", searched for: " + implementationClassName);
		} catch (ClassCastException var5) {
			throw new IllegalStateException(implementationClassName + " must extend ParticleHelper");
		} catch (IllegalAccessException | NoSuchMethodException var6) {
			throw new IllegalStateException("NMSImplementation class " + implementationClassName + " must have a public no parameters constructor");
		} catch (InvocationTargetException | InstantiationException var7) {
			throw new IllegalStateException(var7);
		}
	}

}
