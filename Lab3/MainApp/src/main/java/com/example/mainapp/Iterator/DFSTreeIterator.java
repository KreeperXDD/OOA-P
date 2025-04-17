package com.example.mainapp.Iterator;

import com.example.mainapp.Node;

import java.util.*;

public class DFSTreeIterator implements IIterator<Node> {
    private final Node root;
    private Deque<Iterator<Node>> stack;
    private Node current;

    public DFSTreeIterator(Node root) {
        this.root = root;
        Reset();
    }

    @Override
    public boolean HasNext() {
        while (!stack.isEmpty()) {
            Iterator<Node> top = stack.peek();
            if (top.hasNext()) {
                current = top.next();
                stack.push(current.GetChildren().iterator());
                return true;
            }
            else {
                stack.pop();
            }
        }

        return false;
    }

    @Override
    public Node Next() {
        return current;
    }

    @Override
    public void Reset() {
        stack = new ArrayDeque<>();
        stack.push(Collections.singletonList(root).iterator());
    }
}
