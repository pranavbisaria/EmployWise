package com.employwise.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number must be 10 digit long")
    @Pattern(regexp="(^[6-9]\\d{9}$)", message = "Invalid phone number, should starts with 6-9")
    private String phoneNumber;
    @Email(message = "Invalid Email")
    private String email;
    private String managerId;
    private String profileImage;
}
