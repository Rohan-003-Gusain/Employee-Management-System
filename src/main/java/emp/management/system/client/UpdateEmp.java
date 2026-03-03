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
public class UpdateEmp {
	
	private EmployeeService employeeService;
	
	public UpdateEmp() {
		employeeService = 
				Splash.getContext()
					.getBean(EmployeeService.class);
	}

    public static String selectedEmpId;
    
    private AddEmpEntity currentEmp;

    Label heading, name, fname, phone, email, dob, heduction, aadhar, address, designation, salary, empid, tempid;
    TextField tname, tfname, tphone, temail, taadhar, taddress, tdesignation, tsalary;
    Button update, back;
    
    DatePicker dobPicker;
    ComboBox<String> eduBox;

	public void start(Stage stage) {

		GridPane root = createLayout();
		
		loadEmployeeData();
		
		setActions(stage);
		
		Scene scene = new Scene(root, 900, 700);
		
		var css = getClass().getResource(
				"/css/updateemp.css");
        
		if (css != null) {
			scene.getStylesheets().add(css.toExternalForm());
		}
		
		stage.setScene(scene);
		stage.setTitle("Update Employee");
		
		AnimationFX.fadeIn(stage);	   
	}
	
	private GridPane createLayout() {
		
		GridPane root = new GridPane();
	    root.setAlignment(Pos.TOP_CENTER);
	    root.setVgap(30);
	    root.setHgap(20);
	    root.setId("mainGrid");
	
	    heading = new Label("Update Employee Details");
        heading.setId("top-heading");
        GridPane.setHalignment(heading, HPos.CENTER);

        name = new Label("Name");
        tname = new TextField();
        tname.setEditable(false);
        tname.getStyleClass().add("readonly-field");

        fname = new Label("Father Name");
        tfname = new TextField();
        tfname.setEditable(false);
        tfname.getStyleClass().add("readonly-field");

        phone = new Label("Phone");
        tphone = new TextField();

        email = new Label("Email");
        temail = new TextField();

        dob = new Label("DOB");
        dobPicker = new DatePicker();
        dobPicker.setEditable(false);
        dobPicker.getStyleClass().add("readonly-field");

        aadhar = new Label("Aadhar");
        taadhar = new TextField();
        taadhar.setEditable(false);
        taadhar.getStyleClass().add("readonly-field");

        address = new Label("Address");
        taddress = new TextField();

        heduction = new Label("Education");
	    
	    eduBox  = new ComboBox<>();
	    eduBox.getItems().addAll(
	    		"High School","Intermediate", "Diploma", 
	    		"B.A", "B.Sc", "B.Com", "BCA", 
	    		"B.Tech", "MCA", "M.Tech", "PhD");
	    
	    designation = new Label("Designation");
	    tdesignation = new TextField();

	    salary = new Label("Salary");
	    tsalary = new TextField();

	    empid = new Label("Employee ID");
	    tempid = new Label();
	    
	    update = new Button("Update Details");
	    back = new Button("Back");
	    
	    update.getStyleClass().add("my-button");
	    back.getStyleClass().add("my-button");
	    
	    root.add(heading,0,0,4,1);
	    
	    root.add(name,0,1); root.add(tname,1,1);
        root.add(fname,2,1); root.add(tfname,3,1);

        root.add(phone,0,2); root.add(tphone,1,2);
        root.add(email,2,2); root.add(temail,3,2);

        root.add(dob,0,3); root.add(dobPicker,1,3);
        root.add(aadhar,2,3); root.add(taadhar,3,3);

        root.add(address,0,4); root.add(taddress,1,4);
        root.add(heduction,2,4); root.add(eduBox,3,4);

        root.add(designation,0,5); root.add(tdesignation,1,5);
        root.add(salary,2,5); root.add(tsalary,3,5);

        root.add(empid,0,6);
        root.add(tempid,1,6);

        HBox btnBox = new HBox(100, update, back);
	    btnBox.setAlignment(Pos.CENTER);
        root.add(btnBox,0,9,4,1);

        return root;
	}
	
	private void loadEmployeeData() {
		
	    	currentEmp = 
	    			employeeService.getEmployeeById(selectedEmpId);
	    
	    if (currentEmp == null) {
	    		showAlert("Employee not found");
	    		return;
	    }
	    
	    tname.setText(currentEmp.getName());
	    tfname.setText(currentEmp.getFathername());
	    tphone.setText(currentEmp.getPhoneno());
	    temail.setText(currentEmp.getEmail());
	    taadhar.setText(currentEmp.getAadhar());
	    taddress.setText(currentEmp.getAddress());
	    eduBox.setValue(currentEmp.getHighestEdu());
	    tdesignation.setText(currentEmp.getDesignation());
	    tsalary.setText(
	    		String.valueOf(currentEmp.getSalary()));
	    dobPicker.setValue(currentEmp.getDob());
	    
	    tempid.setText(currentEmp.getEmpId());
	}
    
	private void setActions(Stage stage) {
		update.setOnAction(e -> updateEmployee(stage));
		back.setOnAction(e -> goBack(stage));
	}
	
    private void updateEmployee(Stage stage) {
    	
    		update.setDisable(true);
    		
    		new Thread(() -> {
    			
    			try {
    				currentEmp.setEmail(temail.getText());
    				currentEmp.setDob(dobPicker.getValue());
    				currentEmp.setAddress(taddress.getText());
    				currentEmp.setHighestEdu(eduBox.getValue());
    				currentEmp.setDesignation(tdesignation.getText());
    				currentEmp.setSalary(
    	                Double.parseDouble(tsalary.getText())
    	            );
    	            
    	            employeeService.updateEmployeeById(currentEmp);
    	            
    	            Platform.runLater(() -> {
    	            	
    	            		showAlert("Employee Updated Successfully");
    	                
    	                AnimationFX.fadeOut(stage, () -> {
    	                		try {
    	                        new ViewEmp().start(stage);
    	                    } catch (Exception ex) {
    	                        ex.printStackTrace();
    	                    }
    	                });
    	            });
    	            
    	        } catch (Exception ex) {
    	            
    	        		Platform.runLater(() -> {
    	        			showAlert("Update Failed");
    	        			update.setDisable(false);
    	        		});
    	        }
    		}).start();        
    }
    
    private void goBack(Stage stage) {
        
        AnimationFX.fadeOut(stage, () -> {
        		try {
                new ViewEmp().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    private void showAlert(String msg) {
		Alert alert = 
				new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.showAndWait();
    }

}
