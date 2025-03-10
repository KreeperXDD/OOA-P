package com.example.lab1.SimplePool;

import java.util.Stack;
import java.util.function.Supplier;

public class SimplePool<T> {
    private final Supplier<T> preloadFunction;
    private final Stack<T> pool;

    public SimplePool(Supplier<T> preloadFunction, int capacity) {
        this.preloadFunction = preloadFunction;
        this.pool = new Stack<>();

        for (int i = 0; i < capacity; i++) {
            pool.push(preloadFunction.get());
        }
    }

    public T Get() {
        return pool.isEmpty() ? preloadFunction.get() : pool.pop();
    }

    public void ReturnToPool(T obj) {
        pool.push(obj);
    }
}
