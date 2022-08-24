/**
 * The MainFrame Class provide our main view
 *
 * @author Lu Sacramento
 * @version 2.0.0 Build Aug 23, 2022.
 *
 */
package com.api.views;

// imports
import com.api.controller.Controller;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {

  // COMPONENTS
  // Option names for the buttons and menu
  private String[] textOptions = {
    "Gerar CPF Aleatório",
    "Gerar Dígito Verificador",
    "Validar CPF",
    "Executar",
    "Limpar",
  };

  // menus
  private JMenuBar menuBar;
  private JMenu menuFile;
  private JMenuItem menuItemSave, menuItemClose;
  private JMenu menuFunctions;
  private JMenuItem menuItemGenerate, menuItemGenerateVDs, menuItemValidate;
  private JMenu menuHelp;
  private JMenuItem menuItemAbout;

  // pannels
  private JPanel panelTitle;
  private JLabel labelTitle;
  private String textTitle = "";

  private JPanel panelBody;

  private JPanel panelDescripton;
  private JLabel labelDescription;
  private String textDescription = "";

  private JPanel panelCpf;
  private JLabel labelCpf;
  private JTextField inputCpf;

  private JPanel panelButton;
  private JButton buttonExecute;
  private JButton buttonClear;
  // end COMPONENTS

  // ACTIONS
  private CloseSystem closeSystem;
  private About about;
  private JFileChooser fileChooser;

  // end ACTIONS

  // Construtor
  public MainFrame() {
    super("Gerador e Validador de CPF");
    setLayout(new BorderLayout());
    getContentPane().setBackground(Color.darkGray);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(640, 360);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setVisible(true);
    createMenu();
  } // end MainFrame

  /**
   * @return JFileChooser
   */
  public JFileChooser getFileChooser() {
    return fileChooser;
  }

  /**
   * @return String[]
   */
  public String[] getTextOptions() {
    return this.textOptions;
  }

  /**
   * @return JLabel
   */
  public JLabel getLabelTitle() {
    return this.labelTitle;
  }

  /**
   * @return JTextField
   */
  public JTextField getInputCpf() {
    return this.inputCpf;
  }

  /**
   * Creating menu.
   */
  private void createMenu() {
    // Creating menubar, menus and menu items.
    menuBar = new JMenuBar();
    menuFile = new JMenu("Arquivo");
    menuItemSave = new JMenuItem("Salvar");
    menuItemClose = new JMenuItem("Fechar");
    menuFunctions = new JMenu("Funções");
    menuItemGenerate = new JMenuItem(textOptions[0]);
    menuItemGenerateVDs = new JMenuItem(textOptions[1]);
    menuItemValidate = new JMenuItem(textOptions[2]);
    menuHelp = new JMenu("Ajuda");
    menuItemAbout = new JMenuItem("Sobre");

    // Adding menu items to menu.
    menuFile.add(menuItemSave);
    menuFile.add(menuItemClose);
    menuFunctions.add(menuItemGenerate);
    menuFunctions.add(menuItemGenerateVDs);
    menuFunctions.add(menuItemValidate);
    menuHelp.add(menuItemAbout);

    // Adding menus to menubar.
    menuBar.add(menuFile);
    menuBar.add(menuFunctions);
    menuBar.add(menuHelp);

    // Setting menubar to frame.
    setJMenuBar(menuBar);
  } // end createMenu

  /**
   * Creating panel.
   * @param executeTarefas Listener for execute main tasks for generatting all types of the CPFs.
   */
  public void createPanels(Controller.ExecuteTasks executeTarefas) {
    // Title panel.
    panelTitle = new JPanel();
    panelTitle.setLayout(new FlowLayout());
    labelTitle = new JLabel(textTitle);
    labelTitle.setFont(new Font("Verdana", Font.PLAIN, 16));
    panelTitle.add(labelTitle);

    // Body panel.
    panelBody = new JPanel();
    panelBody.setLayout(new GridLayout(3, 1));

    // Action description panel.
    panelDescripton = new JPanel();
    panelDescripton.setLayout(new FlowLayout());
    labelDescription = new JLabel(textDescription);
    labelDescription.setFont(new Font("Verdana", Font.PLAIN, 12));
    panelDescripton.add(labelDescription);

    // Input data panel.
    panelCpf = new JPanel();
    panelCpf.setLayout(new FlowLayout());
    labelCpf = new JLabel("CPF");
    labelCpf.setFont(new Font("Verdana", Font.PLAIN, 16));
    inputCpf = new JTextField(11);
    inputCpf.setFont(new Font("Verdana", Font.PLAIN, 16));
    panelCpf.add(labelCpf);
    panelCpf.add(inputCpf);

    // Buttons panel.
    panelButton = new JPanel();
    panelButton.setLayout(new FlowLayout());
    buttonExecute = new JButton(textOptions[3]);
    buttonClear = new JButton(textOptions[4]);
    panelButton.add(buttonExecute);
    panelButton.add(buttonClear);

    // Adding event for the buttons.
    buttonExecute.addActionListener(executeTarefas);
    buttonClear.addActionListener(new ClearInput());

    // Adding event for the textbox Enter key.
    inputCpf.addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Controller.tasks();
          }
        }
      }
    );
    // Adding event for the button Enter key.
    buttonExecute.addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Controller.tasks();
          }
        }
      }
    );

    panelBody.add(panelDescripton);
    panelBody.add(panelCpf);
    panelBody.add(panelButton);

    add(panelTitle, BorderLayout.NORTH);
    add(panelBody, BorderLayout.CENTER);

    // set initial visibility in the components.
    panelTitle.setVisible(false);
    panelBody.setVisible(false);
    panelButton.setVisible(false);
  } // end createPanels

  /**
   * Adding shortcuts to menu.
   */
  public void addShortCuts() {
    // Tecla de atalho para menus
    menuFile.setMnemonic(KeyEvent.VK_A);
    menuFunctions.setMnemonic(KeyEvent.VK_F);
    menuHelp.setMnemonic(KeyEvent.VK_J);

    // Tecla de atalho para items de menus
    menuItemSave.setAccelerator(
      KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)
    );
    menuItemClose.setAccelerator(
      KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK)
    );

    menuItemGenerate.setAccelerator(
      KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK)
    );
    menuItemGenerateVDs.setAccelerator(
      KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK)
    );
    menuItemValidate.setAccelerator(
      KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK)
    );

    menuItemAbout.setAccelerator(KeyStroke.getKeyStroke("F1"));
  } // end addShortCuts

  /**
   * Creating menu events  from the Controller.
   * @param loadGenerateAction Listener for load generate actions.
   * @param saveLogFile Listener for save log file.
   */
  public void addEventsMenu(
    Controller.LoadGenerateAction loadGenerateAction,
    Controller.SaveLogfile saveLogFile
  ) {
    menuItemGenerate.addActionListener(loadGenerateAction);
    menuItemGenerateVDs.addActionListener(loadGenerateAction);
    menuItemValidate.addActionListener(loadGenerateAction);
    menuItemSave.addActionListener(saveLogFile);

    closeSystem = new CloseSystem();
    menuItemClose.addActionListener(closeSystem);

    about = new About();
    menuItemAbout.addActionListener(about);
  } // end addEventsMenu

  /**
   * switching task view for the option 1.
   */
  public void setVisibilityOption0() {
    panelTitle.setVisible(true);
    labelTitle.setText(textOptions[0]);
    panelBody.setVisible(true);
    labelDescription.setText(
      "Clique no botão executar para gerar um CPF aleatório"
    );
    panelCpf.setVisible(false);
    panelButton.setVisible(true);
    buttonClear.setVisible(false);
  }

  /**
   * switching task view for the option 2.
   */
  public void setVisibilityOption1() {
    panelTitle.setVisible(true);
    labelTitle.setText(textOptions[1]);
    panelBody.setVisible(true);
    labelDescription.setText(
      "Digite o CPF desejado sem o Dígito Verificador e clique no botão Executar"
    );
    panelButton.setVisible(true);
    panelCpf.setVisible(true);
    buttonClear.setVisible(true);
  }

  /**
   * switching task view for the option 3.
   */
  public void setVisibilityOption2() {
    panelTitle.setVisible(true);
    labelTitle.setText(textOptions[2]);
    panelBody.setVisible(true);
    labelDescription.setText(
      "Digite o CPF completo e clique em Executar para saber se é válido"
    );
    panelButton.setVisible(true);
    panelCpf.setVisible(true);
  }

  /**
   * CloseSystem class for adding system exit task to menu.
   */
  private class CloseSystem implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  } // End CoseSystem

  /**
   *  ClearInput Class for adding cleaning input to clean button.
   */
  private class ClearInput implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      inputCpf.setText("");
    }
  } // end ClearInput

  /*
   * About Class for show About window.
   */
  private class About implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      String out =
        "GERADOR E VALIDADOR DE CPF\nVersão 1.1.0\nSoftware para fins educacionais.\nNão responsabilizamos pelo uso indevido deste aplicativo.\nFeito por: Lu Sacramento.";
      showResultDialog("Sobre", out);
    }
  } // end About

  /**
   * Show customized dialog messages
   * @param title title of dialog message.
   * @param message message of dialog message.
   */
  public void showResultDialog(String title, String message) {
    JFrame jFrame = new JFrame();
    JOptionPane.showMessageDialog(jFrame, message, title, 1);
  } // end showResultDialog

  /**
   * Show input dialog for save log to file.
   * @param file file name.
   * @return String file name.
   */
  public String showInputDialog(String file) {
    String nameFile = JOptionPane.showInputDialog(file);
    return nameFile;
  }

  /*
   * Inicialize File Chooser.
   */

  public void createFileChooser() {
    fileChooser = new JFileChooser();
  }
}
