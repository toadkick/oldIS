package com.eshop.products.dao;

import com.eshop.products.entities.Account;

public interface UserDAO {

    public Account findByLogin(String login);
}
