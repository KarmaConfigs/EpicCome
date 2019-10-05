package epic.welcome.message.handlers.Config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class PluginConfig extends YamlConfiguration {

    public PluginConfig(File f) throws IOException, InvalidConfigurationException{
        reloadConfigFile(f);
    }

    private void reloadConfigFile(File f) throws IOException, InvalidConfigurationException{
        if(!f.exists()){
            f.createNewFile();
        }
        regenConfig(f, YamlConfiguration.loadConfiguration(f));
    }

    private void regenConfig(File f, Configuration current) throws IOException, InvalidConfigurationException{

        String t = getTemplate(f);
        String result = "";
        int i1, i2;
        while((i1 = t.indexOf("[%")) >= 0 && (i2 = t.indexOf("%]")) >= 0){
            result += t.substring(0, i1);
            String value = t.substring(i1+2, i2);
            t = t.substring(i2+2);
            Object v = current.get(value);
            if(v == null)
            {
                v = getDefaultConfig(f).get(value);
            }
            result += v.toString().replaceAll("\"", "\\\\\"");
        }

        result += t;
        f.createNewFile();

        try(PrintWriter pw = new PrintWriter(f)){
            pw.append(result);
        }

        load(new CharArrayReader(result.toCharArray()));
    }

    private static YamlConfiguration defconfig = null;
    private static YamlConfiguration getDefaultConfig(File f){
        defconfig = YamlConfiguration.loadConfiguration(new InputStreamReader(PluginConfig.class.getResourceAsStream(f.getName().replaceAll(".yml", "-") + "defaults.yml")));
        return defconfig;
    }

    private static String template = null;
    @SuppressWarnings("resource")
    private static String getTemplate(File f){
        template = new Scanner(PluginConfig.class.getResourceAsStream(f.getName().replaceAll(".yml", "-") + "template.yml")).useDelimiter("\\A").next();
        return template;
    }
}