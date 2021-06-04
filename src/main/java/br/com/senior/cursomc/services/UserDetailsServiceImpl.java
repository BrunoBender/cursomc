package br.com.senior.cursomc.services;

import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.repositories.ClienteRepository;
import br.com.senior.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository repo;

    //Implementa a busca do usuário do Spring Security pelo UserName/email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cli = repo.findByEmail(email);

        //aborta a execussão, informando que o email passado não está cadastrado
        if(cli == null){
            throw  new UsernameNotFoundException(email);
        }

        System.out.println("Senha: " + cli.getSenha() + " Email: " + cli.getEmail());

        return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfils());
    }
}
