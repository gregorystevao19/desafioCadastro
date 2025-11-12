import java.io.File;
import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);
    private String userInput;

    private String directoryFormularioPerguntas = "src/assets";
    private String fileFormularioPerguntas = "formulario.txt";
    private File file = new File(directoryFormularioPerguntas, fileFormularioPerguntas);

    public void printMenu(){
        System.out.println("----------------- SISTEMA DE PETS -----------------");
        System.out.println("[1] - Cadastrar um novo pet");
        System.out.println("[2] - Alterar os dados do pet cadastrado");
        System.out.println("[3] - Deletar um pet cadastrado");
        System.out.println("[4] - Listar todos os pets cadastrados");
        System.out.println("[5] - Listar pets por algum critério (idade, nome, raça)");
        System.out.println("[6] - Sair");
        System.out.println("---------------------------------------------------");
    }

    public void handleMenuInput(){

        System.out.print("Informe qual opção será executada: ");
        userInput = sc.next();
        int numericUserInput;

        while (true){
            try{
                numericUserInput = Integer.parseInt(userInput.trim());
                if(numericUserInput < 0 || numericUserInput > 6) throw new IllegalArgumentException();
                switch (numericUserInput){
                    case 1 -> cadastrarPet();
                    case 6 -> System.out.println("Finalizando ...");
                }
                return;
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido, informe novamente: ");
                userInput = sc.next();
            }catch (IllegalArgumentException e){
                System.out.print("Opção inexistente, informe novamente: ");
                userInput = sc.next();
            }
        }
    }

    private void cadastrarPet(){

    }
}
