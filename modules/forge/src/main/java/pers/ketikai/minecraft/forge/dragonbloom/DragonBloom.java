/*
 *     Copyright (C) 2024 ideal-state
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package pers.ketikai.minecraft.forge.dragonbloom;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import pers.ketikai.minecraft.forge.dragonbloom.configuration.ClientConfiguration;
import pers.ketikai.minecraft.forge.dragonbloom.proxy.CommonProxy;
import pers.ketikai.minecraft.protocol.dragonbloom.config.CompiledConfiguration;
import pers.ketikai.minecraft.tags.dragonbloom.Tags;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Mod(modid = Tags.ID, name = Tags.NAME, version = Tags.VERSION, dependencies = "required:dragoncore")
public class DragonBloom {

    @eos.moe.dragoncore.u(o = "刷新泛光")
    public static void refresh() {
        getLogger().info("refresh");
        DragonBloomHook.refresh();
        eos.moe.dragoncore.zo.ALLATORIxDEMO();
    }

    @eos.moe.dragoncore.u(o = "启用泛光")
    public static void enable() {
        getLogger().info("enable");
        try {
            getClientConfiguration().setEnabled(true);
        } catch (Exception e) {
            getLogger().error(e);
            return;
        }
        eos.moe.dragoncore.zo.ALLATORIxDEMO();
    }

    @eos.moe.dragoncore.u(o = "禁用泛光")
    public static void disable() {
        getLogger().info("disable");
        ClientConfiguration clientConfiguration;
        try {
            clientConfiguration = getClientConfiguration();
        } catch (Exception e) {
            getLogger().error(e);
            return;
        }
        clientConfiguration.setEnabled(false);
        File file = new File(Minecraft.getMinecraft().gameDir, Tags.ID + "/config.json");
        try {
            ClientConfiguration.save(file, clientConfiguration);
        } catch (IOException e) {
            getLogger().error(e);
        }
        eos.moe.dragoncore.zo.ALLATORIxDEMO();
    }

    @SidedProxy(
            clientSide = "pers.ketikai.minecraft.forge.dragonbloom.proxy.ClientProxy",
            serverSide = "pers.ketikai.minecraft.forge.dragonbloom.proxy.CommonProxy"
    )
    public static CommonProxy proxy;
    private static Logger logger;
    private static volatile CompiledConfiguration configuration;
    private static volatile ClientConfiguration clientConfiguration;

    @NotNull
    public static Logger getLogger() {
        return Objects.requireNonNull(logger);
    }

    @NotNull
    public static CompiledConfiguration getConfiguration() {
        return Objects.requireNonNull(configuration);
    }

    public static void setConfiguration(CompiledConfiguration configuration) {
        DragonBloom.configuration = configuration;
    }

    @NotNull
    public static ClientConfiguration getClientConfiguration() {
        return Objects.requireNonNull(clientConfiguration);
    }

    public static void setClientConfiguration(ClientConfiguration configuration) {
        DragonBloom.clientConfiguration = configuration;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        proxy.onServerStarting(event);
    }
}
