package domain;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {

    Scanner sc = new Scanner(System.in);
    String now = LocalDateTime.now().toString().replaceAll("[-:.]", "");

    private final String directoryFormularioPerguntas = "src/assets";
    private final String fileFormularioPerguntas = "formulario.txt";
    private final File file = new File(directoryFormularioPerguntas, fileFormularioPerguntas);

    public int startMenu() {
        System.out.println("--------------------- SISTEMA DE PETS ---------------------");
        System.out.println("[1] - Iniciar o sistema para cadastro de PETS");
        System.out.println("[2] - Iniciar o sistema para alterar formulário");
        System.out.println("-----------------------------------------------------------");

        int numericInputOpcaoSistema;
        System.out.print("OPÇÃO: ");

        while (true) {
            String userInputOpcaoSistema = sc.nextLine();
            try {
                numericInputOpcaoSistema = Integer.parseInt(userInputOpcaoSistema);
                if (numericInputOpcaoSistema < 1 || numericInputOpcaoSistema > 2) {
                    throw new IllegalArgumentException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Digite novamente: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inválida. Selecione entre [1] ou [2]: ");
            }
        }

        return numericInputOpcaoSistema;
    }

    private static void printMenuGerenciarPets() {
        System.out.println("--------------------- SISTEMA DE PETS ---------------------");
        System.out.println("[1] - Cadastrar um novo pet");
        System.out.println("[2] - Alterar os dados do pet cadastrado");
        System.out.println("[3] - Deletar um pet cadastrado");
        System.out.println("[4] - Listar todos os pets cadastrados");
        System.out.println("[5] - Listar pets por algum critério (idade, nome, raça)");
        System.out.println("[6] - Sair");
        System.out.println("-----------------------------------------------------------");
        System.out.print("Informe qual opção será executada: ");
    }

    public void sistemaGerenciarPets() {
        printMenuGerenciarPets();
        while (true) {
            String userInput = sc.nextLine();

            int numericUserInput;
            try {
                numericUserInput = Integer.parseInt(userInput.trim());
                if (numericUserInput < 0 || numericUserInput > 6) throw new IllegalArgumentException();
                switch (numericUserInput) {
                    case 1 -> cadastrarPet();
                    case 2 -> atualizarDadosPet();
                    case 3 -> deletarPet();
                    case 4 -> listarPets();
                    case 5 -> listarPetsPorCriterio();
                    case 6 -> {
                        System.out.println("Finalizando programa...");
                        return;
                    }
                }
                printMenuGerenciarPets();
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Digite novamente: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inexistente, informe novamente: ");
            }
        }
    }

    private String lerNomePet(String NAO_INFORMADO) {
        String userInput = sc.nextLine();

        if (userInput.trim().isEmpty()) {
            userInput = NAO_INFORMADO;
            return userInput;
        }

        while (userInput.trim().split(" ").length != 2 || Pattern.compile("[^A-Za-z ]").matcher(userInput).find()) {
            System.out.print("Deve ser informado NOME e SOBRENOME válidos (Caracteres [A - Z]): Digite novamente: ");
            userInput = sc.nextLine();
        }
        return userInput;
    }

    private TipoPet lerTipoPet() {
        System.out.println("[1] - GATO");
        System.out.println("[2] - CACHORRO");
        int userInput = sc.nextInt();

        while ((userInput != 1) && (userInput != 2)) {
            System.out.print("Opção inválida, escolha novamente: ");
            userInput = sc.nextInt();
        }

        sc.nextLine();
        return userInput == 1 ? TipoPet.GATO : TipoPet.CACHORRO;
    }

    private SexoPet lerSexoPet() {

        System.out.println("[1] - MACHO");
        System.out.println("[2] - FEMEA");
        int userInput = sc.nextInt();

        while ((userInput != 1) && (userInput != 2)) {
            System.out.print("Opção inválida, escolha novamente: ");
            userInput = sc.nextInt();
        }

        sc.nextLine();
        return userInput == 1 ? SexoPet.MACHO : SexoPet.FEMEA;
    }

    private Endereco lerEnderecoPet(String NAO_INFORMADO) {
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
        return new Endereco(cidade, rua, numeroCasa);
    }

    private String lerIdadePet(String NAO_INFORMADO) {
        while (true) {
            String userInput = sc.nextLine();

            if (userInput.trim().isEmpty()) {
                return NAO_INFORMADO;
            }

            String formatedUserInput = userInput.replaceAll(",", ".");
            try {
                double numericInput = Double.parseDouble(formatedUserInput);
                if (numericInput <= 0 || numericInput > 20) {
                    throw new IllegalArgumentException();
                }
                return formatedUserInput;
            } catch (NumberFormatException e) {
                System.out.print("Informe um número válido: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Informe uma idade MAIOR que 0 e MENOR que 20 anos: ");
            }
        }
    }

    private String lerPesoPet(String NAO_INFORMADO) {
        while (true) {
            String userInput = sc.nextLine();

            if (userInput.trim().isEmpty()) {
                return NAO_INFORMADO;
            }

            String formatedUserInput = userInput.replaceAll(",", ".");
            try {
                double numericInput = Double.parseDouble(formatedUserInput);
                if (numericInput <= 0 || numericInput > 20) {
                    throw new IllegalArgumentException();
                }
                return formatedUserInput;
            } catch (NumberFormatException e) {
                System.out.print("Informe um número válido: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Informe uma idade MAIOR que 0 e MENOR que 20: ");
            }
        }
    }

    private String lerRacaPet(String NAO_INFORMADO) {
        String userInput = sc.nextLine();

        if (userInput.trim().isEmpty()) {
            return NAO_INFORMADO;
        }

        while (Pattern.compile("[^A-Za-z ]").matcher(userInput).find()) {
            System.out.print("Somente caracteres (A-Z) são aceitos. Informe a raça novamente: ");
            userInput = sc.nextLine();
            break;
        }
        return userInput;
    }

    public void salvarArquivoPet(Pet pet, String nomePet, String[] respostasCustomizadas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/Database/" + now + "-" + nomePet.trim().toUpperCase() + ".txt"))) {

            bw.write("NOME: " + pet.getNome() + "\n");
            bw.write("TIPO: " + pet.getTipo() + "\n");
            bw.write("SEXO: " + pet.getSexoPet() + "\n");
            bw.write("ENDEREÇO:" + pet.getEndereço().getRua() + ", " + pet.getEndereço().getNumeroCasa() + ", " + pet.getEndereço().getCidade() + "\n");
            bw.write("IDADE: " + pet.getIdade() + "\n");
            bw.write("PESO: " + pet.getPeso() + "\n");
            bw.write("RAÇA: " + pet.getRaca());

            if(respostasCustomizadas != null){
                for (String resposta : respostasCustomizadas){
                    bw.write("\n[PERGUNTA CUSTOMIZADA]: " + resposta);
                }
            }

            System.out.println("Pet cadastrado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cadastrarPet() {

        final String NAO_INFORMADO = "NÃO INFORMADO";
        int quantidadePerguntasCustomizadas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int index = 0;
            while (br.readLine() != null) {
                if (index > 6) {
                    quantidadePerguntasCustomizadas++;
                    index++;
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            int indexPergunta = 1;

            String nome = "";
            TipoPet tipo = null;
            SexoPet sexoPet = null;
            Endereco endereco = null;
            String idade = "";
            String peso = "";
            String raca = "";

            while ((line = bf.readLine()) != null) {
                if (indexPergunta > 7) break;
                System.out.println(line);
                switch (indexPergunta) {
                    case 1: {
                        nome = lerNomePet(NAO_INFORMADO);
                        break;
                    }
                    case 2: {
                        tipo = lerTipoPet();
                        break;
                    }
                    case 3: {
                        sexoPet = lerSexoPet();
                        break;
                    }
                    case 4: {
                        endereco = lerEnderecoPet(NAO_INFORMADO);
                        break;
                    }
                    case 5: {
                        idade = lerIdadePet(NAO_INFORMADO);
                        break;
                    }
                    case 6: {
                        peso = lerPesoPet(NAO_INFORMADO);
                        break;
                    }
                    case 7: {
                        raca = lerRacaPet(NAO_INFORMADO);
                        break;
                    }
                }
                indexPergunta++;
            }

            String[] respostasPerguntasCustomizadas = new String[quantidadePerguntasCustomizadas];
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linePerguntaCustomizada;
                int index = 0;
                while ((linePerguntaCustomizada = br.readLine()) != null) {
                    if (index > 6) {
                        System.out.println(linePerguntaCustomizada);
                        String resposta = sc.nextLine();
                        if (resposta.trim().isEmpty()) {
                            respostasPerguntasCustomizadas[index - 7] = NAO_INFORMADO;
                            index++;
                            continue;
                        }
                        respostasPerguntasCustomizadas[index - 7] = resposta;
                        index++;
                    }
                    index++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Pet pet = new Pet(nome, tipo, sexoPet, endereco, idade, peso, raca);
            salvarArquivoPet(pet, nome, respostasPerguntasCustomizadas);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarPets() {
        File pasta = new File("src/Database");
        File[] arquivos = pasta.listFiles();

        int indexListagem = 1;
        System.out.println("------------------------ RESULTADOS ------------------------");
        for (File arquivo : arquivos) {
            if (arquivo.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String line;
                    System.out.print(indexListagem + " - ");
                    while ((line = br.readLine()) != null) {
                        System.out.print(line + " - ");
                    }
                    System.out.println("\n");
                    indexListagem++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("------------------------------------------------------------");
    }

    private File[] listarPetsPorCriterio() {

        final String COMECO_NEGRITO = "\u001B[1m";
        final String FIM_NEGRITO = "\u001B[0m";

        int[] criteriosFiltro = new int[8];
        criteriosFiltro[0] = 0;
        int index = 1;
        String userInput = null;

        File pasta = new File("src/Database");
        File[] arquivos = pasta.listFiles();
        File[] filtredArquivos = {};

        System.out.println("---------------------------------------------------");
        System.out.println("Informe quais critérios deseja usar na busca. Por padrão, sempre será exigido a busca por tipo");
        System.out.println("[1] - Nome/Sobrenome");
        System.out.println("[2] - Sexo");
        System.out.println("[3] - Idade");
        System.out.println("[4] - Peso");
        System.out.println("[5] - Raça");
        System.out.println("[6] - Endereço");
        System.out.println("[7] - Data de cadastro");
        System.out.println("[8] - TERMINAR ESCOLHA DE FILTROS");
        System.out.println("---------------------------------------------------");

        while (true) {
            System.out.print("Opção: ");
            userInput = sc.nextLine();
            try {
                int numericUserInput = Integer.parseInt(userInput);

                if (numericUserInput < 1 || numericUserInput > 8) {
                    throw new IllegalArgumentException();
                }

                criteriosFiltro[index] = numericUserInput;
                index++;

                if (numericUserInput == 8 || criteriosFiltro[7] != 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Informe um critério válido: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inválida. Selecione novamente: ");
            }
        }

        String[] parametrosEncontrados = new String[8];

        for (int k : criteriosFiltro) {

            if (k == 8) break;

            switch (k) {
                case 0 -> System.out.print("Critério Padrão [TIPO]. Informe: [GATO / CACHORRO]: ");
                case 1 -> System.out.print("Critério 1 escolhido [Nome/Sobrenome]. Informe valor do filtro: ");
                case 2 -> System.out.print("Critério 2 escolhido [Sexo]. Informe valor do filtro: ");
                case 3 -> System.out.print("Critério 3 escolhido [Idade]. Informe valor do filtro: ");
                case 4 -> System.out.print("Critério 4 escolhido [peso]. Informe valor do filtro: ");
                case 5 -> System.out.print("Critério 5 escolhido [Raça]. Informe valor do filtro: ");
                case 6 -> System.out.print("Critério 6 escolhido [Endereço]. Informe valor do filtro: ");
                case 7 -> System.out.println("Critério 7 escolhido [Data cadastro].");
            }

            String mesFiltro;
            String anoFiltro;
            String valorFiltro = null;

            if (k == 7) {
                System.out.print("Mês [01 - 12]: ");
                mesFiltro = sc.nextLine();
                System.out.print("Ano: ");
                anoFiltro = sc.nextLine();
                valorFiltro = anoFiltro + mesFiltro;
            } else {
                valorFiltro = sc.nextLine();
            }

            if (valorFiltro.isEmpty()) {
                continue;
            }

            if (pasta.exists() && pasta.isDirectory()) {
                if (arquivos != null) {
                    for (File arquivo : arquivos) {
                        if (arquivo.isFile()) {
                            boolean contains = false;
                            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                                String line;
                                if (k == 7 && arquivo.getName().contains(valorFiltro)) {
                                    contains = true;
                                } else {
                                    while ((line = br.readLine()) != null) {
                                        if (line.toLowerCase().contains(valorFiltro.toLowerCase())) {
                                            contains = true;
                                            parametrosEncontrados[k] = line;
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            boolean alredyIncludedInFiltredArquvos = false;
                            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                                if (k == 7) {
                                    for (File arquivoFiltrado : filtredArquivos) {
                                        if (arquivoFiltrado.getName().equals(arquivo.getName())) {
                                            alredyIncludedInFiltredArquvos = true;
                                        }
                                    }
                                } else {
                                    while (br.readLine() != null) {
                                        for (File arquivoFiltrado : filtredArquivos) {
                                            if (arquivoFiltrado.getName().equals(arquivo.getName())) {
                                                alredyIncludedInFiltredArquvos = true;
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (contains && !alredyIncludedInFiltredArquvos) {
                                File[] novoArray = Arrays.copyOf(filtredArquivos, filtredArquivos.length + 1);
                                novoArray[novoArray.length - 1] = arquivo;
                                filtredArquivos = novoArray;
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("Não há Pets cadastrados.");
                }
            } else {
                throw new RuntimeException("Diretório inválido.");
            }
        }

        int indexListagem = 1;
        System.out.println("------------------------ RESULTADOS ------------------------");
        for (File arquivoFiltrado : filtredArquivos) {
            if (arquivoFiltrado.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivoFiltrado))) {
                    String line;
                    System.out.print(indexListagem + " - ");
                    while ((line = br.readLine()) != null) {
                        boolean devePrintarNegrito = false;
                        for (String texto : parametrosEncontrados) {
                            if (texto != null && texto.equals(line)) {
                                devePrintarNegrito = true;
                            }
                        }
                        if (devePrintarNegrito) {
                            System.out.print(COMECO_NEGRITO + line + FIM_NEGRITO + " - ");
                        } else {
                            System.out.print(line + " - ");
                        }
                    }
                    System.out.println("\n");
                    indexListagem++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("------------------------------------------------------------");
        return filtredArquivos;
    }

    private void atualizarDadosPet() {
        File[] petsCadastrados = listarPetsPorCriterio();
        File petSelecionado;

        System.out.print("Informe o número da listagem referente ao Pet que deseja editar: ");

        while (true) {
            try {
                String userInputPetParaEditar = sc.nextLine();
                int numericUserInput = Integer.parseInt(userInputPetParaEditar);

                if (numericUserInput < 1 || numericUserInput > petsCadastrados.length) {
                    throw new IllegalArgumentException();
                }

                petSelecionado = petsCadastrados[numericUserInput - 1];
                break;
            } catch (NumberFormatException e) {
                System.out.print("Informe um Pet válido: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inválida. Selecione novamente: ");
            }
        }

        int quantidadePerguntasCustomizadas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int index = 0;
            while (br.readLine() != null) {
                if (index > 6) {
                    quantidadePerguntasCustomizadas++;
                    index++;
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] perguntasCustomizadas = new String[quantidadePerguntasCustomizadas];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int index = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (index > 6) {
                    String conteudoPerguntaSemIndex = line.replaceAll("^\\s*\\d+\\s*-\\s*(.*)$", "$1");
                    perguntasCustomizadas[index - 7] = "[" + (index - 1) + "]" + " - " + "[" + "ATRIBUTO DE PERGUNTA CUSTOMIZADA" + "] " + conteudoPerguntaSemIndex;
                    index++;
                    continue;
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("[1] - Nome/Sobrenome");
        System.out.println("[2] - Endereço");
        System.out.println("[3] - Idade");
        System.out.println("[4] - Peso");
        System.out.println("[5] - Raça");

        for(String pergunta: perguntasCustomizadas){
            System.out.println(pergunta);
        }

        System.out.print("OPÇÃO: ");

        String userInputAtributoParaEditar = sc.nextLine();

        while (true) {
            try {
                int numericUserInput = Integer.parseInt(userInputAtributoParaEditar);

                if (numericUserInput < 1 || numericUserInput > (5 + quantidadePerguntasCustomizadas)) {
                    throw new IllegalArgumentException();
                }

                switch (numericUserInput) {
                    case 1 -> numericUserInput = 0;
                    case 2 -> numericUserInput = 3;
                    case 3 -> numericUserInput = 4;
                    case 4 -> numericUserInput = 5;
                    case 5 -> numericUserInput = 6;
                    default -> numericUserInput += 1;
                }

                String novaLinha;
                String[] linhasOriginais = new String[(7 + quantidadePerguntasCustomizadas)];

                try (BufferedReader br = new BufferedReader(new FileReader(petSelecionado))) {
                    String line;
                    int indexLine = 0;
                    String novoAtributo;
                    while ((line = br.readLine()) != null) {
                        if (indexLine == numericUserInput) {
                            System.out.println("Valor original: " + line);
                            System.out.print("Novo valor: ");
                            novoAtributo = sc.nextLine();
                            novaLinha = line.replaceAll(
                                    "^([^:]+: ).*$",
                                    "$1" + novoAtributo
                            );
                            linhasOriginais[indexLine] = novaLinha;
                            indexLine++;
                            continue;
                        }
                        linhasOriginais[indexLine] = line;
                        indexLine++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(petSelecionado))) {
                    for (int i = 0; i < linhasOriginais.length; i++) {
                        String conteudoLinha = linhasOriginais[i];
                        if (i != (7 + quantidadePerguntasCustomizadas)) {
                            conteudoLinha += " \n";
                        }
                        bw.write(conteudoLinha);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Editado com sucesso!");
                break;
            } catch (NumberFormatException e) {
                System.out.print("Informe um critério válido: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inválida. Selecione novamente: ");
            }
        }
    }

    private void deletarPet() {
        File[] petsCadastrados = listarPetsPorCriterio();
        File petSelecionado;

        System.out.print("Informe o número da listagem referente ao Pet que deseja excluir: ");

        while (true) {
            try {
                String userInputPetParaEditar = sc.nextLine();
                int numericUserInput = Integer.parseInt(userInputPetParaEditar);

                if (numericUserInput < 1 || numericUserInput > petsCadastrados.length) {
                    throw new IllegalArgumentException();
                }

                petSelecionado = petsCadastrados[numericUserInput - 1];
                break;
            } catch (NumberFormatException e) {
                System.out.print("Informe um Pet válido: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inválida. Selecione novamente: ");
            }
        }

        System.out.print("Deseja confirmar a exclusão do Pet? [SIM/NÃO]? ");
        String userInputConfirmacao = sc.nextLine();

        if (userInputConfirmacao.equalsIgnoreCase("sim") && petSelecionado.delete()) {
            System.out.println("Pet excluído com sucesso!");
        }
    }

    private static void printMenuGerenciarFormulario() {
        System.out.println("--------------------- SISTEMA DE FORMULÁRIOS ---------------------");
        System.out.println("[1] - Cadastrar nova pergunta");
        System.out.println("[2] - Alterar pergunta existente");
        System.out.println("[3] - Deletar pergunta existente");
        System.out.println("[4] - Sair");
        System.out.println("-------------------------------------------------------------------");
        System.out.print("Informe qual opção será executada: ");
    }

    public void sistemaGerenciarFormulario() {
        while (true) {
            printMenuGerenciarFormulario();
            String userInput = sc.nextLine();
            int numericUserInput;
            try {
                numericUserInput = Integer.parseInt(userInput.trim());
                if (numericUserInput < 0 || numericUserInput > 4) throw new IllegalArgumentException();
                switch (numericUserInput) {
                    case 1 -> cadastarPergunta();
                    case 2 -> editarPergunta();
                    case 3 -> excluirPergunta();
                    case 4 -> {
                        System.out.println("Finalizando programa...");
                        return;
                    }
                }
                printMenuGerenciarPets();
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Digite novamente: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inexistente, informe novamente: ");
            }
        }
    }

    private void cadastarPergunta() {
        String pergunta;
        System.out.print("Informe a pergunta: ");
        pergunta = sc.nextLine();

        while (Pattern.compile(".*\\d.*").matcher(pergunta).find() || pergunta.trim().isEmpty()) {
            System.out.print("Somente caracteres (A-Z) são aceitos. Informe a raça novamente: ");
            pergunta = sc.nextLine();
            break;
        }

        String novaLinha = pergunta;
        int quantidadeLinhasAtualmente = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                quantidadeLinhasAtualmente++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] linhasNovoArquivo = new String[quantidadeLinhasAtualmente + 1];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                linhasNovoArquivo[index] = line;
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        linhasNovoArquivo[linhasNovoArquivo.length - 1] = (linhasNovoArquivo.length) + " - " + novaLinha;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < linhasNovoArquivo.length; i++) {
                String conteudoLinha = linhasNovoArquivo[i];
                if (i != linhasNovoArquivo.length - 1) {
                    conteudoLinha += " \n";
                }
                bw.write(conteudoLinha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editarPergunta() {
        int quantidadeLinhasAtualmente = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                quantidadeLinhasAtualmente++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (quantidadeLinhasAtualmente <= 7) {
            System.out.println("Não há perguntas customizadas que podem ser editadas.");
            return;
        }

        System.out.println("Qual pergunta deseja editar? ");
        int indexPerguntas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (indexPerguntas > 6) {
                    System.out.println(line);
                }
                indexPerguntas++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("OPÇÃO: ");
        String userInputPergutaEditar;
        int numericUserInputPerguntaParaEditar;

        while (true) {
            userInputPergutaEditar = sc.nextLine();
            try {
                numericUserInputPerguntaParaEditar = Integer.parseInt(userInputPergutaEditar.trim());
                if (numericUserInputPerguntaParaEditar < 7 || numericUserInputPerguntaParaEditar > quantidadeLinhasAtualmente)
                    throw new IllegalArgumentException();
                break;
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Digite novamente: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inexistente, informe novamente: ");
            }
        }

        System.out.print("Informe o novo valor da pergunta " + numericUserInputPerguntaParaEditar + ": ");
        String novoTextoPergunta = sc.nextLine();

        String[] linhasNovoArquivo = new String[quantidadeLinhasAtualmente];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {

                if (index == numericUserInputPerguntaParaEditar - 1) {
                    linhasNovoArquivo[index] = (index + 1) + " - " + novoTextoPergunta;
                    index++;
                    continue;
                }
                linhasNovoArquivo[index] = line;
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < linhasNovoArquivo.length; i++) {
                String conteudoLinha = linhasNovoArquivo[i];
                if (i != linhasNovoArquivo.length - 1) {
                    conteudoLinha += " \n";
                }
                bw.write(conteudoLinha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void excluirPergunta() {
        int quantidadeLinhasAtualmente = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                quantidadeLinhasAtualmente++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (quantidadeLinhasAtualmente <= 7) {
            System.out.println("Não há perguntas customizadas que podem ser excluídas.");
            return;
        }

        System.out.println("Qual pergunta deseja excluir? ");
        int indexPerguntas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (indexPerguntas > 6) {
                    System.out.println(line);
                }
                indexPerguntas++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("OPÇÃO: ");


        String userInputPergutaExcluir;
        int numericUserInputPerguntaParaExcluir;

        while (true) {
            userInputPergutaExcluir = sc.nextLine();
            try {
                numericUserInputPerguntaParaExcluir = Integer.parseInt(userInputPergutaExcluir.trim());
                if (numericUserInputPerguntaParaExcluir < 7 || numericUserInputPerguntaParaExcluir > quantidadeLinhasAtualmente)
                    throw new IllegalArgumentException();
                break;
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Digite novamente: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Opção inexistente, informe novamente: ");
            }
        }

        System.out.print("Deseja confirmar a exclusão da pergunta: " + numericUserInputPerguntaParaExcluir + "? [SIM/NÃO]: ");
        String userInputConfirmacao = sc.nextLine();

        if (userInputConfirmacao.equalsIgnoreCase("não") || userInputConfirmacao.equalsIgnoreCase("nao") || userInputConfirmacao.equalsIgnoreCase("n")) {
            return;
        }

        String[] linhasNovoArquivo = new String[quantidadeLinhasAtualmente - 1];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int indexIteracao = 0;
            int indexPergunta = 0;
            boolean diminuirIndexDasPerguntasSeguintes = false;
            while ((line = br.readLine()) != null) {
                if (indexIteracao == numericUserInputPerguntaParaExcluir - 1) {
                    diminuirIndexDasPerguntasSeguintes = true;
                    indexIteracao++;
                    continue;
                }
                if (diminuirIndexDasPerguntasSeguintes) {
                    String conteudoPerguntaSemIndex = line.replaceAll("^\\s*\\d+\\s*-\\s*(.*)$", "$1");
                    String perguntaComIndexAjustado = indexPergunta + 1 + " - " + conteudoPerguntaSemIndex;
                    linhasNovoArquivo[indexPergunta] = perguntaComIndexAjustado;
                    indexIteracao++;
                    indexPergunta++;
                    continue;
                }
                linhasNovoArquivo[indexPergunta] = line;
                indexIteracao++;
                indexPergunta++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < linhasNovoArquivo.length; i++) {
                String conteudoLinha = linhasNovoArquivo[i];
                if (i != linhasNovoArquivo.length - 1) {
                    conteudoLinha += " \n";
                }
                bw.write(conteudoLinha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
