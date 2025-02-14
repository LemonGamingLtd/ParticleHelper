package com.owen1212055.particlehelper.api.particle.types.common.velocity;

import com.owen1212055.particlehelper.api.particle.compiled.CompiledParticle;
import com.owen1212055.particlehelper.api.particle.compiled.simple.SimpleCompiledParticle;
import com.owen1212055.particlehelper.api.particle.types.AbstractSingleParticle;
import com.owen1212055.particlehelper.api.particle.types.velocity.YComponentVelocityParticle;
import com.owen1212055.particlehelper.api.type.ParticleType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a particle which has reduced momentum, which in this case is a very
 * small specific amount of particles.
 */
public class ReducedMomentumParticle extends AbstractSingleParticle<ReducedMomentumParticle> implements YComponentVelocityParticle<ReducedMomentumParticle> {

    protected boolean reducedMomentum;
    protected float yVelocity;

    public ReducedMomentumParticle(ParticleType<?, ?> particleType) {
        super(particleType);
    }

    public boolean hasReducedHorizontalMomentum() {
        return this.reducedMomentum;
    }

    public ReducedMomentumParticle setHasReducedHorizontalMomentum(boolean reduced) {
        this.reducedMomentum = reduced;
        return this;
    }

    @Override
    public float velocityY() {
        return this.yVelocity;
    }

    @Override
    public @NotNull ReducedMomentumParticle velocityY(float yVelocity) {
        this.yVelocity = yVelocity;
        return this;
    }

    @Override
    public @NotNull CompiledParticle compile() {
        SimpleCompiledParticle simpleCompiledParticle = new SimpleCompiledParticle(this);
        simpleCompiledParticle.offsetX = this.reducedMomentum ? 0 : 1;
        simpleCompiledParticle.offsetY = this.yVelocity;
        simpleCompiledParticle.speed = 1; // Keep speed constant

        return simpleCompiledParticle.compileSender();
    }
}
