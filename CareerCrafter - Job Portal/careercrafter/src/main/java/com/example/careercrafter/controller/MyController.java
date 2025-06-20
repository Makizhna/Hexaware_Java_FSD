package com.example.careercrafter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.careercrafter.entity.Employers;
import com.example.careercrafter.entity.JobSeekers;
import com.example.careercrafter.entity.Users;
import com.example.careercrafter.repo.EmployerRepository;
import com.example.careercrafter.repo.JobSeekerRepository;
import com.example.careercrafter.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
public class MyController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private EmployerRepository employerRepo;

    @GetMapping("/")
    public String showHomePage() {
        return "homePage";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "createaccount_form";
    }

    @PostMapping("/register")
    @Transactional
    public String handleRegister(@ModelAttribute Users user, HttpSession session) {
        Users savedUser = userRepo.save(user);
        userRepo.flush(); 

        int userId = savedUser.getId();
        session.setAttribute("userId", userId);
        session.setAttribute("user", savedUser);

        if ("job_seeker".equals(user.getRole())) {
            return "redirect:/jobseeker_form";
        } else {
            return "redirect:/employer_form";
        }
    }


    @GetMapping("/jobseeker_form")
    public String jobSeekerForm(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            model.addAttribute("error", "Session expired or invalid. Please register again.");
            return "error";
        }

        model.addAttribute("userId", userId);  
        model.addAttribute("jobSeeker", new JobSeekers()); 
        return "jobseeker_form";
    }




    @GetMapping("/employer_form")
    public String employerForm(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            model.addAttribute("error", "Session expired. Please register again.");
            return "error";
        }

        model.addAttribute("userId", userId);
        return "employer_form";
    }

    


    @PostMapping("/saveEmployer")
    public String saveEmployer(@RequestParam int userId,
                               @ModelAttribute Employers emp,
                               Model model) {

        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            model.addAttribute("error", "User not found for ID: " + userId);
            return "error";
        }

        Employers existing = employerRepo.findById(userId).orElse(null);

        if (existing != null) {
            existing.setName(emp.getName());
            existing.setPosition(emp.getPosition());
            existing.setCompanyName(emp.getCompanyName());
            existing.setLocation(emp.getLocation());
            employerRepo.save(existing);
        } else {
            emp.setUser(user); 
            emp.setEmployeeId(userId);
            employerRepo.save(emp);
        }

        return "redirect:/listEmployers";
    }


    
    @GetMapping("/listJobSeekers")
    public String listJobSeekers(Model model, HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("user");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<JobSeekers> seekers = jobSeekerRepo.findAll();
        model.addAttribute("seekers", seekers);

        return "list_jobseekers";
    }



    @GetMapping("/listEmployers")
    public String listEmployers(Model model) {
        List<Employers> employers = employerRepo.findAll();
        model.addAttribute("employers", employers);
        return "list_employers";
    }

    @GetMapping("/deleteJobSeeker/{id}")
    public String deleteJobSeeker(@PathVariable int id, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null && user.getId() == id) {
            jobSeekerRepo.deleteById(id);
        }
        return "redirect:/listJobSeekers";
    }

    @GetMapping("/deleteEmployer/{id}")
    public String deleteEmployer(@PathVariable int id) {
        employerRepo.deleteById(id);
        return "redirect:/listEmployers";
    }

    @GetMapping("/editJobSeeker/{id}")
    public String editJobSeeker(@PathVariable int id, Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null && user.getId() == id) {
            JobSeekers seeker = jobSeekerRepo.findById(id).orElse(null);
            model.addAttribute("jobSeeker", seeker);
            model.addAttribute("userId", id); 
            return "jobseeker_form";
        }
        return "redirect:/listJobSeekers";
    }

    @GetMapping("/editEmployer/{id}")
    public String editEmployer(@PathVariable int id, Model model) {
        Employers emp = employerRepo.findById(id).orElse(null);
        model.addAttribute("employer", emp);
        
        if (emp != null && emp.getUser() != null) {
            model.addAttribute("userId", emp.getUser().getId()); 
        }

        return "employer_form";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        Users user = userRepo.findByEmailAndPassword(email, password);

        if (user == null) {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }

        session.setAttribute("user", user);

        if ("job_seeker".equals(user.getRole())) {
            return "userhome";
        } else if ("employer".equals(user.getRole())) {
            return "adminhome";
        } else {
            model.addAttribute("error", "Unknown user role.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/list_jobseekers")
    public String listJobSeekersAlias() {
        return "redirect:/listJobSeekers";
    }

    @GetMapping("/list_employers")
    public String listEmployersAlias() {
        return "redirect:/listEmployers";
    }
    
    
    @PostMapping("/saveJobSeeker")
    public String saveJobSeeker(@RequestParam("userId") Integer userId,
                                @ModelAttribute JobSeekers formSeeker,
                                Model model) {

        Optional<Users> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error";
        }

        Users user = optionalUser.get();

        Optional<JobSeekers> optionalSeeker = jobSeekerRepo.findById(userId);

        if (optionalSeeker.isPresent()) {
            
            JobSeekers seeker = optionalSeeker.get();
            seeker.setName(formSeeker.getName());
            seeker.setDob(formSeeker.getDob());
            seeker.setPhone(formSeeker.getPhone());
            seeker.setEducation(formSeeker.getEducation());
            seeker.setExperience(formSeeker.getExperience());
            seeker.setSkills(formSeeker.getSkills());
            seeker.setResumeUrl(formSeeker.getResumeUrl());
            jobSeekerRepo.save(seeker);
        } else {
            //  NEW INSERT – DO NOT set userId manually
            JobSeekers newSeeker = new JobSeekers();
            newSeeker.setName(formSeeker.getName());
            newSeeker.setDob(formSeeker.getDob());
            newSeeker.setPhone(formSeeker.getPhone());
            newSeeker.setEducation(formSeeker.getEducation());
            newSeeker.setExperience(formSeeker.getExperience());
            newSeeker.setSkills(formSeeker.getSkills());
            newSeeker.setResumeUrl(formSeeker.getResumeUrl());

            newSeeker.setUser(user);  //  Sets userId via @MapsId automatically

            jobSeekerRepo.save(newSeeker);
        }

        return "redirect:/listJobSeekers";
    }

}
