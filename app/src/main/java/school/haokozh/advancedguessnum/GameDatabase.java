package school.haokozh.advancedguessnum;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class GameDatabase {

  private SQLiteDatabase db = null;

  private static final String TABLE_NAME = "user_record";
  private static final String ID = "id";
  private static final String NAME = "user_name";
  private static final String PLAY_TIME = "play_time";
  private static final String GUESS_COUNT = "guess_count";

  private static final String CREATE_TABLE =
      "CREATE TABLE " + TABLE_NAME + " (" +
          ID + " INTEGER PRIMARY KEY," +
          NAME + " TEXT," +
          PLAY_TIME + " INTEGER," +
          GUESS_COUNT + " INTEGER )";

  private Context context;

  public GameDatabase(Context context) {
    this.context = context;
  }

  public void open() throws SQLException {
    try {
      db = context.openOrCreateDatabase(
          "user_database",
          Context.MODE_PRIVATE,
          null);

      db.execSQL(CREATE_TABLE);
    } catch (Exception e) {
      Toast.makeText(
          context,
          "user_database has been created",
          Toast.LENGTH_LONG).show();
    }
  }

  public void append(String name, long playtime, int guessCount) {
    String insertText = "INSERT INTO " + TABLE_NAME + "( " +
        NAME + "," +
        PLAY_TIME + "," +
        GUESS_COUNT + " )" +
        "values ('" + name + "'," + playtime + "," + guessCount + ")";

    db.execSQL(insertText);
  }

  public void delete(long id) {
    String deleteText =
        "DELETE FROM " + TABLE_NAME +
            " WHERE " + id + " = " + id;

    db.execSQL(deleteText);
  }

  public Cursor selectAll() {
    String selectText = "SELECT * FROM " + TABLE_NAME;
    return db.rawQuery(selectText, null);
  }

  public Cursor selectById(long id) {
    String selectText = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
    return db.rawQuery(selectText, null);
  }
}
