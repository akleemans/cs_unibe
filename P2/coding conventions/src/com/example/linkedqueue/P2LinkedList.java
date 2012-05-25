package com.example.linkedqueue;

import java.util.Collection;
import java.util.Iterator;

public class P2LinkedList<T> implements Iterable<T> {

	private int size;
	private Tuple<T> header;
	private Tuple<T> tail;

	private static class Tuple<I> {
		public Tuple<I> next;
		public I item;
	}

	public P2LinkedList() {
		this.tail = new Tuple<T>();
		this.tail.next = null;
		this.tail.item = null;
		this.header = this.tail;
		this.size = 0;

	}

	public void add(T e) {
		tail.next = new Tuple<T>();
		tail.item = e;
		tail = tail.next;
		size++;
	}

	public void addAt(int index, T element) {
		assert index >= 0 && index < size;
		Tuple<T> current = header;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		Tuple<T> newItem = new Tuple<T>();
		newItem.item = element;
		newItem.next = current.next;
		current.next = newItem;
		size++;
	}

	public void addAll(Collection<? extends T> c) {
		for (T element : c) {
			this.add(element);
		}
	}

	public void addAll(int index, Collection<? extends T> c) {
		assert index >= 0 && index < size;
		int counter = 0;
		for (T element : c) {
			this.addAt(index + ++counter, element);
		}
	}

	public void clear() {
		this.header = this.tail = null;
		this.size = 0;
	}

	public boolean contains(Object o) {
		Tuple<T> current = header;
		for (int i = 0; i <= size; i++) {
			if (o.equals(current.item)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	public T remove(int index) {
		assert index >= 0 && index < size;
		Tuple<T> current = header;
		for(int i = 0; i < index; i++) {
			current = current.next;
		}
		T item = current.next.item;
		current.next = current.next.next;
		if(current.next == null) {
			this.tail = current;
		}
		size--;
		return item;
	}

	public int size() {
		return this.size;
	}

	public T get(int index) {
		assert index >= 0 && index < size;
		Tuple<T> current = header;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.item;
	}
	
	private class Iter implements Iterator<T> {

		private Tuple<T> current = header;
		
		@Override
		public boolean hasNext() {
			return current.next != null;
		}

		@Override
		public T next() {
			T item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	@Override
	public Iterator<T> iterator() {
		return new Iter();
	}
	

}
