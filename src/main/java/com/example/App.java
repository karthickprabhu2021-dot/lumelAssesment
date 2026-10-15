package com.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;

public class App {
    public static void main(String[] args) {
        String listvalue = "Drink water";

        // String todoList = "//ul[@class='todo-list']//li";
        String todoupdate = "//input[@class='new-todo ng-pristine ng-valid ng-touched']";
        String editList = "//input[@class='new-todo ng-valid ng-dirty ng-touched']";
        String editItems = "//ul[@class='todo-list']//li//label[text()='" + listvalue + "']";
        String checkbox = "//ul[@class='todo-list']//li//label[text()='" + listvalue + "']/label";
        String listcontent = "//ul[@class='todo-list']//li[1]";
        String removeButton = "//button[@class='destroy']";
        String footerItem = "footerItem";
        String count;
        
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
        Page page = browser.newPage();

        page.navigate("https://todomvc.com/examples/angular/dist/browser/#/all");


        //Scenario 1 if not list entry then no footer:
        if (page.locator("footerItem").isDisabled()) {
            System.out.println("Footer is displayed");
        } else {
        Assert.fail("Empty list so footer has to be disabled")
        }


        
        // Scenario 2
        page.locator(todoupdate).dblclick();
        page.locator(todoupdate).fill(listvalue);
        page.locator(todoupdate).press("Enter");
        page.waitForTimeout(2000);

         count = page.locator("footerItem").textContent();

         Assert.assetEqauls(count,"1");

        // Scenario 3

        page.locator(checkbox).isChecked();

        page.locator(checkbox).check();

         count= page.locator("footerItem").textContent();
         Assert.assertEqauls(count,"0");

        // Scenario 4 Hover and delete

        page.locator(listcontent).hover();

        page.locator(removeButton).click();

        if (page.locator("footerItem").isDisabled()) {
            System.out.println("Footer is displayed");
        } else {
        Assert.fail("Empty list so footer has to be disabled")
        }

        // Scenario 5 move to active and completed tab:

        // All tab:
        page.locator(todoupdate).dblclick();
        page.locator(todoupdate).fill(listvalue);
        page.locator(todoupdate).press("Enter");
        page.waitForTimeout(2000);

        page.getByText("All").click();

         count= page.locator("footerItem").textContent();
        Assert.assertEqauls(count,"0");

        // Active tab

        page.getByText("All").click();

         count= page.locator("footerItem").textContent();
         Assert.assertEqauls(count,"0");

         Completedtab:

        page.getByText("Completed").click();

         count= page.locator("footerItem").textContent();
         Assert.assertEqauls(count,"0");

        page.close();
        browser.close();
    }
}