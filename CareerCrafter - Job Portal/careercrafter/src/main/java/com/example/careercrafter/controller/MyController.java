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
import java.util.List;

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
        return "createaccount_form";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "createaccount_form";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute Users user, HttpSession session) {

        Users savedUser = userRepo.save(user);
        int userId = savedUser.getId();

        session.setAttribute("userId", userId);
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
        return "jobseeker_form";
    }


    @GetMapping("/employer_form")
    public String employerForm(HttpSession session, Model model) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Session expired. Please register again.");
            return "error";
        }
        model.addAttribute("userId", userId);
        return "employer_form";
    }

    @PostMapping("/saveJobSeeker")
    public String saveJobSeeker(@RequestParam("userId") int userId,
                                @ModelAttribute JobSeekers jobSeeker,
                                Model model) {
        Users user = userRepo.findById(userId).orElse(null);

        if (user == null) {
            model.addAttribute("error", "Invalid user ID. Please register again.");
            return "error";
        }

        jobSeeker.setUser(user);
        jobSeekerRepo.save(jobSeeker);
        return "redirect:/listJobSeekers";
    }



    @PostMapping("/saveEmployer")
    public String saveEmployer(@RequestParam int userId,
                               @ModelAttribute Employers emp,
                               Model model) {
        if (userId == 0) {
            model.addAttribute("error", "Invalid user ID. Please register again.");
            return "error";
        }
        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            model.addAttribute("error", "User not found for ID: " + userId);
            return "error";
        }
        emp.setUser(user);
        employerRepo.save(emp);
        return "redirect:/listEmployers";
    }

    @GetMapping("/listJobSeekers")
    public String listJobSeekers(Model model) {
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
    public String deleteJobSeeker(@PathVariable int id) {
        jobSeekerRepo.deleteById(id);
        return "redirect:/listJobSeekers";
    }

    @GetMapping("/deleteEmployer/{id}")
    public String deleteEmployer(@PathVariable int id) {
        employerRepo.deleteById(id);
        return "redirect:/listEmployers";
    }

    @GetMapping("/editJobSeeker/{id}")
    public String editJobSeeker(@PathVariable int id, Model model) {
        JobSeekers seeker = jobSeekerRepo.findById(id).orElse(null);
        model.addAttribute("jobSeeker", seeker);
        return "jobseeker_form";
    }

    @GetMapping("/editEmployer/{id}")
    public String editEmployer(@PathVariable int id, Model model) {
        Employers emp = employerRepo.findById(id).orElse(null);
        model.addAttribute("employer", emp);
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
        model.addAttribute("name", user.getEmail());

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

}