package domain;

public enum TipoPet {
    GATO("Gato"),
    CACHORRO("Cachorro");

    private final String descricao;

    TipoPet(String descricao){
        this.descricao = descricao;
    }
}
