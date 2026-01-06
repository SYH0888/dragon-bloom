package pers.ketikai.minecraft.forge.dragonbloom.shaders;

import java.lang.reflect.Field;

public abstract class Shaders {

    private static Field shaders$shaderPackLoaded = null;

    public static boolean isShaderPackLoaded() {
        if (shaders$shaderPackLoaded == null) {
            try {
                Class<?> cls = Shaders.class.getClassLoader().loadClass("net.optifine.shaders.Shaders");
                shaders$shaderPackLoaded = cls.getDeclaredField("shaderPackLoaded");
                shaders$shaderPackLoaded.setAccessible(true);
            } catch (ClassNotFoundException | NoSuchFieldException e) {
                return false;
            }
        }
        try {
            return (boolean) shaders$shaderPackLoaded.get(null);
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
