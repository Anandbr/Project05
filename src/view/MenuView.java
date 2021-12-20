package view;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * This class represents a menu that user can select some items.
 */
public class MenuView extends JMenuBar {

  private final GameSetterPanel gameSettingPanel;

  /**
   * Constructor of MenuView object.
   */
  public MenuView() {
    this.gameSettingPanel = new GameSetterPanel();
    JMenu menu = new JMenu("Game Menu");
    JMenuItem gameSetting = new JMenuItem(new AbstractAction("Setting") {
      @Override
      public void actionPerformed(ActionEvent e) {
        gameSettingPanel.displayDialog();
      }
    });
    menu.add(gameSetting);
    menu.addSeparator();
    this.add(menu);
  }

  /**
   * Return game setting panel.
   *
   * @return game setting panel
   */
  public GameSetterPanel getGameSetting() {
    return gameSettingPanel;
  }
}
