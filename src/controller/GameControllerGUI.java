package controller;

import com.sun.tools.jconsole.JConsoleContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Locale;
import model.dungeon.PlayerStatus;
import model.dungeon.ReadOnlyModel;
import model.dungeon.UnWrappedDungeon;
import model.dungeon.WrappedDungeon;
import model.kruskal.DirectionEnum;
import view.IView ;

public class GameControllerGUI implements GameControllerI, ActionListener, KeyListener {

  private ReadOnlyModel dungeonGame;
  private final IView view;
  private int smell;

  public GameControllerGUI(ReadOnlyModel model, IView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    this.dungeonGame = model;
    this.view = view;
    this.smell = 0;
  }

  @Override
  public void playGame() {
    view.setListeners(this, this);
    view.generateGameView(dungeonGame);
    this.begin();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {

        case "Shoot Button":
          if (!dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
            this.view.removeAllListeners(this, this);
            return;
          }
          view.setListeners(this, this);

          String direction = view.getDirectionInput();
          String dir = null;
          switch (direction.toLowerCase()) {
            case "n":
            case "north":
              dir = "NORTH";
              break;
            case "s":
            case "south":
              dir = "SOUTH";
              break;
            case "e":
            case "east":
              dir = "EAST";
              break;
            case "w":
            case "west":
              dir = "WEST";
              break;
            default:
              break;
          }
          int dis = Integer.parseInt(view.getDistanceInput());
          this.shootArrow(DirectionEnum.valueOf(dir), dis);
          view.clearInputString();
          view.resetFocus();
          break;
        case "Start Button":
          this.beginGame();
          view.resetFocus();
          break;
        case "Restart Button":
          view.removeRestartListener(this);
          view.removeAllListeners(this, this);
          view.setListeners(this, this);
          this.beginGame();
          view.resetFocus();
          break;
        case "Up Button":
          if (!dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
            this.view.removeAllListeners(this, this);
            return;
          }
          view.setListeners(this, this);
          this.movePlayer(DirectionEnum.NORTH);
          view.resetFocus();
          break;
        case "Left Button":
          if (!dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
            this.view.removeAllListeners(this, this);
            return;
          }
          view.setListeners(this, this);
          this.movePlayer(DirectionEnum.WEST);
          view.resetFocus();
          break;
        case "Down Button":
          if (!dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
            this.view.removeAllListeners(this, this);
            return;
          }
          view.setListeners(this, this);
          this.movePlayer(DirectionEnum.SOUTH);
          view.resetFocus();
          break;
        case "Right Button":
          if (!dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
            this.view.removeAllListeners(this, this);
            return;
          }
          view.setListeners(this, this);
          this.movePlayer(DirectionEnum.EAST);
          view.resetFocus();
          break;
        case "PickUp Button":
          if (!dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
            this.view.removeAllListeners(this, this);
            return;
          }
          view.setListeners(this, this);
          String option = view.getPickUpInput();
          String opt = null;
          switch (option.toLowerCase()) {
            case "a":
            case "arrow":
              opt = "CROOKED_ARROW";
              break;
            case "all":
              opt = "ALL";
              break;
            case "d":
            case "diamond":
              opt = "DIAMOND";
              break;
            case "s":
            case "sapphire":
              opt = "SAPPHIRE";
              break;
            case "r":
            case "ruby":
              opt = "RUBY";
              break;
            default:
              opt = option;
          }
          this.pickUpTreasure(opt);
          view.clearInputString();
          view.resetFocus();
          break;

        default:
          throw new IllegalStateException("Error: Unknown button");

      }
    } catch(IllegalArgumentException error) {
      this.view.getPromptPane().setText(error.getMessage());
    }
    if (!dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
      this.view.removeAllListeners(this, this);
    }
  }


  @Override
  public void keyTyped(KeyEvent e) {
    //do nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //do nothing
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
          this.movePlayer(DirectionEnum.NORTH);
          view.setListeners(this, this);
          return;
        case KeyEvent.VK_DOWN:
          this.movePlayer(DirectionEnum.SOUTH);
          view.setListeners(this, this);
          return;
        case KeyEvent.VK_LEFT:
          this.movePlayer(DirectionEnum.WEST);
          view.setListeners(this, this);
          return;
        case KeyEvent.VK_RIGHT:
          this.movePlayer(DirectionEnum.EAST);
          view.setListeners(this, this);
          return;
        default:
          throw new IllegalStateException("Error: Unknown button");
      }
    }
  }

  /**
   * Make the player move one step and update prompt pane.
   *
   * @param dir  specified direction, one of N, E, S, W
   */
  public void movePlayer(DirectionEnum dir) {
    smell = 0;
    if (!dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
      this.view.removeAllListeners(this, this);
    }

    try{
      String moveMessage = String.valueOf(dungeonGame.makeMove(dir));
      String playerMessage = String.valueOf(dungeonGame.getPlayerDesc());
      String desc = moveMessage + playerMessage;
      if (moveMessage.toLowerCase().contains("something smells terrible")) {
        smell = 2;
      }
      else if (moveMessage.toLowerCase().contains("something smells")) {
        smell = 1;
      }
      this.view.getPromptPane().setText(desc);
      this.view.removeAllListeners(this, this);
      view.refreshView(this.dungeonGame, smell);
    }
    catch (Exception e) {
      this.view.getPromptPane().setText(e.getMessage());
      this.view.removeAllListeners(this, this);
      view.refreshView(this.dungeonGame, smell);
    }
  }


  /**
   * Make the player shoot arrow and update prompt pane.
   *
   * @param direction  specified direction
   * @param distance   specified distance
   */
  public void shootArrow(DirectionEnum direction, int distance) {
    String shootArrowMessage = dungeonGame.shootArrow(direction, distance);

    this.view.getPromptPane().setText(shootArrowMessage);
    this.view.removeAllListeners(this, this);
    view.refreshView(this.dungeonGame, smell);
  }

  /**
   * Make the player pickup treasure/Arrow and update prompt pane.
   *
   * @param option option the player wants to pickup

   */
  public void pickUpTreasure(String option) {
    String shootTreasureMessage = String.valueOf(dungeonGame.pickUpTreasure(option));

    this.view.getPromptPane().setText(shootTreasureMessage);
    this.view.removeAllListeners(this, this);

    view.refreshView(this.dungeonGame, smell);

  }

  /**
   * Initialize and begin the game.
   */
  public void beginGame() {
    if (this.view.getMazeType()) {
      this.dungeonGame = new WrappedDungeon(view.getGameSetting().getMazeRow(),
              view.getGameSetting().getMazeCol(),
              view.getGameSetting().getInterconnect(),
              view.getGameSetting().getMazeTreasure(),
              view.getGameSetting().getMazeMonster(),
              true);
    }
    else {
      this.dungeonGame = new UnWrappedDungeon(view.getGameSetting().getMazeRow(),
              view.getGameSetting().getMazeCol(),
              view.getGameSetting().getInterconnect(),
              view.getGameSetting().getMazeTreasure(),
              view.getGameSetting().getMazeMonster(),
              true);
    }
    begin();
  }

  public void begin() {
    int smell = 0;
    String message =  String.valueOf( dungeonGame.enterPlayer());
    String moveMessage =  String.valueOf(dungeonGame.getPlayerDesc());
    this.view.generateGameView(this.dungeonGame);
    if (message.toLowerCase().contains("something smells terrible")) {
      smell = 2;
    }
    else if (message.toLowerCase().contains("something smells")) {
      smell = 1;
    }
    this.view.refreshView(this.dungeonGame, smell);
    this.view.getPromptPane().setText(message + "." + moveMessage);
  }
}
