package ru.apolyakov.video_calls.video_processor.service.calls;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.resources.SpringResource;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
@NoArgsConstructor
public class ProceedCallSchedulerService implements Service {
    private static final long serialVersionUID = -8757303444436397237L;

//    @SpringResource(resourceName = "replyServiceConfig")
//    private transient ReplyServiceConfig config;
//
    @SpringResource(resourceName = "proceedCallsService")
    private transient ProceedCallsService proceedCallsService;
//
//    @SpringResource(resourceName = "errorReply")
//    private transient ErrorReply errorReply;

    private Scheduler scheduler;

    @Override
    public void cancel(ServiceContext serviceContext) {
        log.debug("Calls processor scheduler shutting down..");
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error("Reply scheduler cancel error", e);
        }
    }

    @Override
    public void init(ServiceContext serviceContext) throws Exception {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("proceedCallsService", proceedCallsService);

        JobDetail proceedActiveCallsJob = newJob(ProceedCallsJob.class)
                .withIdentity("proceed_calls_job", "proceed_calls_group")
                .setJobData(jobDataMap)
                .build();

        Trigger proceedCallsTrigger = newTrigger()
                .withIdentity("proceed_calls_job_trigger", "proceed_calls_group")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMilliseconds(1000)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(proceedActiveCallsJob, proceedCallsTrigger);
    }

    @Override
    public void execute(ServiceContext serviceContext) throws Exception {

    }
}
