import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import static java.lang.Integer.parseInt;

public class NewGUI {
    public JPanel panel1;
    private JButton DONEButton;
    private JButton scanItemButton;
    private JTextPane ReceiptView;
    private JTextField enterOrScanBarcodeTextField;
    private JTextPane CASHREGISTERTextPane;
    private JButton removeLastItemButton;
    private JTextPane enterItemQuantityTextPane;
    private JButton applyDiscountCodeButton;
    private JFormattedTextField discountCodeFormattedTextField;
    Stack<String> receipt = new Stack<>();
    public static String input;
    public static String quantity;
    DecimalFormat df = new DecimalFormat("0.00");
    DecimalFormat df2 = new DecimalFormat("0");
    String productListTextOutput = "";
   HashMap<String, List<Double>> productList;

    CashRegisterScanner registerScanner = new CashRegisterScanner();

    public NewGUI() {
        // action listeners for the buttons
        scanItemButton.addActionListener(e -> scanItem());
        removeLastItemButton.addActionListener(e -> removeItem());
        DONEButton.addActionListener(e -> checkout());
        applyDiscountCodeButton.addActionListener(e -> applyDiscount());
        panel1.setPreferredSize(new Dimension(640, 600));
    }

    // applying the discount button actions
    private void applyDiscount() {
        String discountInput = discountCodeFormattedTextField.getText();
        try {
            TestDriver.reg.setDiscount(Double.parseDouble(discountInput));
        } catch (Exception e){
            System.out.println("Please enter a valid discount code");
        }
    }

    // remove item button
    private void removeItem() {
        input = enterOrScanBarcodeTextField.getText();
        quantity = enterItemQuantityTextPane.getText();
        registerScanner.dropScanBarcode(TestDriver.reg);
        printItems();
    }

    // scanning button
    private void scanItem() {
        // intake barcode and send it to cash register
        input = enterOrScanBarcodeTextField.getText();
        quantity = enterItemQuantityTextPane.getText();
        // get product information from cash register
        // call method that gets the database info in cash register
        registerScanner.addScanBarcode(TestDriver.reg);
        printItems();
    }

    // checkout button
    private void checkout() {
        // print the ticket from the cash register
        CASHREGISTERTextPane.setText("PURCHASE SUCCESSFUL. TICKET PRINTED.");
        TestDriver.reg.goToCheckout();
        double subtotal, subtotalWithTax, amountTaxed, tax, finalSale, amountDiscounted, discount;
        subtotal = TestDriver.reg.getSubtotal();
        tax = TestDriver.reg.getTax();
        subtotalWithTax = TestDriver.reg.getSubtotalTax();
        amountTaxed = TestDriver.reg.getAmountTaxed();
        amountDiscounted = TestDriver.reg.getAmountDiscounted();
        discount = TestDriver.reg.getDiscount();
        finalSale = TestDriver.reg.getFinalSale();

        // print subtotal, tax, and discount information
        String totalDisplay = "--------------------\nSubtotal:\t\t$"+ df.format(subtotal) +"\nSubtotal with tax:\t$" + df.format(subtotalWithTax)
                + "\nAmount Taxed:\t$" + df.format(amountTaxed) + "\nHST Tax Percentage:\t" + df.format(tax*100) + "%" + "\nAmount Discounted:\t$"
                + df.format(amountDiscounted) + "\nDiscount Percentage:\t" + df.format(discount*100) + "%"  +"\nAmount Due:\t$"+ df.format(finalSale);
        String output =  productListTextOutput + totalDisplay;
        output = TicketPrinter.formatReceipt(output);
        ReceiptView.setText(output);

    }

    // printing items onto the screen
    private void printItems() {
        productList = Display.displayOutput;
        productListTextOutput = "";
        for (Map.Entry<String, List<Double>> entry : productList.entrySet()) {
            productListTextOutput += "Item: " + entry.getKey() + "     Quantity: " + df2.format(entry.getValue().get(0)) + "     Unit Price: $" + df.format(entry.getValue().get(1)) + "\n";
        }
        ReceiptView.setText(productListTextOutput);
    }


    private void createUIComponents(){

    }



}
