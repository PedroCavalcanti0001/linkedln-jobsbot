package me.pedroeugenio.linkedlnjobsbot.scraper;

import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class JobsFilter {

    protected static String loadFilterFile() throws IOException {
        File file = new File("filtro.txt");
        if (!file.exists())
            createNewFilterFile(file.getAbsolutePath());
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }

    private static void createNewFilterFile(String path) throws IOException {
        FileUtils.newFile(path, "Java developer");
    }

    protected static List<Job> filterByTime(List<Job> jobs) {
        return jobs.stream().filter(e -> e.getTime().toMinutes() <= JobsConstants.TIME_TO_FILTER_JOBS)
                .collect(Collectors.toList());
    }
}
