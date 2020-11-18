package com.example.spring.service;

import com.example.spring.dto.LoginRequest;
import com.example.spring.dto.RegisterRequest;
import com.example.spring.model.Profile;
import com.example.spring.model.User;
import com.example.spring.model.VerificationToken;
import com.example.spring.security.JwtHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@AllArgsConstructor
public class AuthService {
    private final MongoOperations mongoOperations;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtHelper jwtHelper;
    private final MailService mailService;

    public User signUp(RegisterRequest request) {
        var user = new User(
          UUID.randomUUID(),
          request.getEmail(),
          request.getUsername(),
          passwordEncoder.encode(request.getPassword()),
          false,
                LocalDate.now()
        );
        //save user to db
        user = mongoOperations.save(user);
        //generate token
        var token = generateVerificationToken(user);
        //send token to user
        mailService.sendTokenToUser(token,user);

        createProfile(user);
        return user;
    }

    @Transactional
    protected String generateVerificationToken(User user){
        var token = UUID.randomUUID().toString();
        var tokenObj = VerificationToken.builder()
                .token(token)
                .expiryDate(LocalDate.now().plusDays(1))
                .user(user)
                .build();

        mongoOperations.save(tokenObj);
        return token;
    }

    public String login(LoginRequest request){
        var authDetails = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        var authentication = authManager.authenticate(authDetails);
        //SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtHelper.generateToken(authentication);
    }

    public boolean activateAccount(String token){
        var query = query(Criteria.where("token").is(token));
        var tokenObj = mongoOperations.findOne(query,VerificationToken.class);
        if (Objects.nonNull(tokenObj)){
            var user = tokenObj.getUser();
            query = query(Criteria.where("_id").is(user.get_id()));
            var update = Update.update("activated",true);
            var result = mongoOperations.updateFirst(query, update, User.class);
            return result.wasAcknowledged();
        }
        return false;
    }

    @Async
    public void createProfile(User user){
        var profile = Profile.builder()
                ._id(user.get_id())
                .build();
        mongoOperations.save(profile);
    }
}
