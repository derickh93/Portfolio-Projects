package gm;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<E> implements Iterable<E> {

	class DoubleLinkedListIterator implements Iterator<E> {
		int currIndex = 0;
		Node<E> current = front;
		
		public boolean hasNext() {
			return currIndex < size;

		}

		public E next() {
			E temp = null;
			if(!hasNext())
				throw new NoSuchElementException();

			temp = current.getData();
			current = current.getNext();
			currIndex++;
			return temp;

		}
	}


	private Node<E> front;
	private Node<E> rear;

	private int size;

	public DoubleLinkedList() {
		super();
		front = null;
		rear = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public void addToRear(E data) {
		if(front == null) {
			Node<E> node = new Node(data,null,null);
			front = node;
			rear = node;
			size++;
		}
		else {
			Node<E> node = new Node(data,null,rear);
			rear.setNext(node);
			rear = node;
			size++;
		}
	}

	public void addToFront(E data) {
		if(front == null) {
			Node<E> node = new Node(data,null,null);
			front = node;
			rear = node;
			size++;
		}
		else {
			Node<E> node = new Node(data,front,null);
			front.setPrev(node);
			front = node;
			size++;
		}
	}


	// should throw NoSuchElementException if it can't remove
	public E removeFromRear() {
		E temp;
		if(front == null) 
			throw new NoSuchElementException();
		else {
			temp = rear.getData();
			rear = rear.getPrev();
			size--;
		}
		if(rear != null)
			rear.setNext(null);
		return temp;
	}

	// should throw NoSuchElementException if it can't remove
	public E removeFromFront() {
		if(size == 0) 
			throw new NoSuchElementException();
		if(front.getNext() != null)
			front.setPrev(null);
		Node<E> temp = front;
		front = front.getNext();

		size--;

		return temp.getData();
	}

	// takes in 0 based position, should throw NoSuchElementException if it can't remove
	public E get(int pos) {
		Node<E> trav = front;
		E temp;
		int count = 0;
		if(pos+1 > size)
			throw new NoSuchElementException();
		while(trav.getNext() != null && count != pos) {
			trav = trav.getNext();
			count++;
		}
		temp = trav.getData();
		return temp;
	}

	public String toString() {
		String temp = new String() ;
		Node<E> trav = front;
		if(size == 0)
			return "Elements: " + temp + "\nSize: " + size;
		while(trav.getNext() != null) {
			temp += trav.getData() + " <==> ";
			trav = trav.getNext();
		}
		temp += trav.getData();
		return "Elements: " + temp + "\nSize: " + size;
	}

	public String reverseString() {
		StringBuilder str = new StringBuilder();
		Node<E> trav = rear;
		while(trav.getPrev() != null) {
			str.append(trav.getData() + " <==> ");
			trav = trav.getPrev();
		}
		str.append(trav.getData());
		return "Elements: " + str + "\nSize: " + size;
	}

	@Override
	public Iterator<E> iterator() {
		return new DoubleLinkedListIterator();
	}

}
