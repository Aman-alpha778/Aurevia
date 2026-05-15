package com.aurevia.cityexplorer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.aurevia.cityexplorer.model.ManagedCity;
import com.aurevia.cityexplorer.model.User;
import com.aurevia.cityexplorer.repository.ManagedCityRepository;
import com.aurevia.cityexplorer.repository.ManagedPlaceRepository;
import com.aurevia.cityexplorer.repository.ReviewRepository;
import com.aurevia.cityexplorer.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:admin-content-flow;DB_CLOSE_DELAY=-1;MODE=LEGACY",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.open-in-view=false",
        "spring.thymeleaf.cache=false"
})
class AdminContentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManagedCityRepository managedCityRepository;

    @Autowired
    private ManagedPlaceRepository managedPlaceRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User adminUser;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        managedPlaceRepository.deleteAll();
        managedCityRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setFullName("Admin Tester");
        user.setEmail("admin@test.com");
        user.setPassword(passwordEncoder.encode("secret123"));
        user.setRole("ADMIN");
        adminUser = userRepository.save(user);
    }

    @Test
    void portalRendersCompleteBuiltInCitySliderDataset() throws Exception {
        String html = mockMvc.perform(get("/portal")
                        .with(user(adminUser.getEmail()).roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(18, html.split("class=\"city-card\"", -1).length - 1);
        for (String city : new String[] {
                "Jaipur", "Agra", "Varanasi", "Delhi", "Chandigarh", "Udaipur",
                "Jaisalmer", "Goa", "Mumbai", "Maharashtra", "Kochi", "Darjeeling",
                "Leh", "Srinagar", "Manali", "Shimla", "Rishikesh", "Chamba"
        }) {
            assertTrue(html.contains("data-city=\"" + city + "\""), city + " should render in the city slider");
        }
        assertTrue(html.contains("href=\"/admin\""), "Admin navbar link should render for admin users");
        assertTrue(html.contains(">Logout</button>"), "Logout text should render in the navbar");
    }

    @Test
    void adminAddedCityAndPlaceReflectOnPortalAndCityPages() throws Exception {
        mockMvc.perform(post("/admin/cities")
                        .with(user(adminUser.getEmail()).roles("ADMIN"))
                        .with(csrf())
                        .param("name", "Pune")
                        .param("region", "Maharashtra")
                        .param("tagline", "Culture, cafes, and campuses.")
                        .param("heroImage", "https://example.com/pune.jpg")
                        .param("searchKeywords", "Pune\nPoona")
                        .param("bestSeason", "October to February")
                        .param("idealDuration", "2-3 days")
                        .param("cityHighlights", "Historic core\nCafe culture\nWeekend escapes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin#add-content"));

        ManagedCity city = managedCityRepository.findBySlug("pune").orElseThrow();

        mockMvc.perform(post("/admin/places")
                        .with(user(adminUser.getEmail()).roles("ADMIN"))
                        .with(csrf())
                        .param("citySlug", city.getSlug())
                        .param("categorySlug", "hotels")
                        .param("name", "Skyline Suites")
                        .param("description", "A city hotel added from the admin panel.")
                        .param("imageUrl", "https://example.com/skyline.jpg")
                        .param("galleryImages", "https://example.com/skyline-1.jpg\nhttps://example.com/skyline-2.jpg")
                        .param("insight", "A polished stay for business and leisure travellers.")
                        .param("history", "A newer hotel district favourite in Pune.")
                        .param("address", "Baner, Pune")
                        .param("timings", "24/7")
                        .param("priceRange", "INR 6000 - 9000")
                        .param("detailTitleOne", "Stay Style")
                        .param("detailBodyOne", "Contemporary rooms with quick city access.")
                        .param("visitorNotes", "Best for weekend stays\nClose to dining hubs"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin#manage-cities"));

        mockMvc.perform(get("/portal")
                        .with(user(adminUser.getEmail()).roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pune")))
                .andExpect(content().string(containsString("/cities/pune")));

        mockMvc.perform(get("/cities/pune")
                        .with(user(adminUser.getEmail()).roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hotels")))
                .andExpect(content().string(containsString("/cities/pune/categories/hotels")))
                .andExpect(content().string(containsString("1 spots")))
                .andExpect(content().string(containsString("October to February")));

        mockMvc.perform(get("/cities/pune/categories/hotels")
                        .with(user(adminUser.getEmail()).roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Skyline Suites")))
                .andExpect(content().string(containsString("/cities/pune/categories/hotels/places/skylinesuites")));

        mockMvc.perform(get("/cities/pune/categories/hotels/places/skylinesuites")
                        .with(user(adminUser.getEmail()).roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("A polished stay for business and leisure travellers.")))
                .andExpect(content().string(containsString("Baner, Pune")));
    }
}
