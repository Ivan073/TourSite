package samsolutions.site.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import samsolutions.site.tour.entities.UserData;
import samsolutions.site.tour.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImplementation implements UserDetailsService {


    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;


    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository=userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepository.findByUsername(username);
        System.out.println(user);

        if(user==null) {
            throw new UsernameNotFoundException("User not found with this username:"+username);

        }


        System.out.println("Loaded user: " + user.getUsername() + ", Role: " + user.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        //здесь добавлять роли из claim
       // authorities.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    public void createAdminUser() {
        UserData user = userRepository.findByUsername("admin");
        if(user!=null) {
            return;
        }
        UserData admin = new UserData();
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin"));
        admin.setRole("ROLE_ADMIN");
        userRepository.save(admin);
    }
}