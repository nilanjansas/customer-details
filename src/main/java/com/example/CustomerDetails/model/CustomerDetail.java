package com.example.CustomerDetails.model;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="CustomerDetails")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotBlank(message = "First Name is mandatory")
    @Size(min = 1, max = 36, message = "First Name must be between 1 and 36 characters")
    private String firstName;

    @Column
    @NotBlank(message = "Last Name is mandatory")
    @Size(min = 1, max = 36, message = "Last Name must be between 1 and 36 characters")
    private String lastName;
    
    @Column
    private LocalDate dob;

}
