package api.javajuke.lib;

import api.javajuke.service.TrackService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TransactionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println(
                "Starting a transaction for req : {}" +
                req.getRequestURI());

        chain.doFilter(request, response);
        System.out.println(
                "Committing a transaction for req : {}" +
                req.getRequestURI());

        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        TrackService trackService = webApplicationContext.getBean(TrackService.class);

        System.out.println(trackService.getTrack(1).getPath());
    }
}