import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.*;

public class Database {
    //creates two hashmaps to store information from Excel sheet (our version of a database)
    HashMap<String, Double> itemPrices = new HashMap<String, Double>();
    HashMap<String, String> itemNames = new HashMap<String, String>();
    //Gets user directory for file path
    String path = System.getProperty("user.dir") + "//Shop Items.xlsx";

    public Database() {
        //Reads from an Excel sheet to store item information into the hash maps
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheet("Sheet1");

            //creates row and cell iterators to navigate through the Excel sheet
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Iterator<Cell> cellIterator2 = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    Cell nextCell;
                    cellIterator2.next();

                    //Check the cell type and extracts data into the proper hash map
                    //i.e. the name or price hash map
                    switch (cell.getCellType()) {
                        case STRING:
                            nextCell = cellIterator2.next();
                            itemNames.put(nextCell.getStringCellValue(), cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            break;
                        case FORMULA:
                            nextCell = cellIterator.next();
                            itemPrices.put(cell.getStringCellValue(), nextCell.getNumericCellValue());
                            break;
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Returns the price of the item/barcode requested
    public double getItemPrice(String barcode) {
        if (itemPrices.containsKey(barcode)) {
            double itemPrice = itemPrices.get(barcode);
            return itemPrice;
        }
        return -1d;
    }

    //returns the name of the item/barcode requested
    public String getItemName(String barcode){
        if (itemNames.containsKey(barcode)){
            String itemName = itemNames.get(barcode);
            return itemName;
        }
        return "";
    }
}

