package com.example.java_17.model;

public abstract class Validator<T> {
    public abstract void validate(T item);
    public abstract boolean exists(T item);
}