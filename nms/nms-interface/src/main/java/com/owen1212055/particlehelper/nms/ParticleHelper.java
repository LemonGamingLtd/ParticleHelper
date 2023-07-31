package com.owen1212055.particlehelper.nms;

import com.owen1212055.particlehelper.api.particle.compiled.CompiledParticle;
import java.util.function.BiConsumer;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public abstract class ParticleHelper {

	public abstract Particle getBukkitParticle(NamespacedKey key);

	public abstract BiConsumer<Player, Location> getParticleSender(CompiledParticle compiledParticle);

	public abstract CompiledParticle getGroupedSender(CompiledParticle... simpleCompiledParticles);

}
