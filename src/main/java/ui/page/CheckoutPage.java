package ui.page;


import core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

public class CheckoutPage extends BasePage {

    // Step One
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    // Overview (Step Two)
    private final By summaryInfo = By.cssSelector(".summary_info");
    private final By itemTotal = By.cssSelector(".summary_subtotal_label"); // "Item total: $xx.xx"
    private final By tax = By.cssSelector(".summary_tax_label");            // "Tax: $x.xx"
    private final By total = By.cssSelector(".summary_total_label");        // "Total: $xx.xx"
    private final By finishBtn = By.id("finish");

    // Complete
    private final By completeHeader = By.cssSelector(".complete-header");

    public CheckoutPage(WebDriver driver, Waits waits) {
        super(driver, waits);

    }

    public void fillCustomerInformation(String fn, String ln, String zip) {
        type(firstName, fn);
        type(lastName, ln);
        type(postalCode, zip);
        click(continueBtn);
    }

    public BigDecimal getItemTotal() {
        waits.visible(summaryInfo);
        return extractMoney(waits.visible(itemTotal).getText());
    }

    public BigDecimal getTax() {
        waits.visible(summaryInfo);
        return extractMoney(waits.visible(tax).getText());
    }

    public BigDecimal getTotal() {
        waits.visible(summaryInfo);
        return extractMoney(waits.visible(total).getText());
    }

    public void finishCheckout() {
        click(finishBtn);
    }

    public String getConfirmationMessage() {
        return waits.visible(completeHeader).getText().trim();
    }

    private BigDecimal extractMoney(String labelText) {
        // examples: "Item total: $39.98", "Tax: $3.20", "Total: $43.18"
        int dollarIdx = labelText.indexOf('$');
        if (dollarIdx < 0) throw new IllegalArgumentException("No $ found in: " + labelText);

        String amount = labelText.substring(dollarIdx + 1).trim();
        // BigDecimal with 2 decimals
        return new BigDecimal(amount);
    }
}
