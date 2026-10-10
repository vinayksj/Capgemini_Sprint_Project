package GenericUtility;
import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtility {

    public static void takeScreenshot(WebDriver driver,String filename) throws Exception {

        TakesScreenshot tks = (TakesScreenshot) driver;

        File src = tks.getScreenshotAs(OutputType.FILE);

        File dest = new File("./Screenshot/"+filename+".png");

        FileHandler.copy(src, dest);
    }
} 
