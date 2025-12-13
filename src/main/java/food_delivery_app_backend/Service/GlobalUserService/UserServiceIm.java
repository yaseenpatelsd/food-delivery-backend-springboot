package food_delivery_app_backend.Service.GlobalUserService;

import food_delivery_app_backend.Entity.UserEntities.GlobalUserEntity;
import food_delivery_app_backend.Repository.Global.GlobalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIm implements UserDetailsService {
    @Autowired
    private GlobalUserRepository globalUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GlobalUserEntity user= globalUserRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
