package pl.seleniumdemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.ResultsPage;

import java.util.List;

public class HotelSearchTest extends BaseTest {

    @Test
    public void searchHotelTest() throws InterruptedException {

        HotelSearchPage hotelSearchPage= new HotelSearchPage(driver);
        hotelSearchPage.setCity("London");
        hotelSearchPage.setDates("27/04/2026", "29/04/2026");
        hotelSearchPage.setTravellers(1,2);
        hotelSearchPage.performSearch();

        ResultsPage resultsPage= new ResultsPage(driver);

        List<String> hotelNames = resultsPage.getHotelNames();

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

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setDates("25/04/2026", "30/04/2026");
        hotelSearchPage.setTravellers(0,1);
        hotelSearchPage.performSearch();


       /* driver.findElements(By.xpath("//td[@class='day ' and text()='30']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);*/

        ResultsPage resultsPage = new ResultsPage(driver);

        //Assert.assertTrue(noResultHeading.isDisplayed());
        Assert.assertTrue(resultsPage.resultHeading.isDisplayed());
        Assert.assertEquals(resultsPage.getHeadingText(),"No Results Found");

    }

}
