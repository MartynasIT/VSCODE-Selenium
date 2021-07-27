package com.example;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


public class Tests {
    WebDriver driver;

    @BeforeSuite
    public void init() throws IOException {
        Properties props = new Properties();
        FileInputStream fileInputStream = new FileInputStream("C://Users//mgelumb//Desktop//QA//JAVA//Rahul_VS//demo//data.properties");
        props.load(fileInputStream);
        System.setProperty(props.getProperty("browser"), props.getProperty("driver_path"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void test_window_switching() {
        close_tabs();
        driver.get("https://rahulshettyacademy.com/loginpagePractise/");
        driver.findElement(By.cssSelector(".blinkingText")).click();

        Set<String> windows = driver.getWindowHandles(); //[parentid,childid,subchildId]

        Iterator<String> it = windows.iterator();

        String parentId = it.next();

        String childId = it.next();

        driver.switchTo().window(childId);

        System.out.println(driver.findElement(By.cssSelector(".im-para.red")).getText());

        driver.findElement(By.cssSelector(".im-para.red")).getText();

        String emailId = driver.findElement(By.cssSelector(".im-para.red")).getText().split("at")[1].trim().split(" ")[0];

        driver.switchTo().window(parentId);

        driver.findElement(By.id("username")).sendKeys(emailId);


    }

    @Test
    public void test_iterator_open_next_window() throws InterruptedException {
        driver.get("http://qaclickacademy.com/practice.php");

        System.out.println(driver.findElements(By.tagName("a")).size());

        WebElement footerdriver = driver.findElement(By.id("gf-BIG"));// Limiting webdriver scope

        System.out.println(footerdriver.findElements(By.tagName("a")).size());

        //3-
        WebElement coloumndriver = footerdriver.findElement(By.xpath("//table/tbody/tr/td[1]/ul"));
        System.out.println(coloumndriver.findElements(By.tagName("a")).size());

        //4- click on each link in the coloumn and check if the pages are opening-
        for (int i = 1; i < coloumndriver.findElements(By.tagName("a")).size(); i++) {

            String clickonlinkTab = Keys.chord(Keys.CONTROL, Keys.ENTER);

            coloumndriver.findElements(By.tagName("a")).get(i).sendKeys(clickonlinkTab);
            Thread.sleep(5000L);

        }// opens all the tabs
        Set<String> abc = driver.getWindowHandles();//4
        Iterator<String> it = abc.iterator();

        while (it.hasNext()) {

            driver.switchTo().window(it.next());
            System.out.println(driver.getTitle());

        }


    }

    @Test(enabled = false)
    public void test_sum_table() {
        driver.get("http://www.cricbuzz.com/live-cricket-scorecard/18970/pak-vs-sl-2nd-t20i-pakistan-v-sri-lanka-in-uae-2017");
        int sum = 0;
        WebElement table = driver.findElement(By.cssSelector("div[class='cb-col cb-col-100 cb-ltst-wgt-hdr']"));
        int rowcount = table.findElements(By.cssSelector("cb-col cb-col-100 cb-scrd-itms")).size();
        int count = table.findElements(By.cssSelector("div[class='cb-col cb-col-100 cb-scrd-itms'] div:nth-child(3)")).size();

        for (int i = 0; i < count - 2; i++) {
            String value = table.findElements(By.cssSelector("div[class='cb-col cb-col-100 cb-scrd-itms'] div:nth-child(3)")).get(i).getText();
            int valueinteger = Integer.parseInt(value);
            sum = sum + valueinteger;//103
        }
//System.out.println(sum);

        String Extras = driver.findElement(By.xpath("//div[text()='Extras']/following-sibling::div")).getText();
        int extrasValue = Integer.parseInt(Extras);
        int TotalSumValue = sum + extrasValue;
        System.out.println(TotalSumValue);


        String ActualTotal = driver.findElement(By.xpath("//div[text()='Total']/following-sibling::div")).getText();
        int ActualTotalVAlue = Integer.parseInt(ActualTotal);
        if (ActualTotalVAlue == TotalSumValue) {
            System.out.println("Count Matches");
        } else {
            System.out.println("count fails");
        }
    }

    @Test
    public void test_soft_assert() throws IOException {
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");


        List<WebElement> links = driver.findElements(By.cssSelector("li[class='gf-li'] a"));

        SoftAssert a = new SoftAssert();


        for (WebElement link : links) {


            String url = link.getAttribute("href");


            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setRequestMethod("HEAD");

            conn.connect();

            int respCode = conn.getResponseCode();

            System.out.println(respCode);

            a.assertTrue(respCode < 400, "The link with Text" + link.getText() + " is broken with code" + respCode);
        }


        a.assertAll();
    }

    @Test(enabled = false)
    public void test_stream_pagination() {
        driver.get("https://rahulshettyacademy.com/greenkart/#/offers");

        driver.findElement(By.xpath("//tr/th[1]")).click();
        List<WebElement> elementsList = driver.findElements(By.xpath("//tr/td[1]"));
        List<String> originalList = elementsList.stream().map(s -> s.getText()).collect(Collectors.toList());
        List<String> sortedList = originalList.stream().sorted().collect(Collectors.toList());
        Assert.assertTrue(originalList.equals(sortedList));
        List<String> price;


        do {

            List<WebElement> rows = driver.findElements(By.xpath("//tr/td[1]"));

            price = rows.stream().filter(s -> s.getText().contains("Rice"))

                    .map(s -> getPriceVeggie(s)).collect(Collectors.toList());

            price.forEach(a -> System.out.println(a));

            if (price.size() < 1) {

                driver.findElement(By.cssSelector("[aria-label='Next']")).click();

            }

        } while (price.size() < 1);


    }

//    @org.junit.Test
//    public void test_relative_loc(){
//        driver.get("https://rahulshettyacademy.com/angularpractice/");
//
//        WebElement nameEditBox =driver.findElement(By.cssSelector("[name='name']"));
//
//        System.out.println(driver.findElement(withTagName("label").above(nameEditBox)).getText());
//
//        WebElement dateofBirth= driver.findElement(By.cssSelector("[for='dateofBirth']"));
//
//        driver.findElement(withTagName("input").below(dateofBirth)).sendKeys("02/02/1992");
//
//        WebElement iceCreamLabel=driver.findElement(By.xpath("//label[text()='Check me out if you Love IceCreams!']"));
//
//        driver.findElement(withTagName("input").toLeftOf(iceCreamLabel)).click();
//
////Get me the label of first Radio button
//
//        WebElement rb=driver.findElement(By.id("inlineRadio1"));
//
//        System.out.println(driver.findElement(withTagName("label").toRightOf(rb)).getText());
//
//
//    }

    private static String getPriceVeggie(WebElement s) {
        String pricevalue = s.findElement(By.xpath("following-sibling::td[1]")).getText();
        return pricevalue;
    }

    @Test
    public void test_element_height() throws IOException {
        driver.get("https://rahulshettyacademy.com/angularpractice/");

//Switching Window

        driver.switchTo().newWindow(WindowType.WINDOW);

        Set<String> handles = driver.getWindowHandles();

        Iterator<String> it = handles.iterator();

        String parentWindowId = it.next();

        String childWindow = it.next();

        driver.switchTo().window(childWindow);

        driver.get("https://rahulshettyacademy.com/");

        String courseName = driver.findElements(By.cssSelector("a[href*='https://courses.rahulshettyacademy.com/p']"))

                .get(1).getText();

        driver.switchTo().window(parentWindowId);

        WebElement name = driver.findElement(By.cssSelector("[name='name']"));

        name.sendKeys(courseName);

//Screenshot

        File file = name.getScreenshotAs(OutputType.FILE);

        FileUtils.copyFile(file, new File("logo.png"));

//driver.quit();

//GEt Height & Width

        System.out.println(name.getRect().getDimension().getHeight());

        System.out.println(name.getRect().getDimension().getWidth());
    }

    @Test(dataProvider = "getData")
    public void test_data_provider(String username, String pass) {
        System.out.println(username);
        System.out.println(pass);
    }

    @DataProvider
    public Object[][] getData() {
        Object[][] data = new Object[3][2];
        data[0][0] = "first";
        data[0][1] = "csirst";

        data[1][0] = "qqqrst";
        data[1][1] = "qqirst";

        data[2][0] = "fffirst";
        data[2][1] = "ffffcsirst";
        return data;
    }

    @AfterTest
    public void close() {
        driver.quit();
    }

    public void close_tabs() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
    }


}

