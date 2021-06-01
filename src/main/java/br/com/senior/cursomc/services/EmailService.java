package br.com.senior.cursomc.services;

import br.com.senior.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

//define quais operações um serviço de email deve oferecer
@Service
public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);

}
