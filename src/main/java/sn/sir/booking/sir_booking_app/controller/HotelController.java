package sn.sir.booking.sir_booking_app.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sir.booking.sir_booking_app.model.HotelDTO;
import sn.sir.booking.sir_booking_app.service.HotelService;
import sn.sir.booking.sir_booking_app.util.WebUtils;


@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(final HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("hotels", hotelService.findAll());
        return "hotel/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("hotel") final HotelDTO hotelDTO) {
        return "hotel/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("hotel") @Valid final HotelDTO hotelDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "hotel/add";
        }
        hotelService.create(hotelDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("hotel.create.success"));
        return "redirect:/hotels";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("hotel", hotelService.get(id));
        return "hotel/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("hotel") @Valid final HotelDTO hotelDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "hotel/edit";
        }
        hotelService.update(id, hotelDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("hotel.update.success"));
        return "redirect:/hotels";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = hotelService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            hotelService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("hotel.delete.success"));
        }
        return "redirect:/hotels";
    }

}
