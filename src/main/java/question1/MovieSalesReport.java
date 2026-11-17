package question1;

public class MovieSalesReport {

    public static void main(String[] args) {
        // Single-dimensional array for movie names
        String[] movies = {"Napoleon", "Oppenheimer"};

        // Two-dimensional array for monthly ticket sales (JAN, FEB, MAR)
        int[][] ticketSales = {
                {3000, 1500, 1700}, // Napoleon
                {3500, 1200, 1600}  // Oppenheimer
        };

        // this is the helper class that does the calculations for us
        IMovieTickets movieTickets = new MovieTickets();

        // add up all the monthly sales for each movie so we have the totals ready for the report
        int[] totalSales = new int[movies.length];
        for (int i = 0; i < movies.length; i++) {
            totalSales[i] = movieTickets.TotalMovieSales(ticketSales[i]);
        }

        // print out the report header and column labels so it looks nice and readable
        System.out.println("MOVIE TICKET SALES REPORT - 2024");
        System.out.println("------------------------------------------------");
        System.out.printf("%-15s %-10s %-10s %-10s %-10s%n", "Movie", "JAN", "FEB", "MAR", "Total");
        System.out.println("------------------------------------------------");

        // go through each movie and print its monthly numbers plus the total
        for (int i = 0; i < movies.length; i++) {
            System.out.printf("%-15s %-10d %-10d %-10d %-10d%n",
                    movies[i],
                    ticketSales[i][0],
                    ticketSales[i][1],
                    ticketSales[i][2],
                    totalSales[i]);
        }

        System.out.println("------------------------------------------------");

        // figure out which movie sold the most tickets overall and show it at the bottom
        String topMovie = movieTickets.TopMovie(movies, totalSales);
        System.out.println("Top performing movie: " + topMovie);
    }
}
