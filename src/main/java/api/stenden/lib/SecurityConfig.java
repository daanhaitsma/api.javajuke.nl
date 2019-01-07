package api.stenden.lib;

import api.stenden.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApiAuthenticationProvider apiAuthenticationProvider;

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository, ApiAuthenticationProvider apiAuthenticationProvider) {
        this.userRepository = userRepository;
        this.apiAuthenticationProvider = apiAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .addFilterBefore(new AuthenticationFilter(userRepository), BasicAuthenticationFilter.class)
            .antMatcher("/*")
            .authorizeRequests();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(apiAuthenticationProvider);
    }

}