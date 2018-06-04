package com.eshop.products.authentication;


import com.eshop.products.dao.UserDAO;
import com.eshop.products.entities.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyDBAuthenticationService implements UserDetailsService {


    private UserDAO userDAO;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userDAO.findByLogin(username);

        if (account == null) {
            throw new UsernameNotFoundException("Account " //
                    + username + " was not found in the database");
        }

        String role = account.getRole();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        grantList.add(authority);

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        UserDetails userDetails = (UserDetails) new User(account.getLogin(), //
                account.getPassword(), enabled, accountNonExpired, //
                credentialsNonExpired, accountNonLocked, grantList);

        return userDetails;
    }

}
