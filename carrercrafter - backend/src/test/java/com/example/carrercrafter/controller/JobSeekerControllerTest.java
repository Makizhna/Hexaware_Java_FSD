package com.example.carrercrafter.controller;



import com.example.carrercrafter.controller.api.JobSeekerController;
import com.example.carrercrafter.dto.JobSeekerDto;
import com.example.carrercrafter.dto.UserDto;
import com.example.carrercrafter.enums.UserRole;
import com.example.carrercrafter.Service.JobSeekerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(JobSeekerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class JobSeekerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobSeekerService jobSeekerService;

    @Autowired
    private ObjectMapper objectMapper;

    private JobSeekerDto sampleJobSeekerDto() {
        JobSeekerDto dto = new JobSeekerDto();
        dto.setSeekerId(1);
        dto.setName("John Doe");
        dto.setEducation("B.Tech");
        dto.setSkills("Java, Spring");
        dto.setExperience("2 years");
        dto.setPhone("9876543210");
        dto.setDob(LocalDate.of(1999, 5, 10));
        dto.setLocation("Hyderabad");
        dto.setLinkedinUrl("https://linkedin.com/in/johndoe");
        dto.setGithubUrl("https://github.com/johndoe");

        UserDto userDto = new UserDto();
        userDto.setId(101);
        userDto.setName("John");
        userDto.setEmail("john@example.com");
        userDto.setPassword("secret");
        userDto.setRole(UserRole.JOB_SEEKER);

        dto.setUser(userDto);
        return dto;
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testGetAllJobSeekers() throws Exception {
        JobSeekerDto dto = sampleJobSeekerDto();
        Mockito.when(jobSeekerService.getAllJobSeekers()).thenReturn(List.of(dto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobseeker"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("John Doe"))
            .andExpect(jsonPath("$[0].education").value("B.Tech"));
    }

    @Test
    @WithMockUser(roles = "JOB_SEEKER")
    void testCreateJobSeeker() throws Exception {
        JobSeekerDto dto = sampleJobSeekerDto();
        Mockito.when(jobSeekerService.saveJobSeeker(Mockito.any())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/jobseeker")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.education").value("B.Tech"));
    }

    @Test
    @WithMockUser(roles = "JOB_SEEKER")
    void testGetJobSeekerByUserId() throws Exception {
        JobSeekerDto dto = sampleJobSeekerDto();
        Mockito.when(jobSeekerService.getByUserId(101)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobseeker/by-user/101"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.email").value("john@example.com"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testSearchBySkill() throws Exception {
        Mockito.when(jobSeekerService.searchBySkill("java"))
            .thenReturn(List.of(sampleJobSeekerDto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobseeker/search/skill/java"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].skills").value("Java, Spring"));
    }
}

