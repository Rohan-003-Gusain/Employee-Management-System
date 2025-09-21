package com.ems.employeeMS.frontend;



import com.ems.employeeMS.backend.entity.AddEmpEntity;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewEmp extends Application {
	
	private ObservableList<String> getEmployeeIdsFromAPI() {
	    try {
	        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
	        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
	                .uri(new java.net.URI("http://localhost:8080/api/employees/ids"))
	                .GET()
	                .build();

	        java.net.http.HttpResponse<String> response = client.send(request,
	                java.net.http.HttpResponse.BodyHandlers.ofString());

	        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
	        java.util.List<String> ids = mapper.readValue(response.body(),
	                new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>() {});

	        return FXCollections.observableArrayList(ids);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return FXCollections.observableArrayList();
	    }
	}

	
	private ObservableList<AddEmpEntity> getEmployeeFromAPI() {
        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(new java.net.URI("http://localhost:8080/api/employees/all"))
                    .GET()
                    .build();

            java.net.http.HttpResponse<String> response = client.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());

            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            java.util.List<AddEmpEntity> empList = mapper.readValue(response.body(), 
            		new com.fasterxml.jackson.core.type.TypeReference<java.util.List<AddEmpEntity>>() {});

            return FXCollections.observableArrayList(empList);

        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    ComboBox<String> empIdBox;
    TableView<AddEmpEntity> table;
    Label searcLabel;
    Button search, print, update, back;

    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        
        VBox topbox = new VBox(20);
        topbox.setPadding(new Insets(0, 0, 20, 0));
        
        searcLabel = new Label("Search by Employee Id");
        empIdBox = new ComboBox<>();
        empIdBox.setEditable(true);

        HBox searchrow = new HBox(20);
        searchrow.getChildren().addAll(searcLabel, empIdBox);
        searchrow.setAlignment(Pos.CENTER_LEFT);

        search  = new Button("Search");

        print = new Button("Print");
        print.setOnAction(_ -> {
            if (table.getItems().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "No data to print.").showAndWait();
                return;
            }

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(table.getScene().getWindow())) {
                boolean success = job.printPage(table);
                if (success) {
                    job.endJob();
                    new Alert(Alert.AlertType.INFORMATION, "Printing completed successfully.").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Printing failed.").showAndWait();
                }
            }
        });

        update = new Button("Update");
        update.setOnAction(_ -> {
            
            AddEmpEntity selectedEmp = table.getSelectionModel().getSelectedItem();
            if (selectedEmp == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an employee first.");
                alert.showAndWait();
                return;
            }

            UpdateEmp.selectedEmpId = selectedEmp.getEmpId();
            ((Stage) back.getScene().getWindow()).close();
            try {
                new UpdateEmp().start(new Stage());;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        back = new Button("Back");
        back.setOnAction(_ -> {
            ((Stage) back.getScene().getWindow()).close();

            try {
                new MainClass().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox btnBox = new HBox(30);
        btnBox.getChildren().addAll(search, print, update, back);
        btnBox.setAlignment(Pos.CENTER_LEFT);

        topbox.getChildren().addAll(searchrow, btnBox);
        root.setTop(topbox);

        table = new TableView<>();

        TableColumn<AddEmpEntity, String> idcol = new TableColumn<>("EmpID");
        idcol.setCellValueFactory(new PropertyValueFactory<>("empId"));

        TableColumn<AddEmpEntity, String> namecol = new TableColumn<>("Name");
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<AddEmpEntity, String> fnamecol = new TableColumn<>("Father Name");
        fnamecol.setCellValueFactory(new PropertyValueFactory<>("fathername"));

        TableColumn<AddEmpEntity, String> phonecol = new TableColumn<>("Phone No");
        phonecol.setCellValueFactory(new PropertyValueFactory<>("phoneno"));

        TableColumn<AddEmpEntity, String> emailcol = new TableColumn<>("Email");
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<AddEmpEntity, String> dobcol = new TableColumn<>("Dob");
        dobcol.setCellValueFactory(new PropertyValueFactory<>("dob"));

        TableColumn<AddEmpEntity, String> aadharcol = new TableColumn<>("Aadhar");
        aadharcol.setCellValueFactory(new PropertyValueFactory<>("aadhar"));

        TableColumn<AddEmpEntity, String> addresscol = new TableColumn<>("Address");
        addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<AddEmpEntity, String> heducol = new TableColumn<>("Highest Education");
        heducol.setCellValueFactory(new PropertyValueFactory<>("highestEdu"));

        TableColumn<AddEmpEntity, String> desigcol = new TableColumn<>("Designation");
        desigcol.setCellValueFactory(new PropertyValueFactory<>("designation"));

        TableColumn<AddEmpEntity, String> salcol = new TableColumn<>("Salary");
        salcol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        table.getColumns().addAll(
        java.util.Arrays.asList(idcol, namecol, fnamecol, phonecol, emailcol, dobcol, aadharcol, addresscol, heducol, desigcol, salcol )
        );

        table.setPlaceholder(new Label("No Data Avilable"));
        root.setCenter(table);
        
        ObservableList<AddEmpEntity> employees = getEmployeeFromAPI();
        table.setItems(employees);
        
        ObservableList<String> empIds = getEmployeeIdsFromAPI();
        empIdBox.setItems(empIds);
        empIdBox.setEditable(true);

        search.setOnAction(_ -> {
            String newVal = empIdBox.getValue();
            if (newVal == null || newVal.isEmpty())
            return;

            boolean found = false;
            for (AddEmpEntity emp : table.getItems()) {
                if (emp.getEmpId().equals(newVal)) {
                    table.getSelectionModel().select(emp);
                    table.scrollTo(emp);
                    found = true;
                    break;
                }
            } 
            if (!found) {
                table.getSelectionModel().clearSelection();
                empIdBox.getEditor().clear();
            }
        });

        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/css/viewemp.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("View Employee");
        stage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
