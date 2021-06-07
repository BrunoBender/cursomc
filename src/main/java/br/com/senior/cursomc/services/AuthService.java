package br.com.senior.cursomc.services;

import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.repositories.ClienteRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private BCryptPasswordEncoder pe;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ClienteRepository clienteRepository;

    private Random rand = new Random();

    public void sendNewPassord(String email){

        Cliente cliente = clienteService.findByEmail(email);

        if (cliente == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(pe.encode(newPass));
        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente,newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i=0; i<10; i++){
             vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        //gera um número inteiro de 0 até 2
        int opt = rand.nextInt(3);
        //gera um dígito
        if(opt == 0){
            //gera um código que representa um número entre 0 a 9 (segundo a tabela de Unicode)
            return (char) (rand.nextInt(10) + 48);
        }
        //gera letra maiuscula
        else if (opt == 1){
            //gera um código que representa uma letra maiúsculas (segundo a tabela de Unicode)
            return (char) (rand.nextInt(26) + 65);
        }
        //gera letra minúscula
        else{
            //gera um código que representa uma letra maiúsculas (segundo a tabela de Unicode)
            return (char) (rand.nextInt(26) + 97);
        }
    }


}
