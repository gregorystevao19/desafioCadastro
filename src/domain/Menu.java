package domain;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {

    Scanner sc = new Scanner(System.in);
    String now = LocalDateTime.now().toString().replaceAll("[-:.]", "");
    private String userInput;

    private String directoryFormularioPerguntas = "src/assets";
    private String fileFormularioPerguntas = "formulario.txt";
    private File file = new File(directoryFormularioPerguntas, fileFormularioPerguntas);

    public void printMenu() {
        System.out.println("----------------- SISTEMA DE PETS -----------------");
        System.out.println("[1] - Cadastrar um novo pet");
        System.out.println("[2] - Alterar os dados do pet cadastrado");
        System.out.println("[3] - Deletar um pet cadastrado");
        System.out.println("[4] - Listar todos os pets cadastrados");
        System.out.println("[5] - Listar pets por algum critério (idade, nome, raça)");
        System.out.println("[6] - Sair");
        System.out.println("---------------------------------------------------");
    }

    public void handleMenuInput() {

        System.out.print("Informe qual opção será executada: ");
        userInput = sc.nextLine();
        int numericUserInput;

        while (true) {
            try {
                numericUserInput = Integer.parseInt(userInput.trim());
                if (numericUserInput < 0 || numericUserInput > 6) throw new IllegalArgumentException();
                switch (numericUserInput) {
                    case 1 -> cadastrarPet();
                    case 6 -> System.out.println("Finalizando ...");
                }
                return;
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido, informe novamente: ");
                userInput = sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inexistente, informe novamente: ");
                userInput = sc.nextLine();
            }
        }
    }

    private void cadastrarPet() {

        final String NAO_INFORMADO = "NÃO INFORMADO";

        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            int indexPergunta = 1;

            String nome = "";
            TipoPet tipo = null;
            SexoPet sexoPet = null;
            Endereco endereço = null;
            String idade = "";
            String peso = "";
            String raca = "";

            while ((line = bf.readLine()) != null) {
                System.out.println(line);
                switch (indexPergunta) {
                    case 1: {
                        nome = sc.nextLine();

                        if (nome.trim().isEmpty()) {
                            nome = NAO_INFORMADO;
                            break;
                        }

                        while (nome == null || nome.trim().split(" ").length != 2 || Pattern.compile("[^A-Za-z ]").matcher(nome).find()) {
                            System.out.print("Deve ser informado NOME e SOBRENOME válidos (Caracteres [A - Z]): Digite novamente: ");
                            nome = sc.nextLine();
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("[1] - GATO");
                        System.out.println("[2] - CACHORRO");
                        int userInput = sc.nextInt();

                        while (!(userInput == 1) && !(userInput == 2)) {
                            System.out.print("Opção inválida, escolha novamente: ");
                            userInput = sc.nextInt();
                        }

                        sc.nextLine();
                        switch (userInput) {
                            case 1 -> tipo = TipoPet.GATO;
                            case 2 -> tipo = TipoPet.CACHORRO;
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("[1] - MACHO");
                        System.out.println("[2] - FEMEA");
                        int userInput = sc.nextInt();

                        while (!(userInput == 1) && !(userInput == 2)) {
                            System.out.print("Opção inválida, escolha novamente: ");
                            userInput = sc.nextInt();
                        }

                        sc.nextLine();
                        switch (userInput) {
                            case 1 -> sexoPet = SexoPet.MACHO;
                            case 2 -> sexoPet = SexoPet.FEMEA;
                        }
                        break;
                    }
                    case 4: {
                        String cidade = "";
                        String rua = "";
                        String numeroCasa = "";
                        int index = 1;

                        while (index <= 3) {
                            switch (index) {
                                case 1: {
                                    System.out.print("Informe a cidade: ");
                                    cidade = sc.nextLine();
                                    while (cidade.isEmpty()) {
                                        System.out.print("Informe uma Cidade válida: ");
                                        cidade = sc.nextLine();
                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.print("Informe a rua: ");
                                    rua = sc.nextLine();
                                    while (rua.isEmpty()) {
                                        System.out.print("Informe uma Rua válida: ");
                                        rua = sc.nextLine();
                                    }
                                    break;
                                }
                                case 3: {
                                    System.out.print("Informe o número da casa: ");
                                    while (true) {
                                        String userInput = sc.nextLine();
                                        if (userInput.trim().isEmpty()) {
                                            numeroCasa = NAO_INFORMADO;
                                            break;
                                        }
                                        try {
                                            Integer.parseInt(userInput);
                                            numeroCasa = userInput;
                                            break;
                                        } catch (NumberFormatException e) {
                                            System.out.print("Número Inválido. Informe novamente: ");
                                        }
                                    }
                                }
                            }
                            index++;
                        }
                        endereço = new Endereco(cidade, rua, numeroCasa);
                        break;
                    }
                    case 5: {
                        while (true) {
                            String userInput = sc.nextLine();

                            if (userInput.trim().isEmpty()) {
                                idade = NAO_INFORMADO;
                                break;
                            }

                            String formatedUserInput = userInput.replaceAll(",", ".");
                            try {
                                double numericInput = Double.parseDouble(formatedUserInput);
                                if (numericInput <= 0 || numericInput > 20) {
                                    throw new IllegalArgumentException();
                                }
                                idade = formatedUserInput;
                                break;
                            } catch (NumberFormatException e) {
                                System.out.print("Informe um número válido: ");
                            } catch (IllegalArgumentException e) {
                                System.out.print("Informe uma idade MAIOR que 0 e MENOR que 20: ");
                            }
                        }
                        break;
                    }
                    case 6: {
                        while (true) {
                            String userInput = sc.nextLine();

                            if (userInput.trim().isEmpty()) {
                                peso = NAO_INFORMADO;
                                break;
                            }

                            String formatedUserInput = userInput.replaceAll(",", ".");
                            try {
                                double numericInput = Double.parseDouble(formatedUserInput);
                                if (numericInput < 0.5 || numericInput > 20) {
                                    throw new IllegalArgumentException();
                                }
                                peso = formatedUserInput;
                                break;
                            } catch (NumberFormatException e) {
                                System.out.print("Informe um número válido: ");
                            } catch (IllegalArgumentException e) {
                                System.out.print("Informe umm peso MAIOR que 0.5Kg e MENOR que 60Kg: ");
                            }
                        }
                        break;
                    }
                    case 7: {
                        String userInput = sc.nextLine();

                        if (userInput.trim().isEmpty()) {
                            raca = NAO_INFORMADO;
                            break;
                        }


                        while (Pattern.compile("[^A-Za-z ]").matcher(nome).find()) {
                            System.out.print("Somente caracteres (A-Z) são aceitos. Informe a raça novamente: ");
                            userInput = sc.nextLine();
                            break;
                        }
                        raca = userInput;
                        break;
                    }
                    default: {
                        break;
                    }
                }
                indexPergunta++;
            }

            Pet pet = new Pet(nome, tipo, sexoPet, endereço, idade, peso, raca);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/Database/" + now + "-" + nome.trim().toUpperCase() + ".txt"))) {

                bw.write("NOME: " + pet.getNome() + "\n");
                bw.write("TIPO: " + pet.getTipo() + "\n");
                bw.write("SEXO: " + pet.getSexoPet() + "\n");
                bw.write("ENDEREÇO: " + pet.getEndereço().getRua() + ", " + pet.getEndereço().getNumeroCasa() + ", " + pet.getEndereço().getCidade() + "\n");
                bw.write("IDADE: " + pet.getIdade() + "\n");
                bw.write("PESO: " + pet.getPeso() + "\n");
                bw.write("RAÇA: " + pet.getRaca());

                System.out.println("Pet cadastrado com sucesso!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
