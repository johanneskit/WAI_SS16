package app;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerMetaData;

import org.quartz.SchedulerException;

public class AppCore
{
    public static void main(String[] args) throws SchedulerException
    {
        System.out.println("Initializing..");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        System.out.println("Initialization Complete..");

        System.out.println("Not Scheduling any Jobs - relying on XML definitions..");

        // start the schedule
        sched.start();

        System.out.println("Scheduler Started..");

        // wait 3 minutes to give our jobs a chance to run
        try {
            Thread.sleep(3L * 60L * 1000L);
        } catch (Exception e) {
        }
       
        System.out.println("Shutting Down..");
        // shut down the scheduler
        sched.shutdown(true);

        SchedulerMetaData metaData = sched.getMetaData();
        System.out.println("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }
}