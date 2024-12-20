package it.epicode.bookings_manager.mapper;

import it.epicode.bookings_manager.dto.BookingResponse;
import it.epicode.bookings_manager.entity.Booking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    private final ModelMapper modelMapper;

    public BookingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BookingResponse toDto(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setUsername(booking.getUser().getUsername());
        response.setWorkstationId(booking.getWorkstation().getId());
        response.setDate(booking.getDate());
        return response;
    }
}
