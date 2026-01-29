package ui;


import core.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.page.CartPage;
import ui.page.CheckoutPage;
import ui.page.InventoryPage;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@Tag("ui")
public class CheckoutTest extends BaseUiTest {

    private InventoryPage inventoryPage;


    @BeforeEach
    public void login() {
        inventoryPage = loginAsStandardUser();
        assertThat(inventoryPage.isAt())
                .as("Inventory page should be visible after login")
                .isTrue();
    }

    @Test
    void userCanCheckoutWithAtLeastTwoItems_andTotalPriceIsCorrect() {
        // GIVEN: at least 2 items in cart (data-independent)
        int selectedItemCount = inventoryPage.addItemsToCart(2);
        assertThat(inventoryPage.getCartItemCount())
                .as("Cart badge should reflect number of added items")
                .isEqualTo(selectedItemCount);

        // WHEN: proceed to checkout
        inventoryPage.goToCart();

        var cartPage = new CartPage(driver, waits);
        assertThat(cartPage.isAt()).as("Cart page should be visible").isTrue();
        cartPage.clickCheckout();

        var checkoutPage = new CheckoutPage(driver, waits);
        checkoutPage.fillCustomerInformation(
                td.customer.firstName,
                td.customer.lastName,
                td.customer.postalCode
        );

        // THEN: validate price math
        BigDecimal itemTotal = checkoutPage.getItemTotal();
        BigDecimal tax = checkoutPage.getTax();
        BigDecimal total = checkoutPage.getTotal();

        assertThat(itemTotal.add(tax))
                .as("Item total + tax should equal final total")
                .isEqualByComparingTo(total);

        checkoutPage.finishCheckout();

        assertThat(checkoutPage.getConfirmationMessage())
                .as("Checkout should complete successfully")
                .containsIgnoringCase("thank you");
    }
}
