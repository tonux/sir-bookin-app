package sn.sir.booking.sir_booking_app.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sir.booking.sir_booking_app.domain.Customer;
import sn.sir.booking.sir_booking_app.domain.Reservation;
import sn.sir.booking.sir_booking_app.domain.Room;
import sn.sir.booking.sir_booking_app.model.ReservationDTO;
import sn.sir.booking.sir_booking_app.repos.CustomerRepository;
import sn.sir.booking.sir_booking_app.repos.ReservationRepository;
import sn.sir.booking.sir_booking_app.repos.RoomRepository;
import sn.sir.booking.sir_booking_app.util.NotFoundException;
import sn.sir.booking.sir_booking_app.util.WebUtils;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    public ReservationService(final ReservationRepository reservationRepository,
            final CustomerRepository customerRepository, final RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }

    public List<ReservationDTO> findAll() {
        final List<Reservation> reservations = reservationRepository.findAll(Sort.by("id"));
        return reservations.stream()
                .map(reservation -> mapToDTO(reservation, new ReservationDTO()))
                .toList();
    }

    public ReservationDTO get(final Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> mapToDTO(reservation, new ReservationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ReservationDTO reservationDTO) {
        final Reservation reservation = new Reservation();
        mapToEntity(reservationDTO, reservation);
        return reservationRepository.save(reservation).getId();
    }

    public void update(final Long id, final ReservationDTO reservationDTO) {
        final Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(reservationDTO, reservation);
        reservationRepository.save(reservation);
    }

    public void delete(final Long id) {
        reservationRepository.deleteById(id);
    }

    private ReservationDTO mapToDTO(final Reservation reservation,
            final ReservationDTO reservationDTO) {
        reservationDTO.setId(reservation.getId());
        reservationDTO.setDateReservation(reservation.getDateReservation());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setAmount(reservation.getAmount());
        reservationDTO.setDays(reservation.getDays());
        return reservationDTO;
    }

    private Reservation mapToEntity(final ReservationDTO reservationDTO,
            final Reservation reservation) {
        reservation.setDateReservation(reservationDTO.getDateReservation());
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setAmount(reservationDTO.getAmount());
        reservation.setDays(reservationDTO.getDays());
        return reservation;
    }

    public String getReferencedWarning(final Long id) {
        final Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Customer reservationsCustomer = customerRepository.findFirstByReservations(reservation);
        if (reservationsCustomer != null) {
            return WebUtils.getMessage("reservation.customer.reservations.referenced", reservationsCustomer.getId());
        }
        final Room reservationsRoom = roomRepository.findFirstByReservations(reservation);
        if (reservationsRoom != null) {
            return WebUtils.getMessage("reservation.room.reservations.referenced", reservationsRoom.getId());
        }
        return null;
    }

}
