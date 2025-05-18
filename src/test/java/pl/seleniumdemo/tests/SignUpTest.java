package pl.seleniumdemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.LoggedUserPage;
import pl.seleniumdemo.pages.SignUpPage;

import java.util.List;

public class SignUpTest extends BaseTest {


    @Test
    public void signUpTest() {
        String lastName = "Testowy";
        int randomNumber = (int) (Math.random() * 1000);
        String email = "tester" + randomNumber + "@test.pl";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Jacek");
        signUpPage.setLastName(lastName);
        signUpPage.setPhone("123456780");
        signUpPage.setEmail("tester" + randomNumber + "@test.pl");
        signUpPage.setPassword("Testowy123");
        signUpPage.setConfirmpassword("Testowy123");
        signUpPage.SignUp();

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Hi, Jacek Testowy");

    }


    @Test
    public void signUpEmptyFormTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.SignUp();

        List<String> errors = signUpPage.getErrors();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errors.contains("The Email field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The First name field is required."));
        softAssert.assertTrue(errors.contains("The Last Name field is required."));
        softAssert.assertAll();
    }

    @Test
    public void signUpInvalidEmailTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        String lastName = "Testowy";

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Jacek");
        signUpPage.setLastName(lastName);
        signUpPage.setPhone("123456780");
        signUpPage.setEmail("email");
        signUpPage.setPassword("Testowy123");
        signUpPage.setConfirmpassword("Testowy123");

        signUpPage.SignUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));

    }
}
