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
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

public class RoundRobin {

    static CPU cpu = new CPU();
    private static int NumOfP = 0;
    private static int quantum = 0;
    static Process[] Processes;
    static Queue<Process> completed = new LinkedList<>();
    static Queue<Process> ReadyQue = new LinkedList<>();
    static int Context_Switch;
    static Queue<Process> order = new LinkedList<>();

    void RUN() {
        Inputs();
        OrderFirst();
        int Time = 0;
        int Co_Sw;
        while (completed.size() != NumOfP) {
            Receive(Time);

            //context switching
            if (cpu.Timer == 0 && cpu.CurrentProcess.burst > 0 && ReadyQue.size() != 0) {
                Co_Sw = Context_Switch;
                //System.out.println(Time + " Context Switching ");
                while (Co_Sw != 0) {
                    Co_Sw--;
                    IncreasingWaitedTime();
                    Time++;
                    Receive(Time);
                }
            }

            //completion of a process
            if (cpu.CurrentProcess.burst == 0) {
                order.add(cpu.CurrentProcess);
                CompletedQue(cpu.CurrentProcess, Time);
                cpu.Timer = 0;
            }
            if (cpu.Timer == 0) {
               // System.out.print(" " + Time);
                if (cpu.CurrentProcess.burst > 0) {
                    ReadyQue(cpu.CurrentProcess);
                }
                if (ReadyQue.size() != 0) {
                    //System.out.print(" " + ReadyQue.peek().name + " "); //returns head without remove
                    cpu.Timer = quantum;
                    cpu.CurrentP(ReadyQue.poll()); //returns head and remove
                } else {
                    cpu.CurrentProcess = new Process();
                    Time++;
                    continue;
                }
            }
            cpu.CurrentProcess.burst--;
            cpu.Timer--;
             
            IncreasingWaitedTime();
            Time++;
        }
        //System.out.println("");
        Statistic();
    }
    
    static void Statistic() {
          System.out.println("=================Round Robin Scheduling====================");

        System.out.println("----------------------------------");
        for (Process p : order) {
            System.out.print(p.name + " |");
        }
        System.out.println("\n----------------------------------");
        System.out.println("Process     Waiting Time   TurnaroundTime ");
        for (Process p : Processes) {
            int currWT = p.waited, currTT = p.turnaround;
            System.out.println(p.name + "\t\t" + currWT + "\t\t" + currTT);
        }
        System.out.println("Average Waiting Time: ");
        System.out.println(" =" + AverageWaitingT());
        System.out.println("Average Turnaround Time: ");
        System.out.println(" =" + AverageTurnaround());
    }

    static float AverageTurnaround() {
        float sum = 0;
        for(Process p : completed)
        {
            sum += p.turnaround;
        }
        return (float) sum / NumOfP;
    }

    static void Receive(int arrival_T) {
        for (int i = 0; i < NumOfP; i++) {
            if (Processes[i].arrival == arrival_T) {
                ReadyQue(Processes[i]);
            }
        }
    }

    static float AverageWaitingT() {
        float sum = 0;
        for (Process p : completed) {
            sum += p.waited;
        }
        return (float) sum / NumOfP;
    }

    static void CompletedQue(Process p, int T) {
        completed.offer(p);
        p.completion = T;
        p.setTurnaround();
    }

    static void IncreasingWaitedTime() {
        int n = ReadyQue.size();
        for (Process p : ReadyQue) {
            p.waited++;
        }
    }

    static void ReadyQue(Process p) {
        ReadyQue.offer(p);
    }

    private static void OrderFirst() {
        for (int i = 0; i < NumOfP - 1; i++) {
            for (int j = 0; j < NumOfP - 1; j++) {
                if (Processes[j].arrival > Processes[j + 1].arrival)
                    Swap(j, j + 1);
            }
        }
    }

    static void Swap(int i, int j) {
        Process temp;
        temp = Processes[i];
        Processes[i] = Processes[j];
        Processes[j] = temp;
    }


    static void Initialization() {
        Processes = new Process[NumOfP];
        for (int i = 0; i < NumOfP; i++) {
            Processes[i] = new Process();
        }
    }

    static void Inputs() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of Processes: ");
        NumOfP = scan.nextInt();
        System.out.println("Quantum Time: ");
        quantum = scan.nextInt();
        System.out.println("Context Switching: ");
        Context_Switch = scan.nextInt();
        Initialization();
        scan.nextLine();
        System.out.println("Enter processes names: ");
        for (int i = 0; i < NumOfP; i++) {
            Processes[i].name = scan.next();
        }
        System.out.println("Enter processes Arrival times: ");
        for (int i = 0; i < NumOfP; i++) {
            Processes[i].arrival = scan.nextInt();
        }
        System.out.println("Enter processes Burst times: ");
        for (int i = 0; i < NumOfP; i++) {
            Processes[i].burst = scan.nextInt();
        }
    }
}