package me.pedroeugenio.linkedlnjobsbot.utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static void newFile(String path, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(content);
        fileWriter.close();
    }
}