package view;

import model.dungeon.ReadOnlyModel;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;


/**
 * The interface for the view class.
 */
public interface IView {

  /**
   * Generate an initial game view.
   *

   */
  void generateGameView(ReadOnlyModel newModel);

  /**
   * Refresh the game view.
   *
   */
  void refreshView(ReadOnlyModel newModel, int smell);


  /**
   * Get the maze type.
   *
   * @return the type of maze
   */
  boolean getMazeType();

  /**
   * Get the game setting panel.
   *
   * @return the game setting panel
   */
  GameSetterPanel getGameSetting();

  /**
   * Get the direction string from the text field and return it.
   *
   * @return the direction string
   */
  String getDirectionInput();

  /**
   * Get the distance string from the text field and return it.
   *
   * @return the distance string
   */
  String getDistanceInput();

  /**
   * Get the treasure/Arrow option string from the text field and return it.
   *
   * @return the treasure/arrow string
   */

  String getPickUpInput();

  /**
   * Get the prompt pane which show the current status of the game.
   *
   * @return the prompt pane
   */
  JTextPane getPromptPane();

  /**
   * Setters for the listeners.
   *
   * @param clicks the listener for the button clicks
   * @param keys   the listener for the keys
   */
  void setListeners(ActionListener clicks, KeyListener keys);

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard
   * listener attached to it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Remover for the listeners of RestartButton.
   *
   * @param clicks the listener for the button clicks
   */
  void removeRestartListener(ActionListener clicks);

  /**
   * Remover for all listeners.
   *
   * @param clicks the listener for the button clicks
   */
  void removeAllListeners(ActionListener clicks, KeyListener keys);

  /**
   * Clear the text field.
   */
  void clearInputString();

}





