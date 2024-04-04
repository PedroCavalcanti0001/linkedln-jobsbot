package me.pedroeugenio.linkedlnjobsbot.config;


import me.pedroeugenio.linkedlnjobsbot.models.Properties;

public class AppConfig extends AbstractConfigYml<Properties> {
    private static final Properties PROPERTIES = new AppConfig().load();

    @Override
    public String getFilename() {
        return "propriedades.yml";
    }

    @Override
    public String getTemplateName() {
        return "propriedades_template.yml";
    }

    public static Properties getSingleton() {
        return PROPERTIES;
    }
}
