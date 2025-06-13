package com.baio.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private String homePage = "index.html";
    private HomeModelInteface homeModel;

    public HomeController(HomeModelInteface homeModel) {
        this.homeModel = homeModel;
    }

    @RequestMapping("/")
     public String index() {
        System.out.println(this.homePage);
        this.homeModel.showUserName();
        return this.homePage;
     }
}
