package sn.sir.booking.sir_booking_app.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sir.booking.sir_booking_app.domain.Hotel;
import sn.sir.booking.sir_booking_app.domain.Room;
import sn.sir.booking.sir_booking_app.model.HotelDTO;
import sn.sir.booking.sir_booking_app.repos.HotelRepository;
import sn.sir.booking.sir_booking_app.repos.RoomRepository;
import sn.sir.booking.sir_booking_app.util.NotFoundException;
import sn.sir.booking.sir_booking_app.util.WebUtils;


@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public HotelService(final HotelRepository hotelRepository,
            final RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public List<HotelDTO> findAll() {
        final List<Hotel> hotels = hotelRepository.findAll(Sort.by("id"));
        return hotels.stream()
                .map(hotel -> mapToDTO(hotel, new HotelDTO()))
                .toList();
    }

    public HotelDTO get(final Long id) {
        return hotelRepository.findById(id)
                .map(hotel -> mapToDTO(hotel, new HotelDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final HotelDTO hotelDTO) {
        final Hotel hotel = new Hotel();
        mapToEntity(hotelDTO, hotel);
        return hotelRepository.save(hotel).getId();
    }

    public void update(final Long id, final HotelDTO hotelDTO) {
        final Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(hotelDTO, hotel);
        hotelRepository.save(hotel);
    }

    public void delete(final Long id) {
        hotelRepository.deleteById(id);
    }

    private HotelDTO mapToDTO(final Hotel hotel, final HotelDTO hotelDTO) {
        hotelDTO.setId(hotel.getId());
        hotelDTO.setName(hotel.getName());
        hotelDTO.setDescription(hotel.getDescription());
        hotelDTO.setAddress(hotel.getAddress());
        hotelDTO.setPhone(hotel.getPhone());
        hotelDTO.setCity(hotel.getCity());
        return hotelDTO;
    }

    private Hotel mapToEntity(final HotelDTO hotelDTO, final Hotel hotel) {
        hotel.setName(hotelDTO.getName());
        hotel.setDescription(hotelDTO.getDescription());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setPhone(hotelDTO.getPhone());
        hotel.setCity(hotelDTO.getCity());
        return hotel;
    }

    public String getReferencedWarning(final Long id) {
        final Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Room hotelRoom = roomRepository.findFirstByHotel(hotel);
        if (hotelRoom != null) {
            return WebUtils.getMessage("hotel.room.hotel.referenced", hotelRoom.getId());
        }
        return null;
    }

}
