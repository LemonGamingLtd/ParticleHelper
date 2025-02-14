package com.owen1212055.particlehelper.api.particle.types.dust;

import com.owen1212055.particlehelper.api.particle.Particle;
import com.owen1212055.particlehelper.api.particle.types.ColorableParticle;
import com.owen1212055.particlehelper.api.particle.types.SizeableParticle;

public interface DustParticle<T extends Particle<T>> extends SizeableParticle<T>, ColorableParticle<T> {

}
