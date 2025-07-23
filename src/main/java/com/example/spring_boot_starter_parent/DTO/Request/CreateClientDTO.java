package com.example.spring_boot_starter_parent.DTO.Request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record CreateClientDTO(

        @NotBlank(message = "First name is required")
        @Size(min = 1, max = 30, message = "First name must be between 1 and 30 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 1, max = 30, message = "Last name must be between 1 and 30 characters")
        String lastName,

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email,

        @Size(min = 9, max = 15, message = "Phone number should be between 9 and 15 characters")
        String phone,

        @Past(message = "Date of birth must be in past")
        String dateOfBirth,

        @Size(max = 255, message = "Address cannot exceed 255 characters")
        String address
) {
    public CreateClientDTO {

        if(email != null ){
            email = email.toLowerCase().trim();
        }

        if(firstName != null ){
            firstName = firstName.toLowerCase().trim();
        }

        if(lastName != null ){
            lastName = lastName.toLowerCase().trim();
        }

        if(phone != null ){
            phone = phone.toLowerCase().trim();
        }

        if(address != null ){
            address = address.toLowerCase().trim();
        }
    }

    public static CreateClientDTO of (String firstName, String lastName, String email) {
        return new CreateClientDTO(firstName, lastName, email, null, null, null);
    }
}
