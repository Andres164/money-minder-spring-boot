package com.baio.store;

import org.springframework.beans.factory.annotation.Value;

public class HomeModel implements HomeModelInteface {
    @Value("${database.host}")
    private String databaseHost = null;

    public HomeModel() {
        System.out.println(this.databaseHost);
    }

    @Override
    public void showUserName() {
        System.out.println("ANDRESS");
    }
}
