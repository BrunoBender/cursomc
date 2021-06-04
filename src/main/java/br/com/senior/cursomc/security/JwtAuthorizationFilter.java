package br.com.senior.cursomc.security;

import br.com.senior.cursomc.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    //Valida a autorização da requisição, buscando o Token passado pelo header da requisição
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String bearerToken = req.getHeader("Authorization");

        if (bearerToken != null && !bearerToken.isEmpty() && bearerToken.startsWith("Bearer ")) {
            //.substring(7) -> passa o valor da String apartir do sétimo caracter, passando assim, apenas o Token
            UsernamePasswordAuthenticationToken auth = getAuthentication(bearerToken.substring(7));

            if(auth != null){
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                //libera a autorização do usuário que está tentanto acessar o endpoint
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        //vai permitir continuar fazendo a requisição normalmnete depois de fazer os testes acima
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        System.out.println("TOKEN --- "+token);
        if(jwtUtil.tokenValido(token)){
            String username = jwtUtil.getUsername(token);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }

}
