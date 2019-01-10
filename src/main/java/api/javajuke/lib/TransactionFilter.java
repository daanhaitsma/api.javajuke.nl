package api.javajuke.lib;

import api.javajuke.data.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionFilter implements Filter {

    private final List<String> whitelisted;

    /**
     * Constructor for the TransactionFilter class.
     * Fills the whitelisted list with whitelisted endpoints that
     * do not require any authentication.
     */
    public TransactionFilter() {
        whitelisted = new ArrayList<String>() {{
            add("/login");
            add("/register");
        }};
    }

    /**
     * Filters a requests and checks if the specified token in the header is valid by
     * checking if a user with that token exists in the database.
     *
     * @param request  the request that is being received
     * @param response the response that is going to be given back
     * @param chain    the current filter chain
     * @throws IOException when an IO error occurs
     * @throws ServletException when the servlet is invalid
     */
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