package org.power.minigame.worldgen.config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import com.moandjiezana.toml.*;
import org.power.minigame.worldgen.Minigame_WorldGen;

public class Config {
    private static final TomlWriter tomlWriter = new TomlWriter();

    private final File file;
    private final Map<String, Object> config = new HashMap<>();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Config(String name, Map<String, Object> example) {
        File path = new File("config", "minigame");
        path.mkdirs();
        file = new File(path, name);
        if(!file.exists()) {
            try (FileWriter fileWriter = new FileWriter(file)){
                file.createNewFile();
                fileWriter.write(tomlWriter.write(example));
            } catch (IOException e) {
                Minigame_WorldGen.logger.error("Failed to create config files");
                throw new RuntimeException(e);
            }
        }
        reload();
    }
    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(config.get(key));
    }

    public <T> T get(String prefix, String key, Class<T> clazz) {
        return clazz.cast(config.get(prefix+"."+key));
    }

    public void reload() {
        try (FileReader fileReader = new FileReader(file)) {
            config.putAll(new Toml().read(fileReader).toMap());
        } catch (FileNotFoundException e) {
            Minigame_WorldGen.logger.error("Not found the config files, please contact developer");
            throw new RuntimeException(e);
        } catch (IOException e) {
            Minigame_WorldGen.logger.error("Cannot read config files, please contact developer");
            throw new RuntimeException(e);
        }
    }
}
