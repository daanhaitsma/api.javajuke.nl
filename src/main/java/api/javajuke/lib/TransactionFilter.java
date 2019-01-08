package api.javajuke.lib;

import api.javajuke.data.UserRepository;
import api.javajuke.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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
            add("/playlists");
        }};
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        if(whitelisted.contains(req.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        String token = req.getHeader("X-Authorization");

        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        UserRepository userRepository = webApplicationContext.getBean(UserRepository.class);
        userRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException(token + " not found." ));


        chain.doFilter(request, response);
    }
}