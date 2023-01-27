/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package os.ass22;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class Process {
    public String name;
    public int arrival;
    public int burst;
    public int completion;
    public int turnaround;
    public int waited;
    public int priority;
    public int starve;
    public int quantum;
    int countBT = 0;
  ArrayList<Integer> readQueue = new ArrayList<>();
    Process() {
        name = "";
        waited = 0;
        turnaround = 0;
        arrival = -1;
        burst = -1;
        quantum =0;
        starve = 0;
    }

    public void setTurnaround() {
        turnaround = completion - arrival;
    }


    public Process(String name, int arrival_time, int burst_time, int priority) {
        this.name = name;
        this.burst = burst_time;
        this.arrival = arrival_time;
        this.priority = priority;
    }
       public Process(String name, int art, int bt, int priority, int quantum) {
        this.name = name;
        this.arrival = art;
        this.burst = bt;
        this.countBT = bt;
        this.priority = priority;
        this.quantum = quantum;
        this.AddNewQuantum();
    }

 public void calcTime() {
        waited = completion - arrival - burst;
        turnaround = waited + burst;
    }

    public void AddNewQuantum() {
        readQueue.add(quantum);
    }
    public int getPriority() {
        return priority;
    }

    public int getArrival() {
        return arrival;
    }

}