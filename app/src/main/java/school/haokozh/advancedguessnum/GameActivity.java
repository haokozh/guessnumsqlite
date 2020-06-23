package school.haokozh.advancedguessnum;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

  private int userId;
  private int countA;
  private long startTime;
  private int guessCount;

  private EditText output;
  private EditText input;
  private Button enter;
  private Intent intent = this.getIntent();

  private Random random = new Random();
  private Set<Integer> set = new HashSet<>();
  private List<Integer> randList = new ArrayList<>();
  private StringBuilder builder = new StringBuilder();

  private static SQLiteDatabase db;
  private static final String DATABASE_NAME = "user_database";
  private static final String TABLE_NAME = "user_table";
  private static final String ID = "user_id";
  private static final String NAME = "user_name";
  private static final String PLAY_TIME = "play_time";
  private static final String GUESS_COUNT = "guess_count";
  private static final String CREATE_TABLE =
      "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
          ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
          NAME + " TEXT, " +
          PLAY_TIME + " TEXT, " +
          GUESS_COUNT + " TEXT);";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
    setContentView(R.layout.activity_main);

    TextView userNameTextView = findViewById(R.id.user_name);
    input = findViewById(R.id.inputField);
    enter = findViewById(R.id.enterButton);
    output = findViewById(R.id.outputField);

    output.setMovementMethod(new ScrollingMovementMethod());
    output.setVerticalScrollBarEnabled(true);

    setOnClickListener();
    getRandomNumber();
    String userNameResult = userNameTextView.getText() + intent.getStringExtra("user_name");
    userNameTextView.setText(userNameResult);

    startTime = System.currentTimeMillis();
  }

  private void setOnClickListener() {
    enter.setOnClickListener(this::printResult);
  }

  private void getRandomNumber() {
    while (randList.size() < 4) {
      int randNum = random.nextInt(10);
      if (set.add(randNum)) randList.add(randNum);
    }
  }

  private String guess(String num) {
    countA = 0;
    int countB = 0;

    boolean[] check = new boolean[4];
    int[] guess = new int[4];

    for (int i = 0; i < 4; i++) {
      guess[i] = Character.getNumericValue(num.charAt(i));
    }

    for (int i = 0; i < 4; i++) {
      if (randList.get(i) == guess[i]) {
        check[i] = true;
        countA++;
      }
    }

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (randList.get(i) == guess[j] && !check[j]) {
          countB++;
        }
      }
    }

    return countA + "A" + countB + "B";
  }

  private String printRandom() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < randList.size(); i++) {
      result.append(randList.get(i));
    }

    return result.toString();
  }

  private void printResult(View v) {
    String userInput = input.getText().toString();
    if (userInput.length() != 4) {
      output.setText(builder.append("Please enter 4 number.\n").toString());
    } else {
      builder.append(userInput)
          .append(" = > ")
          .append(guess(userInput))
          .append("\t")
          .append("Ans : ")
          .append(printRandom())
          .append("\n");

      guessCount++;

      if (countA == 4) {
        long endTime = System.currentTimeMillis();
        builder.append("DONE.\n")
            .append("Time : ")
            .append((endTime - startTime) / 1000)
            .append(" Second.\n");

        String userName = intent.getStringExtra("user_name");
        long playTime = (endTime - startTime) / 1000;
        createTable();
        insert(userName, playTime, guessCount);
      }
      output.setText(builder.toString());
    }

    input.setText("");
  }

  public void createTable() {
    db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    db.execSQL(CREATE_TABLE);
    db.close();
  }

  public void insert(String name, long playTime, int guessCount) {
    String INSERT = "INSERT INTO " + TABLE_NAME + "( " +
        NAME + ", " + PLAY_TIME + ", " + GUESS_COUNT + ") " +
        "VALUES ('" +name + "', '" + playTime + "', '" + guessCount + "')";

    db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    db.execSQL(INSERT);
    db.close();
  }

}
