package GenericUtility;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

    public String readData(String file, String sheet, int row, int cell) throws Exception {
        Workbook wb = WorkbookFactory.create(new FileInputStream("./src/test/resources/" + file));
        Cell c = wb.getSheet(sheet).getRow(row).getCell(cell);
        return c == null ? "" : c.toString();
    }

    public int getRowCount(String file, String sheet) throws Exception {
        Workbook wb = WorkbookFactory.create(new FileInputStream("./src/test/resources/" + file));
        return wb.getSheet(sheet).getLastRowNum();
    }
}