package domain;

public class Endereco {
    private String cidade;
    private String rua;
    private String numeroCasa;

    public Endereco(String cidade, String rua, String numeroCasa) {
        this.cidade = cidade;
        this.rua = rua;
        this.numeroCasa = numeroCasa;
    }

    public String getCidade() {
        return cidade;
    }

    public String getRua() {
        return rua;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }
}
