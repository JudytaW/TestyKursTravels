package pl.seleniumdemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HotelSearchPage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HotelSearchTest extends BaseTest {

    @Test
    public void searchHotelTest() throws InterruptedException {

        HotelSearchPage hotelSearchPage= new HotelSearchPage(driver);
        hotelSearchPage.setCity("Dubai");
        hotelSearchPage.setDates("27/04/2026", "29/04/2026");
        hotelSearchPage.setTravellers();
        hotelSearchPage.performSearch();


        List<String> hotelNames = driver.findElements(By.xpath("//h4[contains(@class,'list_title')]//b")).stream()
                .map(el -> el.getAttribute("textContent"))
                .collect(Collectors.toList());

        //System.out.println(hotelNames.size());
        //hotelNames.forEach(el-> System.out.println(el));

        hotelNames.forEach(System.out::println);

        Assert.assertEquals(hotelNames.get(0), "Jumeirah Beach Hotel");
        Assert.assertEquals( hotelNames.get(1), "Oasis Beach Tower");
        Assert.assertEquals( hotelNames.get(2), "Rose Rayhaan Rotana");
        Assert.assertEquals( hotelNames.get(3), "Hyatt Regency Perth");

    }

    @Test
    public void searchHotelWithoutNameTest() throws InterruptedException {

        // wprowadzenie daty poprzez wpisanie
        driver.findElement(By.name("checkin")).sendKeys("25/04/2026");
        // użycie kalendarza
        driver.findElement(By.name("checkout")).click();
        driver.findElements(By.xpath("//td[@class='day ' and text()='30']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElement(By.id("travellersInput")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()=' Search']")).click();

        WebElement noResultHeading =  driver.findElement(By.xpath("//div[@class='itemscontainer']//h2"));
        Assert.assertTrue(noResultHeading.isDisplayed());
        Assert.assertEquals(noResultHeading.getText(),"No Results Found");

    }

}
