package domain;

public class Pet {
    private String nome;
    private TipoPet tipo;
    private SexoPet sexoPet;
    private Endereco endereço;
    private String idade;
    private String peso;
    private String raca;

    public String getNome() {
        return nome;
    }

    public TipoPet getTipo() {
        return tipo;
    }

    public SexoPet getSexoPet() {
        return sexoPet;
    }

    public Endereco getEndereço() {
        return endereço;
    }

    public String getIdade() {
        return idade;
    }

    public String getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }

    public Pet(String nome, TipoPet tipo, SexoPet sexoPet, Endereco endereço, String idade, String peso, String raca) {
        this.nome = nome;
        this.tipo = tipo;
        this.sexoPet = sexoPet;
        this.endereço = endereço;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }
}
