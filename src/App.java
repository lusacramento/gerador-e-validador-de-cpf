/**
 *                  Gerador e Validador de CPF
 *
 * Este aplicativo foi desenvolvido para geração de <b>CPF válidos e para validação de CPF</b>.
 * Faz parte dos estudos do curso de Programador de Sistemas oferecido pela Funec - Contagem
 * ofertado pelo programa federal <i>PRONATEC</i>.
 * Inclui as classes:
 * <ul>
 * <li><b>Main</b>: Classe principal do aplicativo, para gerencar o menu e as entradas de dados;</li>
 * <li><b>Cpf</b>: Classe de atributos e métodos que gera e valida CPF.</li>
 * </ul>
 *
 * @author: Lu Sacramento
 * @version: 1.0
 * @date: 11/07/2022
 *
 */
// Importação de bibliotecas
import entity.Cpf;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App {

  // Método Principal
  public static void main(String[] args) throws InterruptedException {
    int option = 0;
    boolean isExit = false;

    displayHomeScreen();

    // cria objeto principal
    Cpf cpf = new Cpf();

    // inicializa o objeto Scanner
    Scanner read = new Scanner(System.in);

    // Menu de opções
    do {
      cpf.clearCpf();
      option = menu(read);
      switch (option) {
        case 1:
          cpf.generateFullCpf();
          awaitToEnter(read);

          break;
        case 2:
          getInput(cpf, read, option);
          awaitToEnter(read);

          break;
        case 3:
          getInput(cpf, read, option);
          awaitToEnter(read);
          break;
        case 4:
          isExit = true;
          break;
        default:
          System.out.println("Escolha uma opção válida!");
          break;
      }
    } while (!isExit);
    read.close();
  } // fim do método main

  // Exibe tela inicial do aplicativo.
  public static void displayHomeScreen() {
    clearConsole();
    System.out.println("---------------------------------");
    System.out.println("|      Gerador de CPF           |");
    System.out.println("_________________________________");
    System.out.println();

    // Aguarda 2 segundos a tela inicial
    try {
      TimeUnit.SECONDS.sleep(2);
      clearConsole();
    } catch (Exception e) {
      System.out.println("Erro ao tentar esperar 2 segundos!");
    }
  } // fim do método displayHomeScreen

  // Exibe menu de opções.
  public static int menu(Scanner read) {
    boolean isValid = false;
    int option = 0;

    do {
      System.out.println("1 - Gerar CPF automaticamente");
      System.out.println("2 - Gerar Dígito Verificador a partir de um prefixo");
      System.out.println("3 - Validar CPF");
      System.out.println("4 - Sair");
      System.out.printf("Informe a opção desejada: ");

      try {
        // Lê a opção do usuário já convertendo para números Inteiros
        option = Integer.parseInt(read.nextLine());
        clearConsole();
      } catch (Exception e) {
        System.out.println("O valor especificado tem que ser número inteiro!");
      }

      if (option >= 1 && option <= 4) {
        isValid = true;
      } else {
        System.out.println("Opção inválida!");
      }
    } while (!isValid);
    return option;
  } // fim do método menu

  // Aguarda o usuário pressionar ENTER para continuar.
  public static void awaitToEnter(Scanner read) {
    System.out.println();
    System.out.println("Precione a tecla ENTER para voltar ao menu...");
    read.nextLine();
    clearConsole();
  } // fim do método awaitToEnter

  // Pega a entrada de dados via teclado do usuário.
  public static void getInput(Cpf cpf, Scanner read, int option) {
    String cpfString = "";
    boolean isValidateDataOption = false;

    do {
      switch (option) {
        case 2:
          System.out.printf(
            "Informe os 9 dígitos do prefixo do CPF. (somente números): "
          );
          break;
        case 3:
          System.out.printf(
            "Informe o CPF completo a ser validado. (somente números): "
          );
          break;
      }

      cpfString = read.nextLine();
      clearConsole();

      // tratamento para opcaco 2
      if (option == 2) {
        if (cpfString.length() == 9) {
          if (isValidateDataOption == cpf.addCpf(cpfString, option)) {
            System.out.println("O CPF deve conter apenas números!");
          } else {
            cpf.generateVD();
            isValidateDataOption = true;
          }
        } else System.out.println("O CPF deve ter 9 dígitos!");
      }

      // tratamento para opcaco 3
      if (option == 3) {
        if (cpfString.length() == 11) {
          if (isValidateDataOption == cpf.addCpf(cpfString, option)) {
            System.out.println("O CPF deve conter apenas números!");
          } else {
            cpf.verifyValidCpf();
            isValidateDataOption = true;
          }
        } else System.out.println("O CPF deve ter 11 dígitos!");
      }
    } while (!isValidateDataOption);
  } // fim do método getInput

  // Limpa o console.
  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  } // fim do método clearConsole
}
