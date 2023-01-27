package os.ass22;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Lenovo
 */
public class Main {

    public static void main(String args[]) {
        boolean work = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose the desired algorithm to run\n" +
                "1. Shortest-Job First (preemptive) (SJF)\n" +
                "2. Round Robin\n" +
                "3. Priority Scheduling \n" +
                "4. AG scheduling \n" +
                "5. Exit");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                 SJF scheduler2 = new SJF();
                scheduler2.RUN();
                break;
            case 2:
                RoundRobin scheduler4 = new RoundRobin();
                scheduler4.RUN();
                break;
            case 3:
                Priority scheduler1 = new Priority();
                scheduler1.RUN();
                break;
            case 4:
            {
                Scanner s = new Scanner(System.in);

        System.out.print("Enter The Number Of Processes: ");
        int processNum = s.nextInt();

        Queue<Process> artQueue = new PriorityQueue<Process>(Comparator.comparingInt(x -> x.arrival));
        for (int i=0; i<processNum; i++) {
            System.out.println("Information For Process #" + (i+1) + ":-");
            System.out.print("Eneter The Process Name: ");
            String name = s.next();
            System.out.print("Enter The Process Arrival Time: ");
            int art = s.nextInt();
            System.out.print("Enter The Process Burst Time: ");
            int bt = s.nextInt();
            System.out.print("Enter The Process Priority: ");
            int priority = s.nextInt();
            System.out.print("Enter The Process Quantum Time: ");
            int quantum = s.nextInt();
            artQueue.add(new Process(name,art,bt,priority,quantum));
            System.out.println("----------------------------------------------------------------------------");
        }
        Ag.run(artQueue, processNum);
            }
                break;
            case 5:
                work = false;
                break;
            default:
                System.out.println("Choose 1-4 based on desired algorithm");
        }

    }
}