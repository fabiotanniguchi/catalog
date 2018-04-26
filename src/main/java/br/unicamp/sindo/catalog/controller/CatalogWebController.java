package br.unicamp.sindo.catalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CatalogWebController {

    private static final String HOMEPAGE = "index.html";
    private static final String ADMIN = "admin.html";

    @RequestMapping("/")
    public String home(Model model) {
        return HOMEPAGE;
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        return ADMIN;
    }
}
