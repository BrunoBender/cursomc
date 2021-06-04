package br.com.senior.cursomc.services;

import br.com.senior.cursomc.domain.enums.Perfil;
import br.com.senior.cursomc.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSS authenticated(){
        //Retorna o usuário que estiver logado no sistema
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e){
            return null;
        }
    }
}
