package sn.sir.booking.sir_booking_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.sir.booking.sir_booking_app.domain.Hotel;


public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
