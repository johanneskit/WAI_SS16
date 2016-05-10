
package jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job
{
    // Required public empty constructor for job initialization
    public SimpleJob() {
    	
    }
   
    /* execute() method is called by the org.quartz.Scheduler when a org.quartz.Trigger
     * fires that is associated with the org.quartz.Job */
    public void execute(JobExecutionContext context) throws JobExecutionException {
       
        System.out.println("Running SimpleJob now :: " + new java.util.Date());
    }
}