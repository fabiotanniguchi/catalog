package br.unicamp.sindo.catalog.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admuser")
public class AdmUserController {

    @Autowired
    private AdmUserService service;

    @GetMapping
    public int list(@RequestParam String user, @RequestParam String password) {
        AdmUserService.LoginResult result = this.service.validate(user, password);
        return result.ordinal();
    }
}
