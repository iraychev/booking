package com.iraychev.server.controller;

import com.iraychev.model.DTO.BookingDTO;
import com.iraychev.server.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking-api/bookings")
public class BookingController implements Controller<BookingDTO> {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingDTO bookingDTO) {

        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAll() {
        List<BookingDTO> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getById(@PathVariable UUID bookingId) {
        BookingDTO bookingDTO = bookingService.getBookingById(bookingId);

        if (bookingDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<BookingDTO>> geAllByListingId(@PathVariable UUID listingId) {
        List<BookingDTO> bookings = bookingService.geAllByListingId(listingId);

        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> update(@PathVariable UUID bookingId, @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBookingById(bookingId, bookingDTO);

        if (updatedBooking != null) {
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> delete(@PathVariable UUID bookingId) {
        if (bookingService.deleteBookingById(bookingId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}