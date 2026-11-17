package question2;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// here is the main form that shows up when you run the program
public class MovieTicketForm extends JFrame {

    // these are all the boxes / fields on the screen
    private final JComboBox<String> cmbMovie;
    private final JTextField txtNumberOfTickets;
    private final JTextField txtTicketPrice;
    private final JTextArea txtReport;

    // this is the class that does the actual calculations and validation
    private final IMovieTickets movieTickets;

    public MovieTicketForm() {
        super("Movie Ticket Sales");
        movieTickets = new MovieTickets();

        // HERE we set up all the input bits the user will interact with
        cmbMovie = new JComboBox<>(new String[]{"Napoleon", "Oppenheimer", "Damsel"});
        txtNumberOfTickets = new JTextField(15);
        txtTicketPrice = new JTextField(15);
        txtReport = new JTextArea(12, 35);
        txtReport.setEditable(false); // report area is just for showing results, not typing

        // HERE we arrange everything on the form so it's not a mess
        setLayout(new BorderLayout());

        // this panel holds the movie dropdown + the two text fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Select Movie:"));
        inputPanel.add(cmbMovie);
        inputPanel.add(new JLabel("Number of Tickets:"));
        inputPanel.add(txtNumberOfTickets);
        inputPanel.add(new JLabel("Ticket Price:"));
        inputPanel.add(txtTicketPrice);

        // HERE the report panel just wraps the text area with a nice border title
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createTitledBorder("Movie Ticket Sales Report"));
        reportPanel.add(new JScrollPane(txtReport), BorderLayout.CENTER);

        // stick the inputs at the top and the report in the middle
        add(inputPanel, BorderLayout.NORTH);
        add(reportPanel, BorderLayout.CENTER);

        // HERE we hook up the menu bar with all the actions (Exit, Process, Clear)
        setJMenuBar(createMenuBar());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // pop the window up in the middle of the screen
    }

    // HERE this builds the File and Tools menus
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu - just Exit for now
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0)); // closes the whole app
        fileMenu.add(exitItem);

        // Tools menu - Process does the sale, Clear wipes the form
        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");

        // when they click Process / Clear, call the methods below
        processItem.addActionListener(e -> processTicketSale());
        clearItem.addActionListener(e -> clearForm());

        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        return menuBar;
    }

    // HERE this is what runs when the user clicks Process
    private void processTicketSale() {
        try {
            // grab whatever the user typed/selected from the form
            String movieName = (String) cmbMovie.getSelectedItem();
            int numberOfTickets = Integer.parseInt(txtNumberOfTickets.getText().trim());
            double ticketPrice = Double.parseDouble(txtTicketPrice.getText().trim());

            // pack it into a little data object so we can validate it easily
            MovieTicketData data = new MovieTicketData(movieName, numberOfTickets, ticketPrice);

            // HERE we do a quick sanity check before we do any math or show anything
            if (!movieTickets.ValidateData(data)) {
                JOptionPane.showMessageDialog(this,
                        "Invalid data. Please ensure:\n"
                                + "- Movie name is selected\n"
                                + "- Number of tickets is greater than 0\n"
                                + "- Ticket price is greater than 0",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return; // stop here if the data is bad
            }

            // HERE we work out the total (VAT included) and build the report text
            double totalPrice = movieTickets.CalculateTotalTicketPrice(numberOfTickets, ticketPrice);

            String report = generateReport(movieName, numberOfTickets, ticketPrice, totalPrice);
            txtReport.setText(report); // show it on the screen

            // once the report looks good on screen, save it to a file too
            saveReportToFile(report);

        } catch (NumberFormatException ex) {
            // this usually means someone typed letters where numbers should be
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric values for number of tickets and ticket price.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // HERE this just builds the report as a big string
    private String generateReport(String movieName, int numberOfTickets, double ticketPrice, double totalPrice) {
        // subtotal is tickets * price, VAT is whatever is left after that from the total
        double subtotal = numberOfTickets * ticketPrice;
        double vat = totalPrice - subtotal;

        // put together the report text line by line so it's easy to read (and save later)
        StringBuilder sb = new StringBuilder();
        sb.append("MOVIE TICKET SALES REPORT\n");
        sb.append("----------------------------------------\n");
        sb.append("Movie: ").append(movieName).append("\n");
        sb.append("Number of Tickets: ").append(numberOfTickets).append("\n");
        sb.append(String.format("Ticket Price: R %.2f%n", ticketPrice));
        sb.append(String.format("Subtotal: R %.2f%n", subtotal));
        sb.append(String.format("VAT (14%%): R %.2f%n", vat));
        sb.append("----------------------------------------\n");
        sb.append(String.format("Total Price (incl. VAT): R %.2f%n", totalPrice));
        return sb.toString();
    }

    // HERE is where the file writer does its thing and saves the report to report.txt
    private void saveReportToFile(String report) {
        // basically we open the file, dump the report text in there, and close it nicely
        try (PrintWriter writer = new PrintWriter(new FileWriter("report.txt"))) {
            writer.print(report);
        } catch (IOException ex) {
            // if something goes wrong while saving, let the user know instead of just failing silently
            JOptionPane.showMessageDialog(this,
                    "Error saving report to file: " + ex.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // HERE this just clears everything so you can start over
    private void clearForm() {
        // reset everything back to a clean slate so the user can start fresh
        cmbMovie.setSelectedIndex(0);
        txtNumberOfTickets.setText("");
        txtTicketPrice.setText("");
        txtReport.setText("");
    }

    // HERE this is the starting point - opens the form on the Swing thread
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieTicketForm().setVisible(true));
    }
}
