/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package scheduling_strategies;

/**

 @author EMAM
 */
public class Job {


          private final String id;
          private final int arrivalTime;
          private final int CPUTime;
          private int priority;

          private int CPUTimeLeft;
          private int completeTime;
          private int waitingTime;
          private int turnAroundTime;
          private boolean finished;

          public int startTimeWaiting;
//          private int submitTime;
//          private int startTime = 0;
//          private int endTime = 0;
//          public int processArrivalTime;
//          private int cpuTime;
//          private int processId;
          private JobFinishEvent evt;



          public Job(String id , int arrivalTime , int CPUTime , JobFinishEvent evt) {
                    this(id , arrivalTime , CPUTime , 4 , evt);
          }



          public Job(String id , int arrivalTime , int CPUTime , int priority , JobFinishEvent evt) {
                    this(id , arrivalTime , CPUTime , priority);
                    this.evt = evt;
          }



          public Job(String id , int arrivalTime , int CPUTime) {
                    this(id , arrivalTime , CPUTime , 4);
          }



          public Job(String id , int arrivalTime , int CPUTime , int priority) {
                    this.id = id;
                    this.arrivalTime = arrivalTime;
                    this.CPUTime = CPUTime;
                    this.priority = priority;
                    //                    default values
                    this.CPUTimeLeft = this.CPUTime;
                    this.waitingTime = 0;
                    this.turnAroundTime = this.CPUTime;
                    this.completeTime = this.arrivalTime + this.CPUTime;
                    this.finished = false;
                    startTimeWaiting = this.arrivalTime;
          }



//          public int getStartProcessingTime() {
//                    
//          }
//          public void tick(int sysTime) {
//                    CPUTimeLeft--;
//                    if ( CPUTimeLeft <= 0 ) {
//                              endTime = sysTime;
//                              evt.onFinish(this);
//                    }
//          }
          public int getCPUTime() {
                    return CPUTime;
          }



          public int getCPUTimeLeft() {
                    return CPUTimeLeft;
          }



//          public int getStartTime() {
//                    return startTime;
//          }
//          public void setStartTime(int startTime) {
//                    this.startTime = startTime;
//          }
//          public int getEndTime() {
//                    return endTime;
//          }
//
//
//
//          public void setEndTime(int endTime) {
//                    this.endTime = endTime;
//          }
          public int getArrivalTime() {
                    return arrivalTime;
          }



          public int getCpuTime() {
                    return CPUTime;
          }



          public String getId() {
                    return this.id;
          }



          public int getCompleteTime() {
                    return this.completeTime;
          }



          public int getPriority() {
                    return priority;
          }



          public int increasePiority() {
                    this.priority++;
                    return this.priority;
          }



          public int getStartTimeWaiting() {
                    return startTimeWaiting;
          }



          public void setStartTimeWaiting(int startTimeWaiting) {
                    this.startTimeWaiting = startTimeWaiting;
          }



//          public void setCompleteTime(int completeTime) {
//                    this.completeTime = completeTime;
//          }
          public int getTurnAroundTime() {
                    return turnAroundTime;
          }



          public int getWaitingTime() {
                    return waitingTime;
          }



          public int decreaseCPUTimeLeft(int i) {
                    if ( i > this.CPUTimeLeft ) {
                              this.CPUTimeLeft = 0;
                    } else {
                              this.CPUTimeLeft -= i;
                    }
                    if ( this.CPUTimeLeft == 0 ) {
                              finished = true;
                    }
                    return this.CPUTimeLeft;
          }



          public int increaseWaitingTime(int i) {
                    this.waitingTime += i;
                    this.turnAroundTime += i;
                    this.completeTime += i;
                    return this.waitingTime;
          }



          public boolean isFinished() {
                    return finished;
          }

}
