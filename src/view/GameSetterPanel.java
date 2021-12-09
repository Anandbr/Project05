package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;



/**
 * This class represents a panel that user can specify all game settings.
 */

public class GameSetterPanel extends JDialog {

  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JTextField rowInput;
  private JTextField colInput;
  private JTextField interconnectivityInput;
  private JTextField treasurePercentInput;
  private JTextField monsterInput;
  private JCheckBox wrapping;
  private JLabel Interconnectivity;
  private int mazeRow;
  private int mazeCol;
  private double treasurePercent;
  private int monster;
  private int interconnect;

  /**
   * Constructor of GameSettingPanel object.
   */
  public GameSetterPanel() {
    setTitle("Game Setting");
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

    buttonCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    });

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    contentPane.registerKeyboardAction(new ActionListener() {
                                         public void actionPerformed(ActionEvent e) {
                                           onCancel();
                                         }
                                       }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }

  private void onOK() {
    setMazeType();
    setMazeRow();
    setMazeCol();
    setMazeWall();
    setTreasurePercent();
    setMazeMonster();
    dispose();
  }

  private void onCancel() {
    dispose();
  }

  /**
   * Display a modal dialog.
   */
  public void displayDialog() {
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  /**
   * Getter for type of maze.
   *
   * @return type of maze
   */
  public boolean getMazeType() {
    if (wrapping.isSelected()) {
      return true;
    }
    else return false;


  }

  /**
   * Getter for number of row of maze.
   *
   * @return number of row of maze
   */
  public int getMazeRow() {
    return this.mazeRow;
  }

  /**
   * Getter for number of col of maze.
   *
   * @return number of col of maze
   */
  public int getMazeCol() {
    return this.mazeCol;
  }

  /**
   * Getter for number of arrow of maze.
   *
   * @return number of arrow of maze
   */
  public double getMazeTreasure() {
    return this.treasurePercent;
  }




  /**
   * Getter for number of monsters of dungeon.
   *
   * @return number of monsters of dungeon
   */
  public int getMazeMonster() {
    return this.monster;
  }

  /**
   * Getter for number of interconnectivity of maze.
   *
   * @return number of interconnectivity of maze
   */
  public int getInterconnect() {
    return this.interconnect;
  }

  /**
   * Setter for type of maze.
   */
  public void setMazeType() {
    boolean mazeType;
    if (wrapping.isSelected()) {
      mazeType = true;
    }

    mazeType =  false;
  }

  /**
   * Setters for the number of rows of maze using user specified input.
   */
  public void setMazeRow() {
    if (rowInput != null) {
      mazeRow = Integer.parseInt(rowInput.getText());
    }
  }

  /**
   * Setters for the number of cols of maze using user specified input.
   */
  public void setMazeCol() {
    if (colInput != null)
    mazeCol = Integer.parseInt(colInput.getText());
  }

  /**
   * Setters for the percent of treasure/Arrow.
   */
  public void setTreasurePercent() {
    treasurePercent = (double)Integer.parseInt(treasurePercentInput.getText());
  }


  /**
   * Setters for the interconnectivity of maze using user specified input.
   */
  public void setMazeWall() {
    interconnect = Integer.parseInt(interconnectivityInput.getText());
  }

  /**
   * Setters for the interconnectivity of maze using user specified input.
   */
  public void setMazeMonster() {
    monster = Integer.parseInt(monsterInput.getText());
  }

}
