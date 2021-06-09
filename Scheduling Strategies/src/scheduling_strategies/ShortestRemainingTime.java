/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package scheduling_strategies;

import java.util.ArrayList;
import java.util.List;

/**

 @author EMAM
 */
public class ShortestRemainingTime extends AllocationStrategy {

      public ShortestRemainingTime(List<Job> jobs) {
            super(jobs);
      }

//          @Override
//          public void run() {
//                    System.out.println("============================================ ");
//                    System.out.println("Process ID | start time | end time ");
//                    System.out.println("============================================ ");
//
//                    List<Job> jobs = this.getJobs();
//
////                    The current time of the system ;
////                              to sync the current process with others
//                    int sysTime = jobs.get(0).getArrivalTime();
////                    The latest time of arriving ;
////                            to not repeat the checking operation 
////                                                (check if thers is new shortest process)
//                    int lastJobArrivalTime = jobs.get(0).getArrivalTime();
//                    for ( Job job : jobs ) {
//                              if ( job.getArrivalTime() > lastJobArrivalTime ) {
//                                        lastJobArrivalTime = job.getArrivalTime();
//                              }
//                              if ( job.getArrivalTime() < sysTime ) {
//                                        sysTime = job.getArrivalTime();
//                              }
//                    }
//                    while ( true ) {
////                            A list of ready processes that has came and wait
//                              List<Integer> readyJob = new ArrayList<>();
//
//                              for ( int i = 0 ; i < jobs.size() ; i++ ) {
////                                                If there is job arriving or already here
////                                                        add it to the list of the ready process
//                                        if ( jobs.get(i).getArrivalTime() <= sysTime && !jobs.get(i).isFinished() ) {
//                                                  readyJob.add(i); // now we have the index of processes that arrived
//                                        }
//                              }
//
////                              No jobs left
//                              if ( readyJob.isEmpty() ) {
//                                        break;
//                              }
//
////                            find the shortest job in readyJob list
//                              int min = 0;
//                              for ( int i = 0 ; i < readyJob.size() ; i++ ) {
//                                        if ( jobs.get(readyJob.get(i)).getCPUTimeLeft() < jobs.get(min).getCPUTimeLeft() ) {
//                                                  min = i;
//                                        }
//                              }
//                              int startJobTime = sysTime;
//                              boolean x = false;
//                              while ( !x ) {
//                                        sysTime++;
//                                        jobs.get(min).decreaseCPUTimeLeft(1);
//                                        for ( int i = 0 ; i < jobs.size() ; i++ ) {
//                                                  if ( jobs.get(i).getArrivalTime() <= sysTime && readyJob.get(i) == null ) {
//                                                            readyJob.add(i);
//                                                            x = true;
//                                                  }
//                                        }
//
//                              }
//
//                              System.out.println("    " + jobs.get(min).getId() + "\t|\t" + startJobTime + "\t|\t" + sysTime);
//                    }
//
////                    getJobs().forEach((job) -> {
////                              System.out.println("    " + job.getId() + "\t|\t" + job.getTurnAroundTime() + "\t|\t" + job.getWaitingTime());
////                              System.out.println("----------------------------------------");
////                    });
//          }
      @Override
      public void run() {
            System.out.println("============================================ ");
            System.out.println("Process ID | start time | end time ");
            System.out.println("============================================ ");

            List<Job> jobs = this.getJobs();

//                    The current time of the system ;
//                              to sync the current process with others
            int sysTime = getSysTime();
//                    The latest time of arriving ;
//                            to not repeat the checking operation 
//                                                (check if thers is new shortest process)
            int lastJobArrivalTime = getLastArriveTime();

            int startProcess = sysTime;
            int lastProcess = 0;
            while ( !allFinish() ) {
                  List<Integer> readyJobs = getReadyJobs(sysTime);
                  int min = getMinCPUTimeLeft(readyJobs);
//                              check if this process is the last one ; to avoid the repaiting
                  if ( min != lastProcess ) {
                        printJob(this.getJobs().get(lastProcess).getId(), startProcess, sysTime);
                        startProcess = sysTime;
                  }
//                              save the time amount that the process wait before excution
                  this.getJobs().get(min).increaseWaitingTime(sysTime - this.getJobs().get(min).getStartTimeWaiting());
startProcessing:  {
                        if ( sysTime >= lastJobArrivalTime ) {
                              sysTime += this.getJobs().get(min).getCPUTimeLeft();
                              this.getJobs().get(min).decreaseCPUTimeLeft(this.getJobs().get(min).getCPUTimeLeft());
                        } else {
                              this.getJobs().get(min).decreaseCPUTimeLeft(1);
                              sysTime++;
                        }
                  }
//                              set the system time that the job stop to be processed
                  this.getJobs().get(min).setStartTimeWaiting(sysTime);
//                              save the current process for the next processing as the last process
                  lastProcess = min;
            }
//                    print the last process info
            printJob(this.getJobs().get(lastProcess).getId(), startProcess, sysTime);

//                    set average waiting and turnaround times 
            System.out.println("============================================ ");
            System.out.println("Process ID | Turnaround time | Waiting time ");
            System.out.println("============================================ ");

            for ( Job job: this.getJobs() ) {
                  this.setAvgWaitingTime(job.getWaitingTime() + this.getAvgWaitingTime());
                  this.setAvgTurnAroundTime(this.getAvgTurnAroundTime() + job.getTurnAroundTime());
                  System.out.println("  " + job.getId() + "  | " + "  " + job.getTurnAroundTime() + "  | " + " " + job.getWaitingTime() + " ");
                  System.out.println("----------------------------------------");
            }
            this.setAvgWaitingTime(this.getAvgWaitingTime() / this.getJobs().size());
            this.setAvgTurnAroundTime(this.getAvgTurnAroundTime() / this.getJobs().size());

      }

