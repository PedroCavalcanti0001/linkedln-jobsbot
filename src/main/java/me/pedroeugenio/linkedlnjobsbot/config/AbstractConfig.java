package me.pedroeugenio.linkedlnjobsbot.config;

import me.pedroeugenio.linkedlnjobsbot.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
abstract class AbstractConfig<T> {
    protected static final Logger LOGGER = LogManager.getLogger(AbstractConfig.class.getName());
    private final Yaml yml;
    private final URL templateContent;

    protected AbstractConfig() {
        this.yml = new Yaml(new Constructor(getConfigurationClazz()));
        this.templateContent = AbstractConfig.class.getClassLoader().getResource(getTemplateName());
    }

    abstract String getFilename();

    abstract String getTemplateName();

    protected T load() {
        File file = new File(getFilename());
        T configuration = null;
        try {
            if (!file.exists())
                newFile(file, joinConfigFile());
            InputStream inputStream = new FileInputStream(file);
            configuration = yml.loadAs(inputStream, this.getConfigurationClazz());
        } catch (FileNotFoundException e) {
            LOGGER.error("Ocorreu um erro ao carregar o arquivo".concat(getFilename()).concat(" " +
                    "utilizando config padrão."), e);
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("Ocorreu um erro ao salvar o template ".concat(getFilename()), e);
        }
        return configuration;
    }

    private void newFile(File file, String content) throws IOException {
        FileUtils.newFile(file.getAbsolutePath(), content);
    }

    private File getFileFromTemplateURI() throws URISyntaxException {
        return new File(templateContent.toURI());
    }

    private String joinConfigFile() throws URISyntaxException, IOException {
        List<String> strings = Files.readAllLines(Objects.requireNonNull(getFileFromTemplateURI()).toPath());
        return StringUtils.join(strings, "\n");
    }

    Class<T> getConfigurationClazz(){
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
