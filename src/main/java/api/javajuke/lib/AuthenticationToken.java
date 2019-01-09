package api.javajuke.lib;

import api.javajuke.data.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionId = -1949976839306453197L;
    private User authenticatedUser;
    private Long id;

    public AuthenticationToken(Long id){
        super(Arrays.asList());
        this.id = id;
    }

    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, User authenticatedUser, Long id) {
        super(authorities);
        this.id = id;
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public Object getCredentials() {
        return authenticatedUser.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }

    public long getId() {
        return id;
    }

}