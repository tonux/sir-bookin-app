package sn.sir.booking.sir_booking_app.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationDTO {

    private Long id;

    private LocalDateTime dateReservation;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Statut status;

    @Size(max = 255)
    private String amount;

    private Integer days;

}
