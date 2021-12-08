package view;

import java.awt.event.KeyEvent;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.Label;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import java.util.Map;
import java.util.Set;

import model.dungeon.ReadOnlyModel;
import model.kruskal.ArrowEnum;
import model.kruskal.DirectionEnum;
import model.kruskal.TreasureEnum;


/**
 * This class represents the implementation of the view.
 */
public class DungeonGameView extends JFrame implements IView {
  private static final long serialVersionUID = 5883479994622814210L;

  private JLabel playerLabel;
  private JButton buttonCancel;
  private JPanel shootPane;
  private JButton shootButton;
  private JButton startButton;
  private JButton reStartButton;
  private JButton upButton;
  private JButton leftButton;
  private JButton downButton;
  private JButton rightButton;
  private JTextField directionInput;
  private JTextField distanceInput;
  private JTextField pickUpInput;
  private JButton pickUpButton;
  private MenuView menu;
  private JPanel mazeMap;
  private JTextPane promptPane;
  private JPanel controlPanel;
  private Map.Entry<Integer, Integer> monsterLoc;
  private Map.Entry<Integer,Integer> treasureLoc;
  private Map.Entry<Integer,Integer> arrowLoc;
  private Set<Map.Entry<Integer, Integer>> roomList;
  private Map.Entry<Integer, Integer> playerLoc;
  private ReadOnlyModel model;

  /**
   * Constructor of GameView object. And initializes with basic control panel.
   *
   * @param caption the caption for the view
   */
  public DungeonGameView(String caption, ReadOnlyModel m) {
    super(caption);
    model = m;
    setSize(500, 1000);
    setLocation(500, 1000);
    this.menu = new MenuView();

    setJMenuBar(menu);
    setFocusable(true);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.playerLabel = this.getImageLabel("player", 20, 20);

    startButton = new JButton("START GAME");
    startButton.setActionCommand("Start Button");
    this.add(startButton);

    reStartButton = new JButton("RESTART");
    reStartButton.setActionCommand("Restart Button");



    shootButton = new JButton("SHOOT");
//    ImageIcon uk = new ImageIcon("image\\target.png");
//    this.shootButton.setIcon(uk);
    shootButton.setActionCommand("Shoot Button");
    this.add(shootButton);

    pickUpButton = new JButton("Pickup");
//    ImageIcon uk = new ImageIcon("image\\target.png");
//    this.shootButton.setIcon(uk);
    pickUpButton.setActionCommand("PickUp Button");
    this.add(pickUpButton);

    upButton = new BasicArrowButton(BasicArrowButton.NORTH);
    upButton.setActionCommand("Up Button");
    leftButton = new BasicArrowButton(BasicArrowButton.WEST);
    leftButton.setActionCommand("Left Button");
    downButton = new BasicArrowButton(BasicArrowButton.SOUTH);
    downButton.setActionCommand("Down Button");
    rightButton = new BasicArrowButton(BasicArrowButton.EAST);
    rightButton.setActionCommand("Right Button");

    controlPanel = new JPanel(new GridLayout(1, 3));

    // first panel: shoot input and start/restart
    JPanel shootPanel = new JPanel(new GridLayout(7, 3));
    shootPanel.add(new Label("Shoot Direction(N, W, S, E)"));


    // the direction text field
    directionInput = new JTextField(10);
    shootPanel.add(directionInput);
    shootPanel.add(new Label(""));
    shootPanel.add(new Label("Shoot Distance"));

    // the distance text field
    distanceInput = new JTextField(10);
    shootPanel.add(distanceInput);
    shootPanel.add(new Label(""));

    //
    pickUpInput = new JTextField(10);
    shootPanel.add(new Label("Pickup (D, R, S, A, ALL)"));

//    pickUpButton.setActionCommand("Pickup Button");


    shootPanel.add(pickUpInput);
    shootPanel.add(new Label(""));

    //


    shootPanel.add(startButton);
    shootPanel.add(reStartButton);
    shootPanel.add(new Label(""));
    shootPanel.add(shootButton);
    shootPanel.add(pickUpButton);
    shootPanel.add(new Label(""));

//    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
//    buttonPanel.add(shootButton);
//    buttonPanel.add(pickUpButton);

    // second panel: move button
//    JPanel movePanel = new JPanel(new GridLayout(2, 3));
    shootPanel.add(new Label(""));
    shootPanel.add(upButton);
    shootPanel.add(new Label(""));
    shootPanel.add(leftButton);
    shootPanel.add(downButton);
    shootPanel.add(rightButton);
    // add to controlPanel
    controlPanel.add(shootPanel);
//    controlPanel.add(buttonPanel);
//    controlPanel.add(shootButton);
//    controlPanel.add(pickUpButton);
//    controlPanel.add(movePanel);
    this.add(controlPanel);


    pack();
    setVisible(true);

  }

