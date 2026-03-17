public class ThreadSort {
	
	static int[] array = {5, 2, 9, 5, 1, 6};
	static int[] finalArray = new int[array.length];

	public static void main(String[] args) {
		SortThread thread1 = new SortThread(1, 0, array.length / 2); // Sort the first half of the array
		SortThread thread2 = new SortThread(2, array.length / 2, array.length); // Sort the second half of the array
		MergeThread thread3 = new MergeThread(3); // Merge the sorted halves of the array
		Thread t1 = new Thread(thread1);
		Thread t2 = new Thread(thread2);
		Thread t3 = new Thread(thread3);
		t1.start();
		t2.start();
		try {
			t1.join(); // Wait for thread1 to finish
			t2.join(); // Wait for thread2 to finish
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t3.start();
		try {
			t3.join(); // Wait for thread3 to finish
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class SortThread implements Runnable {
	
	private int threadId;
	private int start;
	private int end;
	public SortThread(int threadId, int start, int end) {
		// Constructor
		this.threadId = threadId;
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		System.out.println("Sorting from index " + start + " to " + end + " for thread " + threadId);
		System.out.println("Current array: " + java.util.Arrays.toString(ThreadSort.array) + " for thread " + threadId);
		// Sort the array
		for (int i = start; i < end; i++) {
			for (int j = i + 1; j < end; j++) {
				if (ThreadSort.array[i] > ThreadSort.array[j]) {
					int temp = ThreadSort.array[i];
					ThreadSort.array[i] = ThreadSort.array[j];
					ThreadSort.array[j] = temp;
				}
			}
		}
		System.out.println("Final Array: " + java.util.Arrays.toString(ThreadSort.array) + " for thread " + threadId);
	}
}

class MergeThread implements Runnable {

	private int threadId;
	public MergeThread(int threadId) {
		// Constructor
		this.threadId = threadId; // Assign a thread ID for the merge thread
	}
	@Override
	public void run() {
		// Merge the sorted halves of the array
		System.out.println("Merging sorted halves for thread " + threadId);
		int mid = ThreadSort.array.length / 2;
		int i = 0, j = mid, k = 0;
		while (i < mid && j < ThreadSort.array.length) {
			if (ThreadSort.array[i] < ThreadSort.array[j]) {
				ThreadSort.finalArray[k++] = ThreadSort.array[i++];
			} else {
				ThreadSort.finalArray[k++] = ThreadSort.array[j++];
			}
		}
		// Copy any remaining elements from either half
		while (i < mid) {
			ThreadSort.finalArray[k++] = ThreadSort.array[i++];
		}
		while (j < ThreadSort.array.length) {
			ThreadSort.finalArray[k++] = ThreadSort.array[j++];
		}
		System.out.println("Final merged array for thread " + threadId + ": " + java.util.Arrays.toString(ThreadSort.finalArray));
	}
}