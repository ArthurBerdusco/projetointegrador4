package br.senac.ecommerce.tapeteyoga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            System.out.println("O servidor está rodando em: http://" + ip + ":8080");
        } catch (UnknownHostException e) {
            System.err.println("Erro ao obter o endereço IP: " + e.getMessage());
        }
    }
}
