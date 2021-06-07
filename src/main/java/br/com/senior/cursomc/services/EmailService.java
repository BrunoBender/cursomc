package br.com.senior.cursomc.services;

import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

//define quais operações um serviço de email deve oferecer
@Service
public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);

    //mesmos métodos acima, porém para email em HTML
    void sendOrderConfirmationHtmlEmail (Pedido obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}
