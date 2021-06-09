/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package lap;

/**

 @author EMAM
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import scheduling_strategies.AllocationStrategy;
import scheduling_strategies.FirstComeFirstServed;
import scheduling_strategies.Job;
import scheduling_strategies.JobFinishEvent;
import scheduling_strategies.RoundRobin;
import scheduling_strategies.ShortestRemainingTime;

/**
 Application class for Assignment 1, Question 1, compsci215 2013

 @author dber021

 */
public class Question1 {

      static String filePath = "C://Users/EMAM/Desktop/testing.txt";

      private static final int quantumTime = 4;
      private static final String allocationStrategy = "SRT";
//          private static final String allocationStrategy = "RR";
//          private static final String allocationStrategy = "FCFS";

      public static void main(String[] args) {
// Process command line arguments
// read the file
            List<Job> jobList = readTheFile(filePath);

            AllocationStrategy strategy = null;
            if ( allocationStrategy.equalsIgnoreCase("FCFS") ) {
                  strategy = new FirstComeFirstServed(jobList);
            } else if ( allocationStrategy.equalsIgnoreCase("SRT") || allocationStrategy.equalsIgnoreCase("SRTF") ) {
                  strategy = new ShortestRemainingTime(jobList);
            } else if ( allocationStrategy.equalsIgnoreCase("RR") ) {
                  strategy = new RoundRobin(jobList, quantumTime);
            }
            strategy.run();
            System.out.println("===============================================");
            System.out.println("Avg waiting time:" + strategy.getAvgWaitingTime());
            System.out.println("===============================================");
            System.out.println("Avg turn around time:" + strategy.getAvgTurnAroundTime());
            System.out.println("===============================================");

            JobFinishEvent callback = new JobFinishEvent() {
                  @Override
                  public void onFinish(Job j) {
// this will be called when a job is finished.
                  }
            };
            /* // example job addition:
                     ArrayList jobs = new ArrayList();
                     jobs.add(new Job(1, 0, 2, callback));
                     jobs.add(new Job(2, 1, 3, callback));
                     FirstComeFirstServed fcfs = new FirstComeFirstServed(jobs);
                     fcfs.run();
             */
      }

      public static List<Job> readTheFile(String path) {

            BufferedReader br = null;
            List<Job> jobList = null;
            try {
                  br = new BufferedReader(new FileReader(path));
//System.out.println("processId  arrivalTime  cpuTime");
                  jobList = new ArrayList<>();
                  String sCurrentLine;
                  while ( (sCurrentLine = br.readLine()) != null ) {
                        String a[] = sCurrentLine.split(",");
                        String processId = a[0];
                        int arrivalTime = new Integer(a[1]);
                        int cpuTime = new Integer(a[2]);
                        Job job = new Job(processId, arrivalTime, cpuTime);
                        jobList.add(job);
//System.out.println(processId+"  "+ arrivalTime+" " + cpuTime);
                  }

            } catch ( IOException e ) {
            } finally {
                  try {
                        br.close();
                  } catch ( NullPointerException | IOException ex ) {
                  }
            }
            return jobList;
      }

}
