import java.util.*;
public class CashRegister {

    public CashRegister(){
        Display display = new Display();
    }
    //Setting local variables needed to run the cashRegister class
    private double subtotal = 0.00d;
    private double subtotalWithTax = 0.00d;
    private double amountTaxed = 0.00d;
    private double finalSale = 0.00d;
    private double amountDiscounted = 0.00d;
    private final float tax = 0.13f;
    private double discount = 0.00d;
    private boolean addOrDrop = true;

    //Creating a connection to database object to access item information
    private final Database  db = new Database();

    //cart holds item quantity per item and each items' respective price for calculation and manipulation
    private HashMap<String, List<Double>> cart = new HashMap<String, List<Double>>();

    //returns the subtotal
    public double getSubtotal() {
        return subtotal;
    }

    //returns the tax percentage
    public float getTax() {
        return tax;
    }

    //returns the subtotal after tax (subtotal after tax is only calculated after going to checkout)
    public double getSubtotalTax() {
        return subtotalWithTax;
    }

    //returns the amount being taxed on the subtotal (the amount taxed is only calculated after going to checkout)
    public double getAmountTaxed() {
        return amountTaxed;
    }
    //returns the discount percentage
    public double getDiscount() {return discount; }
    //returns the amount being discounted
    public double getAmountDiscounted() {return amountDiscounted; }

    //returns final sale (finale sale is only calculated after proceeding to checkout)
    public double getFinalSale(){
        return finalSale;
    }

    //updates the subtotal
    private void updateSubtotal(double itemPrice, int itemQuantity) {
        if (addOrDrop) {
            subtotal += itemQuantity * itemPrice;
        }
        else {
            subtotal -= itemQuantity * itemPrice;
        }
    }

    //adds item(s) to the cart and updates the item quantity in the cart
    public void addItem(String barcode, int itemQuantity) {

        double itemPrice = db.getItemPrice(barcode);
        String itemName = db.getItemName(barcode);
        addOrDrop = true;
        List<Double> itemInfo = new ArrayList<>();

        if (itemPrice == -1d){
            System.out.println("Item does not exist in the database. \n");
            return;
        }
        else if (itemQuantity <= 0){
            System.out.print("You cannot add an item of zero or less quantity. ");
            System.out.println("If you'd like to remove an item please use the drop item feature. \n");
            return;
        }
        else if (cart.containsKey(barcode)){
            itemInfo = cart.get(barcode);
            itemInfo.set(0, itemInfo.get(0) + itemQuantity);
            cart.put(barcode, itemInfo);
        }
        else {
            itemInfo.add((double) itemQuantity);
            itemInfo.add(itemPrice);
            cart.put(barcode, itemInfo);
        }

        // TODO call addToReceipt(name, quantity, price)
        Display.printProduct(itemName, itemQuantity, itemPrice, addOrDrop);
        System.out.println("Item(s) have been successfully added to the cart. \n");
        updateSubtotal(itemPrice, itemQuantity);
    }

    //removes item(s) from the cart and updates cart quantity
    public void dropItem(String barcode, int itemQuantity) {

        addOrDrop = false;
        double itemPrice =  db.getItemPrice(barcode);
        String itemName = db.getItemName(barcode);

        if (itemPrice == -1d){
            System.out.println("Item does not exist in the database. \n");
            return;
        }
        else if (itemQuantity <= 0){
            System.out.print("You cannot remove an item of zero or less quantity. ");
            System.out.println("If you'd like to add an item please use the add item feature. \n");
            return;
        }
        else if (!cart.containsKey(barcode)){
            System.out.println("Your cart does not contain this item. \n");
            return;
        }
        else if (cart.containsKey(barcode)){
            List<Double> itemInfo = new ArrayList<>();
            itemInfo = cart.get(barcode);
            itemInfo.set(0, itemInfo.get(0) - itemQuantity);

            if (itemInfo.get(0) == 0){
                cart.remove(barcode);
            }

            Display.printProduct(itemName, itemQuantity, itemPrice, addOrDrop);
            System.out.println("Item(s) have been successfully been removed from the cart. \n");
            updateSubtotal(itemPrice, itemQuantity);
        }

    }

    public void setDiscount (double discount){ this.discount = discount/100d;}

    //calculates the amount being taxed on a purchase
    private void calculateAmountTaxed() {
        amountTaxed = subtotal * tax;
    }

    //calculates the subtotal with tax
    private void calculateSubtotalWithTax() {
        subtotalWithTax = subtotal * (1.00f + tax);
    }

    //calculates discount amount
    private void calculateDiscount() {
        amountDiscounted = subtotalWithTax * discount;
    }

    //calculates final sale
    private void calculateFinalSale(){
        finalSale = subtotalWithTax - amountDiscounted;
    }

    //go to checkout method makes all calculations necessary for checkout
    //(applies discount calculates tax etc...)
    public void goToCheckout(){

        //Making Sale calculations
        calculateAmountTaxed();
        calculateSubtotalWithTax();
        calculateDiscount();
        calculateFinalSale();
    }

}