      private int getLastArriveTime() {
            int lastJobArrivalTime = this.getJobs().get(0).getArrivalTime();
            for ( Job job: this.getJobs() ) {
                  if ( job.getArrivalTime() > lastJobArrivalTime ) {
                        lastJobArrivalTime = job.getArrivalTime();
                  }
            }
            return lastJobArrivalTime;
      }

      private int getMinCPUTimeLeft(List<Integer> readyJobs) {
            int min = readyJobs.get(0);
            for ( int i = 0; i < readyJobs.size(); i++ ) {
                  if ( this.getJobs().get(readyJobs.get(i)).getCPUTimeLeft() < this.getJobs().get(min).getCPUTimeLeft() ) {
                        min = readyJobs.get(i);
                  }
            }
            return min;
      }

      private List<Integer> getReadyJobs(int sysTime) {
            List<Integer> readyJobs = new ArrayList<>();

            for ( int i = 0; i < this.getJobs().size(); i++ ) {
//                                                If there is job arriving or already here
//                                                        add it to the list of the ready process
                  if ( this.getJobs().get(i).getArrivalTime() <= sysTime && !this.getJobs().get(i).isFinished() ) {
                        readyJobs.add(i); // now we have the index of processes that arrived
                  }
            }
            return readyJobs;
      }

      private int getSysTime() {
//                    The current time of the system ;
//                              to sync the current process with others
            int sysTime = this.getJobs().get(0).getArrivalTime();
            for ( Job job: this.getJobs() ) {
                  if ( job.getArrivalTime() < sysTime ) {
                        sysTime = job.getArrivalTime();
                  }
            }
            return sysTime;
      }

      private void printJob(String id, int i, int j) {
            System.out.println("    " + id + "\t|\t" + i + "\t|\t" + j);
            System.out.println("----------------------------------------");
      }

      private boolean allFinish() {
            return getJobs().stream().noneMatch((job) -> (!job.isFinished()));
      }

}
