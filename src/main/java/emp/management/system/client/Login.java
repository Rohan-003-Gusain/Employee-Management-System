package emp.management.system.client;

import emp.management.system.base.AnimationFX;
import emp.management.system.server.entity.AdminLoginEntity;
import emp.management.system.server.service.AdminLoginService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login{
		
	public Login() {
		adminLoginService = 
				Splash.getContext()
					.getBean(AdminLoginService.class);
	}
	
	private AdminLoginService adminLoginService;

    public void start(Stage stage) {

        Label userlbl, passlbl, status;
        TextField userfield;
        PasswordField password;
        Button login, exit;

        HBox root = new HBox();

        userlbl = new Label("Username");
        userfield = new TextField();
        userfield.setPromptText("Enter Username");

        HBox hb1 = new HBox(30, userlbl, userfield);

        passlbl = new Label("Password");
        password = new PasswordField();
        password.setPromptText("Enter Password");

        HBox hb2 = new HBox(30, passlbl, password);

        status = new Label();

        login = new Button("Login");

        login.setOnAction(e -> {
        	
        		login.setDisable(true);
        		
        		String username = userfield.getText();
            String pass = password.getText();
            new Thread(() -> {
            		AdminLoginEntity admin =
            			adminLoginService.login(username, pass);

                javafx.application.Platform.runLater(() -> {
                		
                	if (admin != null) {

                        Stage current =
                                (Stage) login.getScene().getWindow();

                        AnimationFX.fadeOut(current, () -> {
                            try {
                                new MainClass().start(new Stage());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });

                    } else {
                        status.setText("Invalid Credentials");
                        login.setDisable(false);
                    }
                });
                
            }).start(); 
            
        });

        exit = new Button("Exit");
        
        exit.setOnAction(e -> {

            Stage current =
                    (Stage) exit.getScene().getWindow();

            AnimationFX.fadeOut(current, () -> {
                current.close();
            });
        });

        VBox vb = new VBox(20, login, exit);
        vb.setId("pos");

        VBox loginbox =
                new VBox(20, hb1, hb2, vb, status);
        loginbox.setId("content");

        Image img = null;
        try (var stream = getClass().getResourceAsStream("/photos/second.jpg")) {
        		
        		if( stream == null)
				throw new RuntimeException("Image not found");
			
			img = new Image(stream);

		} catch (Exception e) {
			System.out.println("Error loading image" + e.getMessage());
		}

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);
        imgView.setTranslateX(50);
        imgView.setTranslateY(40);

        root.getChildren().addAll(loginbox, imgView);

        Scene scene = new Scene(root, 600, 300);
        
        var css = getClass().getResource("/css/login.css");

        if(css != null)
            scene.getStylesheets().add(css.toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.centerOnScreen();

        AnimationFX.fadeIn(stage);
    }
}