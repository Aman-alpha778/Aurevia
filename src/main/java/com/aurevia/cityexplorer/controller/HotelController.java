package com.aurevia.cityexplorer.controller;

import java.security.Principal;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aurevia.cityexplorer.model.HotelBookingForm;
import com.aurevia.cityexplorer.model.HotelSearchForm;
import com.aurevia.cityexplorer.model.FlightSearchForm;
import com.aurevia.cityexplorer.service.UserService;

@Controller
public class HotelController {

    private final UserService userService;

    public HotelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/planner/flights")
    public String flightPlanner(@ModelAttribute("searchForm") FlightSearchForm searchForm,
                                @RequestParam(value = "city", required = false) String city,
                                Model model,
                                Principal principal) {
        if (city != null && !city.isBlank()) {
            searchForm.setFromCity("");
            searchForm.setToCity(city);
        }
        model.addAttribute("currentUser", userService.findByEmail(principal.getName()).orElseThrow());
        return "flight";
    }

    @GetMapping("/planner/hotels")
    public String hotelPlanner(@ModelAttribute("searchForm") HotelSearchForm searchForm,
                               Model model,
                               Principal principal) {
        model.addAttribute("bookingForm", new HotelBookingForm());
        model.addAttribute("currentUser", userService.findByEmail(principal.getName()).orElseThrow());
        return "hotel";
    }

    @PostMapping("/planner/hotels/book")
    public String bookHotel(@Valid @ModelAttribute("bookingForm") HotelBookingForm bookingForm,
                            BindingResult bindingResult,
                            @ModelAttribute("searchForm") HotelSearchForm searchForm,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", userService.findByEmail(principal.getName()).orElseThrow());
            return "hotel";
        }

        redirectAttributes.addFlashAttribute("bookingSuccess",
                "Hotel " + bookingForm.getHotelName() + " booked for " + bookingForm.getGuestName() + ".");
        return "redirect:/planner/hotels?city=" + searchForm.getCity()
                + "&checkIn=" + searchForm.getCheckIn()
                + "&guests=" + searchForm.getGuests();
    }
}
