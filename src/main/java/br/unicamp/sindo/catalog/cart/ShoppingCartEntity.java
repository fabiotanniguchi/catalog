package br.unicamp.sindo.catalog.cart;

import br.unicamp.sindo.catalog.utils.repository.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCartEntity extends BaseEntity {

    /*
    A IDEIA DO CARRINHO COMO ENTIDADE EH GUARDAR O CARRINHO
    APENAS PARA USUARIOS LOGADOS

    PARA USUARIOS NAO LOGADOS, SERA ARMAZENADO NA SESSION MESMO
    E IRA PERDER A LISTA SE SAIR
     */

    private UUID userId;
    private Boolean finalizedOrder;

    @Column(name="user_id", nullable = false)
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Column(name="finalized", nullable = false)
    public Boolean getFinalizedOrder() {
        return finalizedOrder;
    }

    public void setFinalizedOrder(Boolean finalizedOrder) {
        this.finalizedOrder = finalizedOrder;
    }
}
