package question1;

public class MovieTickets implements IMovieTickets {

    @Override
    public int TotalMovieSales(int[] movieTicketSales) {
        int total = 0;
        // just loop through each month's sales and keep adding them up
        for (int sales : movieTicketSales) {
            total += sales;
        }
        return total;
    }

    @Override
    public String TopMovie(String[] movies, int[] totalSales) {
        // start by assuming the first movie is the top one, then see if anything beats it
        int topIndex = 0;
        for (int i = 1; i < totalSales.length; i++) {
            if (totalSales[i] > totalSales[topIndex]) {
                topIndex = i;
            }
        }
        return movies[topIndex];
    }
}
