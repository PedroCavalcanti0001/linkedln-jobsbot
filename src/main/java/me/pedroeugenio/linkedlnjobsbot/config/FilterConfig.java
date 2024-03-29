package me.pedroeugenio.linkedlnjobsbot.config;

import me.pedroeugenio.linkedlnjobsbot.models.JobsFilter;

public class FilterConfig extends AbstractConfig<JobsFilter> {
    private static final JobsFilter JOBS_FILTER = new FilterConfig().load();

    @Override
    String getFilename() {
        return "filtro.yml";
    }

    @Override
    String getTemplateName() {
        return "filtro_template.yml";
    }

    public static JobsFilter getSingleton() {
        return JOBS_FILTER;
    }
}