  private void onCancel() {
    dispose();
  }

  @Override
  public void generateGameView(ReadOnlyModel newModel) {

    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.fill = GridBagConstraints.VERTICAL;
    JPanel base = new JPanel(gridBagLayout);
    this.mazeMap = new JPanel(new GridLayout(newModel.getNumberOfRows(), newModel.getNumberOfColumns()));
    gridBagLayout.setConstraints(this.mazeMap, gridBagConstraints);
    base.add(controlPanel);

    mazeMap.setBackground(Color.black);
    //System.out.println(row);
    //System.out.println(col);

    for (int i = 0; i < newModel.getNumberOfRows(); i++) {
      for (int j = 0; j < newModel.getNumberOfColumns(); j++) {
        JPanel panel = new JPanel();
        LayoutManager overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);
        panel.add(this.getImageLabel("blank", 500, 1000));
        this.mazeMap.add(panel);

      }
    }
    base.add(this.mazeMap);

    this.promptPane = new JTextPane();
    gridBagLayout.setConstraints(this.promptPane, gridBagConstraints);
    base.add(this.promptPane);


    JScrollPane myJScrollPane = new JScrollPane(base,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    setContentPane(myJScrollPane);
    base.revalidate();
    pack();
  }

  @Override
  public void refreshView(ReadOnlyModel newModel) {
    refreshView(newModel, 0);
  }

  @Override
  public void refreshView(ReadOnlyModel newModel, int smell) {
    //System.out.println(maze.getPlayerLocation());
    //System.out.println(maze.getPossibleStep());

    this.promptPane.revalidate();
    for (int i = 0; i < newModel.getNumberOfRows(); i++) {
      for (int j = 0; j < newModel.getNumberOfColumns(); j++) {
        int loc = i * newModel.getNumberOfColumns() + j;
//        JPanel panel = (JPanel) this.mazeMap.getComponentAt(i, j); //todo this is rendering null. need to see how to generate this panel
        JPanel panel = (JPanel) this.mazeMap.getComponent(loc);
        panel.removeAll();

        if (i == (newModel.getPlayer().getCurrentLocation().getKey()) &&
            j == (newModel.getPlayer().getCurrentLocation().getValue())) { //todo why is player loc out of boundary?
          panel.add(this.getImageLabel("player", 20, 20));
          if (smell == 1) {
            panel.add(this.getImageLabel("stench01", 10, 10));
          }

          if (smell == 2) {
            panel.add(this.getImageLabel("stench02", 10, 10));
          }
        }

        Map.Entry<Integer,Integer> tempLoc = new AbstractMap.SimpleEntry<>(i,j);

        if(newModel.getVisitedList().get(tempLoc)) {

          if (newModel.getMonsterList().get(tempLoc) != null) {
            panel.add(this.getImageLabel("otyugh", 10, 10));
          }

          if(newModel.getArrowList().get(tempLoc).get(ArrowEnum.CROOKED_ARROW) > 0) {
            panel.add(this.getImageLabel("arrow-white", 10, 10));

          }

          if(newModel.getTreasureList().get(tempLoc).get(TreasureEnum.DIAMOND) > 0) {
            panel.add(this.getImageLabel("diamond", 10, 10));

          }

          if(newModel.getTreasureList().get(tempLoc).get(TreasureEnum.SAPPHIRE) > 0) {
            panel.add(this.getImageLabel("sapphire", 10, 10));

          }

          if(newModel.getTreasureList().get(tempLoc).get(TreasureEnum.RUBY) > 0) {
            panel.add(this.getImageLabel("ruby", 10, 10));

          }



          panel.add(this.getImageLabel(this.setRoomImage(tempLoc,newModel), 500, 1000));


          } else {
            panel.add(this.getImageLabel("blank", 500, 1000));
          }
          panel.revalidate();
        }
      }

    this.mazeMap.getParent().revalidate();
    pack();
//    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//    Rectangle bounds = env.getMaximumWindowBounds();
//    int width = Math.min(getWidth(), bounds.width);
//    int height = Math.min(getHeight(), bounds.height);
//    setSize(new Dimension(width, height));
//    revalidate();
//    repaint();
  }

