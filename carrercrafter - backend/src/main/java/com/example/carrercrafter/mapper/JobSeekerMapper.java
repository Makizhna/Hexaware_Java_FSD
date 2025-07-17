package com.example.carrercrafter.mapper;
import com.example.carrercrafter.dto.JobSeekerDto;
import com.example.carrercrafter.dto.JobSeekerResumeDto;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.User;

public class JobSeekerMapper {

    public static JobSeekerDto toDto(JobSeeker js) {
        JobSeekerDto dto = new JobSeekerDto();
        dto.setSeekerId(js.getSeekerId());
        dto.setName(js.getName());
        dto.setEducation(js.getEducation());
        dto.setSkills(js.getSkills());
        dto.setExperience(js.getExperience());
        dto.setPhone(js.getPhone());
        dto.setDob(js.getDob());
        dto.setLocation(js.getLocation());
        dto.setLinkedinUrl(js.getLinkedinUrl());
        dto.setGithubUrl(js.getGithubUrl());

        if (js.getUser() != null) {
            dto.setUser(UserMapper.mapToUserDto(js.getUser()));
        }

        if (js.getJobSeekerResume() != null) {
            dto.setJobSeekerResume(JobSeekerResumeMapper.toDto(js.getJobSeekerResume()));
        }

        return dto;
    }

    public static JobSeeker toEntity(JobSeekerDto dto) {
        JobSeeker js = new JobSeeker();
        js.setSeekerId(dto.getSeekerId());
        js.setName(dto.getName());
        js.setEducation(dto.getEducation());
        js.setSkills(dto.getSkills());
        js.setExperience(dto.getExperience());
        js.setPhone(dto.getPhone());
        js.setDob(dto.getDob());
        js.setLocation(dto.getLocation());
        js.setLinkedinUrl(dto.getLinkedinUrl());
        js.setGithubUrl(dto.getGithubUrl());

        if (dto.getUser() != null) {
            User user = UserMapper.mapToUser(dto.getUser());
            js.setUser(user);
        }

        if (dto.getJobSeekerResume() != null) {
            js.setJobSeekerResume(JobSeekerResumeMapper.toEntity(dto.getJobSeekerResume()));
        }

        return js;
    }
}
