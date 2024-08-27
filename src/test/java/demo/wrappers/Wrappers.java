package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     * 
     */

    public void exploreTab(String tabName, WebDriver driver) {
        WebElement element = driver.findElement(By.xpath("//a[@title='" + tabName + "']"));
        element.click();
    }

    public void nextBtn(String rowName, WebDriver driver) {
        WebElement nextBtn = driver.findElement(By.xpath(
                "//span[@id='title' and  contains(text(),'" + rowName
                        + "')]/ancestor::div[@id='dismissible']//button[@aria-label='Next']"));
        while (nextBtn.isDisplayed()) {
            nextBtn.click();

        }
    }

}
