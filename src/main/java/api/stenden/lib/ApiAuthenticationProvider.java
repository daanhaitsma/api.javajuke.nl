package api.stenden.lib;

import api.stenden.data.UserRepository;
import api.stenden.data.model.User;
import org.omg.CORBA.UnknownUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ApiAuthenticationProvider implements AuthenticationProvider {

    // This would be a JPA repository to snag your user entities
    private final UserRepository userRepository;

    @Autowired
    public ApiAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AuthenticationToken authenticationToken = (AuthenticationToken)authentication;

        return userRepository.findById(authenticationToken.getId())
                .orElseThrow(() -> new SecurityException("Wrong token"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }

}