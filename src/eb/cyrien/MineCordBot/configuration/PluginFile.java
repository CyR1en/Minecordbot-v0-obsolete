package eb.cyrien.MineCordBot.configuration;

import eb.cyrien.MineCordBot.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PluginFile {

    private YamlConfiguration file;
    private File f;

    public PluginFile(Main p, String name, boolean copy) {
        super();
        f = new File(String.valueOf(p.getDataFolder().toString()) + "/" + name + ".yml");
        if (!f.exists()) {
            if (copy) {
                p.saveResource(String.valueOf(name) + ".yml", false);
            } else {
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                }
            }
        }
        file = YamlConfiguration.loadConfiguration(this.f);
    }

    public void renew(Main p, String name, boolean copy ) {
        f = new File(String.valueOf(p.getDataFolder().toString()) + "/" + name + ".yml");
        if (f.exists()) {
            if (copy) {
                f.delete();
                p.saveResource(String.valueOf(name) + ".yml", false);
            } else {
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                }
            }
        }
        file = YamlConfiguration.loadConfiguration(this.f);
    }

    public ConfigurationSection get(final String path) {
        return file.getConfigurationSection(path);
    }

    public YamlConfiguration getConfig() {
        return file;
    }

    public void save() throws IOException {
        file.save(f);
        file = YamlConfiguration.loadConfiguration(f);
    }

    public void reload() {
        file = YamlConfiguration.loadConfiguration(f);
    }
}
