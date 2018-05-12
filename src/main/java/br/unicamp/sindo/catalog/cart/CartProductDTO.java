package br.unicamp.sindo.catalog.cart;

import java.util.UUID;

public class CartProductDTO {

    public UUID cartProductId;
    public UUID productId;
    public Integer productOrigin;
    public Long qty;
    public Double unitPrice;
    public UUID userId;

    public static CartProductDTO from(ShoppingCartProductEntity product) {
        CartProductDTO dto = new CartProductDTO();
        dto.cartProductId = product.getId();
        dto.productId = product.getProductId();
        dto.productOrigin = product.getProductOrigin();
        dto.qty = product.getQty();
        dto.unitPrice = product.getUnitPrice();
        dto.userId = product.getUserId();
        return dto;
    }
}
