package sn.sir.booking.sir_booking_app.repos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sn.sir.booking.sir_booking_app.domain.Customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    // init data before each Test
    @BeforeEach
    void setUp() {
        Customer customer1 = new Customer("toto", "toto", "toto@gmail.com", "777777777");
        Customer customer2 = new Customer("tata", "tata", "tata@gmail.com", "770000000");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    public void testFindAll(){
        //when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertEquals(2, customers.size());
    }

    @Test
    public void testFindByEmail(){
        //given
        String email = "toto@gmail.com";


        //when
        Customer customer = customerRepository.findByEmail(email);

        //then
        assertNotNull(customer);
        assertTrue(customer.getId()>0);
        assertEquals(email, customer.getEmail());
        assertEquals("toto", customer.getFirstName());
    }
}