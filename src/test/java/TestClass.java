import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TestClass {
    String baseUrl = "http://automationpractice.com/";
    WebDriver driver;
    Faker faker = new Faker();
    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());

    @BeforeMethod
    public void getUrlAndMaximizeWindow(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @Test(priority = 1)
    public void registerAccount(){
        WebElement signInBtn = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a"));
        signInBtn.click();

        //wait for page to load
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement registrationEmail = driver.findElement(By.id("email_create"));
        registrationEmail.sendKeys(fakeValuesService.bothify("?????##@somemail.com"));

        WebElement createAcc = driver.findElement(By.id("SubmitCreate"));
        createAcc.click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Form Elements/Variables
        WebElement gender2 = driver.findElement(By.id("id_gender2"));
        WebElement firstName = driver.findElement(By.id("customer_firstname"));
        WebElement lastName = driver.findElement(By.id("customer_lastname"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("passwd"));
        Select day = new Select(driver.findElement(By.id("days")));
        Select month = new Select (driver.findElement(By.id("months")));
        Select year = new Select(driver.findElement(By.id("years")));
        WebElement newsletter = driver.findElement(By.id("newsletter"));
        WebElement specOffers = driver.findElement(By.id("optin"));
        WebElement company = driver.findElement(By.id("company"));
        WebElement address1 = driver.findElement(By.id("address1"));
        WebElement address2 = driver.findElement(By.id("address2"));
        WebElement city = driver.findElement(By.id("city"));
        Select state = new Select(driver.findElement(By.id("id_state")));
        WebElement zipCode = driver.findElement(By.id("postcode"));
        Select country = new Select(driver.findElement(By.id("id_country")));
        WebElement additionalInfo = driver.findElement(By.id("other"));
        WebElement homePhone = driver.findElement(By.id("phone"));
        WebElement mobilePhone = driver.findElement(By.id("phone_mobile"));
        WebElement alias = driver.findElement(By.id("alias"));
        WebElement submitBtn = driver.findElement(By.id("submitAccount"));

        //Personal information
        gender2.click();
        firstName.sendKeys(faker.name().firstName());
        lastName.sendKeys(faker.name().lastName());
        email.clear();
        email.sendKeys(faker.bothify(fakeValuesService.bothify("?????##@yahoo.com")));
        password.sendKeys(faker.bothify(fakeValuesService.bothify("???##??")));
        day.selectByIndex(Functions.randomRange(1, 31));
        month.selectByIndex(Functions.randomRange(1, 12));
        year.selectByIndex(Functions.randomRange(1, 120));
        newsletter.click();
        specOffers.click();

        //Address Details
        company.sendKeys(faker.company().name());
        address1.sendKeys(faker.address().fullAddress());
        address2.sendKeys(faker.address().fullAddress());
        city.sendKeys(faker.address().city());
        state.selectByIndex(Functions.randomRange(1, 50));
        zipCode.sendKeys(Functions.randomRangeToString(10000, 99999));
        additionalInfo.sendKeys(faker.backToTheFuture().quote());

        homePhone.sendKeys(faker.phoneNumber().cellPhone());
        mobilePhone.sendKeys(faker.phoneNumber().cellPhone());
        alias.clear();
        alias.sendKeys(faker.address().country());
        submitBtn.click();

        //Verify account creation by checking the 'Sign Out' button
        WebElement signOutBtn = driver.findElement(By.className("logout"));
        Assert.assertEquals(true, signOutBtn.isDisplayed());
        signOutBtn.click();
    }

    @Test(priority = 2)
    public void verifyProductsInSection(){
        //Scroll to section
        JavascriptExecutor jsScroll = (JavascriptExecutor)driver;
        jsScroll.executeScript("window.scrollBy(0,1000)");
        String expectedResult = "7";

        //Sort of a workaround using javascript by printing the number in the alert pop up
        String script = "alert(number = $('ul#homefeatured > li').length);";
        JavascriptExecutor jsScript = (JavascriptExecutor)driver;
        String result = (String) jsScript.executeScript(script);


        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Here I am using value that's displayed in alert to assert... pretty sure it is wrong, but it was my only reliable solution
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println(alertText);
        Assert.assertEquals(expectedResult, alertText);
        alert.accept();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(priority = 3)
    public void saveResultsToTxt(){
        String productTitle, productTitle2, productTitle3, productTitle4, productTitle5;

        WebElement searchBar = driver.findElement(By.id("search_query_top"));
        WebElement searchBtn = driver.findElement(By.name("submit_search"));

        searchBar.sendKeys("Printed dresses");
        searchBtn.click();


        JavascriptExecutor jsScroll = (JavascriptExecutor)driver;
        jsScroll.executeScript("window.scrollBy(0,1000)");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement product1 = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[1]/div/div[2]/h5"));
        WebElement product2 = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[2]/div/div[2]/h5"));
        WebElement product3 = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[3]/div/div[2]/h5"));
        WebElement product4 = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[4]/div/div[2]/h5"));
        WebElement product5 = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[5]/div/div[2]/h5"));

        //This part could use a  loop.. Looks repetitive but it does the job
        productTitle = product1.getText();
        productTitle2 = product2.getText();
        productTitle3 = product3.getText();
        productTitle4 = product4.getText();
        productTitle5 = product5.getText();

        Functions.saveResultInTextFile(productTitle, productTitle2, productTitle3, productTitle4, productTitle5);
    }

    @AfterMethod
    public void exitDriver(){
        driver.quit();
    }
}
