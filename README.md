# OS-Scheduling-and-Threading-Simulator
This project contains two Java programs:

1. **Multithreaded Sorting Application**
   - Splits an integer array into two halves
   - Uses two sorting threads to sort each half
   - Uses a third merging thread to merge the two sorted halves into one final sorted array

2. **CPU Scheduling Simulator**
   - Simulates the following CPU scheduling algorithms:
     - First Come First Serve (FCFS)
     - Shortest Job First (SJF)
     - Preemptive Priority Scheduling
     - Round Robin (RR)
   - Reads process data from an input file
   - Prints execution progress for each millisecond
   - Calculates:
     - Average waiting time
     - Average response time
     - Average turnaround time
     - CPU utilization rate

## Project Structure

- `Multithread Sorting/ThreadSort.java`
- `CPU Scheduling/CPUSimulator.java`
- `CPU Scheduling/input.txt`

## Requirements

- Java JDK 21 or later
- Command Prompt or PowerShell

## How to Run

Download or extract the project folder first.

### 1. Multithread Sorting

Open a terminal in the `Multithread Sorting` folder and run:

java -jar ThreadSort.jar

### 2. CPU Scheduler Simulator

Open a terminal in the `CPU Scheduling` folder and run:

java -jar CPUSimulator.jar

When running CPUSimulator.jar, the program will prompt for:

- the path to input.txt
- the scheduling algorithm
- the time quantum if Round Robin is selected