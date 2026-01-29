package ui.page;

import core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.net.URI;
import java.util.List;
import java.util.Locale;

public class InventoryPage extends BasePage {

    private final By inventoryContainer = By.id("inventory_container");
    private final By sortDropdown = By.cssSelector("select[data-test='product_sort_container'], select.product_sort_container");
    private final By itemName = By.cssSelector(".inventory_item_name, [data-test='inventory-item-name']");
    private final By cartLink = By.cssSelector(".shopping_cart_link, [data-test='shopping-cart-link']");
    private final By addToCartButtons = By.cssSelector(".inventory_item button[id^='add-to-cart']");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge, [data-test='shopping-cart-badge']");

    public InventoryPage(WebDriver driver, Waits waits) {
        super(driver, waits);

    }

    public boolean isAt() {
        try {
            waits.visible(inventoryContainer);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void sortByNameZA() {
        var dropdown = waits.visible(sortDropdown);
        new Select(dropdown).selectByValue("za"); // SauceDemo: "Name (Z to A)"
    }

    public List<String> getItemNamesInUiOrder() {
        waits.visible(itemName);
        return driver.findElements(itemName).stream()
                .map(e -> e.getText().trim())
                .toList();
    }


    public void addItemToCartByName(String itemNameText) {
        String dataTest = toDataTestAddToCart(itemNameText);
        By addByDataTest = By.cssSelector("[data-test='" + dataTest + "']");
        try {
            waits.clickable(addByDataTest).click();
            return;
        } catch (TimeoutException ignored) {
            // Fallback to name-based lookup if data-test does not exist in this UI version.
        }

        By itemContainer = By.xpath(
                "//div[contains(@class,'inventory_item') and .//*[contains(@class,'inventory_item_name') and normalize-space()='" + itemNameText + "']]"
        );
        By addButtonInside = By.xpath(".//button[contains(@id,'add-to-cart') or contains(@data-test,'add-to-cart')]");
        var container = waits.visible(itemContainer);
        container.findElement(addButtonInside).click();
    }

    public void goToCart() {
        waits.clickable(cartLink).click();
        try {
            waits.urlContains("cart");
        } catch (TimeoutException e) {
            // Fallback for flakey cart icon clicks in headless runs.
            driver.get(getBaseUrl() + "/cart.html");
            waits.urlContains("cart");
        }
    }

    private String toDataTestAddToCart(String itemNameText) {
        String slug = itemNameText.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return "add-to-cart-" + slug;
    }

    private String getBaseUrl() {
        URI uri = URI.create(driver.getCurrentUrl());
        return uri.getScheme() + "://" + uri.getHost();
    }

    public int addItemsToCart(int count) {
        waits.visible(inventoryContainer);
        List<org.openqa.selenium.WebElement> buttons = driver.findElements(addToCartButtons);

        if (buttons.size() < count) {
            throw new IllegalStateException("Not enough items to satisfy basket size");
        }

        for (int i = 0; i < count; i++) {
            buttons.get(i).click();
        }

        return count;
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(waits.visible(cartBadge).getText().trim());
        } catch (TimeoutException e) {
            return 0;
        }
    }

}
