package br.com.senior.cursomc.security;

import br.com.senior.cursomc.dto.CredenciasDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//Intersepta a requisição de Login (http://localhost:8080/login), o Spring Security faz isso automaticamente extendendo a classe UsernamePasswordAuthenticationFilter
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication (HttpServletRequest req,
                                                 HttpServletResponse res) throws AuthenticationException {
        try{
            //pega os dados que vieram na requisição e os converte para CredenciasDto
            CredenciasDto creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CredenciasDto.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

            //.authenticate -> ele que verifica se o usuário e senha são válidos, o Framework faz isso com base nas definições passadas no UserDetails e UserDetailsService
            Authentication auth = authenticationManager.authenticate(authToken);
            //informa para o Spring Security se a autenticação ocorreu com sucesso ou não
            return auth;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    //Caso a autenticação ocorra com sucesso, esse método irá gerar um Token e irá acressentar ele no responce da requisição
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException{

        //.getPrincipal -> retorna o usuário do Spring Security e pega o email da pessoa que fez o login
        String username = ((UserSS) auth.getPrincipal()).getUsername();
        //gera o Toke apartir das informações do usuário
        String token = jwtUtil.generateToken(username);
        res.addHeader("Authorization", "Bearer " + token);
    }

    //Caso a implementação de autenticação falhe (usuário inválido), esta classe irá personalizar o retorno do erro
    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }
}
