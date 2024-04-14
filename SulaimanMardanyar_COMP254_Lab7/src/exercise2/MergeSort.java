package exercise2;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {

	/**exercise 2 question**/
	//Bottom up merge sort support for queue
	public static <K> void merge(Queue<K> in, Queue<K> out, Comparator<K> comp) {

	}
	public static<K> void mergeSortBottomUpQueue(Queue<K> original, Comparator<K> comp) {
		int n = original.size();
		Queue<K> src = original;
		Queue<K> dest = new LinkedQueue<>();
		Queue<K> temp = new LinkedQueue<>();
		for(int i = 1; i < n; i *= 2) {
			merge(src, dest,comp);
		}
		//empty dest into original as src is now sorted
		while(!dest.isEmpty()){
			original.enqueue(dest.dequeue());
		}
	}

	public static void main(String[] args) {
		// below is for visualisation purposes
//		for(int i = 1; i < 20; i *= 2) {
//			for(int j = 0; j < 20; j += 2*i){
//				System.out.println("J: " + j + ", I: " + i);
//			}
//		}

		Comparator<Integer> comp = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		};

		//testing bottom up merge sort for queues
		LinkedQueue<Integer> queue = new LinkedQueue<>();
		queue.enqueue(54);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(5);
		queue.enqueue(1);
		queue.enqueue(9);
		queue.enqueue(8);
		queue.enqueue(11);

		//sort
		System.out.println("\n___Bottom Up Merge Sort with Queues___");
		mergeSortBottomUpQueue(queue, comp);
		while(!queue.isEmpty()) {
			System.out.print(queue.dequeue() + ", ");
		}
		System.out.println("\n___Bottom Up Merge Sort with Queues Completed___");
	}
}
