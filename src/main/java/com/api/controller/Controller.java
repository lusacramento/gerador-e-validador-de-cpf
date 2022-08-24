/**
 * This Controller Class manage the system updating view interacting with the entities.
 * @author Lu Sacramento
 * @version 2.0.0 Build Aug 23, 2022.
 *
 */
package com.api.controller;

import com.api.models.entity.Cpf;
import com.api.models.entity.Log;
import com.api.views.MainFrame;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

  private static MainFrame mainFrame;
  private LoadGenerateAction loadGenerateAction;
  private ExecuteTasks executeTasks;
  private SaveLogfile saveLogFile = new SaveLogfile();

  private static ArrayList<Log> logs = new ArrayList<Log>();

  // Construtor
  public Controller() {
    // Starting GUI interface.
    mainFrame = new MainFrame();

    // Loading tasks.
    executeTasks = new ExecuteTasks();
    mainFrame.createPanels(executeTasks);

    // Adding shortcut keys.
    mainFrame.addShortCuts();

    // Loading main actions.
    loadGenerateAction = new LoadGenerateAction();
    mainFrame.addEventsMenu(loadGenerateAction, saveLogFile);
  }

  /**
   * Starting App
   */
  public static void startApp() {
    Controller controller = new Controller();
  }

  /**
   * Starting tasks
   */
  public static void tasks() {
    String stringCpf = mainFrame.getInputCpf().getText();
    String option = mainFrame.getLabelTitle().getText();

    Cpf cpf = new Cpf();

    if (option == mainFrame.getTextOptions()[0]) {
      // 1 - Generate full CPF
      generateFullCpf(cpf, option);
    } else if (option == mainFrame.getTextOptions()[1]) {
      if (Cpf.validateCpf(stringCpf, 1)) {
        // 2 = Generate verificator digits
        generateVerificatorDigits(cpf, stringCpf, option);
      } else {
        System.out.println("CPF inválido!");
      }
    } else if (option == mainFrame.getTextOptions()[2]) {
      if (Cpf.validateCpf(stringCpf, 2)) {
        // 3 - Check full CPF
        checkFullCpf(cpf, stringCpf, option);
      }
    }
  } // end Tasks

  /**
   0 - Genarate automatically full CPF.
   * @param cpf CPF Class
   * @param option Option text
   */
  private static void generateFullCpf(Cpf cpf, String option) {
    cpf.generateFullCpf();
    String printResult =
      ("CPF gerado: " + cpf.formatCpfOut(cpf.getGenerated()));
    mainFrame.showResultDialog(mainFrame.getTextOptions()[0], printResult);

    // Criando Log
    Log log = new Log(
      option,
      cpf.formatCpfOut(cpf.getOriginal()),
      cpf.formatCpfOut(cpf.getGenerated()),
      true
    );
    // adding to logs list
    logs.add(log);
  }

  // 1 - Generate verificators digits.
  private static void generateVerificatorDigits(
    Cpf cpf,
    String stringCpf,
    String option
  ) {
    cpf.setOriginal(stringCpf);
    cpf.setGenerated();
    String printResult =
      ("CPF gerado: " + cpf.formatCpfOut(cpf.getGenerated()));
    mainFrame.showResultDialog(mainFrame.getTextOptions()[1], printResult);

    // Creating Log.
    Log log = new Log(
      option,
      cpf.formatCpfOut(cpf.getOriginal()),
      cpf.formatCpfOut(cpf.getGenerated()),
      true
    );
    // adding logs list.
    logs.add(log);
    mainFrame.getInputCpf().setText("");
  }

  // 2 - Check if the CPF is valid.

  static void checkFullCpf(Cpf cpf, String stringCpf, String option) {
    cpf.setOriginal(stringCpf);
    boolean isValid = cpf.verifyValidCpf();
    String textValid, printResult;
    textValid = isValid ? "CPF VÁLIDO\n" : "CPF INVÁLIDO";
    printResult =
      textValid +
      "\nCPF informado: " +
      cpf.formatCpfOut(cpf.getOriginal()) +
      "\nCPF válido: " +
      cpf.formatCpfOut(cpf.getGenerated()) +
      ".";
    mainFrame.showResultDialog(mainFrame.getTextOptions()[2], printResult);

    // Criating Log.
    Log log = new Log(
      option,
      cpf.formatCpfOut(cpf.getOriginal()),
      cpf.formatCpfOut(cpf.getGenerated()),
      isValid
    );
    // adding logs list.
    logs.add(log);
    mainFrame.getInputCpf().setText("");
  }

  /**
   * Showing errors
   * @param title title of error dialog
   * @param message message of error dialog
   */
  public static void showError(String title, String message) {
    mainFrame.showResultDialog(title, message);
  }

  /**
   * Load Generate Action Class
   */

  public class LoadGenerateAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals(mainFrame.getTextOptions()[0])) {
        mainFrame.setVisibilityOption0();
      } else if (e.getActionCommand().equals(mainFrame.getTextOptions()[1])) {
        mainFrame.setVisibilityOption1();
      } else if (e.getActionCommand().equals(mainFrame.getTextOptions()[2])) {
        mainFrame.setVisibilityOption2();
      }
    } // end LoadGenerateAction
  }

  /**
   * Execute tasks Class
   */
  public class ExecuteTasks implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      tasks();
    }
  } // end ExecuteTasks

  /**
   * Save Log File Class
   */
  public class SaveLogfile implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      // Verify if exist generated CPF
      if (logs.size() == 0) {
        mainFrame.showResultDialog(
          "Erro",
          "Não existe CPFs para salvar em arquivo"
        );
      } else {
        // Open dialog message asking the file name.
        String fileName = mainFrame.showInputDialog("Nome do arquivo");
        mainFrame.createFileChooser();
        File fileLog = new File(fileName);
        mainFrame.getFileChooser().setSelectedFile(fileLog);
        // Creating buffer for write the file.
        BufferedWriter writer;
        int sf = mainFrame.getFileChooser().showSaveDialog(null);
        if (sf == mainFrame.getFileChooser().APPROVE_OPTION) {
          // Write file.
          try {
            writer =
              new BufferedWriter(
                new FileWriter(mainFrame.getFileChooser().getSelectedFile())
              );
            for (Log log : logs) {
              if (log.getOption() != mainFrame.getTextOptions()[2]) {
                writer.write(
                  log.getOption() + "\n" + log.getGenerated() + "\n"
                );
              } else {
                String option = log.getIsValid() ? "VERDADEIRO" : "FALSO";
                writer.write(
                  log.getOption() +
                  "\n" +
                  option +
                  " Digitado: " +
                  log.getOriginal() +
                  " - Gerado: " +
                  log.getGenerated() +
                  "\n"
                );
              }
              writer.write(
                "----------------------------------------------------------------------\n"
              );
            }
            writer.write("FIM DE ARQUIVO");
            writer.close();
            // Show dialog message of the result.
            mainFrame.showResultDialog("O arquivo foi salvo", "Arquivo salvo!");
          } catch (IOException IOe) {
            IOe.printStackTrace();
          }
        } else if (sf == mainFrame.getFileChooser().CANCEL_OPTION) {
          mainFrame.showResultDialog(
            null,
            "O salvamento de arquivo foi cancelado"
          );
        }
      }
    }
  } // end SaveLogfile
}
