package question2;

public class MovieTickets implements IMovieTickets {

    private static final double VAT_RATE = 0.14;

    @Override
    public double CalculateTotalTicketPrice(int numberOfTickets, double ticketPrice) {
        // first get the basic total, then tack on the 14% VAT so we get the final amount
        double subtotal = numberOfTickets * ticketPrice;
        double vat = subtotal * VAT_RATE;
        return subtotal + vat;
    }

    @Override
    public boolean ValidateData(MovieTicketData movieTicketData) {
        // make sure we actually got some data to work with
        if (movieTicketData == null) {
            return false;
        }

        // movie name can't be blank, otherwise we don't know what they're buying tickets for
        if (movieTicketData.getMovieName() == null || movieTicketData.getMovieName().trim().isEmpty()) {
            return false;
        }

        // ticket price and number of tickets have to be more than zero to make sense
        if (movieTicketData.getTicketPrice() <= 0) {
            return false;
        }

        if (movieTicketData.getNumberOfTickets() <= 0) {
            return false;
        }

        return true;
    }
}
