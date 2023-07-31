package com.owen1212055.particlehelper.plugin;

import com.owen1212055.particlehelper.api.particle.compiled.CompiledParticle;
import com.owen1212055.particlehelper.api.particle.compiled.simple.ParticleChannel;
import com.owen1212055.particlehelper.api.particle.compiled.simple.SimpleCompiledParticle;
import com.owen1212055.particlehelper.nms.ParticleHelper;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

public class Plugin extends JavaPlugin implements Listener, ParticleChannel {

    private final @NotNull ParticleHelper particleHelper = getParticleHelperNms();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        com.owen1212055.particlehelper.api.ParticleHelper.bootstrapParticleChannel();
    }

    @Override
    public BiConsumer<Player, Location> getSender(SimpleCompiledParticle particle) {
        return particleHelper.getParticleSender(particle);
    }

    @Override
    public CompiledParticle getGroupedSender(CompiledParticle... compiledParticles) {
        return particleHelper.getGroupedSender(compiledParticles);
    }

    public ParticleHelper getParticleHelperNms() {
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
