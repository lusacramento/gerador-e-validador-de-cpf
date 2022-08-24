/**
 * This Cpf Class is a entity for  to save, to generate and to check the CPFs.
 * @author Lu Sacramento
 * @version 2.0.0 Build Aug 23, 2022.
 */

package com.api.models.entity;

import com.api.controller.Controller;
import java.util.ArrayList;

public class Cpf {

  private ArrayList<Integer> original, generated;

  private int vD1, vD2;

  // Constructor
  public Cpf() {
    this.original = new ArrayList<Integer>();
    this.generated = new ArrayList<Integer>();
    this.vD1 = 0;
    this.vD2 = 0;
  } // end construtor

  // Getters and Setters
  // Getters
  public ArrayList<Integer> getOriginal() {
    return original;
  }

  public ArrayList<Integer> getGenerated() {
    return generated;
  }

  public int getVD1() {
    return vD1;
  }

  public int getVD2() {
    return vD2;
  }

  // Setters

  /**
   * @param cpf Expect to receive string cpf for to convert to list.
   */
  public void setOriginal(String cpf) {
    char c;
    for (int i = 0; i < cpf.length(); i++) {
      c = cpf.charAt(i);
      this.original.add(Character.getNumericValue(c));
    }
  }

  /**
   * set full Cpf
   */
  public void setGenerated() {
    this.generated = new ArrayList<Integer>(getOriginal());
    if (this.generated.size() == 11) {
      this.generated.remove(10);
      this.generated.remove(9);
    }
    setVD1(this.generated);
    this.generated.add(getVD1());
    setVD2(this.generated);
    this.generated.add(getVD2());
  }

  // setvD1 define o valor do 1o dígito verificador 1 baseado no prefixo do CPF
  /**
   * set the value of first verificator digit based of CPF prefix
   * @param cpf prefix cpf
   */
  public void setVD1(ArrayList<Integer> cpf) {
    int sum = 0, mod = 0;

    for (int i = 0; i < 9; i++) {
      // pega o valor do cpf na posição i
      int currentCpf = cpf.get(i);
      // Acumula a soma dos dígitos aplicando o cálculo cpf na posição i
      sum += (currentCpf * (10 - i));
    }
    // Calcula o Dígito Verificador 1o
    mod = (sum * 10 % 11);
    this.vD1 = mod == 10 ? 0 : mod;
  }

  // setvD2() define o valor do 2o dígito verificador 1 baseado no prefixo e no 1o
  // dígito verificador do CPF

  /**
   * set the value of second verificator digit based of CPF prefix and first verificator digit
   * @param cpf cpf prefix and first verificator digit
   */
  public void setVD2(ArrayList<Integer> cpf) {
    int sum = 0, mod = 0;

    for (int i = 0; i < 9; i++) {
      int currentCpf = cpf.get(i); // get the value of CPF in i position.

      sum += (currentCpf * (11 - i));
    }
    sum += (this.vD1 * 2);
    mod = (sum * 10 % 11);

    this.vD2 = mod == 10 ? 0 : mod;
  } // fim dos Getters e Setters

  // End Getters and Setters

  /**
   * Clear the main attributs of CPF Class.
   */
  public void clearCpf() {
    if (this.original.size() > 0) {
      this.original.clear();
    }

    if (this.generated.size() > 0) {
      this.generated.clear();
    }
    this.vD1 = 0;
    this.vD2 = 0;
  } // end clearCpf

  // Formata o CPF para imprimir o resultado.

  /** Format CPF to print result.
   * @param cpf full cpf list
   * @return cpf formated in String format.
   */
  public String formatCpfOut(ArrayList<Integer> cpf) {
    String formatedCpf = "";
    for (int i = 0; i < cpf.size(); i++) {
      formatedCpf += cpf.get(i);
      if (i == 2 || i == 5) {
        formatedCpf += ".";
      }
      if (i == 8) {
        formatedCpf += "-";
      }
    }
    return formatedCpf;
  } // end printCpf

  /**
   * Generate a full CPF random.
   */
  public void generateFullCpf() {
    for (int i = 0; i < 9; i++) {
      int random = (int) (Math.random() * 10);
      this.original.add(random);
    }
    this.setGenerated();
  } // end generateFullCpf

  /**
   * Check if a valid CPF
   * @return true or false
   */
  public boolean verifyValidCpf() {
    boolean isValid = false;
    // genarate a copy (assistant) of CPF prefix and add verificators digits
    setGenerated();

    if (getOriginal().get(9) == getVD1() && getOriginal().get(10) == getVD2()) {
      isValid = true;
    }
    return isValid;
  } // fim do método verifyValidCpf

  // converte para um array de inteiros.

  /**
   * Verify if the String characters contains only numbers.Case positive, convert to integers Array.
   *
   * @param cpf String CPF
   * @param option int Option
   * @return
   */
  public static boolean validateCpf(String cpf, int option) {
    boolean isValidCpf = false, isContainsLetter = false;
    char c;

    for (int i = 0; i < cpf.length(); i++) {
      c = cpf.charAt(i);
      if (c < '0' || c > '9') {
        Controller.showError("Erro!", "O CPF deve conter apenas números!");
        isContainsLetter = true;
        break;
      }
    }

    if (option == 1 && cpf.length() != 9) Controller.showError(
      "Erro!",
      "O prefixo do CPF deve conter 9 dígitos!"
    ); else if (option == 2 && cpf.length() != 11) Controller.showError(
      "Erro!",
      "O CPF deve conter 11 dígitos!"
    ); else if (!isContainsLetter) isValidCpf = true;
    return isValidCpf;
  } // end validateCpf
}
