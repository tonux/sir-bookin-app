package sn.sir.booking.sir_booking_app.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sir.booking.sir_booking_app.domain.Hotel;
import sn.sir.booking.sir_booking_app.domain.Reservation;
import sn.sir.booking.sir_booking_app.model.RoomDTO;
import sn.sir.booking.sir_booking_app.model.TypeRoom;
import sn.sir.booking.sir_booking_app.repos.HotelRepository;
import sn.sir.booking.sir_booking_app.repos.ReservationRepository;
import sn.sir.booking.sir_booking_app.service.RoomService;
import sn.sir.booking.sir_booking_app.util.CustomCollectors;
import sn.sir.booking.sir_booking_app.util.WebUtils;


@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    public RoomController(final RoomService roomService, final HotelRepository hotelRepository,
            final ReservationRepository reservationRepository) {
        this.roomService = roomService;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("typeRoomValues", TypeRoom.values());
        model.addAttribute("hotelValues", hotelRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Hotel::getId, Hotel::getName)));
        model.addAttribute("reservationsValues", reservationRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Reservation::getId, Reservation::getAmount)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "room/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("room") final RoomDTO roomDTO) {
        return "room/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("room") @Valid final RoomDTO roomDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "room/add";
        }
        roomService.create(roomDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("room.create.success"));
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("room", roomService.get(id));
        return "room/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("room") @Valid final RoomDTO roomDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "room/edit";
        }
        roomService.update(id, roomDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("room.update.success"));
        return "redirect:/rooms";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        roomService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("room.delete.success"));
        return "redirect:/rooms";
    }

}
