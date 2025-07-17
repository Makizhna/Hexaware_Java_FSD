package com.example.carrercrafter.mapper;

import com.example.carrercrafter.dto.EmployerDto;
import com.example.carrercrafter.entities.Employer;
import com.example.carrercrafter.entities.User;

public class EmployerMapper {

    public static EmployerDto toDto(Employer emp) {
        if (emp == null) return null;

        EmployerDto dto = new EmployerDto();
        dto.setEmployeeId(emp.getEmployeeId());
        dto.setName(emp.getName());
        dto.setPosition(emp.getPosition());
        dto.setWorkEmail(emp.getWorkEmail());         
        dto.setLinkedinUrl(emp.getLinkedinUrl()); 
        dto.setCompanyName(emp.getCompanyName());
        dto.setLocation(emp.getLocation());
        dto.setUser(UserMapper.mapToUserDto(emp.getUser()));
        dto.setActive(emp.isActive());
        dto.setCompanyWebsite(emp.getCompanyWebsite());     
        dto.setCompanyBio(emp.getCompanyBio());             

        if (emp.getUser() != null) {
            dto.setUser(UserMapper.mapToUserDto(emp.getUser()));
        }
        return dto;
    }

    public static Employer toEntity(EmployerDto dto, User user) {
        if (dto == null) return null;

        Employer emp = new Employer();
        emp.setEmployeeId(dto.getEmployeeId());
        emp.setName(dto.getName());
        emp.setPosition(dto.getPosition());
        emp.setWorkEmail(dto.getWorkEmail());         
        emp.setLinkedinUrl(dto.getLinkedinUrl()); 
        emp.setCompanyName(dto.getCompanyName());
        emp.setLocation(dto.getLocation());
        emp.setCompanyWebsite(dto.getCompanyWebsite());     
        emp.setCompanyBio(dto.getCompanyBio()); 
        emp.setUser(user);
        emp.setActive(dto.isActive()); 
        return emp;
    }
    
    
    
    
    
}
