package com.owen1212055.particlehelper.api.particle.types.dust.simple;

import com.owen1212055.particlehelper.api.particle.types.dust.MultiDustParticleImpl;
import com.owen1212055.particlehelper.api.type.ParticleType;

public class MultiDustParticle extends MultiDustParticleImpl<MultiDustParticle> {
    public MultiDustParticle(ParticleType<?, ?> particleType) {
        super(particleType);
    }
}
