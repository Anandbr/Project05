package controller;

import com.sun.source.tree.IfTree;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Locale;
import model.dungeon.DungeonInterface;
import model.dungeon.PlayerStatus;
import model.dungeon.ReadOnlyModel;
import model.dungeon.UnWrappedDungeon;
import model.dungeon.WrappedDungeon;
import model.kruskal.DirectionEnum;
import view.IView ;


public class GameControllerGUI implements GameControllerI, ActionListener, KeyListener {

  private ReadOnlyModel dungeonGame;
  private IView view;

  public GameControllerGUI(ReadOnlyModel model, IView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    this.dungeonGame = model;
    this.view = view;
    //todo

  }


  @Override
  public void playGame() {
    //todo
    view.setListeners(this, this);
    view.generateGameView(dungeonGame); //todo



  }



  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      // read from the input text field
      case "Shoot Button":
        view.setListeners(this, this);
        String dir = view.getDirectionInput();
        int dis = Integer.parseInt(view.getDistanceInput());
        this.shootArrow(DirectionEnum.valueOf(dir), dis);
        view.clearInputString();
        view.resetFocus();
        break;
      case "Start Button":
        this.beginGame();

        //System.out.println("INBETWEEN");
        //System.out.println(view.getMazeType());
        //System.out.println("NOW?" + view.getMazeType());
        //System.out.println("INBETWEEN");
        view.resetFocus();
        break;
      case "Restart Button":
        view.removeRestartListener(this);
        view.removeAllListeners(this);
        view.setListeners(this, this);
        this.beginGame();
        view.resetFocus();
        break;
      case "Up Button":
        view.setListeners(this, this);
        this.movePlayer(DirectionEnum.NORTH);
        view.resetFocus();
        break;
      case "Left Button":
        view.setListeners(this, this);
        this.movePlayer(DirectionEnum.WEST);
        view.resetFocus();
        break;
      case "Down Button":
        view.setListeners(this, this);
        this.movePlayer(DirectionEnum.SOUTH);
        view.resetFocus();
        break;
      case "Right Button":
        view.setListeners(this, this);
        this.movePlayer(DirectionEnum.EAST);
        view.resetFocus();
        break;
      case "Pickup Button":
        view.setListeners(this, this);
        String option = view.getPickUpInput();
        this.pickUpTreasure(option);
        view.clearInputString();
        view.resetFocus();
        break;

      default:
        throw new IllegalStateException("Error: Unknown button");
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
//    view.setListeners(this, this);
    if (e.getKeyChar() == 'd') {
      System.out.println("Hi");
    }
//    view.resetFocus();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyChar() == 'f') {
      System.out.println("NoNoNo");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        view.setListeners(this, this);
        this.movePlayer(DirectionEnum.NORTH);
        view.resetFocus();
        return;
      case KeyEvent.VK_DOWN:
        view.setListeners(this, this);
        this.movePlayer(DirectionEnum.SOUTH);
        view.resetFocus();
        return;
      case KeyEvent.VK_LEFT:
        view.setListeners(this, this);
        this.movePlayer(DirectionEnum.WEST);
        view.resetFocus();
        return;
      case KeyEvent.VK_RIGHT:
        view.setListeners(this, this);
        this.movePlayer(DirectionEnum.EAST);
        view.resetFocus();
        return;
      default:
        throw new IllegalStateException("Error: Unknown button");
    }
  }

  /**
   * Make the player move one step and update prompt pane.
   *
   * @param dir  specified direction, one of N, E, S, W
   */
  public void movePlayer(DirectionEnum dir) {
    int smell = 0;
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
      this.view.removeAllListeners(this);
      view.refreshView(this.dungeonGame, smell);
    }
    catch (Exception e) {
      this.view.getPromptPane().setText(e.getMessage());
      this.view.removeAllListeners(this);
      view.refreshView(this.dungeonGame, smell);
    }


    //this.view.setChangingMark(dungeonGame.getAdajacencyList()), dungeonGame.smell(),
            //dungeonGame.getPlayer().getCurrentLocation();

//    System.out.println(this.dungeonGame);
  }


  /**
   * Make the player shoot arrow and update prompt pane.
   *
   * @param direction  specified direction
   * @param distance   specified distance
   */
  public void shootArrow(DirectionEnum direction, int distance) {
    String shootArrowMessage = dungeonGame.shootArrow(direction, distance);

    PlayerStatus playerCondition = dungeonGame.getPlayer().getPlayerStatus();

    this.view.getPromptPane().setText(shootArrowMessage);
    this.view.removeAllListeners(this);

    //todo CHECK WHETHER arrow refresh is required
    view.refreshView(this.dungeonGame);
  }



  /**
   * Make the player pickup treasure/Arrow and update prompt pane.
   *
   * @param option option the player wants to pickup

   */
  public void pickUpTreasure(String option) {
    String shootTreasureMessage = String.valueOf(dungeonGame.pickUpTreasure(option));

    PlayerStatus playerCondition = dungeonGame.getPlayer().getPlayerStatus();
    this.view.getPromptPane().setText(shootTreasureMessage);
    this.view.removeAllListeners(this);

    //todo check whether refresh is required

    view.refreshView(this.dungeonGame);


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
              false);
      //this.dungeonGame.enterPlayer();

      //this.view.setUnchangedMark(dungeonGame.getTreasureList(),dungeonGame.getArrowList(),
              //dungeonGame.getMonsterList());
    }
    else {
      this.dungeonGame = new UnWrappedDungeon(view.getGameSetting().getMazeRow(),
              view.getGameSetting().getMazeCol(),
              view.getGameSetting().getInterconnect(),
              view.getGameSetting().getMazeTreasure(),
              view.getGameSetting().getMazeMonster(),
              false);
      //this.dungeonGame.enterPlayer();

      //this.view.setUnchangedMark(dungeonGame.getTreasureList(),dungeonGame.getArrowList(),
              //dungeonGame.getMonsterList());
    }

    dungeonGame.enterPlayer();
    String moveMessage =  String.valueOf(dungeonGame.getPlayerDesc());

    this.view.generateGameView(this.dungeonGame);
    // this.view.setController(this);
//    this.printCurrentInfo("");

//    this.view.setChangingMark(maze.getRoomList(), maze.smell(),
//        maze.getPlayerLocation(), maze.getPlayerQueue());
//    this.view.setChangingMark(dungeonGame.getPlayer().getCurrentLocation());

     //todo
    this.view.refreshView(this.dungeonGame);
    this.view.getPromptPane().setText(moveMessage);
  }

  /**
   * Update prompt pane with information of current user.
   *
   * @param cur previous information on prompt pane.
   */
  public void printCurrentInfo(String cur) {
    int i = 0;
//    dungeonGame.enterPlayer();  //check todo
    String curLoc =
            dungeonGame.getPlayerDesc()+" Doors Lead to  :" +
                     dungeonGame.getPossibleMoves(dungeonGame.getPlayer().getCurrentLocation());

    String prompt = "Move(click or type arrow keys) or Shoot(click) or PickUp(select option)\n";
    this.view.getPromptPane().setText(cur + curLoc + prompt);
  }

}
