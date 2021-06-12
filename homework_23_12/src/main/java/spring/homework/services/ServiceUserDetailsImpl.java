package spring.homework.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.homework.domain.User;
import spring.homework.repositories.UserDao;

@Service
public class ServiceUserDetailsImpl implements UserDetailsService {

    private final UserDao userDao;

    public ServiceUserDetailsImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Boolean isExist=userDao.existsByLogin(login);
        if(!isExist) {
            throw new UsernameNotFoundException("User isn't exist");
        }
        User user=userDao.findByLogin(login);
        return SecurityUser.fromUser(user);
    }
}
