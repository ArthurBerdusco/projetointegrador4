package br.senac.ecommerce.tapeteyoga.model;

public enum GrupoUsuario {
    ADMIN("admin"),
    USER("user");

    private String grupo;

    GrupoUsuario(String grupo) {
        this.grupo = grupo;
    }

    public String getGrupo() {
        return grupo;
    }
}
