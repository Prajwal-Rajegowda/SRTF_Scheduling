/* Shortest Remaining Time First Job Scheduling Program for OS Activity */
import java.util.Scanner;

class Job 
{
    String jobName;
    int cpuCycles, arrivalTime;
    int burstTime;
    int endPoint = 0;
    int flag = 0;
    Job(String jobName, int cpuCycles, int arrivalTime)
    {
        this.jobName = jobName;
        this.cpuCycles = cpuCycles;
        this.arrivalTime = arrivalTime;
        this.burstTime = cpuCycles;
    }
    public void execution(int cpuCycleTime)
    {
        System.out.println("CPU Cycle " + cpuCycleTime + " : Process " + jobName + " in execution");
        if(cpuCycles > 1)
            cpuCycles--;
        else
            flag = 1;
    }
}

public class SRTFProgram
{
    static int top = -1;
    public static void main(String args[])
    {
        Scanner read = new Scanner(System.in);
        int numberOfJobs;
        
        System.out.println("Enter the number of Jobs to be Scheduled ");
        numberOfJobs = read.nextInt();
       
        System.out.println("Enter the Name and CPU Cycles for the jobs : ");
        Job process[] = new Job[numberOfJobs]; 
        
        int totalTimeOfExe = 0;

        for(int param = 0; param < numberOfJobs; param++)
        {
            System.out.print("Enter name : ");
            String jobName = read.next();
            
            System.out.print("Enter CPU Burst Cycles : ");
            int cpuCycles = read.nextInt();

            System.out.print("Enter Arrival Time : ");
            int arrivalTime = read.nextInt();

            process[param] = new Job(jobName,cpuCycles,arrivalTime);
            totalTimeOfExe += cpuCycles;
        }
        
        System.out.println("\nThe jobs are :");
        for(int param1 = 0; param1 <numberOfJobs; param1++)
            System.out.println("Job Name : " + process[param1].jobName + "\t CPU Burst Cycles : " + process[param1].cpuCycles + "\t Arrival Time : " + process[param1].arrivalTime);
       
        System.out.println("\nTotal Time of execution : " +totalTimeOfExe);
        
        int cpuCycleCount = 0;
        Job jobStack[] = new Job[numberOfJobs];
        int count = 0;
        
        Job processDone[] = new Job[numberOfJobs];
       
        while(cpuCycleCount < totalTimeOfExe)
        {
            for(int param = 0; param< numberOfJobs; param++)
                {
                    if(process[param].arrivalTime == cpuCycleCount)
                        {
                            push(jobStack, process[param]);
                        }
                }

            for(int param = 0; param <= top; param++)
               {
                 for(int param2 = 0; param2<= top-param-1; param2++)
                    {

                        if(jobStack[param2].cpuCycles <= jobStack[param2+1].cpuCycles)
                        {
                            Job temp_var;
                            temp_var = jobStack[param2];
                            jobStack[param2] = jobStack[param2+1];
                            jobStack[param2+1] = temp_var;
                        }
                    }
               }
            
            cpuCycleCount++; 
               
            jobStack[top].execution(cpuCycleCount);

            if(jobStack[top].flag == 1)
               {
                   jobStack[top].endPoint = cpuCycleCount;
                   processDone[count++] = pop(jobStack);
               }           
        }

        System.out.println("\nThe Pattern of Scheduling is :");
        float turnAroundTime = 0;
        float waitingTime = 0;

        for(int param1 = 0; param1 < numberOfJobs; param1++)
        {
            int turnAround = processDone[param1].endPoint - processDone[param1].arrivalTime;
            int waiting = turnAround-processDone[param1].burstTime;           
            System.out.println("Job Name : " + processDone[param1].jobName + "\t Turn Around time : " + turnAround + "\t Waiting Time : " + waiting);
            turnAroundTime += turnAround;
            waitingTime += waiting;
        }
        
        System.out.println("Avg Turn Around time : " + (turnAroundTime/5));
        System.out.println("Avg Waiting time : " + (waitingTime/5));
    }
    
    static void push(Job jobStack[],Job  item)
    {
            jobStack[++top] = item;
    } 
    
    static Job pop(Job jobStack[])
    {
        Job temp_obj;
        if(top == 0)
        {
            temp_obj = jobStack[top];
            top = -1;
        }
        else
        {    
            temp_obj = jobStack[top--];
        }
        return temp_obj;
    }
}