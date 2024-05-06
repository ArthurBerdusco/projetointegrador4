package br.senac.ecommerce.tapeteyoga.controller.store;

import java.math.BigDecimal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.senac.ecommerce.tapeteyoga.model.Frete;

@Configuration
public class FreteConfig {

    @Bean
    public Frete freteExpresso() {
        return new Frete("Expresso", new BigDecimal(2.5)); // Preço por km para entrega expressa: R$2.50
    }

    @Bean
    public Frete freteEconomico() {
        return new Frete("Econômico", new BigDecimal(1.5)); // Preço por km para entrega econômica: R$1.50
    }

    @Bean
    public Frete fretePadrao() {
        return new Frete("Padrão", new BigDecimal(2.0)); // Preço por km para entrega padrão: R$2.00
    }
}
