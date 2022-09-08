package me.pedroeugenio.linkedlnjobsbot.config;


import me.pedroeugenio.linkedlnjobsbot.models.Properties;

public class AppConfig extends AbstractConfig<Properties> {
    private static final Properties PROPERTIES = new AppConfig().load();

    protected AppConfig() {
        super(new Properties());
    }

    @Override
    String getFilename() {
        return "propriedades.yml";
    }

    @Override
    String getTemplateName() {
        return "propriedades_template.yml";
    }

    public static Properties getSingleton() {
        return PROPERTIES;
    }
}
