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
public class RoundRobin extends AllocationStrategy {


          private double avgWaitingTime;
          private double avgTurnAroundTime;

          private final int quantum;



          public RoundRobin(List<Job> jobs) {
                    this(jobs , 4);
          }



          public RoundRobin(List<Job> jobs , int quantum) {
                    super(jobs);
                    this.quantum = quantum;
          }



          public int getQuantum() {
                    return quantum;
          }



          @Override
          public void run() {
                    List<Job> jobs = this.getJobs();
                    System.out.println("============================================ ");
                    System.out.println("Process ID | start time | end time ");
                    System.out.println("============================================ ");
                    int sysTime = jobs.get(0).getArrivalTime();
                    for ( Job job : jobs ) {
                              if ( job.getArrivalTime() < sysTime ) {
                                        sysTime = job.getArrivalTime();
                              }
                    }

                    jobs.forEach((job) -> {
                              job.increaseWaitingTime(-1 * job.getArrivalTime());
                    });
                    int pastJobsProcessTime = 0;
                    while ( !allFinish() ) {
                              for ( Job job : jobs ) {
                                        if ( job.isFinished() ) {
                                        } else {
                                                  int thisjobProcessTime = 0;
                                                  if ( job.getCPUTimeLeft() > this.quantum ) {
                                                            thisjobProcessTime = this.quantum;
                                                  } else {
                                                            thisjobProcessTime = job.getCPUTimeLeft();
                                                  }
                                                  job.decreaseCPUTimeLeft(thisjobProcessTime);
                                                  sysTime += thisjobProcessTime;

                                                  job.increaseWaitingTime(pastJobsProcessTime - job.getStartTimeWaiting());
                                                  job.setStartTimeWaiting(sysTime);
                                                  pastJobsProcessTime += thisjobProcessTime;

                                                  printJob(job.getId() , sysTime - thisjobProcessTime , sysTime);
                                        }
                              }
                    }
                    int sum = 0;
                    this.avgTurnAroundTime
                            = jobs
                                    .stream()
                                    .map(
                                            (job) -> job.getTurnAroundTime())
                                    .reduce(sum , Math::addExact)
                            / (double) jobs.size();
                    sum = 0;
                    this.avgWaitingTime
                            = jobs
                                    .stream()
                                    .map(
                                            (job) -> job.getWaitingTime())
                                    .reduce(sum , Math::addExact)
                            / (double) jobs.size();

                    System.out.println("============================================ ");
                    System.out.println("Process ID | Turnaround time | Waiting time ");
                    System.out.println("============================================ ");

                    jobs.forEach((job) -> {
                              printJob(job.getId() , job.getTurnAroundTime() , job.getWaitingTime());
                    });
          }



          private boolean allFinish() {
                    return getJobs().stream().noneMatch((job) -> (!job.isFinished()));
          }



          private void printJob(String id , int i , int j) {
                    System.out.println("    " + id + "\t|\t" + i + "\t|\t" + j);
                    System.out.println("----------------------------------------");
          }

}
