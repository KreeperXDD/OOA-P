package com.example.mainapp;

import com.example.mainapp.Iterator.BFSTreeIterator;
import com.example.mainapp.Iterator.DFSTreeIterator;
import com.example.mainapp.Iterator.IIterator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeController {

    @FXML private TreeView<String> treeView;
    @FXML private TextArea historyArea;
    @FXML private ComboBox<String> iteratorTypeBox;

    private Node root;
    private IIterator<Node> iterator;
    private final List<Node> history = new ArrayList<>();   
    private final Map<Node, TreeItem<String>> nodeToTreeItemMap = new HashMap<>();
    private final Map<TreeItem<String>, Node> treeItemToNodeMap = new HashMap<>();

    @FXML
    private void initialize() {
        iteratorTypeBox.getItems().addAll("DFS (поиск в глубину)", "BFS (поиск в ширину)");
        iteratorTypeBox.getSelectionModel().selectFirst();
        iteratorTypeBox.setOnAction(event -> ResetIterator());

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
                SetIteratorBySelection();
                historyArea.clear();
            });
        }).start();
    }

    private TreeItem<String> BuildTreeItem(Node node) {
        TreeItem<String> item = new TreeItem<>(node.GetName());
        item.setExpanded(false);

        // Добавляем в обе карты
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

    private void SetIteratorBySelection() {
        String selected = iteratorTypeBox.getSelectionModel().getSelectedItem();
        iterator = "BFS (поиск в ширину)".equals(selected)
                ? new BFSTreeIterator(root)
                : new DFSTreeIterator(root);
        ResetIterator();
    }

    @FXML
    private void onTraverseClicked(ActionEvent event) {
        if (iterator == null) return;

        List<Node> traversalOrder = new ArrayList<>();
        while (iterator.HasNext()) {
            traversalOrder.add(iterator.Next());
        }

        ShowVisualizationWindow(traversalOrder);

        history.clear();
        traversalOrder.forEach(this::ProcessNode);
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

                    // Используем обратную карту
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
        ResetIterator();
    }

    private void ResetIterator() {
        if (iterator != null) iterator.Reset();
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