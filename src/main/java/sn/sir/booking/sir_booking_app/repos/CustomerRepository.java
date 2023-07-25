package sn.sir.booking.sir_booking_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.sir.booking.sir_booking_app.domain.Customer;
import sn.sir.booking.sir_booking_app.domain.Reservation;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findFirstByReservations(Reservation reservation);

    Customer findByEmail(String email);

}
