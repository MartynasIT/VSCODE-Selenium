package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Page {

    WebDriver driver;

    Page(WebDriver driver) {
        this.driver = driver;
    }

    public boolean check_if_exists(WebElement element, By by) {
        try {
            driver.findElement(by);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }


    public class MainPage {
        Locator loc_obj = new Locator();
        Locator.MainPageLocators main_page = loc_obj.new MainPageLocators();
        WebElement search_text_element = driver.findElement(main_page.SEARCH_FIELD);

        public void click_spam() {
            driver.findElement(main_page.ACCEPT_ALL).click();
        }

        public void click_careers() {
            WebElement element_company = driver.findElement(main_page.COMPANY_BUTTON);
            WebElement element_career = driver.findElement(main_page.CAREERS_BUTTON);
            Actions actions = new Actions(driver);
            actions.moveToElement(element_company);
            actions.click(element_career);
            actions.perform();

        }

        public void click_contact_us() {
            driver.findElement(main_page.CONTACT_US).click();
        }

        public void click_search() {
            WebElement element_search = driver.findElement(main_page.SEARCH_BTN);
            Actions actions = new Actions(driver);
            actions.click(element_search);
            actions.click(search_text_element);
            actions.perform();
        }

        public void click_submit_search() {
            driver.findElement(main_page.SUBMIT_BTN_XPATH).click();
        }

        public boolean check_search_results() throws InterruptedException {
            int xpathCount = driver.findElements(main_page.SEARCH_ARTICLES).size();
            if (xpathCount > 0)
                print_articles();
            return xpathCount > 0;
        }

        public void print_articles() throws InterruptedException {
            WebElement main = driver.findElement(new By.ById("content"));
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            while (check_if_exists(main, new By.ById("load-more"))){
                driver.findElement(new By.ById("load-more")).click();
                Thread.sleep(2000);
            }

            List<WebElement> articles = main.findElements(new By.ByXPath("//div[@class='content-grid search-results']/div[1]/article"));
            for (WebElement article : articles){
                WebElement header = article.findElement(By.className("article-title"));
                System.out.println(header.getText());
            }
        }

    }

    public class CareersPage {
        Locator loc_obj = new Locator();
        Locator.CareerPageLocators career_page = loc_obj.new CareerPageLocators();

        boolean check_ltu_work_exists() {
            driver.findElement(new By.ByLinkText(career_page.EUROPE_BUTTON_LINK_TEXT)).click();
            driver.findElement(new By.ByCssSelector(career_page.LITHUANIA_BUTTON_CSS)).click();
            int xpathCount = driver.findElements(By.xpath(career_page.JOB_ARTCILES_XPATH)).size();
            return xpathCount > 0;
        }
    }

    public class ContactPage {
        Locator loc_obj = new Locator();
        Locator.ContactPageLocators contact_page = loc_obj.new ContactPageLocators();

        boolean check_if_all_listings_have_photos() {
            List<WebElement> blocks = driver.findElements(By.cssSelector(contact_page.LOCATION_BLOCK_CSS));
            for (WebElement block : blocks) {
                List<WebElement> rows = block.findElements(By.className("row"));
                for (WebElement row : rows) {
                    List<WebElement> cols = row.findElements(new By.ByClassName("col-md-4"));
                    for (WebElement col : cols) {
                        WebElement wrapper = col.findElement(By.className("image-wrapper"));
                        if (!check_if_exists(wrapper, new By.ByTagName("img")))
                            return false;
                    }
                }
            }
            return true;
        }
    }
}
