package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Config.JWTProvider;
import com.example.TTTotNghiep.Repository.CommuneRepository;
import com.example.TTTotNghiep.Repository.OrderRepository;
import com.example.TTTotNghiep.Repository.UserRepository;
import com.example.TTTotNghiep.Request.LoginRequest;
import com.example.TTTotNghiep.Response.AuthResponse;
import com.example.TTTotNghiep.Service.CustomerUserDetailService;
import com.example.TTTotNghiep.Request.UserRequest;
import com.example.TTTotNghiep.Service.OrderServiceImpl;
import com.example.TTTotNghiep.Service.UserServicesImp;
import com.example.TTTotNghiep.model.Orders;
import com.example.TTTotNghiep.model.USER_ROLE;
import com.example.TTTotNghiep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerUserDetailService customerUserDetailService;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createHandleUser(@RequestBody UserRequest userrequest) throws Exception {
        User user = userrequest.convertToUser(communeRepository.getReferenceById(userrequest.getCommune()));
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if(isEmailExist!= null){
            throw new Exception("Email is already used with another account");
        }

        User createUser = new User();
        createUser.setEmail(user.getEmail());
        createUser.setPass(passwordEncoder.encode(user.getPass()));
//        createUser.setPass(user.getPass());
        createUser.setBirthday(user.getBirthday());
        createUser.setCommune(user.getCommune());
        createUser.setFullname(user.getFullname());
        createUser.setPhoto(user.getPhoto());
        //photo?
        createUser.setNumberphone(user.getNumberphone());
        createUser.setGender(user.getGender());
        createUser.setStatus(0);
        createUser.setCUSTOMERRole(user.getCUSTOMERRole());
        //createUser.setDepartment(1);
        //createUser.setEmployeeType(1);
        //createUser.setCommune(00001);
        createUser.setAddressDescription(user.getAddressDescription());

        User savedUser = userRepository.save(createUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPass());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setRole(createUser.getCUSTOMERRole());
        authResponse.setMessage("Sign up success");
        orderService.createCart(jwt);


        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandleUser(@RequestBody LoginRequest request) throws Exception{
        String userName = request.getEmail();
        String password = request.getPassword();
        Authentication authentication = authenticate(userName, password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        System.out.println("role: " + role);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setRole(USER_ROLE.valueOf(role));
        authResponse.setMessage("Sign in success");


        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(userName);

        if (userDetails == null){
            throw  new BadCredentialsException("Invalid Username");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw  new BadCredentialsException("Wrong password");
        }

        return new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());
    }

    @RestController
    @RequestMapping("/api/users")
    public static class UserCustomerController {
        @Autowired
        private UserServicesImp userServicesImp;

        @GetMapping("/profile")
        public ResponseEntity<User> findUserByJWT(@RequestHeader("Authorization") String jwt) throws Exception {
            User user = userServicesImp.findUserByJwtToken(jwt);
            System.out.println(user.getCUSTOMERRole());
            return new ResponseEntity<>(user, HttpStatus.OK );
        }
    }
}
