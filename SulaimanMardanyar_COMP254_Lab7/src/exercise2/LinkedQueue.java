package exercise2;

public class LinkedQueue<E> implements Queue<E>{
	private SinglyLinkedList<E> list = new SinglyLinkedList<>();
	public LinkedQueue() {}
	public int size() { return list.size(); }
	public boolean isEmpty() { return list.isEmpty(); }
	public void enqueue(E element) {
		if(size() == 0) {
			list.addFirst(element);
		} else {
			list.addLast(element);
		}
	}
	public E dequeue() {
		return list.removeFirst();
	}
	public E first() {
		return list.first();
	}
	public String toString() {
		return list.toString();
	}
}
