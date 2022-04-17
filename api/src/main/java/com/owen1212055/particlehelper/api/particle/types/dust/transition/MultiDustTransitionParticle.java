package com.owen1212055.particlehelper.api.particle.types.dust.transition;

import com.owen1212055.particlehelper.api.particle.compiled.*;
import com.owen1212055.particlehelper.api.particle.types.dust.*;
import com.owen1212055.particlehelper.api.type.*;
import org.bukkit.*;

public class MultiDustTransitionParticle extends MultiDustParticle implements TransitionDustParticle {

    private Color fade;

    public MultiDustTransitionParticle(ParticleType<?, ?> particleType) {
        super(particleType);
    }

    @Override
    public Color getFadeColor() {
        return this.fade;
    }

    @Override
    public void setFadeColor(Color color) {
        this.fade = color;
    }

    @Override
    public CompiledParticle compile() {
        SimpleCompiledParticle simpleCompiledParticle = new SimpleCompiledParticle(this);
        simpleCompiledParticle.speed = 1; // Keep speed constant
        simpleCompiledParticle.data = new Particle.DustTransition(color, fade, size);

        return simpleCompiledParticle.compileSender();
    }
}
