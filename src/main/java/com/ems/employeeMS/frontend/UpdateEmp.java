package com.ems.employeeMS.frontend;

import org.springframework.stereotype.Component;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@Component
public class UpdateEmp extends Application{

    public static String selectedEmpId;

    Label heading, name, fname, phone, email, dob, heduction, aadhar, address, designation, salary, empid, tempid;
    TextField tname, tfname, tphone, temail, tdob, taadhar, taddress, tdesignation, tsalary;
    Button add, back;
    
    private AddEmpEntity getEmployeeFromAPI(String empId) {
        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(new java.net.URI("http://localhost:8080/api/employees/get/" + empId))
                    .GET()
                    .build();

            java.net.http.HttpResponse<String> response = client.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());

            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            AddEmpEntity emp = mapper.readValue(response.body(), AddEmpEntity.class);

            return emp;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    private void updateEmployeeToAPI(AddEmpEntity emp) {
        try {
            String json = String.format(
                "{"
                + "\"empId\":\"%s\","
                + "\"email\":\"%s\","
                + "\"dob\":\"%s\","
                + "\"address\":\"%s\","
                + "\"highestEdu\":\"%s\","
                + "\"designation\":\"%s\","
                + "\"salary\":\"%s\""
                + "}",
                emp.getEmpId(),
                emp.getEmail(),
                emp.getDob(), 
                emp.getAddress(),
                emp.getHighestEdu(),
                emp.getDesignation(),
                emp.getSalary()
            );

            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(new java.net.URI("http://localhost:8080/api/employees/update"))
                    .header("Content-Type", "application/json")
                    .PUT(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                    .build();

            java.net.http.HttpResponse<String> response = client.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());

            System.out.println("Response: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void start(Stage primaryStage) {

        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_CENTER);
        root.setId("mainGrid");

        heading = new Label("Update Employee Details");
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
        tdob = new TextField();
        tdob.setEditable(false);
        tdob.getStyleClass().add("readonly-field");

        aadhar = new Label("Aadhar");
        taadhar = new TextField();
        taadhar.setEditable(false);
        taadhar.getStyleClass().add("readonly-field");
        
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
        tempid = new Label(" " + selectedEmpId);
        tempid.setId("tempid-css");

        
        AddEmpEntity emp = getEmployeeFromAPI(selectedEmpId);
        
        if (emp != null) {
            tname.setText(emp.getName());
            tfname.setText(emp.getFathername());
            tphone.setText(emp.getPhoneno());
            temail.setText(emp.getEmail());
            taadhar.setText(emp.getAadhar());
            taddress.setText(emp.getAddress());
            eduBox.setValue(emp.getHighestEdu());
            tdesignation.setText(emp.getDesignation());
            tsalary.setText(emp.getSalary());
            tdob.setText(emp.getDob());
        }

        add = new Button("Update Details");
        back = new Button("Back");

        add.setTranslateY(50);
        add.getStyleClass().add("my-button");
        back.setTranslateY(50);
        back.getStyleClass().add("my-button");

        add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

            	emp.setName(tname.getText());
            	emp.setFathername(tfname.getText());
                emp.setEmail(temail.getText());
                emp.setDob(tdob.getText());
                emp.setAddress(taddress.getText());
                emp.setHighestEdu(eduBox.getValue());
                emp.setDesignation(tdesignation.getText());
                emp.setSalary(tsalary.getText());
                
                updateEmployeeToAPI(emp);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Employee Updated");
                alert.setContentText("Employee ID: " + emp.getEmpId() + " has been updated.");
                alert.showAndWait();

                ((Stage) add.getScene().getWindow()).close();

                try {
                    new ViewEmp().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        back.setOnAction(e -> {
            ((Stage) back.getScene().getWindow()).close();
            try {
                new ViewEmp().start(new Stage());
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
        root.add(tdob, 1, 3);
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
        scene.getStylesheets().add(getClass().getResource("/css/updatemp.css").toExternalForm());
        primaryStage.setScene(scene);
        root.requestFocus();
        primaryStage.setTitle("Add Employee");
        primaryStage.show();
        
        System.out.println("Selected Employee ID: " + selectedEmpId);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