  /**
   * Generate a new label with selected image.
   *
   * @param type   name of image
   * @param width  width of image
   * @param height height of image
   * @return a new label with image
   */
  private JLabel getImageLabel(String type, int width, int height) {
    JLabel label = new JLabel();
    String location = "dungeon-images/color-cells/%s.png";
    ImageIcon icon = new ImageIcon(String.format(location, type));
    label.setSize(width, height);
    label.setIcon(icon);
    return label;
  }

  /**
   * Return the image name of one room.
   *
   * @param newModel new model
   * @return image name of this room
   */
  private String setRoomImage(Map.Entry<Integer,Integer> tempLoc, ReadOnlyModel newModel) {
    String imageType = "";

    EnumSet<DirectionEnum> directions = newModel.getPossibleMoves(tempLoc);

    if (directions.contains(DirectionEnum.NORTH) && directions.contains(DirectionEnum.SOUTH) &&
            !directions.contains(DirectionEnum.EAST) && !directions.contains(DirectionEnum.WEST)) {
      imageType = "NS";
    } else if (directions.contains(DirectionEnum.NORTH) && directions.contains(DirectionEnum.EAST) &&
            !directions.contains(DirectionEnum.SOUTH) && !directions.contains(DirectionEnum.WEST)) {
      imageType = "NE";
    } else if (directions.contains(DirectionEnum.NORTH) && directions.contains(DirectionEnum.WEST) &&
            !directions.contains(DirectionEnum.EAST) && !directions.contains(DirectionEnum.SOUTH)) {
      imageType = "NW";
    } else if (directions.contains(DirectionEnum.EAST) && directions.contains(DirectionEnum.WEST) &&
            !directions.contains(DirectionEnum.NORTH) && !directions.contains(DirectionEnum.SOUTH)) {
      imageType = "EW";
    } else if (directions.contains(DirectionEnum.SOUTH) && directions.contains(DirectionEnum.EAST) &&
            !directions.contains(DirectionEnum.NORTH) && !directions.contains(DirectionEnum.WEST)) {
      imageType = "SE";
    } else if (directions.contains(DirectionEnum.SOUTH) && directions.contains(DirectionEnum.WEST) &&
            !directions.contains(DirectionEnum.NORTH) && !directions.contains(DirectionEnum.EAST)) {
      imageType = "SW";
    } else if (directions.contains(DirectionEnum.NORTH) && directions.contains(DirectionEnum.EAST) &&
            directions.contains(DirectionEnum.WEST) && !directions.contains(DirectionEnum.SOUTH)) {
      imageType = "NEW";
    } else if (directions.contains(DirectionEnum.NORTH) && directions.contains(DirectionEnum.SOUTH) &&
            directions.contains(DirectionEnum.EAST) && !directions.contains(DirectionEnum.WEST)) {
      imageType = "NSE";
    } else if (directions.contains(DirectionEnum.NORTH) && directions.contains(DirectionEnum.SOUTH) &&
            directions.contains(DirectionEnum.WEST) && !directions.contains(DirectionEnum.EAST)) {
      imageType = "NSW";
    } else if (directions.contains(DirectionEnum.SOUTH) && directions.contains(DirectionEnum.EAST) &&
            directions.contains(DirectionEnum.WEST) && !directions.contains(DirectionEnum.NORTH)) {
      imageType = "SEW";
    } else if (directions.contains(DirectionEnum.SOUTH) && !directions.contains(DirectionEnum.EAST) &&
            !directions.contains(DirectionEnum.WEST) && !directions.contains(DirectionEnum.NORTH)) {
      imageType = "S";
    } else if (!directions.contains(DirectionEnum.SOUTH) && directions.contains(DirectionEnum.EAST) &&
            !directions.contains(DirectionEnum.WEST) && !directions.contains(DirectionEnum.NORTH)) {
      imageType = "E";
    } else if (!directions.contains(DirectionEnum.SOUTH) && !directions.contains(DirectionEnum.EAST) &&
            directions.contains(DirectionEnum.WEST) && !directions.contains(DirectionEnum.NORTH)) {
      imageType = "W";
    } else if (!directions.contains(DirectionEnum.SOUTH) && !directions.contains(DirectionEnum.EAST) &&
            !directions.contains(DirectionEnum.WEST) && directions.contains(DirectionEnum.NORTH)) {
      imageType = "N";
    } else {
      imageType = "NSEW";
    }

    return imageType;
  }

