package com.example.mainapp;

import com.example.mainapp.Iterator.BFSTreeIterator;
import com.example.mainapp.Iterator.DFSTreeIterator;
import com.example.mainapp.Iterator.IIterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class TreeController {

    @FXML   private TreeView<String> treeView;
    @FXML   private TextArea historyArea;
    @FXML   private ComboBox<String> iteratorTypeBox;

    private Node root;
    private IIterator<Node> iterator;
    private final List<String> history = new ArrayList<>();

    @FXML private void initialize() {

        root = BuildSampleTree();
        treeView.setRoot(BuildTreeItem(root));
        treeView.getRoot().setExpanded(true);

        iteratorTypeBox.getItems().addAll("DFS (поиск в глубину)", "BFS (поиск в ширину)");
        iteratorTypeBox.getSelectionModel().selectFirst();

        SetIteratorBySelection();

        iteratorTypeBox.setOnAction(event -> {
            SetIteratorBySelection();
            historyArea.clear();
            history.clear();
            treeView.setRoot(BuildTreeItem(root));
            treeView.getRoot().setExpanded(true);
            iterator.Reset();
        });
    }

    private void SetIteratorBySelection() {
        String selected = iteratorTypeBox.getSelectionModel().getSelectedItem();
        if (("BFS (поиск в ширину)").equals(selected)) {
            iterator = new BFSTreeIterator(root);
        }else {
            iterator = new DFSTreeIterator(root);
        }
    }

    private TreeItem<String> BuildTreeItem(Node node) {
        TreeItem<String> item = new TreeItem<>(node.GetName());
        for (Node child : node.GetChildren()) {
            item.getChildren().add(BuildTreeItem(child));
        }
        return item;
    }

    private Node BuildSampleTree() {
        Node root = new Node("root");
        Node folder1 = new Node("folder1");
        folder1.AddChild(new Node("file1.txt"));
        folder1.AddChild(new Node("file2.txt"));

        Node folder2 = new Node("folder2");
        folder2.AddChild(new Node("file3.txt"));

        root.AddChild(folder1);
        root.AddChild(folder2);
        return root;
    }

    public void onNextClicked(ActionEvent actionEvent) {
        if (iterator.HasNext()) {
            Node next = iterator.Next();
            history.add(next.GetName());
            UpdateHistory();
        }else {
            historyArea.appendText("Обход завершен");
        }
    }

    private void UpdateHistory() {
        historyArea.clear();
        history.forEach(name -> historyArea.appendText(name + "\n"));
    }

    public void onResetClicked(ActionEvent actionEvent) {
        ResetIterator();
    }

    private void ResetIterator() {
        iterator.Reset();
        history.clear();
        UpdateHistory();
    }
}