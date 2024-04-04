package me.pedroeugenio.linkedlnjobsbot.config;

import me.pedroeugenio.linkedlnjobsbot.utils.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilterConfig implements IConfig {

    @Override
    public String getFilename() {
        return "filtro.txt";
    }

    @Override
    public String getTemplateName() {
        return "filtro_template.txt";
    }

    private String load() {
        try {
            File file = new File(getFilename());
            if (!file.exists())
                newFile(file, joinConfigFile());
            try (FileInputStream inputStream = new FileInputStream(getFilename())) {
                return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            }
        }catch (Exception e){
            System.out.println("dsads");
        }
        return "";
    }
    public static String getFilter() {
        return new FilterConfig().load();
    }

    private void newFile(File file, String content) throws IOException {
        FileUtils.newFile(file.getAbsolutePath(), content);
    }

    private String joinConfigFile() throws URISyntaxException, IOException {
        InputStream resourceAsStream = AbstractConfigYml.class.getClassLoader().getResourceAsStream(getTemplateName());
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(resourceAsStream),
                StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining("\n"));
    }
}
