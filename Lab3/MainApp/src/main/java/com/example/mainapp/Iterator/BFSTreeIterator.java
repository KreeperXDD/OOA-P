package com.example.mainapp.Iterator;

import com.example.mainapp.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSTreeIterator implements IIterator<Node> {
    private final Node root;
    private Queue<Node> queue;

    public BFSTreeIterator(Node root) {
        this.root = root;
        Reset();
    }

    @Override
    public boolean HasNext() {
        return !queue.isEmpty();
    }

    @Override
    public Node Next() {
        Node current = queue.poll();

        List<Node> children = current.GetChildren();
        if (children != null && !children.isEmpty()) {
            queue.addAll(children);
        }

        return current;
    }

    @Override
    public void Reset() {
        queue = new LinkedList<>();
        queue.add(root);
    }
}
