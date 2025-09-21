package com.ems.employeeMS.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Adminlogin")
@Data
@NoArgsConstructor
public class AdminLoginEntity {
   
    @Id
    private String username;

    private String password;

}
