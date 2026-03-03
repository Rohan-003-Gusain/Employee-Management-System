package emp.management.system.client;

import emp.management.system.base.AnimationFX;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainClass {

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
        add.setOnAction(e -> {
        	
        	Stage current =
                    (Stage) add.getScene().getWindow();
        		
        		AnimationFX.fadeOut(current, () -> {
                try {
                    new AddEmp().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        	});
        root.getChildren().add(add);

        view = new Button("View Employee");
        view.setLayoutX(565);
        view.setLayoutY(270);
        view.setOnAction(e -> {
        		
        		Stage current =
                    (Stage) view.getScene().getWindow();
        		
        		AnimationFX.fadeOut(current, () -> {
                try {
                    new ViewEmp().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        root.getChildren().add(view);

        remove = new Button("Remove Employee");
        remove.setLayoutX(450);
        remove.setLayoutY(350);
        remove.setOnAction(e -> {
        	
        		Stage current =
                    (Stage) remove.getScene().getWindow();
        		
        		AnimationFX.fadeOut(current, () -> {
                try {
                    new RemoveEmp().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        root.getChildren().add(remove);

        Scene scene = new Scene(root, 1080, 650);
        scene.getStylesheets().add(getClass().getResource("/css/mainclass.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Page");

        AnimationFX.fadeIn(primaryStage);
    }

 
}
