package question2;

public interface IMovieTickets {
    // sets the rules for calculating ticket totals and checking if the entered data is valid
    double CalculateTotalTicketPrice(int numberOfTickets, double ticketPrice);

    boolean ValidateData(MovieTicketData movieTicketData);
}
