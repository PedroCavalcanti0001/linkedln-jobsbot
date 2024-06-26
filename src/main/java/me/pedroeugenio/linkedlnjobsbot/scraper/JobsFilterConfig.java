package me.pedroeugenio.linkedlnjobsbot.scraper;

import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import me.pedroeugenio.linkedlnjobsbot.config.FilterConfig;
import me.pedroeugenio.linkedlnjobsbot.models.Job;

import java.util.List;
import java.util.stream.Collectors;

class JobsFilterConfig {
    protected static final String CURRENT_FILTER = FilterConfig.getFilter();

    protected static List<Job> performFilters(List<Job> jobs) {
        List<Job> filterByTime = filterByTime(jobs);
        return promotedJobsFilter(filterByTime);
    }

    private static List<Job> filterByTime(List<Job> jobs) {
        return jobs.stream().filter(e -> e.getDiffInDuration().toMinutes() <= JobsConstants.PROPERTIES.getJobsInterval())
                .collect(Collectors.toList());
    }

    private static List<Job> promotedJobsFilter(List<Job> jobs) {
        Boolean allowPromoted = AppConfig.getSingleton().getAllowPromoted();
        if (!allowPromoted)
            return jobs.stream().filter(e -> !e.getStrTime().isEmpty()).collect(Collectors.toList());

        return jobs;
    }
}
