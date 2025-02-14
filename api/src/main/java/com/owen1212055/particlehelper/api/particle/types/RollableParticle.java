package com.owen1212055.particlehelper.api.particle.types;

import com.owen1212055.particlehelper.api.particle.Particle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a particle which can have its rotation (roll) adjusted.
 */
public interface RollableParticle<T extends Particle<T>> extends Particle<T> {

    float roll();

    @Contract("_ -> this")
    @NotNull T roll(float roll);
}
