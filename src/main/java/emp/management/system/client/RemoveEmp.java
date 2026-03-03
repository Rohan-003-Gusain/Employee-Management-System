package emp.management.system.client;

import emp.management.system.base.AnimationFX;
import emp.management.system.server.entity.AddEmpEntity;
import emp.management.system.server.service.EmployeeService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
public class RemoveEmp {
	
	private EmployeeService employeeService;
	
	public RemoveEmp() {
		employeeService = 
				Splash.getContext()
						.getBean(EmployeeService.class);
	}

    public void start(Stage stage) {

        Label empid;
		Label name, phone, email, tname, tphone, temail;
        ComboBox<String> empidBox;
        Button delete, back;

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

        new Thread(() -> {
        		var empIds = employeeService.getAllEmployeeIds();
        		Platform.runLater(() ->
                empidBox.setItems(
                    FXCollections.observableArrayList(empIds)
                )
            );
        }).start();
        
        empidBox.setEditable(false);
        
        empidBox.setOnAction(e -> {
        	
            String selectedId = empidBox.getValue();
            if (selectedId == null) return;
            
            new Thread(() -> {
            	
            	 	AddEmpEntity emp = employeeService.getEmployeeById(selectedId);
                 
                 Platform.runLater(() -> {
                 	
                 	if (emp != null) {
                         tname.setText(emp.getName());
                         tphone.setText(emp.getPhoneno());
                         temail.setText(emp.getEmail());
                         
                     } else {
                         tname.setText("");
                         tphone.setText("");
                         temail.setText("");

                     }
                 });
            }).start();
            
        });
        
        delete = new Button("Delete");
        
        delete.setOnAction(e -> {
        	
            String id = empidBox.getValue();
            
            if(id == null){
                showAlert(
                  "Please select Id first");
                return;
            }
            
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete?",
                    ButtonType.YES, ButtonType.NO);

            if(confirm.showAndWait().get() != ButtonType.YES)
                return;
            
            delete.setDisable(true);
            
            new Thread(() -> {
            	
            		boolean remove = 
            				employeeService.removeEmployeeById(id);
            		
            		Platform.runLater(() -> {
            			if (remove) {
            				showAlert("Employee deleted Successfully");
            				
            				clearFields(empidBox, tname, tphone, temail);
            				
            				refreshEmployeeIds(empidBox);
                                
            			} else {
            				showAlert("Remove Failed!");
            			}
            			
            			delete.setDisable(false);
            		});
            		
            }).start();

        });

        back = new Button("Back");
        back.setOnAction(e -> {
        	
            Stage current = (Stage) back.getScene().getWindow();

            AnimationFX.fadeOut(current, () -> {
            		try {
                    new MainClass().start(current);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        HBox btnbox = new HBox(50);
        btnbox.setAlignment(Pos.CENTER);
        btnbox.getChildren().addAll(delete, back);

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
        
        ImageView imgview = new ImageView();
        
        try {
        	
        		var url = getClass().getResource("/photos/delete.png");
            
        		if (url == null)
        			throw new RuntimeException("Image not found");
            
        		imgview.setImage(new Image(url.toExternalForm()));
			
		} catch (Exception e) {
			System.out.println("Image load failed");
		}

        imgview.setFitWidth(200);
        imgview.setFitHeight(200);
        imgview.setTranslateY(90);
        imgview.setTranslateX(180);

        root.getChildren().addAll(gp, imgview);

        Scene scene = new Scene(root, 800, 400);
        
        var css = getClass().getResource("/css/removeemp.css");

        if(css != null)
            scene.getStylesheets().add(css.toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Remove Employee");
        
        AnimationFX.fadeIn(stage);

    }
    
    private void clearFields(
            ComboBox<String> empidBox,
            Label tname,
            Label tphone,
            Label temail) {

        tname.setText("");
        tphone.setText("");
        temail.setText("");

        empidBox.setValue(null);
        empidBox.getSelectionModel().clearSelection();
    }
    
    private void refreshEmployeeIds(ComboBox<String> empidBox) {

        new Thread(() -> {

            var empIds = employeeService.getAllEmployeeIds();

            Platform.runLater(() ->
                empidBox.setItems(
                    FXCollections.observableArrayList(empIds)
                )
            );

        }).start();
    }
    
    private void showAlert(String msg) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.showAndWait();
	}

}
