package com.employwise.model;

import com.employwise.payload.EmployResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Employ {
    @Id
    private String id = UUID.randomUUID().toString();
    @Field
    private String name;
    @Field
    private String phoneNumber;
    @Field
    private String email;
    @Field
    private String managerRelPath;
    @Field
    private String profileImage;
    @Field
    private LocalDateTime createdAt;

    public Employ(String name, String phoneNumber, String email, String managerRelPath, String profileImage) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.managerRelPath = managerRelPath;
        this.profileImage = profileImage;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }

    public EmployResponse mapEmployResponse() {
        String managerRelPathPart = this.managerRelPath != null ? this.managerRelPath.split("/")[0] : null;
        return new EmployResponse(
                this.id,
                this.name,
                this.phoneNumber,
                this.email,
                managerRelPathPart,
                this.profileImage,
                this.createdAt
        );
    }
}
