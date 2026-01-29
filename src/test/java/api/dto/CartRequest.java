package api.dto;

import java.util.List;

public record CartRequest(
        int userId,
        String date,
        List<CartItem> products
) {
    public record CartItem(int productId, int quantity) {
    }
}