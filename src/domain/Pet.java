package domain;

public class Pet {
    private String nome;
    private TipoPet tipo;
    private SexoPet sexoPet;
    private Endereco endereco;
    private String idade;
    private String peso;
    private String raca;

    public Pet(String nome, TipoPet tipo, SexoPet sexoPet, Endereco endereco, String idade, String peso, String raca) {
        this.nome = nome;
        this.tipo = tipo;
        this.sexoPet = sexoPet;
        this.endereco = endereco;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

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
        return endereco;
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
}
