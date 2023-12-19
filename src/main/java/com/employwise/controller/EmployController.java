package com.employwise.controller;

import com.employwise.exception.ResourceNotFoundException;
import com.employwise.model.Employ;
import com.employwise.payload.EmployRequest;
import com.employwise.payload.EmployResponse;
import com.employwise.payload.ManagerAndPathDTO;
import com.employwise.service.EmployService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employ")
public class EmployController {
    private final EmployService employService;

    @PostMapping("/create")
    public ResponseEntity<EmployResponse> createEmploy(@Valid @RequestBody EmployRequest employRequest) {
        String managerPath = null;
        Employ manager = null;
        if(employRequest.getManagerId() != null && !employRequest.getManagerId().isBlank()) {
            ManagerAndPathDTO managerAndPathDTO = this.employService.getManagerPath(employRequest.getManagerId());
            if (managerAndPathDTO == null) {
                throw new ResourceNotFoundException("Manager", "id", employRequest.getManagerId());
            }
            managerPath = managerAndPathDTO.getManagerPath();
            manager = managerAndPathDTO.getManager();
        }

        return new ResponseEntity<>(this.employService.createEmploy(employRequest, managerPath, manager), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployResponse> updateEmploy(@PathVariable String id, @Valid @RequestBody EmployRequest employRequest) {
        Employ employ = this.employService.findById(id);
        if (employ == null) {
            throw new ResourceNotFoundException("Employ", "id", id);
        }

        String managerPath = null;
        Employ manager = null;
        if(employRequest.getManagerId() != null && !employRequest.getManagerId().isBlank()) {
            ManagerAndPathDTO managerAndPathDTO = this.employService.getManagerPath(employRequest.getManagerId());
            if (managerAndPathDTO == null) {
                throw new ResourceNotFoundException("Manager", "id", employRequest.getManagerId());
            }
            managerPath = managerAndPathDTO.getManagerPath();
            manager = managerAndPathDTO.getManager();
        }

        return new ResponseEntity<>(this.employService.updateEmploy(employRequest, employ, managerPath, manager), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmploy(@PathVariable String id) {
        this.employService.deleteEmploy(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/nth-manager/{n}")
    public ResponseEntity<EmployResponse> getNthManager(@PathVariable String id, @PathVariable int n) {
        EmployResponse nthManager = this.employService.getNthManager(id, n);

        return ResponseEntity.ok(nthManager);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<EmployResponse>> getAllEmploys(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order
    ) {
        return new ResponseEntity<>(this.employService.getAllEmploys(page, size, sortBy, order), HttpStatus.OK);
    }
}
