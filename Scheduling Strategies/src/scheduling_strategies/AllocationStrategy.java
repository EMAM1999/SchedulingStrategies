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
/* implement this class for all three strategies */
public abstract class AllocationStrategy {


          private final List<Job> Jobs;
          private double avgWaitingTime;
          private double avgTurnAroundTime;



          public AllocationStrategy(List<Job> jobs) {
                    Jobs = jobs;
          }



          public double getAvgTurnAroundTime() {
                    return avgTurnAroundTime;
          }



          public void setAvgTurnAroundTime(double avgTurnAroundTime) {
                    this.avgTurnAroundTime = avgTurnAroundTime;
          }



          public double getAvgWaitingTime() {
                    return avgWaitingTime;
          }



          public void setAvgWaitingTime(double avgWaitingTime) {
                    this.avgWaitingTime = avgWaitingTime;
          }



          public List<Job> getJobs() {
                    return Jobs;
          }



          public abstract void run();
// update current job by 1 tick
// check if the job queue might need to be changed.
// check for jobs to add to the queue

}
