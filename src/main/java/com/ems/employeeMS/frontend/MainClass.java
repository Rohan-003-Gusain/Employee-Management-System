package com.ems.employeeMS.frontend;


import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class MainClass extends Application {

    Label heading;
    Button add, view, remove;

    public void start (Stage primaryStage) {

        Pane root = new Pane();

        Image bgimg = new Image(getClass().getResourceAsStream("/photos/home.jpg"));
        ImageView bgview = new ImageView(bgimg);
        bgview.setFitWidth(1120);
        bgview.setFitHeight(630);
        root.getChildren().add(bgview);

        heading = new Label("Employee Management System");
        heading.setLayoutX(340);
        heading.setLayoutY(155);
        root.getChildren().add(heading);

        add = new Button("Add Employee");
        add.setLayoutX(355);
        add.setLayoutY(270);
        add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    ((Stage) add.getScene().getWindow()).close();
                    new AddEmp().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        root.getChildren().add(add);

        view = new Button("View Employee");
        view.setLayoutX(565);
        view.setLayoutY(270);
        view.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    ((Stage) view.getScene().getWindow()).close();
                    new ViewEmp().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        root.getChildren().add(view);

        remove = new Button("Remove Employee");
        remove.setLayoutX(450);
        remove.setLayoutY(350);
        remove.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    ((Stage) remove.getScene().getWindow()).close();
                    new RemoveEmp().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        root.getChildren().add(remove);

        Scene scene = new Scene(root, 1080, 630);
        scene.getStylesheets().add(getClass().getResource("/css/mainclass.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Page");
        primaryStage.show();

    }

    public static void main (String [] args) {
        launch(args);
    }
    
}
