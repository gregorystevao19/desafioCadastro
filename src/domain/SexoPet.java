package domain;

public enum SexoPet {
    MACHO("Macho"),
    FEMEA("Fêmea");

    private final String descricao;

    SexoPet(String descricao){
        this.descricao = descricao;
    }
}
