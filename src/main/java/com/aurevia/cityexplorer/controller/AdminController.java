package com.aurevia.cityexplorer.controller;

import java.security.Principal;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aurevia.cityexplorer.model.AdminCityForm;
import com.aurevia.cityexplorer.model.AdminPlaceForm;
import com.aurevia.cityexplorer.model.AdminUserForm;
import com.aurevia.cityexplorer.service.PortalService;
import com.aurevia.cityexplorer.service.UserService;

@Controller
public class AdminController {

    private final PortalService portalService;
    private final UserService userService;

    public AdminController(PortalService portalService, UserService userService) {
        this.portalService = portalService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model, Principal principal) {
        populateDashboardModel(model, principal);
        return "admin";
    }

    @PostMapping("/admin/admins")
    public String addAdmin(@Valid @ModelAttribute("adminUserForm") AdminUserForm adminUserForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal,
                           Model model) {
        if (!bindingResult.hasErrors() && userService.emailExists(adminUserForm.getEmail())) {
            bindingResult.rejectValue("email", "exists", "An admin with this email already exists.");
        }
        if (bindingResult.hasErrors()) {
            populateDashboardModel(model, principal);
            model.addAttribute("activeAdminSection", "add-admin");
            return "admin";
        }
        userService.registerAdmin(adminUserForm);
        redirectAttributes.addFlashAttribute("adminSuccess", adminUserForm.getEmail() + " added as admin.");
        return "redirect:/admin#add-admin";
    }

    

    @PostMapping("/admin/cities")
    public String addCity(@Valid @ModelAttribute("cityForm") AdminCityForm cityForm,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Principal principal,
                          Model model) {
        if (bindingResult.hasErrors()) {
            populateDashboardModel(model, principal);
            model.addAttribute("activeAdminSection", "add-city");
            return "admin";
        }









        var city = portalService.addManagedCity(cityForm);
        redirectAttributes.addFlashAttribute("adminSuccess", city.getName() + " added successfully. You can now add hotels, popular places, cafes, and more for this city below.");
        redirectAttributes.addFlashAttribute("selectedCitySlug", city.getSlug());
        redirectAttributes.addFlashAttribute("selectedCityName", city.getName());
        redirectAttributes.addFlashAttribute("activeAdminSection", "add-content");
        return "redirect:/admin#add-content";
    }

    @PostMapping("/admin/cities/{cityId}/update")
    public String updateCity(@PathVariable Long cityId,
                             @Valid @ModelAttribute("updateCityForm") AdminCityForm cityForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal,
                             Model model) {
        if (bindingResult.hasErrors()) {
            populateDashboardModel(model, principal);
            model.addAttribute("activeAdminSection", "manage-cities");
            return "admin";
        }
        var city = portalService.updateManagedCity(cityId, cityForm);
        redirectAttributes.addFlashAttribute("adminSuccess", city.getName() + " updated.");
        return "redirect:/admin#manage-cities";
    }

    @PostMapping("/admin/cities/{cityId}/delete")
    public String deleteCity(@PathVariable Long cityId, RedirectAttributes redirectAttributes) {
        portalService.deleteManagedCity(cityId);
        redirectAttributes.addFlashAttribute("adminSuccess", "City removed.");
        return "redirect:/admin#manage-cities";
    }

    @PostMapping("/admin/places")
    public String addPlace(@Valid @ModelAttribute("placeForm") AdminPlaceForm placeForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal,
                           Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                portalService.getCity(placeForm.getCitySlug().trim()).orElseThrow();
            } catch (Exception ignored) {
                bindingResult.rejectValue("citySlug", "invalid", "Select a valid city from the list.");
            }
        }
        if (bindingResult.hasErrors()) {
            populateDashboardModel(model, principal);
            model.addAttribute("activeAdminSection", "add-content");
            return "admin";
        }
        portalService.addManagedPlace(placeForm);
        redirectAttributes.addFlashAttribute("adminSuccess", placeForm.getName() + " added to " + placeForm.getCitySlug() + ".");
        return "redirect:/admin#manage-cities";
    }

    @PostMapping("/admin/places/{placeId}/update")
    public String updatePlace(@PathVariable Long placeId,
                              @Valid @ModelAttribute("updatePlaceForm") AdminPlaceForm placeForm,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Principal principal,
                              Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                portalService.getCity(placeForm.getCitySlug().trim()).orElseThrow();
            } catch (Exception ignored) {
                bindingResult.rejectValue("citySlug", "invalid", "Select a valid city from the list.");
            }
        }
        if (bindingResult.hasErrors()) {
            populateDashboardModel(model, principal);
            model.addAttribute("activeAdminSection", "manage-cities");
            return "admin";
        }
        portalService.updateManagedPlace(placeId, placeForm);
        redirectAttributes.addFlashAttribute("adminSuccess", placeForm.getName() + " updated.");
        return "redirect:/admin#manage-cities";
    }

    @PostMapping("/admin/places/{placeId}/delete")
    public String deletePlace(@PathVariable Long placeId, RedirectAttributes redirectAttributes) {
        portalService.deleteManagedPlace(placeId);
        redirectAttributes.addFlashAttribute("adminSuccess", "Content removed.");
        return "redirect:/admin#manage-cities";
    }

    private void populateDashboardModel(Model model, Principal principal) {
        if (!model.containsAttribute("adminUserForm")) {
            model.addAttribute("adminUserForm", new AdminUserForm());
        }
        if (!model.containsAttribute("cityForm")) {
            model.addAttribute("cityForm", new AdminCityForm());
        }
        if (!model.containsAttribute("placeForm")) {
            AdminPlaceForm placeForm = new AdminPlaceForm();
            Object selectedCitySlug = model.asMap().get("selectedCitySlug");
            if (selectedCitySlug instanceof String slug && !slug.isBlank()) {
                placeForm.setCitySlug(slug);
            }
            model.addAttribute("placeForm", placeForm);
        }
        model.addAttribute("admins", userService.getAdmins());
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("managedCities", portalService.getManagedCities());
        model.addAttribute("managedPlaces", portalService.getManagedPlaceViews());
        model.addAttribute("allCities", portalService.getCityCards());
        model.addAttribute("categoryOptions", portalService.getAdminCategoryOptions());
        model.addAttribute("seedAdminEmail", "admin@aurevia.com");
        model.addAttribute("currentUser", userService.findByEmail(principal.getName()).orElseThrow());
    }
}
