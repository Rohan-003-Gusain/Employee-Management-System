package com.ems.employeeMS.frontend;

import com.ems.employeeMS.backend.entity.AddEmpEntity;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RemoveEmp extends Application {
	
	// Get Employee IDs From API
	private ObservableList<String> getEmployeeIdsFromAPI() {
	    try {
	        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
	        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
	                .uri(new java.net.URI("http://localhost:8080/api/employees/ids"))
	                .GET()
	                .build();

	        java.net.http.HttpResponse<String> response = client.send(request,
	                java.net.http.HttpResponse.BodyHandlers.ofString());

	        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
	        java.util.List<String> ids = mapper.readValue(response.body(),
	                new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>() {});

	        return FXCollections.observableArrayList(ids);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return FXCollections.observableArrayList();
	    }
	}
	
	// Get Employee By IDs From API
	private AddEmpEntity getEmployeeByIdAPI(String id) {
	    try {
	        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
	        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
	                .uri(new java.net.URI("http://localhost:8080/api/employees/get/" + id))
	                .GET()
	                .build();

	        java.net.http.HttpResponse<String> response = client.send(request,
	                java.net.http.HttpResponse.BodyHandlers.ofString());

	        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
	        return mapper.readValue(response.body(), AddEmpEntity.class);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	// Delete Employee By ID From API
	private boolean deleteEmployeeByIdAPI(String id) {
	    try {
	        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
	        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
	                .uri(new java.net.URI("http://localhost:8080/api/employees/delete/" + id))
	                .DELETE()
	                .build();

	        java.net.http.HttpResponse<String> response = client.send(request,
	                java.net.http.HttpResponse.BodyHandlers.ofString());

	        return response.statusCode() == 200; // या API के अनुसार check करो

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    public void start(Stage stage) {

        Label empid, name, phone, email, tname, tphone, temail;
        ComboBox<String> empidBox;
        Button dlt, back;

        HBox root = new HBox();

        empid = new Label("Empoyee ID");
        empidBox = new ComboBox<>();

        name = new Label("Name");
        tname = new Label();
        tname.getStyleClass().add("readonly-field");

        phone = new Label("Phone No");
        tphone = new Label();
        tphone.getStyleClass().add("readonly-field");

        email = new Label("Email");
        temail = new Label();
        temail.getStyleClass().add("readonly-field");

        ObservableList<String> empIds = FXCollections.observableArrayList(getEmployeeIdsFromAPI());
        empidBox.setItems(empIds);
        empidBox.setEditable(true);
        empidBox.setOnAction(_ -> {
            String selectedId = empidBox.getValue();
            if (selectedId != null) {
            	
                AddEmpEntity emp = getEmployeeByIdAPI(selectedId);
                if (emp != null) {
                    tname.setText(emp.getName());
                    tphone.setText(emp.getPhoneno());
                    temail.setText(emp.getEmail());
                } else {
                    tname.setText("");
                    tphone.setText("");
                    temail.setText("");
                    empidBox.getEditor().setText("");

                }
            }
        });
        
        dlt = new Button("Delete");
        dlt.setOnAction(_ -> {
            String id = empidBox.getValue();
            if(id != null) {
                boolean deleted = deleteEmployeeByIdAPI(id) ;

                if (deleted) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee deleted Successfully.");
                    alert.showAndWait();

                    tname.setText("");
                    tphone.setText("");
                    temail.setText("");
                    
                    empidBox.getEditor().clear();
                    empidBox.getSelectionModel().clearSelection();

                    empidBox.getItems().remove(id);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete Failed!").show();
                }
            }
        });

        back = new Button("Back");
        back.setOnAction(_ -> {
            ((Stage) back.getScene().getWindow()).close();

            try {
                new MainClass().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox btnbox = new HBox(50);
        btnbox.setAlignment(Pos.CENTER);
        btnbox.getChildren().addAll(dlt,back);

        GridPane gp = new GridPane();
        gp.setId("grid-pane");

        gp.add(empid, 0, 0);
        gp.add(empidBox, 1, 0);
        gp.add(name, 0, 1);
        gp.add(tname, 1, 1);
        gp.add(phone, 0, 2);
        gp.add(tphone, 1, 2);
        gp.add(email, 0, 3);
        gp.add(temail, 1, 3);
        gp.add(btnbox, 0, 5, 2, 1);

        Image img = new Image(getClass().getResource("/photos/delete.png").toExternalForm());
        ImageView imgview = new ImageView(img);
        imgview.setFitWidth(200);
        imgview.setFitHeight(200);
        imgview.setTranslateY(90);
        imgview.setTranslateX(180);

        root.getChildren().addAll(gp, imgview);

        Scene scene = new Scene(root, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/css/removemp.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Remove Employee");
        stage.show();

    }
    
    public static void main(String [] args) {
        launch(args);
    }


}
