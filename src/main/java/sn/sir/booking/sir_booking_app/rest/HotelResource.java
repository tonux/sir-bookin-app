package sn.sir.booking.sir_booking_app.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.sir.booking.sir_booking_app.model.HotelDTO;
import sn.sir.booking.sir_booking_app.service.HotelService;


@RestController
@RequestMapping(value = "/api/hotels", produces = MediaType.APPLICATION_JSON_VALUE)
public class HotelResource {

    private final HotelService hotelService;

    public HotelResource(final HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(hotelService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createHotel(@RequestBody @Valid final HotelDTO hotelDTO) {
        final Long createdId = hotelService.create(hotelDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateHotel(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final HotelDTO hotelDTO) {
        hotelService.update(id, hotelDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHotel(@PathVariable(name = "id") final Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
