package spring_microservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import spring_microservice.dto.validators.CreatePatientValidationGroup;

import java.io.Serializable;

@Setter
@Getter
public class PatientRequestDto implements Serializable {
    @NotBlank(message = "Name is required")
    @Size(max = 50,message = "Name should not exceed 50 characters")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "birth date is required")
    private String dateOfBirth;
    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered date is required")
    private String registeredDate;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(String dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getRegisteredDate() {
//        return registeredDate;
//    }
//
//    public void setRegisteredDate(String registeredDate) {
//        this.registeredDate = registeredDate;
//    }


}
