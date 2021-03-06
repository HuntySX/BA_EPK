package com.company.UI;

import com.company.UI.javafxgraph.fxgraph.graph.Graph;
import com.company.UI.javafxgraph.fxgraph.graph.Model;
import com.company.UI.javafxgraph.fxgraph.layout.base.Layout;
import com.company.UI.javafxgraph.fxgraph.layout.random.RandomLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {


    Graph graph;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL url = new File("src/main/java/com/company/UI/Main.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        loader.setLocation(url);
        Parent root = loader.load();
        Scene scene = new Scene(root, 1600, 900);

        UI_Controller UIController = loader.getController();
        BorderPane pane = UIController.getCanvaspane();
        VBox Box = UIController.getRightbox();

        UIController.initializeButtons();

        graph = new Graph(Box, UIController);

        UIController.setModel(graph.getModel());
        UIController.setEPK(graph.getModel().getEPK());
        UIController.setPrimaryStage(primaryStage);

        pane.setCenter(graph.getScrollPane());

        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); not working
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("EPCSim");
        primaryStage.show();

        addGraphComponents();

        Layout layout = new RandomLayout(graph);
        layout.execute();
    }

    private void addGraphComponents() {

        Model model = graph.getModel();

        graph.beginUpdate();

        graph.endUpdate();

    }
}