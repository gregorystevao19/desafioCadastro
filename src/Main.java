import domain.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        int funcaoSistema = menu.startMenu();
        switch (funcaoSistema){
            case 1 -> menu.sistemaGerenciarPets();
            case 2 -> menu.sistemaGerenciarFormulario();
        }
    }
}
