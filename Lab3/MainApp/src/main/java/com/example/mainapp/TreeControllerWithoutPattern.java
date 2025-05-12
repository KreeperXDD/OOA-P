package com.example.mainapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TreeControllerWithoutPattern {
    @FXML
    private TreeView<String> treeView;
    @FXML private TextArea historyArea;
    @FXML private ComboBox<String> iteratorTypeBox;

    private Node root;
    private final List<Node> history = new ArrayList<>();
    private final Map<Node, TreeItem<String>> nodeToTreeItemMap = new HashMap<>();
    private final Map<TreeItem<String>, Node> treeItemToNodeMap = new HashMap<>();
    private String currentTraversalType = "DFS (поиск в глубину)";

    @FXML
    private void initialize() {
        iteratorTypeBox.getItems().addAll("DFS (поиск в глубину)", "BFS (поиск в ширину)");
        iteratorTypeBox.getSelectionModel().selectFirst();
        iteratorTypeBox.setOnAction(event -> currentTraversalType = iteratorTypeBox.getValue());

        LoadFileSystemTree();
    }

    private void LoadFileSystemTree() {
        historyArea.setText("Загрузка структуры файловой системы...");
        new Thread(() -> {
            Node fileSystemRoot = BuildFileSystemTree(new File("."));
            Platform.runLater(() -> {
                root = fileSystemRoot;
                treeView.setRoot(BuildTreeItem(root));
                treeView.getRoot().setExpanded(true);
                historyArea.clear();
            });
        }).start();
    }

    private TreeItem<String> BuildTreeItem(Node node) {
        TreeItem<String> item = new TreeItem<>(node.GetName());
        item.setExpanded(false);

        nodeToTreeItemMap.put(node, item);
        treeItemToNodeMap.put(item, node);

        for (Node child : node.GetChildren()) {
            item.getChildren().add(BuildTreeItem(child));
        }
        return item;
    }

    private Node BuildFileSystemTree(File file) {
        Node node = new Node(file.getName());
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (child.isDirectory() || child.isFile()) {
                        node.AddChild(BuildFileSystemTree(child));
                    }
                }
            }
        }
        return node;
    }

    @FXML
    private void onTraverseClicked(ActionEvent event) {
        if (root == null) return;

        List<Node> traversalOrder;
        if ("BFS (поиск в ширину)".equals(currentTraversalType)) {
            traversalOrder = performBFS(root);
        } else {
            traversalOrder = performDFS(root);
        }

        ShowVisualizationWindow(traversalOrder);

        history.clear();
        traversalOrder.forEach(this::ProcessNode);
    }

    private List<Node> performBFS(Node root) {
        List<Node> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(current);
            queue.addAll(current.GetChildren());
        }
        return result;
    }

    private List<Node> performDFS(Node root) {
        List<Node> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            result.add(current);
            List<Node> children = current.GetChildren();
            Collections.reverse(children); // Для сохранения порядка посещения
            for (Node child : children) {
                stack.push(child);
            }
        }
        return result;
    }

    private void ProcessNode(Node node) {
        history.add(node);
        UpdateHistory();

        TreeItem<String> treeItem = nodeToTreeItemMap.get(node);
        if (treeItem != null) {
            ExpandParents(treeItem);
            treeView.scrollTo(treeView.getRow(treeItem));
        }
    }

    private void ExpandParents(TreeItem<?> item) {
        TreeItem<?> parent = item.getParent();
        while (parent != null) {
            parent.setExpanded(true);
            parent = parent.getParent();
        }
    }

    private void UpdateHistory() {
        historyArea.clear();
        history.forEach(node -> historyArea.appendText(node.GetName() + "\n"));
        UpdateTreeViewStyles();
    }

    private void UpdateTreeViewStyles() {
        treeView.setCellFactory(tv -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    TreeItem<String> ti = getTreeItem();
                    if (ti != null && history.contains(treeItemToNodeMap.get(ti))) {
                        setStyle("-fx-background-color: #e0f7fa; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    @FXML
    private void onResetClicked(ActionEvent event) {
        ResetTraversal();
    }

    private void ResetTraversal() {
        history.clear();
        UpdateHistory();
        nodeToTreeItemMap.values().forEach(item -> item.setExpanded(false));
        treeView.getRoot().setExpanded(true);
    }

    private void ShowVisualizationWindow(List<Node> traversalOrder) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("visualization-view.fxml"));
            Parent root = loader.load();

            VisualizationController controller = loader.getController();
            controller.SetTraversalOrder(traversalOrder);

            Stage stage = new Stage();
            stage.setTitle("Визуализация обхода");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
