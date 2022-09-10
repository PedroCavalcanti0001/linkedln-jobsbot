package me.pedroeugenio.linkedlnjobsbot.data.impl;

import me.pedroeugenio.linkedlnjobsbot.data.JobRepository;
import me.pedroeugenio.linkedlnjobsbot.models.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobRepositoryMemImpl implements JobRepository {
    private static final List<Job> JOB_LIST = new ArrayList<>();

    @Override
    public Optional<Job> findJobById(Long id) {
        return JOB_LIST.stream().filter(e -> e.getJobId().equals(id)).findFirst();
    }

    @Override
    public void add(Job job) {
        JOB_LIST.add(job);
    }

    @Override
    public void remove(Long id) {
        JOB_LIST.removeIf(job -> job.getJobId().equals(id));
    }

    @Override
    public List<Job> findAll() {
        return JOB_LIST;
    }

    @Override
    public void addAll(List<Job> all) {
        JOB_LIST.addAll(all);
    }
}