  @Override
  public boolean getMazeType() {
    return menu.getGameSetting().getMazeType();
  }

  @Override
  public GameSetterPanel getGameSetting() {
    return menu.getGameSetting();
  }

  @Override
  public String getDirectionInput() {
    return this.directionInput.getText();
  }

  @Override
  public String getPickUpInput() {
    return this.pickUpInput.getText();
  }

  @Override
  public String getDistanceInput() {
    return this.distanceInput.getText();
  }

  @Override
  public JTextPane getPromptPane() {
    return this.promptPane;
  }

  @Override
  public void setListeners(ActionListener clicks, KeyListener keys) {
    this.addKeyListener(keys);
//    for (KeyListener k : this.getKeyListeners()) {
//      this.addKeyListener(k);
//    }
    this.startButton.addActionListener(clicks);
    this.reStartButton.addActionListener(clicks);
    this.upButton.addActionListener(clicks);
    this.leftButton.addActionListener(clicks);
    this.downButton.addActionListener(clicks);
    this.rightButton.addActionListener(clicks);
    this.shootButton.addActionListener(clicks);
    this.pickUpButton.addActionListener(clicks);
    this.directionInput.addActionListener(clicks);
    this.distanceInput.addActionListener(clicks);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void removeRestartListener(ActionListener clicks) {
    this.reStartButton.removeActionListener(clicks);
  }

  @Override
  public void removeAllListeners(ActionListener clicks, KeyListener keys) {
//    for (KeyListener k : this.getKeyListeners()) {
      this.removeKeyListener(keys);
//    }
    this.startButton.removeActionListener(clicks);
    this.upButton.removeActionListener(clicks);
    this.leftButton.removeActionListener(clicks);
    this.downButton.removeActionListener(clicks);
    this.rightButton.removeActionListener(clicks);
    this.shootButton.removeActionListener(clicks);
    this.pickUpButton.removeActionListener(clicks);
    this.directionInput.removeActionListener(clicks);
    this.distanceInput.removeActionListener(clicks);
  }

  @Override
  public void clearInputString() {
    directionInput.setText("");
    distanceInput.setText("");
  }

  //todo this is setting the player location based on the input in game setting whereas the room row and col is being considered 3 * 3 in next todo. need to change that.
  //@Override
  //public void setChangingMark(Entry<Integer, Integer> playerLoc) {
  //  this.playerLoc = playerLoc;

  //}


}
