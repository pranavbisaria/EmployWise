package com.employwise.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String managerId;
    private String profileImage;
    private LocalDateTime createdAt;
}
