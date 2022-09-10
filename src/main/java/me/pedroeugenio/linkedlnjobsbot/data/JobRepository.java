package me.pedroeugenio.linkedlnjobsbot.data;

import me.pedroeugenio.linkedlnjobsbot.models.Job;

import java.util.List;
import java.util.Optional;

public interface JobRepository {
    Optional<Job> findJobById(Long id);
    void add(Job job);
    void remove(Long id);
    List<Job> findAll();
    void addAll(List<Job> all);
}