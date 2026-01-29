package ui.page;


import core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By cartList = By.cssSelector(".cart_list, [data-test='cart-list']");
    private final By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver, Waits waits) {
        super(driver, waits);
    }

    public boolean isAt() {
        try {
            waits.visible(cartList);
            return true;
        } catch (TimeoutException e) {
            return waits.urlContains("cart");
        }
    }

    public void clickCheckout() {
        waits.clickable(checkoutBtn).click();
    }
}
