package me.pedroeugenio.linkedlnjobsbot.scraper;

import me.pedroeugenio.linkedlnjobsbot.config.FilterConfig;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.models.JobsFilter;

import java.util.List;
import java.util.stream.Collectors;

class JobsFilterConfig {
    protected static final JobsFilter JOBS_FILTER = FilterConfig.getSingleton();

    protected static List<Job> filterByTime(List<Job> jobs) {
        return jobs.stream().filter(e -> e.getTime().toMinutes() <= JobsConstants.PROPERTIES.getJobsInterval())
                .collect(Collectors.toList());
    }
}
