package school.haokozh.advancedguessnum;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {

  private Button buttonQuery;
  private Button buttonDelete;
  private Button buttonBack;

  private RecyclerView output;
  private EditText input;

  private Cursor cursor;
  private List<User> userList = new ArrayList<>();
  private ArrayAdapter<User> adapter;

  private static SQLiteDatabase db;
  private static final String DATABASE_NAME = "user_database";
  private static final String TABLE_NAME = "user_table";
  private static final String ID = "user_id";
  private static final String NAME = "user_name";
  private static final String PLAY_TIME = "play_time";
  private static final String GUESS_COUNT = "guess_count";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rank);

    buttonBack = findViewById(R.id.button_back);
    buttonQuery = findViewById(R.id.button_query);
    buttonDelete = findViewById(R.id.button_delete);
    input = findViewById(R.id.id_input);

    output = findViewById(R.id.recyclerview);


    setOnClickListener();
  }

  private void setOnClickListener() {
    buttonBack.setOnClickListener(v -> {
      startActivity(
          new Intent(
              RankActivity.this,
              MainActivity.class
          ));
    });

    buttonQuery.setOnClickListener(v -> {
      if (TextUtils.isEmpty(input.getText())) {
        String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        cursor = db.rawQuery(SELECT_ALL, null);
        cursor.moveToFirst();

        final int idIndex = cursor.getColumnIndex(ID);
        final int nameIndex = cursor.getColumnIndex(NAME);
        final int playTimeIndex = cursor.getColumnIndex(PLAY_TIME);
        final int guessCountIndex = cursor.getColumnIndex(GUESS_COUNT);


        while (!cursor.isAfterLast()) {
          final long id = cursor.getLong(idIndex);
          final String name = cursor.getString(nameIndex);
          final long playTime = cursor.getLong(playTimeIndex);
          final int guessCount = cursor.getInt(guessCountIndex);

          userList.add(new User(id, name, playTime, guessCount));
        }

        cursor.close();
        db.close();
      } else {

      }
    });

    buttonDelete.setOnClickListener(v -> {
      int id = Integer.parseInt(input.getText().toString());
      String DELETE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE user_id = " + id;

      db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
      db.execSQL(DELETE_BY_ID);
      db.close();
    });
  }
}
