import model.dungeon.ReadOnlyModel;
import view.GameSetterPanel;
import view.IView;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JTextPane;

/**
 * Mock view to test the view.
 */
public class MockViewTest implements IView {
  private List<String> gameLog;

  /**
   * Constructor for mock view.
   *
   * @param log log.
   */
  public MockViewTest(List<String> log) {
    this.gameLog = log;
  }

  @Override
  public void generateGameView(ReadOnlyModel newModel) {
    gameLog.add("generateGameView was called");
  }

  @Override
  public void refreshView(ReadOnlyModel newModel, int smell) {
    gameLog.add("generateGameView was called");
  }

  @Override
  public boolean getMazeType() {
    gameLog.add("generateGameView was called");
    return false;
  }

  @Override
  public GameSetterPanel getGameSetting() {
    gameLog.add("Gamesetter panel was called");
    return new GameSetterPanel();
  }



  @Override
  public String getDirectionInput() {
    gameLog.add("direction input was called");
    return "Up";
  }


  @Override
  public String getDistanceInput() {
    gameLog.add("Distance input was called");
    return "Up";
  }

  @Override
  public String getPickUpInput() {
    gameLog.add("Distance input was called");
    return "Up";
  }

  @Override
  public JTextPane getPromptPane() {
    gameLog.add("Distance input was called");
    return new JTextPane();
  }



  @Override
  public void setListeners(ActionListener clicks, KeyListener keys) {
    gameLog.add("set listener was called was called");
  }

  @Override
  public void resetFocus() {
    gameLog.add("reset focus was called");
  }

  @Override
  public void removeRestartListener(ActionListener clicks) {
    gameLog.add("Distance input was called");
  }

  @Override
  public void removeAllListeners(ActionListener clicks, KeyListener keys) {
    gameLog.add("Distance input was called");
  }


  @Override
  public void clearInputString() {
    gameLog.add("Distance input was called");
  }
}

