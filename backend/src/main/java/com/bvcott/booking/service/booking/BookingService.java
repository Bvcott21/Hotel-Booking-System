package com.bvcott.booking.service.booking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bvcott.booking.dto.booking.CreateBookingAndTransactionDTO;
import com.bvcott.booking.exception.user.ActionNotAllowedException;
import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;
import com.bvcott.booking.model.payment.BookingTransaction;
import com.bvcott.booking.model.payment.PaymentDetails;
import com.bvcott.booking.model.payment.TransactionStatus;
import com.bvcott.booking.model.user.Customer;
import com.bvcott.booking.repository.hotel.HotelRepository;
import com.bvcott.booking.repository.hotel.HotelRoomRepository;
import com.bvcott.booking.repository.payment.PaymentDetailsRepository;
import com.bvcott.booking.repository.payment.TransactionRepository;
import com.bvcott.booking.repository.user.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.model.booking.Booking;
import com.bvcott.booking.repository.booking.BookingRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepo;
    private final HotelRepository hotelRepo;
    private final HotelRoomRepository hotelRoomRepo;
    private final TransactionRepository transactionRepo;
    private final PaymentDetailsRepository paymentDetailsRepo;
    private final CustomerRepository customerRepo;

    public List<Booking> findAll() {
        log.info("findAll triggered");
        return bookingRepo
            .findAll();
    }

    public Booking findById(UUID bookingId) {
        log.info("findById triggered");
        return bookingRepo
            .findById(bookingId)
            .orElseThrow(() -> 
                new ResourceNotFoundException("Booking notfound with the provided ID."));    
    }

    public BookingTransaction createBookings(UUID customerId, CreateBookingAndTransactionDTO dto) {
        log.info("createBookings triggered");
        BookingTransaction txn = new BookingTransaction();

        log.debug("Finding customer by ID: {}", customerId);
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with the provided ID."));

        log.debug("converting booking DTOs to booking entities");
        BookingTransaction finalTxn = txn;
        List<Booking> bookingsToSave = dto
                .getBookings()
                .stream()
                .map(bookingDto -> {
                    log.debug("Creating booking model object for DTO: {}", bookingDto);
                    Booking booking = new Booking();
                    log.debug("Fetching Hotel with ID: {}", bookingDto.getHotelId());
                    Hotel hotel = hotelRepo.findById(bookingDto.getHotelId())
                            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
                    booking.setHotel(hotel);
                    log.debug("Hotel found, linking to booking");

                    log.debug("Looking for Rooms with IDs: {}", bookingDto.getHotelRoomIds());
                    List<HotelRoom> rooms = bookingDto
                            .getHotelRoomIds()
                            .stream().map(roomId
                                    -> hotelRoomRepo.findById(roomId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Room not found")))
                            .toList();

                    log.debug("Checking if all rooms belong to hotel.");
                    if(!new HashSet<>(hotel.getRooms()).containsAll(rooms)) {
                        throw new ActionNotAllowedException("One or more rooms do not belong to the specified hotel.");
                    }
                    log.debug("Rooms found, linking to booking");
                    booking.setRooms(rooms);

                    log.debug("Setting checkin and checkout");
                    booking.setCheckin(bookingDto.getCheckin());
                    booking.setCheckout(bookingDto.getCheckout());

                    log.debug("Calculating price");
                    double totalPrice = 0.0;

                    for (HotelRoom room : rooms) {
                        room.setCheckin(bookingDto.getCheckin());
                        room.setCheckout(bookingDto.getCheckout());
                        long daysBetween = ChronoUnit.DAYS.between(booking.getCheckin(), booking.getCheckout());
                        totalPrice += (room.getPrice() * daysBetween);
                    }

                    log.debug("Total price: {}", totalPrice);
                    booking.setPrice(totalPrice);

                    log.debug("Linking booking to customer and transaction");
                    customer.addBooking(booking);
                    finalTxn.addBooking(booking);

                    return booking;
                })
                .toList();

                log.debug("Finding Payment Details by ID: {}", dto.getPaymentDetailsId());
                PaymentDetails pDetails = paymentDetailsRepo
                        .findById(dto.getPaymentDetailsId())
                        .orElseThrow(() -> new ResourceNotFoundException("Payment details not found with the provided ID."));

                log.debug("Payment Details found, checking if it's owned by the requester.");
                if (customer.getPaymentDetails().stream().noneMatch(pd -> pd.getPaymentDetailsId().equals(pDetails.getPaymentDetailsId()))) {
                    throw new ActionNotAllowedException("Payment details don't belong to the customer, transaction cannot be processed.");
                }

                log.debug("Payment details belong to requester, updating transactions payment details, status, adding transaction to customer, setting creation time and persisting change...");
                txn.setPaymentDetails(pDetails);
                txn.setStatus(TransactionStatus.PENDING);
                customer.addTransaction(txn);
                txn.setCreatedAt(LocalDateTime.now());
                customerRepo.save(customer);
                txn = transactionRepo.save(txn);

                log.debug("Transaction persisted successfully, returning {}", txn);
                return txn;

    }
}
