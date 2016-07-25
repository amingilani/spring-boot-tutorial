package com.example.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.Application;
import app.models.User;
import app.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Value("${app.secret}")
    private String applicationSecret;

    @Autowired
    private UserRepository repo;

    @Autowired
    private HttpSession httpSession;

    public final String CURRENT_USER_KEY = "CURRENT_USER";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findOneByUserNameOrEmail(username, username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        httpSession.setAttribute(CURRENT_USER_KEY, user);
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), auth);
    }

    public void autoLogin(User user) {
        autoLogin(user.getUserName());
    }

    public void autoLogin(String username) {
        UserDetails userDetails = this.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
        if(auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    public User register(User user) {
        user.setPassword(encodeUserPassword(user.getPassword()));

        if (this.repo.findOneByUserName(user.getUserName()) == null && this.repo.findOneByEmail(user.getEmail()) == null) {
            String activation = createActivationToken(user, false);
            user.setToken(activation);
            this.repo.save(user);
            return user;
        }

        return null;
    }


    public String encodeUserPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public Boolean delete(Long id) {
        this.repo.delete(id);
        return true;
    }


    public void updateUser(User user) {
        updateUser(user.getUserName(), user);
    }

    public User getLoggedInUser() {
        return getLoggedInUser(false);
    }

    public User getLoggedInUser(Boolean forceFresh) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) httpSession.getAttribute(CURRENT_USER_KEY);
        if(forceFresh || httpSession.getAttribute(CURRENT_USER_KEY) == null) {
            user = this.repo.findOneByUserName(userName);
            httpSession.setAttribute(CURRENT_USER_KEY, user);
        }
        return user;
    }
}
