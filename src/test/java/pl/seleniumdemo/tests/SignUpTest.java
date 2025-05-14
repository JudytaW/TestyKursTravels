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

        LoggedUserPage loggedUserPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstName("Jacek")
                .setLastName(lastName)
                .setPhone("123456780")
                .setEmail("tester" + randomNumber + "@test.pl")
                .setPassword("Testowy123")
                .setConfirmpassword("Testowy123")
                .SignUp();

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Hi, Jacek Testowy");

    }


    @Test
    public void signUpEmptyFormTest() {
        SignUpPage signUpPage = new HotelSearchPage(driver).openSignUpForm();
        // gdyby usunąć signUpPage dostalibyśmy się do zalogowanego użytkownika a ten test kończy się niepowodzeniem
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

        String lastName = "Testowy";

        SignUpPage signUpPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstName("Jacek")
                .setLastName(lastName)
                .setPhone("123456780")
                .setEmail("email")
                .setPassword("Testowy123")
                .setConfirmpassword("Testowy123");

        signUpPage.SignUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));

    }
}
