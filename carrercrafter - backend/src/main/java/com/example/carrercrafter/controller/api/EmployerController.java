package com.example.carrercrafter.controller.api;

import com.example.carrercrafter.Service.EmployerService;
import com.example.carrercrafter.dto.EmployerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/employer")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployerController {

    @Autowired
    private EmployerService employerService;
    
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public ResponseEntity<EmployerDto> create(@RequestBody EmployerDto dto) {
        return ResponseEntity.ok(employerService.saveEmployer(dto));
    }
    

    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<EmployerDto>> getAll() {
        return ResponseEntity.ok(employerService.getAllEmployers());
    }
    

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployerDto> getById(@PathVariable int id) {
        EmployerDto dto = employerService.getEmployerById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
    

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployerDto> update(@PathVariable int id, @RequestBody EmployerDto dto) {
        EmployerDto updated = employerService.updateEmployer(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    

    @PreAuthorize("hasRole('EMPLOYER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.noContent().build();
    }
    

    @PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    @GetMapping("/search/location/{location}")
    public ResponseEntity<List<EmployerDto>> searchByLocation(@PathVariable String location) {
        return ResponseEntity.ok(employerService.searchByLocation(location));
    }
    

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<EmployerDto> getByUserId(@PathVariable int userId) {
    	//System.out.println("Logged in roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        EmployerDto dto = employerService.getByUserId(userId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();

    }
    
    
    @GetMapping("/public")
    @PreAuthorize("permitAll()")
    public List<EmployerDto> getAllPublicEmployers() {
        return employerService.getAllEmployers();
    }

}
