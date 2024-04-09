package com.employwise.service.impl;

import com.employwise.exception.ResourceNotFoundException;
import com.employwise.model.Employ;
import com.employwise.payload.EmployRequest;
import com.employwise.payload.EmployResponse;
import com.employwise.payload.ManagerAndPathDTO;
import com.employwise.repository.EmployRepository;
import com.employwise.service.EmployService;
import com.employwise.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployServiceImpl implements EmployService {
    private final EmployRepository employRepository;
    private final NotifyService notifyService;

    @Override
    public EmployResponse createEmploy(EmployRequest request, String managerPath, Employ manager) {
        Employ employ = this.employRepository.save(
                new Employ(
                        request.getName(),
                        request.getPhoneNumber(),
                        request.getEmail(),
                        managerPath,
                        request.getProfileImage())
        );

        if (managerPath!=null)
            this.notifyService.notifyManager(
                    manager.getEmail(),
                    manager.getName(),
                    employ.getName(),
                    employ.getEmail(),
                    employ.getPhoneNumber()
            );

        return employ.mapEmployResponse();
    }

    @Override
    public EmployResponse updateEmploy(
            EmployRequest request, Employ employ, String managerPath, Employ manager
    ) {
        boolean notify = !Objects.equals(managerPath, employ.getManagerRelPath());

        // Update all the employees under the current employee
        String managerPathToBeUpdated = employ.getId();
        if (employ.getManagerRelPath() != null) managerPathToBeUpdated += "/" + employ.getManagerRelPath();
        List<Employ> employList = this.employRepository.findByManagerRelPath(managerPathToBeUpdated);

        String newManagerPath = employ.getId();
        if (managerPath != null) newManagerPath += "/" + managerPath;

        final String finalNewManagerPath = newManagerPath;
        employList.forEach(e -> e.setManagerRelPath(finalNewManagerPath));
        this.employRepository.saveAll(employList);

        employ.setName(request.getName());
        employ.setEmail(request.getEmail());
        employ.setPhoneNumber(request.getPhoneNumber());
        employ.setProfileImage(request.getProfileImage());
        employ.setManagerRelPath(managerPath);
        Employ response = this.employRepository.save(employ);

        if (notify && managerPath!=null)
            this.notifyService.notifyManager(
                    manager.getEmail(),
                    manager.getName(),
                    employ.getName(),
                    employ.getEmail(),
                    employ.getPhoneNumber()
            );


        return response.mapEmployResponse();
    }

    @Override
    public void deleteEmploy(String id) {
        try {
            this.employRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Employ", "id", id);
        }
    }

    @Override
    public EmployResponse getNthManager(String id, int n) {
        Employ employee = this.employRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));

        String[] pathNodes = employee.getManagerRelPath().split("/");

        if (n < 1 || n > pathNodes.length) {
            throw new RuntimeException(n+ "th manager is not found!");
        }

        Optional<Employ> manager = this.employRepository.findById(pathNodes[n-1]);
        return manager.map(Employ::mapEmployResponse).orElse(null);
    }

    @Override
    public ManagerAndPathDTO getManagerPath(String managerId) {
        Optional<Employ> employ = this.employRepository.findById(managerId);

        if (employ.isPresent()) {
            String path = employ.get().getId();
            if (employ.get().getManagerRelPath() != null) path += "/" + employ.get().getManagerRelPath();

            return new ManagerAndPathDTO(path, employ.get());
        }
        return null;
    }

    @Override
    public List<EmployResponse> getAllEmploys(int page, int size, String sortField, String sortOrder) {
        Sort sort =
                Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employ> employPage = this.employRepository.findAll(pageable);
        return employPage.getContent().stream().map(Employ::mapEmployResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Employ findById(String id) {
        return this.employRepository.findById(id).orElse(null);
    }
}
