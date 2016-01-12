package com.walmart.homework.data;

public class Optional<T> {

	private T value;
	
	public Optional(T t) {
		this.value = t;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
