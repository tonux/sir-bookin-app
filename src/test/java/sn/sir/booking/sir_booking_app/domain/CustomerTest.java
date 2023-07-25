package sn.sir.booking.sir_booking_app.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    public void testCustomerConstructorAndGetters(){
        // Given
        Long id = 1L;
        String firstName = "toto";
        String lastName = "tata";
        String email = "toto@gmail.com";
        String phone = "777777777";

        //when
        Customer customer = new Customer(id, firstName, lastName, email, phone);

        //Then
        assertEquals(id, customer.getId());
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
    }

    @Test
    public void testCustomerConstructorAndSetters(){
        //Given
        Customer customer = new Customer();

        //When
        customer.setId(1L);
        customer.setFirstName("toto");
        customer.setLastName("tata");
        customer.setEmail("toto@gmail.com");
        customer.setPhone("777777777");

        //then
        assertEquals(1L, customer.getId());
        assertEquals("toto", customer.getFirstName());
        assertEquals("tata", customer.getLastName());
        assertEquals("toto@gmail.com", customer.getEmail());
        assertEquals("777777777", customer.getPhone());
    }

}