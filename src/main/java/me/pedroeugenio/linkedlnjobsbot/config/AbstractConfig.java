package me.pedroeugenio.linkedlnjobsbot.config;

import me.pedroeugenio.linkedlnjobsbot.utils.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
abstract class AbstractConfig<T> {
    protected static final Logger LOGGER = LogManager.getLogger(AbstractConfig.class.getName());
    private final Yaml yml;

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

    private String joinConfigFile() throws URISyntaxException, IOException {
        InputStream resourceAsStream = AbstractConfig.class.getClassLoader().getResourceAsStream(getTemplateName());
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(resourceAsStream),
                StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining("\n"));
    }

    private Class<T> getConfigurationClazz(){
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
