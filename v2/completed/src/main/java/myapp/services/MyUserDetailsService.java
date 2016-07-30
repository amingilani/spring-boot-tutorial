package myapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;


@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

@Autowired
private UserRepository userRepository;


public MyUserDetailsService() {
        super();
}

@Override
public UserDetails loadUserByUsername(String userName)
throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
                return null;
        }
        List<GrantedAuthority> auth = AuthorityUtils
                                      .commaSeparatedStringToAuthorityList("ROLE_USER");
        String password = user.getPassword();
        return new org.springframework.security.core.userdetails.User(userName, password,
                                                                      auth);
}



}
