import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Display {
    static HashMap<String, List<Double>> displayOutput = new HashMap<>();

    // constructor
    public Display(){
        // create window for the GUI
        JFrame frame = new JFrame("NewGUI");
        frame.setContentPane(new NewGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // printing the data onto the screen
    public static void printProduct(String name, int quantity, double price, boolean addOrDrop){
        List<Double> itemInfo = new ArrayList<>();
        if(displayOutput.containsKey(name) && addOrDrop){
            itemInfo = displayOutput.get(name);
            itemInfo.set(0,  itemInfo.get(0) + quantity);
        }
        else if(displayOutput.containsKey(name) && (displayOutput.get(name).get(0) - quantity == 0)){
            displayOutput.remove(name);
            return;
        }
        else if(displayOutput.containsKey(name) && !addOrDrop){
            itemInfo = displayOutput.get(name);
            itemInfo.set(0,  itemInfo.get(0) - quantity);
        }
        else {
            itemInfo.add((double)quantity);
            itemInfo.add(price);
        }
        displayOutput.put(name, itemInfo);

    }

}
