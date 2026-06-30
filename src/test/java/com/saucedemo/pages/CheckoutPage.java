package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.saucedemo.base.BasePage;

public class CheckoutPage extends BasePage {

    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator zipCodeInput;
    private final Locator continueButton;
    private final Locator cancelButton;
    private final Locator finishButton;
    private final Locator errorMessage;
    private final Locator confirmationHeader;
    private final Locator itemTotal;
    private final Locator taxAmount;
    private final Locator totalAmount;
    private final Locator backHomeButton;

    public CheckoutPage(Page page) {
        super(page);
        firstNameInput = page.locator("[data-test='firstName']");
        lastNameInput = page.locator("[data-test='lastName']");
        zipCodeInput = page.locator("[data-test='postalCode']");
        continueButton = page.locator("[data-test='continue']");
        cancelButton = page.locator("[data-test='cancel']");
        finishButton = page.locator("[data-test='finish']");
        errorMessage = page.locator("[data-test='error']");
        confirmationHeader = page.locator(".complete-header");
        itemTotal = page.locator(".summary_subtotal_label");
        taxAmount = page.locator(".summary_tax_label");
        totalAmount = page.locator(".summary_total_label");
        backHomeButton = page.locator("[data-test='back-to-products']");
    }

    public boolean isOnCheckoutStep1() {
        return page.url().contains("/checkout-step-one.html");
    }

    public boolean isOnCheckoutStep2() {
        return page.url().contains("/checkout-step-two.html");
    }

    public boolean isOnConfirmationPage() {
        return page.url().contains("/checkout-complete.html");
    }

    public void enterCheckoutInfo(String firstName, String lastName, String zip) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(zipCodeInput, zip);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public void clickCancel() {
        click(cancelButton);
    }

    public void clickFinish() {
        click(finishButton);
    }

    public void clickBackHome() {
        click(backHomeButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isVisible(errorMessage);
    }

    public String getConfirmationMessage() {
        return getText(confirmationHeader);
    }

    public double getItemTotal() {
        return parsePrice(getText(itemTotal));
    }

    public double getTaxAmount() {
        return parsePrice(getText(taxAmount));
    }

    public double getTotal() {
        return parsePrice(getText(totalAmount));
    }

    private double parsePrice(String text) {
        String trimmed = text.substring(text.indexOf('$') + 1).trim();
        return Double.parseDouble(trimmed);
    }
}
