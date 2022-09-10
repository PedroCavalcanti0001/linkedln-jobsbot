package me.pedroeugenio.linkedlnjobsbot.tasks;

import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import me.pedroeugenio.linkedlnjobsbot.data.JobRepository;
import me.pedroeugenio.linkedlnjobsbot.data.impl.JobRepositoryMemImpl;
import me.pedroeugenio.linkedlnjobsbot.exceptions.JobAlreadySubmittedException;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.utils.TimeUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.Optional;

public class JobControlTask {
    private static final Logger LOGGER = LogManager.getLogger(JobControlTask.class.getName());
    private static final JobRepository JOB_REPOSITORY = new JobRepositoryMemImpl();

    public void verify(Job job) throws JobAlreadySubmittedException {
        Optional<Job> jobById = JOB_REPOSITORY.findJobById(job.getJobId());
        if (jobById.isPresent() && jobById.get().getJobId() != 0L) {
            LOGGER.info(String.valueOf(jobById.get()).concat(" já havia sido enviada antes."));
            if (canRemove(job))
                JOB_REPOSITORY.remove(job.getJobId());
            throw new JobAlreadySubmittedException();
        } else
            JOB_REPOSITORY.add(job);
    }

    private boolean canRemove(Job job) {
        Duration diff = TimeUtils.getTimeDifference(job.getTime());
        Integer removeTime = AppConfig.getSingleton().getRemoveJobFromDbTime();
        return diff.toMinutes() >= removeTime;
    }
}
