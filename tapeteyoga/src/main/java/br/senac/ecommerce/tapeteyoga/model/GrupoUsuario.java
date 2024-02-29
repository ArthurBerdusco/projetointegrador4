package br.senac.ecommerce.tapeteyoga.model;

public enum GrupoUsuario {
    ADMINISTRADOR("administrador"),
    ESTOQUISTA("estoquista");

    private String grupo;

    GrupoUsuario(String grupo) {
        this.grupo = grupo;
    }

    public String getGrupo() {
        return grupo;
    }
}
