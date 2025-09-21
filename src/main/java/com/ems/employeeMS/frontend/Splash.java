package com.ems.employeeMS.frontend;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;

import java.net.Socket;

import com.ems.employeeMS.backend.BackendApplication;


public class Splash extends Application {
	
    public void start(Stage primaryStage) {

        Image img = new Image(getClass().getResourceAsStream("/photos/front.gif"));
        ImageView imgview = new ImageView(img);
        imgview.setFitWidth(1170);
        imgview.setFitHeight(650);

        StackPane root = new StackPane();
        root.getChildren().add(imgview);

        Scene scene = new Scene(root, 1170, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Splash Screen");
        primaryStage.show();

        long splashStart = System.currentTimeMillis();
        int minSplashTime = 3000; // 3 sec minimum
        
     // Backend thread
        new Thread(() -> {
            try {
               
                try (Socket s = new Socket("localhost", 8080)) {
                   
                } catch (Exception ex) {
                    
                    BackendApplication.main(new String[] {});
                }

                // Wait until backend is ready
                boolean backendReady = false;
                while (!backendReady) {
                    try (Socket s = new Socket("localhost", 8080)) {
                        backendReady = true;
                    } catch (Exception e) {
                        Thread.sleep(500);
                    }
                }

                // Ensure minimum splash time
                long elapsed = System.currentTimeMillis() - splashStart;
                long remaining = minSplashTime - elapsed;
                if (remaining > 0) Thread.sleep(remaining);

                // Launch frontend
                Platform.runLater(() -> {
                    primaryStage.close();
                    try {
                        new Login().start(new Stage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public static void main (String [] args) {
        launch(args);
    }
    
}
