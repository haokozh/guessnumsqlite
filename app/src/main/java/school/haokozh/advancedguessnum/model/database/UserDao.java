package school.haokozh.advancedguessnum.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insert(User user);

  @Query("DELETE FROM user_table")
  void deleteAll();

  @Query("DELETE FROM user_table WHERE play_time LIKE :playTime")
  void deleteByPlayTime(long playTime);

  @Query("DELETE FROM user_table WHERE guess_count LIKE :guessCount")
  void deleteByGuessCount(int guessCount);

  @Query("SELECT * FROM user_table ORDER BY user_name ASC")
  LiveData<List<User>> getAllUsers();

  @Query("SELECT * FROM user_table WHERE user_name LIKE :userName")
  LiveData<List<User>> getRankByName(String userName);

  @Query("SELECT * FROM user_table ORDER BY play_time ASC")
  LiveData<List<User>> getRankByPlayTime();

  @Query("SELECT * FROM user_table ORDER BY guess_count ASC ")
  LiveData<List<User>> getRankByGuessCount();
}
