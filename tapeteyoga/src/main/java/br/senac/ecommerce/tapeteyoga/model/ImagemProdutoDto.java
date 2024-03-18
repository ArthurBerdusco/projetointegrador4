
package br.senac.ecommerce.tapeteyoga.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImagemProdutoDto {
    
    private Integer id;
    
    private String nomeArquivo;

    private MultipartFile arquivo;
    
    private int ordenacao;
    
    private boolean principal;

}
