/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package os.ass22;

/**
 *
 * @author Lenovo
 */
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

public class Ag extends Process {

    public Ag(String name, int art, int bt, int priority, int quantum) {
        super(name, art, bt, priority, quantum);
    }

    public Ag() {
        super();
    }

    public static void run(Queue<Process> artQueue, int processNum) {
        int completed = 0;
        int time = 0;
        int FCFSEndTime = 0;
        int priorityEndTime = 0;
        int SJFEndTime = 0;
        boolean isPreemtive = true;
        //ready queue that take the process which will prepare excute
        Queue<Process> readyQueue = new UniqueQueue<>();
        //SJFQueue to compare brust time of each process
        Queue<Process> SJFQueue = new PriorityQueue<>(Comparator.comparingInt(x -> x.countBT));
        //priorityQueue to compare priority time of each process
        Queue<Process> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(x -> x.priority));
        //that take current process
        List<Process> CPUScheduler = new LinkedList<>();
        //list processEndTime that store the time of end of each process
        List<Integer> processEndTime = new LinkedList<>();
        //stor name and time of process
        HashSet<Process> processes = new HashSet<>();
        Process currProcess = null;
        while (completed != processNum) {
            //the loop will work till all process are not completed(dead)
            if (!artQueue.isEmpty() && time == artQueue.peek().arrival) {
                //this condation cheack if queue is not empty and current time equal arrival time
                readyQueue.add(artQueue.peek());
                SJFQueue.add(artQueue.peek());
                priorityQueue.add(artQueue.peek());
                artQueue.poll();
                //add the elem thats on face of queue then remove it from art queue "the queue that contain all process"
            }

            if (readyQueue.isEmpty()) {
                time++;
                continue;
                //if there is no elem to excute we will increas time that is in logic
            }

            if (currProcess == null) {
                //if their is any current process we will make pointer point on the peek of ready queue "move it"
                currProcess = readyQueue.peek();
                CPUScheduler.add(currProcess);
                processEndTime.add(time);
                //time clac
                FCFSEndTime = time + (int) Math.ceil(currProcess.quantum / 4.0);
                priorityEndTime = time + (int) Math.ceil(currProcess.quantum / 2.0);
                SJFEndTime = priorityEndTime + (int) Math.floor(currProcess.quantum / 2.0);
                isPreemtive = true;
            }

            if (currProcess.countBT <= 0) {
                //check shortest jop                
                //if burst time of process was end we remove that process and increase the completed counter and make pointer =null
                //and make quantem by zero then add it into queue to read his history
                readyQueue.remove(currProcess);
                SJFQueue.remove(currProcess);
                priorityQueue.remove(currProcess);
                currProcess.quantum = 0;
                currProcess.AddNewQuantum();
                currProcess = null;
                completed++;
                continue;
            }

            if (time < FCFSEndTime) {
                //if first come process take it time we decrase brust time then remove it from beek of queue then add it to the end
                currProcess.countBT--;
                SJFQueue.remove(currProcess);
                SJFQueue.add(currProcess);
                time++;
                continue;
            }

            if (isPreemtive && currProcess != priorityQueue.peek()) {
                //make quantum 25%
                currProcess.quantum += (int) Math.ceil((currProcess.quantum - (int) Math.ceil(currProcess.quantum / 4.0)) / 2.0);
                currProcess.AddNewQuantum();
                currProcess = priorityQueue.peek();
                readyQueue.add(currProcess);
                CPUScheduler.add(currProcess);
                processEndTime.add(time);
                //calc time
                FCFSEndTime = time + (int) Math.ceil(currProcess.quantum / 4.0);
                priorityEndTime = time + (int) Math.ceil(currProcess.quantum / 2.0);
                SJFEndTime = priorityEndTime + (int) Math.floor(currProcess.quantum / 2.0);
                isPreemtive = false;
                continue;
            }

            if (time < priorityEndTime) {
                currProcess.countBT--;
                SJFQueue.remove(currProcess);
                SJFQueue.add(currProcess);
                time++;
                continue;
            }

            if (currProcess != SJFQueue.peek()) {
                //if there is no process are ready to exucte we continue on process and make quantem 50
                currProcess.quantum += (currProcess.quantum - (int) Math.ceil(currProcess.quantum / 2.0));
                currProcess.AddNewQuantum();
                currProcess = SJFQueue.peek();
                readyQueue.add(currProcess);
                CPUScheduler.add(currProcess);
                processEndTime.add(time);
                FCFSEndTime = time + (int) Math.ceil(currProcess.quantum / 4.0);
                priorityEndTime = time + (int) Math.ceil(currProcess.quantum / 2.0);
                SJFEndTime = priorityEndTime + (int) Math.floor(currProcess.quantum / 2.0);
                isPreemtive = true;
                continue;
            }

            if (time < SJFEndTime) {
                currProcess.countBT--;
                SJFQueue.remove(currProcess);
                SJFQueue.add(currProcess);
                time++;
                continue;
            }

            currProcess.quantum *= 2;
            currProcess.AddNewQuantum();
            isPreemtive = true;
            readyQueue.add(currProcess);
        }
        processEndTime.add(time);
          //print
        System.out.print("----------------------------------------------------------------------------");
        System.out.println("-------------");
        for (int i = 0; i < CPUScheduler.size(); i++) {
            Process p = CPUScheduler.get(i);
            System.out.print("|   " + p.name + "\t");
            processes.add(p);
            p.completion = processEndTime.get(i + 1);
        }

        System.out.println("|");

        for (Process p : CPUScheduler) {
            System.out.print("|-------");
        }

        System.out.print("|");
        System.out.println();

        for (int endTime : processEndTime) {
            System.out.print(endTime + "\t");
        }
        System.out.println();
        System.out.println();
        int TotalWaiting = 0;
        int TotalTurnaround = 0;
        System.out.println("Process Name" + "\t\t" + "Waiting Time" + "\t\t" + "Turnaround Time" + "\t\t" + "History Quantum");
        for (Process p : processes) {
            p.calcTime();
            TotalWaiting += p.waited;
            TotalTurnaround += p.turnaround;
            System.out.print(p.name + "\t\t\t" + p.waited + "\t\t\t" + p.turnaround + "\t\t\t");
            for (int i = 0; i < p.readQueue.size(); i++) {
                System.out.print(p.readQueue.get(i));
                if (i < p.readQueue.size() - 1) {
                    System.out.print("->");
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Average Waiting Time = " + TotalWaiting / (double) processNum);
        System.out.println("Average Turnaround Time = " + TotalTurnaround / (double) processNum);
    }
}
