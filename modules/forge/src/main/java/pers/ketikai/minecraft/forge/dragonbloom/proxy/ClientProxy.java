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

package pers.ketikai.minecraft.forge.dragonbloom.proxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.jetbrains.annotations.NotNull;
import pers.ketikai.minecraft.forge.dragonbloom.DragonBloom;
import pers.ketikai.minecraft.forge.dragonbloom.configuration.ClientConfiguration;
import pers.ketikai.minecraft.forge.dragonbloom.listener.ConfigurationListener;
import pers.ketikai.minecraft.forge.dragonbloom.packet.ForgePacketChannel;
import pers.ketikai.minecraft.protocol.dragonbloom.codec.GsonPacketCodec;
import pers.ketikai.minecraft.protocol.dragonbloom.config.Configuration;
import pers.ketikai.minecraft.protocol.dragonbloom.util.Entry;
import pers.ketikai.minecraft.tags.dragonbloom.Tags;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ClientProxy extends CommonProxy {

    private static volatile ForgePacketChannel channel;

    @NotNull
    public static ForgePacketChannel channel() {
        return Objects.requireNonNull(channel);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(new ConfigurationListener());
        channel = new ForgePacketChannel(Tags.ID,
                Entry.of(Configuration.ID, new GsonPacketCodec<>(Configuration.class)));

        File file = new File(Minecraft.getMinecraft().gameDir, Tags.ID + "/config.json");
        try {
            DragonBloom.setClientConfiguration(ClientConfiguration.load(file));
        } catch (IOException e) {
            DragonBloom.getLogger().error(e);
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
}
