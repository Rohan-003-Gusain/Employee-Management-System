package emp.management.system.client;

import java.util.List;

import emp.management.system.base.AnimationFX;
import emp.management.system.server.entity.AddEmpEntity;
import emp.management.system.server.service.EmployeeService;
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

public class ViewEmp {
	
	private EmployeeService employeeService;
	
	public ViewEmp() {

	    employeeService =
	            Splash.getContext()
	                    .getBean(EmployeeService.class);
	}

    private ComboBox<String> empIdBox;
    private TableView<AddEmpEntity> table;
    
    Button search, print, update, back;

    public void start(Stage stage) {
    	
        BorderPane root = createLayout();
        
        loadData();
        
        setActions(stage);
        
        Scene scene = new Scene(root, 900, 600);
        
        var css = getClass().getResource(
                "/css/viewemp.css");
        
        if (css != null) 
        	scene.getStylesheets().add(css.toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("View Employee");
        
        AnimationFX.fadeIn(stage);
    }
    
    private BorderPane createLayout(){

        BorderPane root = new BorderPane();

        Label searchLabel =
                new Label("Search by Employee Id");

        empIdBox = new ComboBox<>();
        empIdBox.setEditable(true);

        HBox searchRow =
                new HBox(20,searchLabel,empIdBox);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        search = new Button("Search");
        print  = new Button("Print");
        update = new Button("Update");
        back   = new Button("Back");

        HBox btnBox =
                new HBox(30,search,print,update,back);
        btnBox.setAlignment(Pos.CENTER_LEFT);

        VBox top =
                new VBox(20,searchRow,btnBox);
        top.setPadding(new Insets(0,0,20,0));

        root.setTop(top);

        table = createTable();

        root.setCenter(table);

        return root;
    }
    
    private TableView<AddEmpEntity> createTable() {
    	
    		TableView<AddEmpEntity> table = new TableView<>();
    		
    		table.getColumns().addAll(
    				List.of(
		    			column("EmpID","empId"),
		    			column("Name","name"),
		    	        column("Father Name","fathername"),
		    	        column("Phone","phoneno"),
		    	        column("Email","email"),
		    	        column("Dob","dob"),
		    	        column("Aadhar","aadhar"),
		    	        column("Address","address"),
		    	        column("Education","highestEdu"),
		    	        column("Designation","designation"),
		    	        column("Salary","salary")
    				)
    		);
    		
    		table.setPlaceholder(new Label("No Data Available"));
    		
    		return table; 			
    }
    
    private TableColumn<AddEmpEntity, String> 
    column(String title, String property) {
    		
    		TableColumn<AddEmpEntity, String> col = 
    				new TableColumn<>(title);
    		
    		col.setCellValueFactory(
    				new PropertyValueFactory<>(property));
    		
    		return col;
    }
    
    private void loadData() {
    		ObservableList<String> empIds = FXCollections.observableArrayList(employeeService.getAllEmployeeIds());
        empIdBox.setItems(empIds);
        
        table.setItems(
        		FXCollections.observableArrayList
        		(employeeService.getAllEmployees()));
    }
    
    private void setActions(Stage stage) {
    		search.setOnAction(e -> searchEmployee());
    		
    		print.setOnAction(e -> printTable());
    		
    		update.setOnAction(e -> openUpdate(stage));

    		back.setOnAction(e ->
    			AnimationFX.fadeOut(stage,
    					() -> goBack(stage)));
    }
    
    private void searchEmployee(){

        String id = empIdBox.getValue();

        if(id==null || id.isEmpty()) return;

        for(AddEmpEntity emp : table.getItems()){

            if(emp.getEmpId().equals(id)){
                table.getSelectionModel()
                        .select(emp);
                table.scrollTo(emp);
                return;
            }
        }

        table.getSelectionModel().clearSelection();
        empIdBox.getEditor().clear();
    }
    
    private void openUpdate(Stage stage){

        AddEmpEntity emp =
                table.getSelectionModel()
                        .getSelectedItem();

        if(emp==null){
            showAlert(
              "Please select employee first");
            return;
        }

        UpdateEmp.selectedEmpId =
                emp.getEmpId();

        AnimationFX.fadeOut(stage,()->{
            try{
                new UpdateEmp().start(stage);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }
    
    private void printTable() {

        if (table.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "No data to print")
            			.showAndWait();
            return;
        }

        PrinterJob job =
                PrinterJob.createPrinterJob();
        
        if (job == null) {
            showAlert(
                "No Data found."
            );
            return;
        }

        if (job.printPage(table)) {
            job.endJob();
            showAlert("Printing completed successfully.");
        } else {
            showAlert("Printing failed.");
        }
        
    }
    
    private void goBack(Stage stage) {
        try {
            new MainClass().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showAlert(String msg) {
		Alert alert = 
				new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.showAndWait();
    }
    
    
}
