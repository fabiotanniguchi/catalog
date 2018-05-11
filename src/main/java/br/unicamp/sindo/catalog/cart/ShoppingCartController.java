package br.unicamp.sindo.catalog.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    @GetMapping(value = "/{userId}")
    public List<CartProductDTO> allUserCartProducts(@PathVariable(name="id") UUID userId) {
        return service.fetchAllByUser(userId);
    }

    @PostMapping
    public CartProductDTO putOnCart(@RequestBody CartProductDTO dto) {
        return service.insertOrUpdateCartProduct(dto);
    }

    @DeleteMapping
    public void removeFromCart(@RequestBody CartProductDTO dto){
        service.removeCartProduct(dto);
    }
}
