package samsolutions.site.tour.controllers;

//import in.mahesh.tasks.service.UserServiceImplementation;
//import in.mahesh.tasks.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import samsolutions.site.tour.converters.UserConverter;
import samsolutions.site.tour.dtos.AuthResponse;
import samsolutions.site.tour.dtos.LoginRequest;
import samsolutions.site.tour.dtos.SignupRequest;
import samsolutions.site.tour.dtos.UserProfile;
import samsolutions.site.tour.entities.UserData;
import samsolutions.site.tour.jwt.JwtConstant;
import samsolutions.site.tour.jwt.JwtProvider;
import samsolutions.site.tour.repository.UserRepository;
import samsolutions.site.tour.services.UserServiceImplementation;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserServiceImplementation customUserDetails;

   // @Autowired
   // private UserService userService;




    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest user)  {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        String mobile = user.getMobile();
        String role = user.getRole();

        UserData isEmailExist = userRepository.findByUsername(username);
        if (isEmailExist != null) {
            return new ResponseEntity<AuthResponse>(new AuthResponse(), HttpStatus.NOT_FOUND);

        }

        UserData createdUser = UserConverter.convertToEntity(user);

        UserData savedUser = userRepository.save(createdUser);
        userRepository.save(savedUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

    }



    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        customUserDetails.createAdminUser();

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserProfile> getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                authHeader = authHeader.substring(7);
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                @SuppressWarnings("deprecation")
                Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(authHeader).getBody();
                System.out.print(claims);
                String username = String.valueOf(claims.get("username"));
                UserData userData = userRepository.findByUsername(username);
                UserProfile profile = UserConverter.convertToProfile(userData);
                return new ResponseEntity<>(profile,HttpStatus.OK);
            }
            catch (Exception e) {
                throw new BadCredentialsException("Invalid token", e);
            }
        }
        else{
            return new ResponseEntity<>(new UserProfile(),HttpStatus.NOT_FOUND);
        }
    }




    private Authentication authenticate(String username, String password) {

        System.out.println(username+"---++----"+password);

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("Sign in user details"+ userDetails);

        if(userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch: "+userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }



} 