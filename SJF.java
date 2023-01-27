package os.ass22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class SJF {

    private ArrayList<Process> processes;
    private ArrayList<Integer> BurstTimes = new ArrayList<>();
    private ArrayList<Integer> Order = new ArrayList<>();
    private int ContextSwitch;

    public SJF() {

        processes=new ArrayList<>();
        Inputs();
        Collections.sort(processes, Comparator.comparing(Process::getArrival));
        for (int i = 0; i < processes.size(); i++) BurstTimes.add(processes.get(i).burst);

    }


    public void RUN() {
        int shortestRemaining = Integer.MAX_VALUE;
        int time = 0;
        int n = processes.size();
        boolean exist = false;

        int currP = 0;
        int prevP = -5;

        while (n > 0) {
            for (int i = 0; i < processes.size(); i++) {
                if (BurstTimes.get(i) > 0 && BurstTimes.get(i) < shortestRemaining && processes.get(i).getArrival() <= time) {
                    currP = i;
                    shortestRemaining = BurstTimes.get(i);
                    exist = true;
                }

            }
            if (!exist)
                time++;
            else {

                if (prevP != -5 && prevP != currP) {
                    time += ContextSwitch;
                    Order.add(prevP);
                }

                int dec = BurstTimes.get(currP) - 1;
                BurstTimes.set(currP, dec);

                shortestRemaining = dec;

                if (shortestRemaining == 0)
                    shortestRemaining = Integer.MAX_VALUE;

                prevP = currP;

                if (dec == 0) {
                    n--;
                    exist = false;

                    int UpdateWT = time + 1 - processes.get(currP).burst - processes.get(currP).arrival;

                    processes.get(currP).waited=(Math.max(0, UpdateWT));

                    processes.get(currP).turnaround=(processes.get(currP).waited + processes.get(currP).burst);
                }
                time++;
            }

        }

        Order.add(prevP);
        print();
    }

    float AverageTurnaround() {
        float sum = 0;
        for (Process p:processes) {
            sum += p.turnaround;
        }
        return (float) sum / processes.size();
    }

    float AverageWaitingTime() {
        float sum = 0;
        for (Process p :processes ) {
            sum += p.waited;
        }
        return (float) sum / processes.size();
    }
    void print() {
        System.out.println("=================SJF Scheduling====================");

        System.out.println("----------------------------------");
        for (int i = 0; i < Order.size(); i++) {
            System.out.print(processes.get(Order.get(i)).name);
            if (i == Order.size() - 1)
                System.out.println();
            else
                System.out.print(" | ");
        }
        System.out.println("\n----------------------------------");



        System.out.println("Process     Waiting Time   TurnaroundTime ");
        for (Process p :processes ) {
            int wt = p.waited, Tar = p.turnaround;
            System.out.println(p.name + "\t\t\t\t" + wt + "\t\t\t\t" + Tar);
        }

        System.out.print("Average Waiting Time: ");
        System.out.println(" =" + AverageWaitingTime());
        System.out.print("Average Turnaround Time: ");
        System.out.println(" =" + AverageTurnaround());

    }
    void Inputs() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of Processes: ");
        int NumOfP = scan.nextInt();


        System.out.println("Enter processes names: ");
        for (int x = 0; x < NumOfP; x++) {
            Process proc=new Process();
            proc.name= scan.next();
            processes.add(proc);
        }

        System.out.println("Enter processes Arrival times: ");
        for (int x = 0; x < NumOfP; x++) {
            processes.get(x).arrival = scan.nextInt();
        }

        System.out.println("Enter processes  Burst times: ");
        for (int x = 0; x < NumOfP; x++) {
            processes.get(x).burst = scan.nextInt();
        }

        System.out.println("Enter Context Switch: ");
        ContextSwitch=scan.nextInt();

    }
}