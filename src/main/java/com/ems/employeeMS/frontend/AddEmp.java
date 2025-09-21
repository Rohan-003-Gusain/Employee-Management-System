package com.ems.employeeMS.frontend;

import com.ems.employeeMS.backend.entity.AddEmpEntity;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class AddEmp extends Application {

    private int generateEmpId() {
        return (int)(Math.random() * 90000) + 10000;
    }

    int number = generateEmpId();

    Label heading, name, fname, phone, email, dob, heduction, aadhar, address, designation, salary, empid, tempid;
    TextField tname, tfname, tphone, temail, taadhar, taddress, tdesignation, tsalary;
    Button add, back;

    private void saveEmployeeToAPI(AddEmpEntity emp) {
    	try {
			String json = String.format(
				"{"
					+ "\"empId\":\"%s\","
				    + "\"name\":\"%s\","
				    + "\"fathername\":\"%s\","
				    + "\"phoneno\":\"%s\","
				    + "\"email\":\"%s\","
				    + "\"dob\":\"%s\","
				    + "\"aadhar\":\"%s\","
				    + "\"address\":\"%s\","
				    + "\"highestEdu\":\"%s\","
				    + "\"designation\":\"%s\","
				    + "\"salary\":\"%s\""
				    + "}",
			emp.getEmpId(),
			emp.getName(),
			emp.getFathername(),
			emp.getPhoneno(),
			emp.getEmail(),
			emp.getDob(),
			emp.getAadhar(),
			emp.getAddress(),emp.
			getHighestEdu(),emp.
			getDesignation(),
			emp.getSalary()
			);
			
			java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(new java.net.URI("http://localhost:8080/api/employees/add"))
                    .header("Content-Type", "application/json")
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                    .build();
            
            java.net.http.HttpResponse<String> response = client.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());
			
            System.out.print("Response : " + response.body());
            
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    public void start(Stage primaryStage) {

        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_CENTER);
        root.setId("mainGrid");

        heading = new Label("Fill Employee Details");
        GridPane.setHalignment(heading, HPos.CENTER); 
        heading.setId("top-heading");

        name = new Label("Name");
        tname = new TextField();

        fname = new Label("Father Name");
        tfname = new TextField();

        phone = new Label("Phone No");
        tphone = new TextField();

        email = new Label("E-Mail");
        temail = new TextField();

        dob = new Label("Date of birth");
        DatePicker dobPicker = new DatePicker();
        dobPicker.setEditable(false);

        aadhar = new Label("Aadhar");
        taadhar = new TextField();
        
        address = new Label("Address");
        taddress = new TextField();

        heduction = new Label("Highest Education");
        ComboBox<String> eduBox  = new ComboBox<>();
        eduBox.getItems().addAll("High School",
        "Intermediate", "Diploma", "B.A", "B.Sc", "B.Com", "BCA", "B.Tech", "MCA", "M.Tech", "PhD");
        eduBox.setPromptText("Select");

        designation = new Label("Designation");
        tdesignation = new TextField();

        salary = new Label("Salary");
        tsalary  = new TextField();

        empid = new Label("Employee ID: ");
        number = generateEmpId();
        tempid = new Label(" " + number);
        tempid.setId("tempid-css");

        add = new Button("Add Details");
        back = new Button("Back");

        add.setTranslateY(50);
        add.getStyleClass().add("my-button");
        back.setTranslateY(50);
        back.getStyleClass().add("my-button");

        add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                AddEmpEntity aee = new AddEmpEntity();
                
                aee.setEmpId(String.valueOf(number));
                aee.setName(tname.getText());
                aee.setFathername(tfname.getText());
                aee.setPhoneno(tphone.getText());
                aee.setEmail(temail.getText());
                aee.setDob(String.valueOf(dobPicker.getValue()));
                aee.setAadhar(taadhar.getText());
                aee.setAddress(taddress.getText());
                aee.setHighestEdu(eduBox.getValue());
                aee.setDesignation(tdesignation.getText());
                aee.setSalary(tsalary.getText());
                
                saveEmployeeToAPI(aee);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Employee Added");
                alert.setContentText("Employee ID: " + aee.getEmpId() + " has been added.");
                alert.showAndWait();

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

                number = generateEmpId();
                tempid.setText(" " + number);

            }
        });

        back.setOnAction(e -> {
            ((Stage) back.getScene().getWindow()).close();

            try {
                new MainClass().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        root.setVgap(30);
        root.setHgap(20);

        HBox butnbox = new HBox(50);
        butnbox.getChildren().addAll(add, back);
        butnbox.setAlignment(Pos.CENTER);

        root.add(heading, 0, 0, 4, 1);

        root.add(name, 0, 1);
        root.add(tname, 1, 1);
        root.add(fname, 2, 1);
        root.add(tfname, 3, 1);

        root.add(phone, 0, 2);
        root.add(tphone, 1, 2);
        root.add(email, 2, 2);
        root.add(temail, 3, 2);

        root.add(dob, 0, 3);
        root.add(dobPicker, 1, 3);
        root.add(aadhar, 2, 3);
        root.add(taadhar, 3, 3);

        root.add(address, 0, 4);
        root.add(taddress, 1, 4);
        root.add(heduction, 2, 4);
        root.add(eduBox, 3, 4);

        root.add(designation, 0, 5);
        root.add(tdesignation, 1, 5);
        root.add(salary, 2, 5);
        root.add(tsalary, 3, 5);

        root.add(empid, 0, 6);
        root.add(tempid, 1, 6);

        root.add(butnbox, 0, 7, 4, 1);

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/css/addemp.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Employee");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
