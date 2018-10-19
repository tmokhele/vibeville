package bttc.app.service;


import bttc.app.model.AccountInformation;
import bttc.app.model.AccountUser;
import bttc.app.repository.UserRepository;
import bttc.app.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        return null;
    }

    public UserDetails loadUserById(String userId) {
        AccountInformation firebaseAuthResponse = userRepository.existsByIdToken(userId);
        AccountUser accountUser = firebaseAuthResponse.getUsers().get(0);
       return new UserPrincipal(accountUser.getLocalId(),accountUser.getEmail(),accountUser.getEmail(),accountUser.getEmail(),accountUser.getPasswordHash(),null) ;

    }
}
