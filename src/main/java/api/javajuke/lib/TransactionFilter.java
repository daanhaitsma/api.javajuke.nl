package api.javajuke.lib;

import api.javajuke.data.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionFilter implements Filter {

    private final List<String> whitelisted;

    public TransactionFilter() {
        whitelisted = new ArrayList<String>() {{
            add("/login");
            add("/register");
        }};
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        // Whitelisted requests like /register and /login do not need authorization
        if(whitelisted.contains(req.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        // Header contains a key with X-Authorization and a token value
        String token = req.getHeader("X-Authorization");

        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        UserRepository userRepository = webApplicationContext.getBean(UserRepository.class);

        // If the user is not found it will return a 401 Unauthorized response
        if(!userRepository.findByToken(token).isPresent()) {
            ((HttpServletResponse) response)
                    .setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Return the request response
        chain.doFilter(request, response);
    }
}