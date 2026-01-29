package ui;

import core.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.page.InventoryPage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
public class SortingTest extends BaseUiTest {

    private InventoryPage inventoryPage;

    @BeforeEach
    public void login() {
        inventoryPage = loginAsStandardUser();
        assertThat(inventoryPage.isAt())
                .as("Inventory page should be visible after login")
                .isTrue();
    }


    @Test
    void shouldSortItemsByNameZtoA() {

        // Act: apply sorting
        inventoryPage.sortByNameZA();

        // Assert: validate sorting
        List<String> uiOrder = inventoryPage.getItemNamesInUiOrder();

        List<String> expected = new ArrayList<>(uiOrder);
        expected.sort(Comparator.reverseOrder()); // Z -> A

        assertThat(uiOrder)
                .as("UI item names should be sorted by name Z-A")
                .containsExactlyElementsOf(expected);
    }
}
