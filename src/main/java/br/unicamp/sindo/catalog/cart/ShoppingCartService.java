package br.unicamp.sindo.catalog.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartProductRepository repository;

    public List<CartProductDTO> fetchAllByUser(UUID userId){
        List<CartProductDTO> resultList = new ArrayList<>();

        try {
            List<ShoppingCartProductEntity> cartProducts = repository.findByUserId(userId);

            for(ShoppingCartProductEntity product : cartProducts){
                CartProductDTO dto = CartProductDTO.from(product);
                resultList.add(dto);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultList;
    }

    public CartProductDTO insertOrUpdateCartProduct(CartProductDTO dto){
        List<ShoppingCartProductEntity> existingProducts = repository.findByUserIdAndProductId(dto.userId, dto.productId);

        if(existingProducts == null || existingProducts.size() == 0){
            ShoppingCartProductEntity entity = new ShoppingCartProductEntity();
            entity.setProductId(dto.productId);
            entity.setProductOrigin(dto.productOrigin);
            entity.setQty(dto.qty);
            entity.setUnitPrice(dto.unitPrice);
            entity.setUserId(dto.userId);
            entity.setCreatedAt(new Date());
            entity.setUpdatedAt(new Date());

            entity = repository.save(entity);

            return CartProductDTO.from(entity);
        }

        ShoppingCartProductEntity first = existingProducts.get(0);
        first.setQty(first.getQty() + dto.qty);
        first.setUpdatedAt(new Date());

        first = repository.save(first);

        return CartProductDTO.from(first);
    }

    public void removeCartProduct(CartProductDTO dto){
        List<ShoppingCartProductEntity> existingProducts = repository.findByUserIdAndProductId(dto.userId, dto.productId);

        if(existingProducts == null || existingProducts.size() == 0){
            return;
        }

        long qtyToRemove = dto.qty;
        for(ShoppingCartProductEntity product : existingProducts){
            if(qtyToRemove <= 0){
                break;
            }
            long difference = product.getQty() - qtyToRemove;
            long newQty = Math.max(difference, 0);

            product.setQty(newQty);
            product.setUpdatedAt(new Date());

            repository.save(product);

            qtyToRemove = Math.negateExact(difference);
        }
    }

}
