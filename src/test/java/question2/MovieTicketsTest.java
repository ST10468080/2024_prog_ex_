package question2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTicketsTest {

    @Test
    public void CalculateTotalTicketPrice_CalculatedSuccessfully() {
        // set up a simple purchase: 2 tickets at R100 each
        IMovieTickets movieTickets = new MovieTickets();
        int numberOfTickets = 2;
        double ticketPrice = 100.0;
        double expectedTotal = 228.0; // (2 * 100) + 14% VAT = 228.0

        // run the calculation
        double actualTotal = movieTickets.CalculateTotalTicketPrice(numberOfTickets, ticketPrice);

        // confirm we get the VAT-included total we expect
        assertEquals(expectedTotal, actualTotal, 0.001);
    }

    @Test
    public void ValidateData_InvalidTicketPrice_ReturnsFalse() {
        // try feeding in bad data (price is 0) to make sure validation catches it
        IMovieTickets movieTickets = new MovieTickets();
        MovieTicketData invalidData = new MovieTicketData("Napoleon", 2, 0.0);

        // ask the validator what it thinks
        boolean isValid = movieTickets.ValidateData(invalidData);

        // it should definitely say "nope, not valid"
        assertFalse(isValid);
    }
}
