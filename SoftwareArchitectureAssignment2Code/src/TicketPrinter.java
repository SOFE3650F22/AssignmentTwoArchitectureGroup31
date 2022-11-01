import java.util.Stack;

public class TicketPrinter {
    // uses the ticket scanner
    // accesses the cash register, which accesses the database

    public void printTicket(){
        System.out.println("Printing ticket...");
    }

    // remove any extra characters in the receipt string
    static String formatReceipt(String stack) {
        String formattedString = stack
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .replace("{", "")
                .replace("}", "")
                .replace("=", "\t")
                .trim();
        return formattedString;
    }

}
