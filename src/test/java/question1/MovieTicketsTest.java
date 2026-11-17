package question1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTicketsTest {

    @Test
    public void CalculateTotalSales_ReturnsExpectedTotalSales() {
        // set up some ticket numbers like we would get from the report
        IMovieTickets movieTickets = new MovieTickets();
        int[] napoleonSales = {3000, 1500, 1700};
        int expectedTotal = 6200;

        // ask the method to do the adding up
        int actualTotal = movieTickets.TotalMovieSales(napoleonSales);

        // check that it came back with the right total
        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    public void TopMovieSales_ReturnsTopMovie() {
        // give it two movies and their totals to see which one wins
        IMovieTickets movieTickets = new MovieTickets();
        String[] movies = {"Napoleon", "Oppenheimer"};
        int[] totalSales = {6200, 6300};
        String expectedTopMovie = "Oppenheimer";

        // let the method pick the top performer
        String actualTopMovie = movieTickets.TopMovie(movies, totalSales);

        // make sure it picked the right one
        assertEquals(expectedTopMovie, actualTopMovie);
    }
}
