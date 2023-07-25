package sn.sir.booking.sir_booking_app.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import sn.sir.booking.sir_booking_app.domain.Customer;
import sn.sir.booking.sir_booking_app.model.CustomerDTO;
import sn.sir.booking.sir_booking_app.repos.CustomerRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void findAll() {
        //Given
        List<Customer> initCustomers = List.of(
                new Customer(1L, "toto", "toto", "toto@gmail.com", "778889988"),
                new Customer(2L, "tata", "tata", "tata@gmail.com", "778889911"));

        // simulate the behavior of the customerRepository to call Database and return the initCustomers
        when(customerRepository.findAll()).thenReturn(initCustomers);

        //when
        List<CustomerDTO> customers = customerService.findAll();

        //Then
        assertEquals(initCustomers.size(), customers.size());
    }
}