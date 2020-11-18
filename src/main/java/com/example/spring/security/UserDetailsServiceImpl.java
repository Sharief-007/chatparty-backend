package com.example.spring.security;

import com.example.spring.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MongoOperations operations;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var query = Query.query(Criteria.where("username").is(s));
        var userObj = operations.findOne(query, User.class);
        if (Objects.nonNull(userObj)){
            return UserDetailsImpl.builder()
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(userObj.isActivated())
                    .password(userObj.getPassword())
                    .username(userObj.getUsername())
                    .build();
        }else {
            throw new UsernameNotFoundException("User not found 404");
        }
    }
}
