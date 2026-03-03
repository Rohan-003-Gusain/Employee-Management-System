package emp.management.system.server.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity 
@Table(name = "employee_details")
@Data
@NoArgsConstructor

public class AddEmpEntity {

    @Id
    private String empId;
    private String name;
    private String fathername;
    private String phoneno;
    private String email;
    private LocalDate dob;
    private String aadhar;
    private String address;
    private String highestEdu;
    private String designation;
    private Double salary;

}
