package program2;


public interface LinkedStackADT<T> {
	void push(T d);
	T pop() throws RuntimeException;
	T peek() throws RuntimeException;
	boolean isEmpty();
	int getSize();
}