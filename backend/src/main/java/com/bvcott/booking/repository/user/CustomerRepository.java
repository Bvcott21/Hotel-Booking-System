package com.bvcott.booking.repository.user;

import com.bvcott.booking.model.user.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
}
