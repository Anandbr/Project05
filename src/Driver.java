import controller.GameControllerGUI;
import controller.GameControllerI;
import model.dungeon.DungeonInterface;
import model.dungeon.UnWrappedDungeon;
import view.DungeonGameView;
import view.IView;

public class Driver {

  public static void main(String[] args) {

      System.out.println("GUI MODE");
      // Create the view
    DungeonInterface dungeonModel = new UnWrappedDungeon(3,3,0,100,2,false);
      IView view = new DungeonGameView("Hunt the Wumpus!", dungeonModel);

    GameControllerI c = new GameControllerGUI(dungeonModel, view);
    c.playGame();
    }

  }
