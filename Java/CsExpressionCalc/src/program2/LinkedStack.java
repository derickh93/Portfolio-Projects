package program2;

public class LinkedStack<T> implements LinkedStackADT<T> {
	private class Node<E>{
		private E data;
		private Node<E> next;
		public Node() {
			data = null;
			next = null;
		}
		public Node(E d) {
			data = d;
			next = null;
		}
		public Node(E d, Node<E> n) {
			data = d;
			next = n;
		}
	}
	private Node<T> front;
	private int size;

	public void push(T d) {
		front = new Node<T>(d, front);
		size++;
	}

	public T pop() throws RuntimeException {
		if(isEmpty())
			throw new RuntimeException("Stack empty exception...");
		size--;
		T d = front.data;
		front = front.next;
		return d;
	}

	public T peek() throws RuntimeException {
		if(isEmpty())
			throw new RuntimeException("Stack empty exception...");
		return front.data;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public int getSize() {
		return size;
	}
	public String toString() {
		String str = "Stack - size: " + size + "\n";
		Node<T> trav = front;
		while(trav != null) {
			str += trav.data + "\n";
			trav=trav.next;
		}
		return str;
	}
}