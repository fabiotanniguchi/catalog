package br.unicamp.sindo.catalog.cart;

import br.unicamp.sindo.catalog.utils.repository.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="shopping_cart_product")
public class ShoppingCartProductEntity extends BaseEntity {

    private UUID shoppingCartId;
    private UUID productId;
    private Integer productOrigin;
    private Long qty;
    private Double unitPrice;

    @Column(name="cart_id", nullable = false)
    public UUID getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(UUID shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Column(name="product_id", nullable = false)
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    @Column(name="product_origin", nullable = false)
    public Integer getProductOrigin() {
        return productOrigin;
    }

    public void setProductOrigin(Integer productOrigin) {
        this.productOrigin = productOrigin;
    }

    @Column(name="qty", nullable = false)
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name="unit_price", nullable = false)
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
