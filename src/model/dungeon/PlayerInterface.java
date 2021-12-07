package model.dungeon;

import java.util.Map;


/**
 * This interface represents the methods that can be implemented to build Player.
 *
 */

public interface PlayerInterface {


  /**
   * Gets the current location of the player.
   * @return the current location of the player in the maze
   * */
  Map.Entry<Integer, Integer> getCurrentLocation();

  /**
   * Gets the current Diamond count of the player.
   * @return the current diamond count of the player.
   * */
  double getDiamondCount();


  /**
   * Gets the current Sapphire count of the player.
   * @return the current Sapphire count of the player.
   * */
  double getSapphireCount();

  /**
   * Gets the current ruby count of the player.
   *
   * @return the current ruby count of the player.
   * */
  double getRubyCount();


  /**
   * sets the current location of the player.
   *
   * @param location the current location.
   * */
  void setCurrentLocation(Map.Entry<Integer, Integer> location);

  /**
   * Increases treasure count depending on treasure type.
   *
   * @param treasure treasure in the current location.
   * */
  void increaseTreasure(String treasure);

  /**
   * Increases arrow count .
   *
   * @param arrow no of arrow in that he decides to pick.
   * */
  void increaseArrow(String arrow);

  /**
   * Gets the no of arrow with player .
   *
   * @return no of arrow wth player.
   * */
  int getArrowCount();

  /**
   * Decreases arrow count after shoot .
   *
   *
   * */
  void decreaseArrowCount();

  /**
   * gets playerstatus, whether alive, dead or winner .
   *
   * @return player status
   * */

  PlayerStatus getPlayerStatus();



  /**
   * sets playerstatus, whether alive, dead or winner .
   *
   * @param health new status of player
   * */
  void setPlayerStatus(PlayerStatus health);


  /**
   * Prints player properties.
   *
   * @return String of player properties
   * */
  StringBuilder printPlayerDescription();


}