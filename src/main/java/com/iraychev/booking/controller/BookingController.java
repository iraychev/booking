package com.iraychev.booking.controller;

import com.iraychev.booking.DTO.BookingDTO;
import com.iraychev.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        ResponseEntity<BookingDTO> response = bookingService.createBooking(bookingDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable UUID bookingId) {
        BookingDTO bookingDTO = bookingService.getBookingById(bookingId);
        if (bookingDTO != null) {
            return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable UUID bookingId, @RequestBody BookingDTO bookingDTO) {
        ResponseEntity<BookingDTO> response = bookingService.updateBookingById(bookingId, bookingDTO);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID bookingId) {
        if (bookingService.deleteBookingById(bookingId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}