package com.aurevia.cityexplorer;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.aurevia.cityexplorer.model.User;
import com.aurevia.cityexplorer.repository.ReviewRepository;
import com.aurevia.cityexplorer.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:review-flow;DB_CLOSE_DELAY=-1;MODE=LEGACY",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.open-in-view=false",
        "spring.thymeleaf.cache=false"
})
class ReviewFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setFullName("Review Tester");
        user.setEmail("reviewer@aurevia.com");
        user.setPassword(passwordEncoder.encode("secret123"));
        user.setRole("USER");
        testUser = userRepository.save(user);
    }

    @Test
    void cityReviewSubmissionShowsBackOnCityPage() throws Exception {
        String comment = "This Chandigarh city review should appear in the slider.";

        mockMvc.perform(post("/cities/chandigarh/reviews")
                        .with(user(testUser.getEmail()).roles("USER"))
                        .with(csrf())
                        .param("rating", "5")
                        .param("comment", comment))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cities/chandigarh#reviews"));

        mockMvc.perform(get("/cities/chandigarh")
                        .with(user(testUser.getEmail()).roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(comment)))
                .andExpect(content().string(containsString("Review Tester")))
                .andExpect(content().string(containsString("review-slider")));
    }

    @Test
    void placeReviewSubmissionShowsBackOnPlacePage() throws Exception {
        String comment = "Sukhna Lake review should appear on the same place page slider.";

        mockMvc.perform(post("/cities/chandigarh/categories/popular-places/places/sukhnalake/reviews")
                        .with(user(testUser.getEmail()).roles("USER"))
                        .with(csrf())
                        .param("rating", "4")
                        .param("comment", comment))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cities/chandigarh/categories/popular-places/places/sukhnalake#reviews"));

        mockMvc.perform(get("/cities/chandigarh/categories/popular-places/places/sukhnalake")
                        .with(user(testUser.getEmail()).roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(comment)))
                .andExpect(content().string(containsString("Review Tester")))
                .andExpect(content().string(containsString("Recent Place Reviews")));
    }
}
