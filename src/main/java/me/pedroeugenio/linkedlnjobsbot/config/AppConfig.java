package me.pedroeugenio.linkedlnjobsbot.config;

import me.pedroeugenio.linkedlnjobsbot.models.Properties;
import me.pedroeugenio.linkedlnjobsbot.utils.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;


public class AppConfig {
    protected static final Logger LOGGER = LogManager.getLogger(AppConfig.class.getName());
    private final static Yaml YAML = new Yaml(new Constructor(Properties.class));

    public static Properties load() {
        File file = new File("propriedades.yml");
        Properties properties = new Properties();
        if (!file.exists())
            createNewPropertiesFile(file, properties);
        try {
            InputStream inputStream = new FileInputStream(file);
            properties = YAML.loadAs(inputStream, Properties.class);
        } catch (FileNotFoundException e) {
            LOGGER.error("Ocorreu um erro ao carregar o arquivo propriedades.yml, utilizando config padrão.", e);
        }
        return properties;
    }

    private static void createNewPropertiesFile(File file, Properties properties) {
        try {
            FileUtils.newFile(file.getAbsolutePath(), YAML.dumpAsMap(properties));
        } catch (IOException e) {
            LOGGER.error("Ocorreu um erro ao tentar carregar o arquivo propriedades.yml", e);
        }
    }


}
