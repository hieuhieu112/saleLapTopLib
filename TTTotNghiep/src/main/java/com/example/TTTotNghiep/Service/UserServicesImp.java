package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Config.JWTProvider;
import com.example.TTTotNghiep.Repository.UserRepository;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImp implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJWT(jwt);
        User user = userRepository.findByEmail(email);
        return user;

    }

    public User findUserByJwtTokenSignUp(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJWTSignUp(jwt);
        User user = userRepository.findByEmail(email);
        return user;

    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if(user==null){
            throw  new Exception("User not found");
        }
        return user;
    }

    @Override
    public List<User> findAllCustomer() throws Exception {
        return userRepository.findAllUserByType(0);
    }

    @Override
    public List<User> findAllEmployee() throws Exception {
        return userRepository.findAllUserByType(2);
    }
}
