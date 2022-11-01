public class CashRegisterScanner {
    //creates local variables needed to run this class
    private String barcode;
    private int itemQuantity;

    //scans barcode and sends barcode to cash register to be added
    public void addScanBarcode(CashRegister register){
        barcode = NewGUI.input;
        try {
            itemQuantity = Integer.parseInt(NewGUI.quantity);
            register.addItem(barcode, itemQuantity);
        } catch (Exception e){
            System.out.println("Please enter an Integer for the item Quantity");
        }
    }

    //scans barcode and sends barcode to cash register to be removed
    public void dropScanBarcode(CashRegister register){
        barcode = NewGUI.input;
        try {
            itemQuantity = Integer.parseInt(NewGUI.quantity);
            register.dropItem(barcode, itemQuantity);
        } catch(Exception e){
            System.out.println("Please enter an Integer for the item Quantity");
        }
    }

}
