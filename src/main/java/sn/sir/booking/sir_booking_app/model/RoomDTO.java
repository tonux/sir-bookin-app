package sn.sir.booking.sir_booking_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoomDTO {

    private Long id;

    @Size(max = 255)
    private String number;

    @Size(max = 255)
    private String description;

    private TypeRoom typeRoom;

    private Long hotel;

    private Long reservations;

}
