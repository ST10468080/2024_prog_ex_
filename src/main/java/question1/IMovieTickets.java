package question1;

public interface IMovieTickets {
    // handy contract for anything that can add up movie sales and find the top movie
    int TotalMovieSales(int[] movieTicketSales);

    String TopMovie(String[] movies, int[] totalSales);
}
