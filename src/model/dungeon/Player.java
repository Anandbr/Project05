package model.dungeon;

import java.util.Map;

/**
 * This class represents a player.
 *
 */

public class Player implements PlayerInterface {

  private Map.Entry<Integer, Integer> currentLocation;
  private double diamondCount;
  private double sapphireCount;
  private double rubyCount;
  private int arrowCount;
  private PlayerStatus playerHealth;
  private final String name;

  /**
   * Constructor for Player.
   *
   * @param name : name of Player.
   *
   */
  public Player(String name) {
    this.name = name;
    this.diamondCount = 0.0;
    this.sapphireCount = 0.0;
    this.rubyCount = 0.0;
    this.arrowCount = 3;
    this.playerHealth = PlayerStatus.ALIVE;

  }

  /**
   * Sets the current location of the player.
   * */
  @Override
  public void setCurrentLocation(Map.Entry<Integer, Integer> location) {
    if (location.getKey() < 0 || location.getValue() < 0) {
      throw new IllegalArgumentException("location can't be less than 0");
    }
    currentLocation = location;
  }

  /**
   * Gets the current location of the player.
   * @return the current location of the player in the maze
   * */
  @Override
  public Map.Entry<Integer, Integer> getCurrentLocation() {
    return currentLocation;
  }

  /**
   * Gets the current Diamond count of the player.
   * @return the current diamond count of the player.
   * */
  @Override
  public double getDiamondCount() {
    return diamondCount;
  }

  @Override
  public void increaseTreasure(String treasure) {
    switch (treasure) {
      case "DIAMOND":
        this.diamondCount += DungeonInterface.DAIMONDCOUNTPERROOM;
        break;
      case "RUBY":
        this.rubyCount += DungeonInterface.RUBYCOUNTPERROOM;
        break;
      case "SAPPHIRE":
        this.sapphireCount += DungeonInterface.SAPPHIRECOUNTPERROOM;
        break;
      default:
        break;
    }
  }

  @Override
  public void increaseArrow(String arrow) {

    this.arrowCount += 1;

  }

  /**
   * Decrease arrow count of player by 1.
   * */
  public void decreaseArrowCount() throws IllegalStateException {
    if (arrowCount == 0) {
      throw new IllegalStateException("Arrows already zero.");
    }
    this.arrowCount -= 1 ;
  }


  /**
   * Gets the current Sapphire count of the player.
   * @return the current Sapphire count of the player.
   * */
  @Override
  public double getSapphireCount() {
    return sapphireCount;
  }

  /**
   * Gets the current ruby count of the player.
   * @return the current ruby count of the player.
   * */
  @Override
  public double getRubyCount() {
    return rubyCount;
  }

  @Override
  public int getArrowCount() {
    return this.arrowCount;
  }

  @Override
  public PlayerStatus getPlayerStatus() {
    return this.playerHealth;
  }

  @Override
  public void setPlayerStatus(PlayerStatus health) {
    this.playerHealth = health;
  }


  @Override
  public StringBuilder printPlayerDescription() {
    StringBuilder playerDesc = new StringBuilder();
    playerDesc.append("Player: ");
    playerDesc.append(name);
    playerDesc.append(" with treasure, Diamond: ");
    playerDesc.append(diamondCount);
    playerDesc.append(" Ruby: ");
    playerDesc.append(rubyCount);
    playerDesc.append(" Sapphire: ");
    playerDesc.append(sapphireCount);
    playerDesc.append(" Arrows: ");
    playerDesc.append(arrowCount);
    playerDesc.append("; is at Location ");
    playerDesc.append(getCurrentLocation().getKey());
    playerDesc.append(",");
    playerDesc.append(getCurrentLocation().getValue());
    return playerDesc;

  }

  @Override
  public String toString() {
    return "Player: " + name
            + " with treasure, Diamond: "
            + diamondCount + " Ruby: "
            + rubyCount + " Sapphire: "
            + sapphireCount + ". At Location "
            + getCurrentLocation().getKey() + "."
            + getCurrentLocation().getValue();
  }





}