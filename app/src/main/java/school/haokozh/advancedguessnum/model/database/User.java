package school.haokozh.advancedguessnum.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  private int id;

  @NonNull
  @ColumnInfo(name = "user_name")
  private String userName;

  @ColumnInfo(name = "guess_count")
  private int guessCount;

  @ColumnInfo(name = "play_time")
  private long playTime;


  public User(@NonNull String userName) {
    this.userName = userName;
  }

  public int getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public int getGuessCount() {
    return guessCount;
  }

  public long getPlayTime() {
    return playTime;
  }
}
