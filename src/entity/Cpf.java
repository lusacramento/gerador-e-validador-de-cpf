/**
 * Esta classe faz parte do aplicativo <b>Gerador e Validador de CPF</b>.
 * Contém os atributos e métodos para geração e validação de CPF.
 * @author: Lu Sacramento
 * @version: 1.0
 */
package entity;

import java.util.ArrayList;

public class Cpf {

  // Variável principal de CPF
  private ArrayList<Integer> original, assistant, prefix;
  private int vD1, vD2;

  // Construtor
  public Cpf() {
    this.original = new ArrayList<Integer>();
    this.assistant = new ArrayList<Integer>();
    this.prefix = new ArrayList<Integer>();
    this.vD1 = 0;
    this.vD2 = 0;
  } // fim do construtor

  // Getters e Setters
  public ArrayList<Integer> getOriginal() {
    return original;
  }

  public void setOriginal(ArrayList<Integer> original) {
    this.original = original;
  }

  public ArrayList<Integer> getAssistant() {
    return assistant;
  }

  public void setAssistant(ArrayList<Integer> assistant) {
    this.assistant = assistant;
  }

  public ArrayList<Integer> getPrefix() {
    return prefix;
  }

  public void setPrefix(ArrayList<Integer> prefix) {
    this.prefix = prefix;
  }

  public int getvD1() {
    return vD1;
  }

  // setvD1 define o valor do 1o dígito verificador 1 baseado no prefixo do CPF
  public void setvD1() {
    int sum = 0, mod = 0;

    for (int i = 0; i < 9; i++) {
      // pega o valor do cpf na posição i
      int currentCpf = prefix.get(i);
      // Acumula a soma dos dígitos aplicando o cálculo cpf na posição i
      sum += (currentCpf * (10 - i));
    }
    // Calcula o Dígito Verificador 1o
    mod = (sum * 10 % 11);
    this.vD1 = mod == 10 ? 0 : mod;
  }

  public int getvD2() {
    return vD2;
  }

  // setvD2() define o valor do 2o dígito verificador 1 baseado no prefixo e no 1o
  // dígito verificador do CPF
  public void setvD2() {
    int sum = 0, mod = 0;

    for (int i = 0; i < 9; i++) {
      int currentCpf = this.original.get(i); // pega o valor do cpf na posição i

      sum += (currentCpf * (11 - i));
    }
    sum += (this.vD1 * 2);
    mod = (sum * 10 % 11);

    this.vD2 = mod == 10 ? 0 : mod;
  } // fim dos Getters e Setters

  // -------------- Métodos ---------------------

  // Limpa os atributos principais da classe CPF.
  public void clearCpf() {
    if (this.original.size() > 0) {
      this.original.clear();
    }
    if (this.assistant.size() > 0) {
      this.assistant.clear();
    }
    if (this.prefix.size() > 0) {
      this.prefix.clear();
    }
    this.vD1 = 0;
    this.vD2 = 0;
  } // fim do método clearCpf

  // Imprime o CPF formatado em tela.
  public void printCpf(ArrayList<Integer> cpf) {
    String cpfPrinted = "";
    for (int i = 0; i < cpf.size(); i++) {
      cpfPrinted += cpf.get(i);
      if (i == 2 || i == 5) {
        cpfPrinted += ".";
      }
      if (i == 8) {
        cpfPrinted += "-";
      }
    }
    System.out.println(cpfPrinted);
  } // fim do método printCpf

  // Gera um prefixo (9 dígitos iniciais sem os dígitos verificadores) aleatório
  // do CPF.
  public void generatePrefix() {
    for (int i = 0; i < 9; i++) {
      int random = (int) (Math.random() * 10);
      this.prefix.add(random);
    }
  } // fim do método generatePrefix

  // Gera um CPF aleatório com todos os 11 dígitos.
  public void generateFullCpf() {
    generatePrefix();
    generateVD();
  } // fim do método generateFullCpf

  // Adiciona no atributo principal de CPF o prefixo e os 2 dígitos verificadores
  // gerados por Setters.
  public void generateVD() {
    // copia array sem referência
    this.original = new ArrayList<>(this.prefix);
    setvD1();
    setvD2();
    this.original.add(this.vD1);
    this.original.add(this.vD2);

    printCpf(this.original);
  } // fim do método generateVD

  // Verifica se um CPF é válido.
  public void verifyValidCpf() {
    // gera uma cópia (assistant) do prefixo do CPF e adiciona os 2 dígitos
    // verificadores para verificar se o CPF é válido.
    this.assistant = new ArrayList<>(this.prefix);
    setvD1();
    this.assistant.add(this.vD1);
    setvD2();
    this.assistant.add(this.vD2);

    if (getOriginal().get(9) == getvD1() && getOriginal().get(10) == getvD2()) {
      System.out.println("CPF VÁLIDO");
    } else {
      System.out.println("CPF INVÁLIDO");
    }

    System.out.printf("CPF de Entrada: ");
    printCpf(this.original);
    System.out.println();
    System.out.printf("CPF Validado:   ");
    printCpf(this.assistant);
  } // fim do método verifyValidCpf

  // Verifica se a cadeia de caracteres contém apenas números e caso for positivo,
  // converte para um array de inteiros.
  public boolean addCpf(String st, int option) {
    char c;
    boolean isValidateDataOption = true;

    // Limpando arrays de cpf
    clearCpf();

    for (int i = 0; i < st.length(); i++) {
      c = st.charAt(i);
      if (c < '0' || c > '9') {
        // sai do loop caso encontre um caractere não numérico.
        i = st.length();
        isValidateDataOption = false;
      } else {
        // caso opção 2, atribui o CPF lido no prefixo.
        // caso opção 3, atribui os 11 dígitos no original e os 9 dígitos no prefixo.
        if (option == 2) {
          this.prefix.add(Character.getNumericValue(c));
        } else {
          this.original.add(Character.getNumericValue(c));
          if (i < (st.length() - 2)) {
            this.prefix.add(Character.getNumericValue(c));
          }
        }
      }
    }
    return isValidateDataOption;
  }
}
