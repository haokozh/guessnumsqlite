package school.haokozh.advancedguessnum;

public class User {

  long id;
  String name;
  long playTime;
  int guessCount;

  public User(long id, String name, long playTime, int guessCount) {
    this.id = id;
    this.name = name;
    this.playTime = playTime;
    this.guessCount = guessCount;
  }
}
