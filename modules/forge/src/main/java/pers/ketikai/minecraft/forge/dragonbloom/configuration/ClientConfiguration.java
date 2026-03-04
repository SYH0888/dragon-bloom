package pers.ketikai.minecraft.forge.dragonbloom.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import pers.ketikai.minecraft.forge.dragonbloom.DragonBloom;
import pers.ketikai.minecraft.forge.dragonbloom.proxy.ClientProxy;
import pers.ketikai.minecraft.tags.dragonbloom.Tags;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class ClientConfiguration {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save(File file, ClientConfiguration clientConfiguration) throws IOException {
        Path path = file.toPath();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            String json = GSON.toJson(clientConfiguration);
            writer.write(json);
            writer.flush();
        }
    }

    public static ClientConfiguration load(File file) throws IOException {
        Path path = file.toPath();
        boolean first = false;
        try (InputStream resource = ClientConfiguration.class.getClassLoader().getResourceAsStream("config.json")) {
            if (resource != null) {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    Files.copy(resource, path, StandardCopyOption.REPLACE_EXISTING);
                    first = true;
                }
            }
        }
        ClientConfiguration clientConfiguration;
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            clientConfiguration = GSON.fromJson(reader, ClientConfiguration.class);
        }
        if (first && System.getProperty("bloom.mobile") != null) {
            clientConfiguration.setEnabled(false);
        }
        return clientConfiguration;
    }

    @NotNull
    private Boolean enabled = true;

    public ClientConfiguration() {
    }

    public ClientConfiguration(@NotNull Boolean enabled) {
        this.enabled = Objects.requireNonNull(enabled);
    }

    public @NotNull Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(@NotNull Boolean enabled) {
        this.enabled = Objects.requireNonNull(enabled);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientConfiguration that = (ClientConfiguration) o;
        return Objects.equals(getEnabled(), that.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEnabled());
    }
}
