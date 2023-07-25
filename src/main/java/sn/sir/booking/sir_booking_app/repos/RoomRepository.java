package sn.sir.booking.sir_booking_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.sir.booking.sir_booking_app.domain.Hotel;
import sn.sir.booking.sir_booking_app.domain.Reservation;
import sn.sir.booking.sir_booking_app.domain.Room;


public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findFirstByHotel(Hotel hotel);

    Room findFirstByReservations(Reservation reservation);

}
