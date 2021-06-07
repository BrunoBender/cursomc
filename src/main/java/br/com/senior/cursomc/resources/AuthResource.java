package br.com.senior.cursomc.resources;

import br.com.senior.cursomc.security.JwtUtil;
import br.com.senior.cursomc.security.UserSS;
import br.com.senior.cursomc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

    @Autowired
    private JwtUtil jwtUtil;
    //Usado para renovar o token de acesso do usuário quando o existente estiver próximo de expirar

    //É protegido por altenticação, o usuártio deve estar logado para acessar
    @RequestMapping(value="/refresh_token", method= RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        //pega o usuário logado
        UserSS user = UserService.authenticated();
        //É gerado um novo Token
        String token = jwtUtil.generateToken(user.getUsername());
        //Adiciona o novo token no response da requisição
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
