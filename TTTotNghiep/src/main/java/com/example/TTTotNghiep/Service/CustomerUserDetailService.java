package com.example.TTTotNghiep.Service;

import com.example.TTTotNghiep.Repository.UserRepository;
import com.example.TTTotNghiep.model.USER_ROLE;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user==null){
            throw  new UsernameNotFoundException("Can not find user with email"+username);

        }

        USER_ROLE CUSTOMERRole = user.getCUSTOMERRole();

        List<GrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority(CUSTOMERRole.toString()));
        try {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorityList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
