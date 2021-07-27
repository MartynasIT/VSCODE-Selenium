package com.example;

import org.openqa.selenium.By;

public class Locator {

    public class MainPageLocators {
        By COMPANY_BUTTON = new By.ByXPath("//span[contains(text(),'Company')]");
        By ACCEPT_ALL = new By.ById("hs-eu-confirmation-button");
        By CAREERS_BUTTON = new By.ByLinkText("Careers");
        By SEARCH_BTN = new By.ByClassName("search-icon");
        By SEARCH_FIELD = new By.ByClassName("s");
        By SUBMIT_BTN_XPATH = new By.ByXPath("//header/div[1]/div[1]/div[1]/div[2]/form[1]/input[2]");
        By CONTACT_US = new By.ByXPath("//header/div[1]/nav[1]/div[2]/ul[1]/li[6]");
        By SIGN_ME_UP_BTN = new By.ById("gform_submit_button_1");
        By EMAIL_FIELD = new By.ByXPath("//input[@id='input_1_2']");
        By SEARCH_ARTICLES = new By.ByXPath("//main//article");
    }

    public class CareerPageLocators {
        String EUROPE_BUTTON_LINK_TEXT = "Europe";
        String LITHUANIA_BUTTON_CSS = "[data-value='lithuania-vilnius']";
        String JOB_ARTCILES_XPATH = "/html//main[@id='content']/div[@class='jobs-listing']/div/div[2]/article";

    }

    public class ContactPageLocators {
        String LOCATION_BLOCK_CSS = ".locations-block";
    }

}
