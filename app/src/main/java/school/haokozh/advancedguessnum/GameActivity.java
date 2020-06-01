package school.haokozh.advancedguessnum;

import android.content.Intent;
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
    setContentView(R.layout.activity_main);

    TextView userNameTextView = findViewById(R.id.user_name);
    output = findViewById(R.id.outputField);
    input = findViewById(R.id.inputField);
    enter = findViewById(R.id.enterButton);

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

  private void insertUserRecord(String userName, long playTime, int guessCount) {
    GameDatabase db = new GameDatabase(GameActivity.this);
    db.open();
    db.append(userName, playTime, guessCount);
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

        insertUserRecord(userName, playTime, guessCount);
      }
      output.setText(builder.toString());
    }

    input.setText("");
  }
}
