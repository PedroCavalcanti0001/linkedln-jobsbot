package me.pedroeugenio.linkedlnjobsbot.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static void newFile(String path, String content) throws IOException {
        PrintWriter fileWriter = new PrintWriter(path, StandardCharsets.UTF_8.name());
        fileWriter.write(content);
        fileWriter.close();
    }
}