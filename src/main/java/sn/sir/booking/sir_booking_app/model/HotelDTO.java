package sn.sir.booking.sir_booking_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HotelDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String phone;

    @Size(max = 255)
    private String city;

}
