/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package scheduling_strategies;

import java.util.List;

/**

 @author EMAM
 */
public class FirstComeFirstServed extends AllocationStrategy {


//          int proceessArrivalTime;
//          int waitingTime;
          public FirstComeFirstServed(List<Job> jobs) {
                    super(jobs);
          }



          @Override
          public void run() {
                    int temp = 0;
                    int count = 0;
                    System.out.println("============================================ ");
                    System.out.println("Process ID | Turnaround time | Waiting time ");
                    System.out.println("============================================ ");
                    for ( Job job : this.getJobs() ) {
                              if ( count != 0 ) {
                                        job.increaseWaitingTime(temp - job.getArrivalTime());
                              } else {
                                        count++;
                              }
                              temp = job.getCompleteTime();
                              this.setAvgWaitingTime(job.getWaitingTime() + this.getAvgWaitingTime());
                              this.setAvgTurnAroundTime(this.getAvgTurnAroundTime() + job.getTurnAroundTime());
                              System.out.println("  " + job.getId() + "  | " + "  " + job.getTurnAroundTime() + "  | " + " " + job.getWaitingTime() + " ");
                              System.out.println("----------------------------------------");
                    }
                    this.setAvgWaitingTime(this.getAvgWaitingTime() / this.getJobs().size());
                    this.setAvgTurnAroundTime(this.getAvgTurnAroundTime() / this.getJobs().size());
          }

          public static final int AVG_WAITING_TIME = 0;
          public static final int AVG_TURN_AROUND_TIME = 1;
          public static final int JOBS = 2;



          public static Object[] run(List<Job> jobs) {
                    FirstComeFirstServed fcfs = new FirstComeFirstServed(jobs);
                    fcfs.run();
                    return new Object[] {
                              fcfs.getAvgWaitingTime() ,
                              fcfs.getAvgTurnAroundTime() ,
                              fcfs.getJobs() };
          }

}
