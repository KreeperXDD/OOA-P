package com.example.mainapp;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private final List<Node> children = new ArrayList<Node>();

    public Node(String name) {
        this.name = name;
    }

    public void AddChild(Node child) {
        children.add(child);
    }

    public String GetName() {
        return name;
    }

    public List<Node> GetChildren() {
        return children;
    }
}
