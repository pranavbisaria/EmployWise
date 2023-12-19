package com.employwise.payload;

import com.employwise.model.Employ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerAndPathDTO {
    private String managerPath;
    private Employ manager;
}
