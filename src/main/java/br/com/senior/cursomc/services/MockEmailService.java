package br.com.senior.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

//Simula o envio do Email no log da aplicação
public class MockEmailService extends AbstractEmailService{

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Simulando envio de email...");
        LOG.info(msg.toString());
        LOG.info("Email enviado");
    }
}
