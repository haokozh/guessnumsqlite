package school.haokozh.advancedguessnum.model.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class UserRepository {

  private UserDao userDao;
  private LiveData<List<User>> allRankByName;
  private LiveData<List<User>> allRankByPlayTime;
  private LiveData<List<User>> allRankByGuessCount;

  UserRepository(Application application) {
    UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
    userDao = db.userDao();
    allRankByName = userDao.getAllUsers();
    allRankByPlayTime = userDao.getRankByPlayTime();
    allRankByGuessCount = userDao.getRankByGuessCount();
  }

  LiveData<List<User>> getAllRankByName() {
    return allRankByName;
  }

  LiveData<List<User>> getRankByName(String userName) {
    return userDao.getRankByName(userName);
  }

  LiveData<List<User>> getAllRankByPlayTime() {
    return allRankByPlayTime;
  }

  LiveData<List<User>> getAllRankByGuessCount() {
    return allRankByGuessCount;
  }

  void insert(User user) {
    UserRoomDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
  }

  void deleteAll() {
    UserRoomDatabase.databaseWriteExecutor.execute(() -> userDao.deleteAll());
  }

  void deleteByPlayTime(long playTime) {
    UserRoomDatabase.databaseWriteExecutor.execute(() -> userDao.deleteByPlayTime(playTime));
  }

  void  deleteByGuessCount(int guessCount) {
    UserRoomDatabase.databaseWriteExecutor.execute(() -> userDao.deleteByGuessCount(guessCount));
  }
}
