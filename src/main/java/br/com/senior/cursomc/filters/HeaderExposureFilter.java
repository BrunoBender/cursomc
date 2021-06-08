package br.com.senior.cursomc.filters;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Filtro que intersepta todas as requisições e expõe o Header location para que o Angular consiga ler e sege seu ciclo normal
@Component
public class HeaderExposureFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("access-control-expose-headers", "loc ation");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
