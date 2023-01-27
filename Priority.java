/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package os.ass22;

/**
 *
 * @author Lenovo
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Priority {
    private ArrayList<Process> processes, CopyProcesses;
    private ArrayList<Process> Order;

    Priority() {
        processes = new ArrayList<>();
        Inputs();
        CopyProcesses = new ArrayList<>();
        for (Process pr : processes) {
            CopyProcesses.add(pr);
        }
        Order = new ArrayList<>();
        Collections.sort(CopyProcesses, Comparator.comparing(Process::getPriority));
    }

    void RUN() {
        int currt = 0;
        int curridx = -1;
        int n = CopyProcesses.size();
        while (n > 0) {
            curridx = -1;
            Collections.sort(CopyProcesses, Comparator.comparing(Process::getPriority));
            for (int i = 0; i < CopyProcesses.size(); i++) {
                if (CopyProcesses.get(i).arrival <= currt) {
                    curridx = i;
                    n--;
                    break;
                }
            }

            if (curridx == -1) {
                currt++;
                continue;
            }

            Process currp = CopyProcesses.get(curridx);

            CopyProcesses.remove(curridx);
            int waittime = currt - currp.arrival;
            currp.waited = waittime;
            int turnaround = waittime + currp.burst;

            currt += currp.burst;
            currp.turnaround = turnaround;
            Order.add(currp);
        }
        print();
    }


    void print() {
        System.out.println("=================Priority Scheduling====================");

        System.out.println("----------------------------------");
        for (Process p : Order) {
            System.out.print(p.name + " |");
        }
        System.out.println("\n----------------------------------");

        System.out.println("Process     Waiting Time   TurnaroundTime ");
        for (Process p : processes) {
            int currWT = p.waited, currTT = p.turnaround;
            System.out.println(p.name + "\t\t" + currWT + "\t\t" + currTT);
        }
        System.out.print("Average Waiting Time: ");
        System.out.println(" =" + AverageWaitingTime());
        System.out.print("Average Turnaround Time: ");
        System.out.println(" =" + AverageTurnaround());

    }

    float AverageTurnaround() {
        float sum = 0;
        for (Process p : Order) {
            sum += p.turnaround;
        }
        return (float) sum / processes.size();
    }

    float AverageWaitingTime() {
        float sum = 0;
        for (Process p : Order) {
            sum += p.waited;
        }
        return (float) sum / processes.size();
    }

    void Inputs() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of Processes: ");
        int NumOfP = scan.nextInt();
        System.out.println("Enter processes names: ");

        for (int x = 0; x < NumOfP; x++) {
            Process proc = new Process();
            proc.name = scan.next();
            processes.add(proc);
        }
        System.out.println("Enter processes Arrival times: ");
        for (int x = 0; x < NumOfP; x++) {
            processes.get(x).arrival = scan.nextInt();
        }
        System.out.println("Enter processes Burst times: ");
        for (int x = 0; x < NumOfP; x++) {
            processes.get(x).burst = scan.nextInt();
        }
        System.out.println("Enter processes Priority : ");
        for (int x = 0; x < NumOfP; x++) {
            processes.get(x).priority = scan.nextInt();
        }

    }


}