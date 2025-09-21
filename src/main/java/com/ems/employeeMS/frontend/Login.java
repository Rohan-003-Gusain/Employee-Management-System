package com.ems.employeeMS.frontend;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;

public class Login extends Application {

	Label status;
	Label userlbl, passlbl;
    TextField userfield;
    PasswordField passwordf;
    Button login, back;
	
    public void start(Stage stage) {

        HBox root = new HBox();
        
        userlbl = new Label("Username");
        userfield = new TextField();
        userfield.setPromptText("Enter Username");

        HBox hb1 = new HBox(30);
        hb1.getChildren().addAll(userlbl, userfield);

        passlbl = new Label("Password");
        passwordf = new PasswordField();
        passwordf.setPromptText("Enter Password");

        HBox hb2 = new HBox(30);
        hb2.getChildren().addAll(passlbl, passwordf);

        status = new Label();

        login = new Button("Login");
        back = new Button("Back");

        VBox vb = new VBox(20);
        vb.setId("pos");
        vb.getChildren().addAll(login, back);

        VBox loginbox = new VBox(20);
        loginbox.setId("content");
        loginbox.getChildren().addAll(hb1, hb2, vb, status);

        Image img = new Image(getClass().getResource("/photos/second.jpg").toExternalForm());
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);
        imgView.setTranslateX(50);
        imgView.setTranslateY(40);

        root.getChildren().addAll(loginbox, imgView);

        Scene scene = new Scene(root,600, 300);
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.show();
        
        login.setOnAction(e -> {
        	String username = userfield.getText();
        	String password = passwordf.getText();
        	
        	if(username.isEmpty() || password.isEmpty()) {
        		status.setText("Username and Password can not be empty");
        		return ;
        	}
        	
        	loginUser(username, password);
        });
    }
    
    private void loginUser(String username, String password) {
    	new Thread(() -> {
    		try {
    			HttpClient client = HttpClient.newHttpClient();
    			String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
				
    			HttpRequest request = HttpRequest.newBuilder()
    					.uri(URI.create("http://localhost:8080/api/admin/login"))
    					.header("Content-Type", "application/json")
    					.POST(BodyPublishers.ofString(json))
    					.build();
    			
    			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    			
    			Platform.runLater(() -> {
    				if(response.body() != null && !response.body().isEmpty() && response.body().contains(username)) {
    					status.setText("Login Successful");
    					try {
    						((Stage) login.getScene().getWindow()).close();
							new MainClass().start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
    				} else {
    					status.setText("Login Failed");
    				}
    			});
    			
			} catch (Exception e) {
				Platform.runLater(() -> status.setText("Error : " + e.getMessage()));
			}
    	}).start();
    }
    
    public static void main (String [] args) {
        launch(args);
    }

}
