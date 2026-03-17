import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.Comparator;

public class CPUSimulator {
	private static Scanner scanner = new Scanner(System.in);
	static ArrayList<Process> processes = new ArrayList<>();
	static int currentTime = 0;
	static int completedProcesses = 0;
	static HashMap<Integer, Integer> waitingTimes = new HashMap<>();
	static double avgWaitingTime;
	static HashMap<Integer, Integer> responseTime = new HashMap<>();
	static double avgResponseTime;
	static int turnaroundTime;
	static double avgTurnaroundTime;
	static double CPUUtilization;
	static Queue<Process> readyQueue = new LinkedList<>();

	public static void FCFS(ArrayList<Process> processes) {

		// Sort processes by arrival time
		Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime).thenComparingInt(Process::getProcessId));

		// Implement First-Come, First-Served scheduling algorithm here
		while(completedProcesses < processes.size()) {

			// Loop through processes to find the next process to execute
			for(Process process : processes) {
				if(process.getArrivalTime() <= currentTime && process.getRemainingBurstTime() > 0) {
					while (process.getRemainingBurstTime() > 0) {
						System.out.println("Time " + currentTime + "ms: Process " + process.getProcessId() + " is executing.");
						currentTime++;
						CPUUtilization++;
						process.setRemainingBurstTime(process.getRemainingBurstTime() - 1);
					}
					waitingTimes.put(process.getProcessId(), currentTime - process.getArrivalTime() - process.getBurstTime());
					completedProcesses++;
				} else if (process.getArrivalTime() > currentTime) {
					System.out.println("Time " + currentTime + "ms: CPU is idle.");
					currentTime++;
				}
			}
		}
		
		// Print waiting times for each process
		System.out.println("\nProcess Waiting Times:");
		for (Process process : processes) {
			System.out.println("Process " + process.getProcessId() + ": " + waitingTimes.get(process.getProcessId()) + "ms");
			avgWaitingTime += waitingTimes.get(process.getProcessId());  // Calculate total waiting time for average calculation
		}

		avgWaitingTime /= (double) processes.size();  // Calculate average waiting time
		System.out.println("Average Waiting Time: " + avgWaitingTime + "ms\n");

		// Print Response time & average response time for each process (which is the same as waiting time for FCFS)
		System.out.println("Response Times:");
		for (Process process : processes) {
			System.out.println("Process " + process.getProcessId() + ": " + waitingTimes.get(process.getProcessId()) + "ms");
		}

		System.out.println("Average Response Time: " + avgWaitingTime + "ms\n");

		// Print Turnaround time & average turnaround time for each process
		System.out.println("Turnaround Times:");
		for (Process process : processes) {
			turnaroundTime = waitingTimes.get(process.getProcessId()) + process.getBurstTime();
			System.out.println("Process " + process.getProcessId() + ": " + turnaroundTime + "ms");
			avgTurnaroundTime += turnaroundTime;  // Calculate total turnaround time for average calculation
		}

		avgTurnaroundTime /= (double) processes.size();  // Calculate average turnaround time
		System.out.println("Average Turnaround Time: " + avgTurnaroundTime + "ms\n");

		// Calculate CPU Utilization
		CPUUtilization /= (double) currentTime;
		System.out.println("CPU Utilization: " + CPUUtilization * 100 + "%\n");
	}

	public static void SJF(ArrayList<Process> processes) {

		// Sort processes by arrival time and burst time
		Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime).thenComparingInt(Process::getBurstTime).thenComparingInt(Process::getProcessId));

		// Implement Shortest Job First scheduling algorithm here
		while(completedProcesses < processes.size()) {
			Process shortestJob = null;

			// Loop through processes to find the shortest job that has arrived and is not completed
			for(Process process : processes) {
				if(process.getArrivalTime() <= currentTime && process.getRemainingBurstTime() > 0) {
					if(shortestJob == null || process.getBurstTime() < shortestJob.getBurstTime() || (process.getBurstTime() == shortestJob.getBurstTime() && process.getArrivalTime() < shortestJob.getArrivalTime())) {
						shortestJob = process;
					}
				}
			}

			if (shortestJob != null) {
				while (shortestJob.getRemainingBurstTime() > 0) {
					System.out.println("Time " + currentTime + "ms: Process " + shortestJob.getProcessId() + " is executing.");
					currentTime++;
					CPUUtilization++;
					shortestJob.setRemainingBurstTime(shortestJob.getRemainingBurstTime() - 1);
				}
				waitingTimes.put(shortestJob.getProcessId(), currentTime - shortestJob.getArrivalTime() - shortestJob.getBurstTime());
				completedProcesses++;
			} else {
				System.out.println("Time " + currentTime + "ms: CPU is idle.");
				currentTime++;
			}
		}

		// Print waiting times for each process
		System.out.println("\nProcess Waiting Times:");
		for (Process process : processes) {
			System.out.println("Process " + process.getProcessId() + ": " + waitingTimes.get(process.getProcessId()) + "ms");
			avgWaitingTime += waitingTimes.get(process.getProcessId());  // Calculate total waiting time for average calculation
		}

		avgWaitingTime /= (double) processes.size();  // Calculate average waiting time
		System.out.println("Average Waiting Time: " + avgWaitingTime + "ms\n");

		// Print Response time & average response time for each process (which is the same as waiting time for SJF)
		System.out.println("Response Times:");
		for (Process process : processes) {
			System.out.println("Process " + process.getProcessId() + ": " + waitingTimes.get(process.getProcessId()) + "ms");
		}

		System.out.println("Average Response Time: " + avgWaitingTime + "ms\n");

		// Print Turnaround time & average turnaround time for each process
		System.out.println("Turnaround Times:");
		for (Process process : processes) {
			turnaroundTime = waitingTimes.get(process.getProcessId()) + process.getBurstTime();
			System.out.println("Process " + process.getProcessId() + ": " + turnaroundTime + "ms");
			avgTurnaroundTime += turnaroundTime;  // Calculate total turnaround time for average calculation
		}

		avgTurnaroundTime /= (double) processes.size();  // Calculate average turnaround time
		System.out.println("Average Turnaround Time: " + avgTurnaroundTime + "ms\n");

		// Calculate CPU Utilization
		CPUUtilization /= (double) currentTime;
		System.out.println("CPU Utilization: " + CPUUtilization * 100 + "%\n");
	}

	public static void PPS(ArrayList<Process> processes) {

		// Sort processes by arrival time and priority (assuming lower number means higher priority)
		Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime).thenComparingInt(Process::getPriority).thenComparingInt(Process::getBurstTime));

		// Implement Preemptive Priority Scheduling algorithm here
		while (completedProcesses < processes.size()) {
			Process highestPriorityProcess = null;

			// Loop through processes to find the highest priority process that has arrived and is not completed
			for (Process process : processes) {
				if (process.getArrivalTime() <= currentTime && process.getRemainingBurstTime() > 0) {
					if (highestPriorityProcess == null || process.getPriority() < highestPriorityProcess.getPriority() || (process.getPriority() == highestPriorityProcess.getPriority() && process.getArrivalTime() < highestPriorityProcess.getArrivalTime())) {
						highestPriorityProcess = process;
					}
				}
			}

			if (highestPriorityProcess != null) {
				if (!responseTime.containsKey(highestPriorityProcess.getProcessId())) {
					responseTime.put(highestPriorityProcess.getProcessId(), currentTime - highestPriorityProcess.getArrivalTime());
				}
				System.out.println("Time " + currentTime + "ms: Process " + highestPriorityProcess.getProcessId() + " is executing.");
				currentTime++;
				CPUUtilization++;
				highestPriorityProcess.setRemainingBurstTime(highestPriorityProcess.getRemainingBurstTime() - 1);

				if (highestPriorityProcess.getRemainingBurstTime() == 0) {
					waitingTimes.put(highestPriorityProcess.getProcessId(), currentTime - highestPriorityProcess.getArrivalTime() - highestPriorityProcess.getBurstTime());
					completedProcesses++;
				}
			} else {
				System.out.println("Time " + currentTime + "ms: CPU is idle.");
				currentTime++;
			}
		}

		// Print waiting times for each process
		System.out.println("\nProcess Waiting Times:");
		for (Process process : processes) {
			System.out.println("Process " + process.getProcessId() + ": " + waitingTimes.get(process.getProcessId()) + "ms");
			avgWaitingTime += waitingTimes.get(process.getProcessId());  // Calculate total waiting time for average calculation
		}
		avgWaitingTime /= (double) processes.size();  // Calculate average waiting time
		System.out.println("Average Waiting Time: " + avgWaitingTime + "ms\n");

		// Print Response time & average response time for each process
		System.out.println("Response Times:");
		for (Process process : processes) {
			System.out.println("Process " + process.getProcessId() + ": " + responseTime.get(process.getProcessId()) + "ms");
			avgResponseTime += responseTime.get(process.getProcessId());  // Calculate total response time for average calculation
		}
		avgResponseTime /= (double) processes.size();  // Calculate average response time
		System.out.println("Average Response Time: " + avgResponseTime + "ms\n");

		// Print Turnaround time & average turnaround time for each process
		System.out.println("Turnaround Times:");
		for (Process process : processes) {
			turnaroundTime = waitingTimes.get(process.getProcessId()) + process.getBurstTime();
			System.out.println("Process " + process.getProcessId() + ": " + turnaroundTime + "ms");
			avgTurnaroundTime += turnaroundTime;  // Calculate total turnaround time for average calculation
		}
		avgTurnaroundTime /= (double) processes.size();  // Calculate average turnaround time
		System.out.println("Average Turnaround Time: " + avgTurnaroundTime + "ms\n");

		// Calculate CPU Utilization
		CPUUtilization /= (double) currentTime;
		System.out.println("CPU Utilization: " + CPUUtilization * 100 + "%\n");
	}

	public static void RR(int timeQuantum, ArrayList<Process> processes) {

		// Sort processes by arrival time
		Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime).thenComparingInt(Process::getProcessId));

		// Implement Round Robin scheduling algorithm here
		while (completedProcesses < processes.size()) {

			for (Process process : processes) {
				if (process.getArrivalTime() <= currentTime && process.getRemainingBurstTime() > 0 && !readyQueue.contains(process)) {
					readyQueue.offer(process);
				}
			}

			if (readyQueue.isEmpty()) {
				System.out.println("Time " + currentTime + "ms: CPU is idle.");
				currentTime++;
				continue;
			}

			Process currentProcess = readyQueue.poll();
			int timeQuantumCounter = 0;

			if (!responseTime.containsKey(currentProcess.getProcessId())) {
				responseTime.put(currentProcess.getProcessId(), currentTime - currentProcess.getArrivalTime());
			}

			while (timeQuantumCounter < timeQuantum && currentProcess.getRemainingBurstTime() > 0) {
				System.out.println("Time " + currentTime + "ms: Process " + currentProcess.getProcessId() + " is executing.");
				currentTime++;
				CPUUtilization++;
				currentProcess.setRemainingBurstTime(currentProcess.getRemainingBurstTime() - 1);
				timeQuantumCounter++;

				// Check for new arrivals during execution
				for (Process process : processes) {
					if (process.getArrivalTime() <= currentTime && process.getRemainingBurstTime() > 0 && !readyQueue.contains(process) && process != currentProcess) {
						readyQueue.offer(process);
					}
				}
			}

			if (currentProcess.getRemainingBurstTime() > 0) {
				readyQueue.offer(currentProcess); // Re-add the process to the end of the queue if it's not finished
			} else {
				waitingTimes.put(currentProcess.getProcessId(), currentTime - currentProcess.getArrivalTime() - currentProcess.getBurstTime());
				completedProcesses++;
			}
		}

		// Print waiting times for each process
		System.out.println("\nProcess Waiting Times:");
		for (Process process : processes) {
			System.out.println("Process " + process.getProcessId() + ": " + waitingTimes.get(process.getProcessId()) + "ms");
			avgWaitingTime += waitingTimes.get(process.getProcessId());  // Calculate total waiting time for average calculation
		}
		avgWaitingTime /= (double) processes.size();  // Calculate average waiting time
		System.out.println("Average Waiting Time: " + avgWaitingTime + "ms\n");

		// Print Response time & average response time for each process
		System.out.println("Response Times:");
		for (Process process : processes) {
			System.out.println("Process " + process.getProcessId() + ": " + responseTime.get(process.getProcessId()) + "ms");
			avgResponseTime += responseTime.get(process.getProcessId());  // Calculate total response time for average calculation
		}
		avgResponseTime /= (double) processes.size();  // Calculate average response time
		System.out.println("Average Response Time: " + avgResponseTime + "ms\n");

		// Print Turnaround time & average turnaround time for each process
		System.out.println("Turnaround Times:");
		for (Process process : processes) {
			turnaroundTime = waitingTimes.get(process.getProcessId()) + process.getBurstTime();
			System.out.println("Process " + process.getProcessId() + ": " + turnaroundTime + "ms");
			avgTurnaroundTime += turnaroundTime;  // Calculate total turnaround time for average calculation
		}
		avgTurnaroundTime /= (double) processes.size();  // Calculate average turnaround time
		System.out.println("Average Turnaround Time: " + avgTurnaroundTime + "ms\n");

		// Calculate CPU Utilization
		CPUUtilization /= (double) currentTime;
		System.out.println("CPU Utilization: " + CPUUtilization * 100 + "%\n");
	}

	public static void main(String[] args) {
		System.out.print("Enter the file path: ");
		String filePath = scanner.nextLine();
		File file = new File(filePath);

		// Read processes from the file
		try {
			Scanner fileScanner = new Scanner(file);
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.trim().split("\\s+");
					int processId = Integer.parseInt(parts[0].trim());
					int arrivalTime = Integer.parseInt(parts[1].trim());
					int burstTime = Integer.parseInt(parts[2].trim());
					int priority = Integer.parseInt(parts[3].trim());
					Process process = new Process(processId, arrivalTime, burstTime, priority, burstTime);
					processes.add(process);
			}
			fileScanner.close();
			System.out.println("Processes loaded successfully:");
			for (Process process : processes) {
				System.out.println("Process ID: " + process.getProcessId() + ", Arrival Time: " + process.getArrivalTime() + ", Burst Time: " + process.getBurstTime() + ", Priority: " + process.getPriority());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Please check the path and try again.");
			e.printStackTrace();
		}

		// Ask user for scheduling algorithm choice
		System.out.println("Choose a scheduling algorithm:");
		System.out.println("1. First-Come, First-Served (FCFS)");
		System.out.println("2. Shortest Job First (SJF)");
		System.out.println("3. Preemptive Priority Scheduling");
		System.out.println("4. Round Robin (RR)\n");
		System.out.print("Enter your choice (1-4): ");
		int choice = scanner.nextInt();

		// Call the appropriate scheduling method based on user choice
		switch (choice) {
			case 1:
				System.out.println("You chose First-Come, First-Served (FCFS)");
				// Call FCFS scheduling method here
				FCFS(processes);
				break;
			case 2:
				System.out.println("You chose Shortest Job First (SJF)");
				// Call SJF scheduling method here
				SJF(processes);
				break;
			case 3:
				System.out.println("You chose Preemptive Priority Scheduling");
				// Call Preemptive Priority Scheduling method here
				PPS(processes);
				break;
			case 4:
				System.out.println("You chose Round Robin (RR)");
				// Ask user for time quantum
				System.out.print("Enter the time quantum: ");
				int timeQuantum = scanner.nextInt();
				// Call Round Robin scheduling method here
				RR(timeQuantum, processes);
				break;
			default:
				System.out.println("Invalid choice. Please choose a number between 1 and 4.");
		}
	}
}

class Process {
	private int processId;
	private int arrivalTime;
	private int burstTime;
	private int priority;
	private int remainingBurstTime;

	public Process(int processId, int arrivalTime, int burstTime, int priority, int remainingBurstTime) {
		this.processId = processId;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
		this.remainingBurstTime = remainingBurstTime;
	}

	public int getProcessId() {
		return processId;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public int getPriority() {
		return priority;
	}

	public int getRemainingBurstTime() {
		return remainingBurstTime;
	}

	public void setRemainingBurstTime(int remainingBurstTime) {
		this.remainingBurstTime = remainingBurstTime;
	}
}

