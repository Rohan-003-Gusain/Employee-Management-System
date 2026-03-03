package emp.management.system.client;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import emp.management.system.base.AnimationFX;
import emp.management.system.server.BackendApplication;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Splash extends Application {
	
	private static ConfigurableApplicationContext context;
	
	@Override
	public void init() {
		context = 
				SpringApplication.run(BackendApplication.class);
	}
	
	public static ConfigurableApplicationContext getContext() {
		return context;
	}
	
	@Override
    public void start(Stage primaryStage) {

    		Image img = null;
        try (var stream = getClass().getResourceAsStream("/photos/front.gif")) {
			
        		if( stream == null)
				throw new RuntimeException("Image not found");
			
			img = new Image(stream);

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
    		
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(1170);
        imgView.setPreserveRatio(true);
        imgView.setSmooth(true);

        StackPane root = new StackPane();
        root.getChildren().add(imgView);

        Scene scene = new Scene(root, 1170, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Splash Screen - Employee Management System");
        AnimationFX.fadeIn(primaryStage);

        PauseTransition delay  = 
        			new PauseTransition(Duration.seconds(3));
        
        delay.setOnFinished(e -> {
            
        		AnimationFX.fadeOut(primaryStage, () -> {
	            try {
	                	new Login().start(new Stage());
	            } catch (Exception ex) {
	            		ex.printStackTrace();
	            }
	            	
        		});
        });
        		
        delay.play();

    }
        
    public static void main (String [] args) {
        launch(args);
    }
    
}
