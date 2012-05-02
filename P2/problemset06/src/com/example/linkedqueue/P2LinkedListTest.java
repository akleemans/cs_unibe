package com.example.linkedqueue;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;


public class P2LinkedListTest {
	
	private P2LinkedList<Integer> list;
	
	@Before
	public void setUp() {
		this.list = new P2LinkedList<Integer>();
	}

	@Test
	public void shouldBeEmptyAfterInitialization() throws Exception {
		assertTrue(list.size() == 0);
	}
	
	@Test
	public void shouldHaveSizeOneAfterAdd() {
		list.add(42);
		assertTrue(list.size() == 1);
	}
	
	@Test
	public void shouldContainNumberAfterAdd() {
		list.add(42);
		assertTrue(list.contains(42));
	}
	
	@Test
	public void shouldFindNumberAtFirstIndexAfterAdd() {
		list.add(42);
		assertTrue(list.get(0) == 42);
	}
	
	@Test
	public void shouldFindNumbersWherePut() {
		list.add(42);
		list.add(13);
		assertTrue(list.get(0) == 42);
		assertTrue(list.get(1) == 13);
	}
	
	@Test
	public void shouldAddCollectionRight() {
		LinkedList<Integer> integerList = new LinkedList<Integer>();
		integerList.add(3);
		integerList.add(4);
		integerList.add(5);
		this.list.addAll(integerList);
		assertTrue(list.size() == 3);
		assertTrue(list.get(0) == 3);
		assertTrue(list.get(1) == 4);
		assertTrue(list.get(2) == 5);
	}
	
	@Test
	public void shouldContainAfterAddAtIndex() {
		this.list.add(42);
		this.list.add(13);
		this.list.addAt(1, 2);
		assertTrue(this.list.contains(2));
	}
	
	@Test
	public void shouldBeAtRightPositionAfterAddAtIndex() {
		this.list.add(42);
		this.list.add(13);
		this.list.add(14);
		this.list.addAt(2, 2);
		assertTrue(this.list.get(0) == 42);
		assertTrue(this.list.get(1) == 13);
		assertTrue(this.list.get(2) == 2);
		assertTrue(this.list.get(3) == 14);
	}
	
	@Test
	public void shouldAddCollectionAtIndex() {
		LinkedList<Integer> integerList = new LinkedList<Integer>();
		integerList.add(3);
		integerList.add(4);
		integerList.add(5);
		this.list.add(1);
		this.list.add(2);
		this.list.add(6);
		this.list.addAll(2, integerList);
		assertTrue(this.list.size() == 6);
		assertTrue(this.list.get(0) == 1);
		assertTrue(this.list.get(1) == 2);
		assertTrue(this.list.get(2) == 3);
		assertTrue(this.list.get(3) == 4);
		assertTrue(this.list.get(4) == 5);
		assertTrue(this.list.get(5) == 6);
	}
	
	@Test
	public void shouldRemoveRightItem() {
		this.list.add(1);
		this.list.add(2);
		this.list.add(3);
		this.list.add(4);
		assertTrue(this.list.remove(1) == 2); 
		assertTrue(this.list.size() == 3);
		assertTrue(this.list.get(0) == 1);
		assertTrue(this.list.get(1) == 3);
		assertTrue(this.list.get(2) == 4);
	}
	
	@Test
	public void shouldRemoveLast() {
		this.list.add(1);
		this.list.add(2);
		this.list.remove(1);
		assertTrue(list.size() == 1);
		this.list.add(3);
		assertTrue(this.list.get(1) == 3);
	}
	
	// NOTE	I've added these two additional test, please take care
	//		of these bugs as well. I want all our custom collection
	//		to implement the full API of Iterable! 
	//		-- CTO of ACME Software Inc.
	
	@Test
	public void shouldIterateEmptyList() {
		Iterator<Integer> it = list.iterator();
		assertNotNull(it);
		assertFalse(it.hasNext());
		try {
			it.next();
			fail("expected NoSuchElementException!");
		}
		catch (NoSuchElementException ex) {
			assertNotNull(ex);
		}
	}
	
	@Test 
	public void shouldIterateListOfThree() {
		list.add(15);
		list.add(16);
		list.add(17);
		Iterator<Integer> it = list.iterator();
		assertNotNull(it);
		assertTrue(it.hasNext());
		assertTrue(it.next() == 15);
		assertTrue(it.hasNext());
		assertTrue(it.next() == 16);
		assertTrue(it.hasNext());
		assertTrue(it.next() == 17);
		assertFalse(it.hasNext());
		try {
			it.next();
			fail("expected NoSuchElementException!");
		}
		catch (NoSuchElementException ex) {
			assertNotNull(ex);
		}
	}
	
}
