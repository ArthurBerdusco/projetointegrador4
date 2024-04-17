package br.senac.ecommerce.tapeteyoga.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    private String cpf;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    private String gender;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private BillingAddress billingAddress;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<DeliveryAddress> deliveryAddresses;


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", billingAddress=" + billingAddress + // Chamada ao toString() de BillingAddress
                ", deliveryAddresses=" + deliveryAddresses +
                '}';
    }

}
