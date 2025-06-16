package com.example.careercrafter;

import com.example.careercrafter.entity.Users;
import com.example.careercrafter.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepo;

    @Test
    public void testSaveUser() {
        Users user = new Users("test@example.com", "pass123", "job_seeker");
        Users saved = userRepo.save(user);
        assertNotNull(saved.getId());
        assertEquals("test@example.com", saved.getEmail());
    }

    @Test
    public void testFindByEmailAndPassword() {
        Users user = new Users("login@example.com", "loginpass", "employer");
        userRepo.save(user);
        Users found = userRepo.findByEmailAndPassword("login@example.com", "loginpass");
        assertNotNull(found);
        assertEquals("employer", found.getRole());
    }
}
