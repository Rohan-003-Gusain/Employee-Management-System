package emp.management.system.client;

import emp.management.system.base.AnimationFX;
import emp.management.system.server.entity.AddEmpEntity;
import emp.management.system.server.service.EmployeeService;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddEmp {
	
	private EmployeeService employeeService;
	
	public AddEmp() {
		employeeService = 
    			Splash.getContext()
    				.getBean(EmployeeService.class);
	}

    private int generateEmpId() {
        return (int)(Math.random() * 90000) + 10000;
    }
    
    private void refreshEmpId() {
        number = generateEmpId();
        tempid.setText(String.valueOf(number));
    }

    int number = generateEmpId();

    TextField tname, tfname, tphone, temail,
              taadhar, taddress,
              tdesignation, tsalary;

    Label tempid;

    public void start(Stage primaryStage) {

        GridPane root = new GridPane();
        root.setId("mainGrid");
        root.setAlignment(Pos.TOP_CENTER);
        root.setVgap(25);
        root.setHgap(20);

        Label heading = new Label("Fill Employee Details");
        heading.setId("top-heading");
        GridPane.setHalignment(heading, HPos.CENTER);

        tname = new TextField();
        tfname = new TextField();
        tphone = new TextField();
        temail = new TextField();
        taadhar = new TextField();
        taddress = new TextField();
        tdesignation = new TextField();
        tsalary = new TextField();

        DatePicker dobPicker = new DatePicker();
        dobPicker.setEditable(false);

        ComboBox<String> eduBox = new ComboBox<>();
        eduBox.getItems().addAll(
                "High School","Intermediate","Diploma",
                "B.A","B.Sc","B.Com","BCA",
                "B.Tech","MCA","M.Tech","PhD");

        Label empid = new Label("Employee ID:");
        tempid = new Label(String.valueOf(number));
        tempid.setId("tempid-css");

        Button add = new Button("Add Details");
        add.getStyleClass().add("my-button");
        
        Button back = new Button("Back");
        back.getStyleClass().add("my-button");

        add.setOnAction(e -> {
        	
        		if (!validateForm(dobPicker)) {
        			showAlert( 
        					"Please fill all requried fields");
        			return;
        		}
        		
        		add.setDisable(true);
        		
        		new Thread(() -> {
        			try {
						AddEmpEntity emp = 
								buildEmployee(dobPicker, eduBox);
						
						employeeService.addEmployee(emp);
						
						Platform.runLater(() -> {
							showAlert( 
									"Employee Added Successfuly");
							clearForm(eduBox, dobPicker);
							refreshEmpId();
							add.setDisable(false);
						});
						
					} catch (Exception ex) {
						Platform.runLater(() -> 
							showAlert( 
									"Error Saving Employee"));
						add.setDisable(false);
					}
        		}).start();
        });

        // ================= BACK =================
        back.setOnAction(e -> {
        		Stage current =
                    (Stage) back.getScene().getWindow();
        		
        		AnimationFX.fadeOut(current, () -> {
                try {
                    new MainClass().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        // ================= UI =================

        root.add(heading,0,0,4,1);

        root.add(new Label("Name"),0,1);
        root.add(tname,1,1);
        root.add(new Label("Father Name"),2,1);
        root.add(tfname,3,1);

        root.add(new Label("Phone"),0,2);
        root.add(tphone,1,2);
        root.add(new Label("Email"),2,2);
        root.add(temail,3,2);

        root.add(new Label("DOB"),0,3);
        root.add(dobPicker,1,3);
        root.add(new Label("Aadhar"),2,3);
        root.add(taadhar,3,3);

        root.add(new Label("Address"),0,4);
        root.add(taddress,1,4);
        root.add(new Label("Education"),2,4);
        root.add(eduBox,3,4);

        root.add(new Label("Designation"),0,5);
        root.add(tdesignation,1,5);
        root.add(new Label("Salary"),2,5);
        root.add(tsalary,3,5);

        root.add(empid,0,6);
        root.add(tempid,1,6);

        HBox btnBox = new HBox(100, add, back);
        btnBox.setAlignment(Pos.CENTER);

        root.add(btnBox,0,9,4,1);

        Scene scene = new Scene(root,900,650);
        var css = getClass().getResource("/css/addemp.css");

        if(css != null)
            scene.getStylesheets().add(css.toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Employee");
        
        AnimationFX.fadeIn(primaryStage);
    }
    
    private AddEmpEntity buildEmployee(
	    		DatePicker dobPicker, 
	    		ComboBox<String> eduBox) {
    	
    		AddEmpEntity emp = new AddEmpEntity();

        emp.setEmpId(String.valueOf(number));
        emp.setName(tname.getText());
        emp.setFathername(tfname.getText());
        emp.setPhoneno(tphone.getText());
        emp.setEmail(temail.getText());
        emp.setDob(dobPicker.getValue());
        emp.setAadhar(taadhar.getText());
        emp.setAddress(taddress.getText());
        emp.setHighestEdu(eduBox.getValue());
        emp.setDesignation(tdesignation.getText());
        
        try {
            emp.setSalary(
                Double.parseDouble(tsalary.getText()));
        } catch (Exception e) {
            throw new RuntimeException("Invalid Salary");
        }
        
        return emp;
    	
    }
    
    private boolean validateForm(DatePicker dobPicker) {
    		return !tname.getText().isEmpty()
                    && !tphone.getText().isEmpty()
                    && !tsalary.getText().isEmpty()
                    && dobPicker.getValue() != null;
    }

    private void showAlert(String msg) {
        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void clearForm(
            ComboBox<String> eduBox,
            DatePicker dobPicker) {

        tname.clear();
        tfname.clear();
        tphone.clear();
        temail.clear();
        taadhar.clear();
        taddress.clear();
        tdesignation.clear();
        tsalary.clear();

        eduBox.setValue(null);
        dobPicker.setValue(null);

    }

}