package pers.ketikai.minecraft.forge.dragonbloom;

import blockbuster.BedrockScheme;
import blockbuster.emitter.BedrockEmitter;
import eos.moe.dragoncore.eea;
import eos.moe.dragoncore.kea;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.ketikai.minecraft.forge.dragonbloom.render.BloomEffect;
import pers.ketikai.minecraft.forge.dragonbloom.render.BloomHelper;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class DragonBloomHook {

    public static void hookBedrockEmitterSetScheme(@NotNull BedrockEmitter emitter, @Nullable BedrockScheme scheme, @Nullable Map<String, String> variables) {
        String schemeKey = scheme == null ? null : scheme.identifier;
        if (schemeKey != null) {
            try {
                if (!DragonBloom.getConfiguration().isMatched(schemeKey)) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String effect = emitter.effect;
            if (effect == null) {
                emitter.effect = "@";
            } else if (!effect.contains("@")) {
                emitter.effect = "@" + effect;
            }
            emitter.bloom = true;
        }
    }

    private static Field kea_x;
    private static Field kea_f;
    static  {
        try {
            kea_x = kea.class.getDeclaredField("x");
            kea_x.setAccessible(true);
            kea_f = kea.class.getDeclaredField("f");
            kea_f.setAccessible(true);
        } catch (NoSuchFieldException ignored) {}
    }

    private static ResourceLocation getGlowTexture(kea that) {
        if (that == null) {
            return null;
        }
        try {
            return (ResourceLocation) kea_f.get(that);
        } catch (IllegalAccessException e) {
            DragonBloom.getLogger().error("", e);
        }
        return null;
    }

    private static final ThreadLocal<Boolean> flag = ThreadLocal.withInitial(() -> false);

    public static kea hookEeaFunc_77036_a0(kea kea, eea eea, EntityLivingBase entity, float a, float b, float c, float d, float e, float f) {
        try {
            DragonBloom.getConfiguration();
        } catch (Exception ignored) {
            return kea;
        }
        if (getGlowTexture(kea) == null) {
            return kea;
        }
//        eos.moe.lidless.rn.n();
        BloomHelper.start();
        flag.set(true);
        return kea;
    }

    public static kea hookEeaFunc_77036_a1(kea kea, eea eea, EntityLivingBase entity, float a, float b, float c, float d, float e, float f) {
        if (flag.get()) {
//            eos.moe.lidless.rn.ALLATORIxDEMO();
            BloomHelper.end();
            flag.remove();
        }
        return kea;
    }

    public static void refresh() {
        if (flag.get()) {
            BloomEffect.refresh();
            flag.remove();
        }
    }

    public static eos.moe.dragoncore.tw hookOmALLATORIxDEMO(eos.moe.dragoncore.tw a, eos.moe.dragoncore.bd b) {
        eos.moe.dragoncore.lp.ALLATORIxDEMO(
                DragonBloom.class,
                b, a
        );
        return a;
    }
}
