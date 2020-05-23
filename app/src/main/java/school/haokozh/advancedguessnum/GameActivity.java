package school.haokozh.advancedguessnum;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

  private int countA;
  private long startTime;

  EditText output;
  EditText input;
  Button enter;

  Random random = new Random();
  Set<Integer> set = new HashSet<>();
  List<Integer> randList = new ArrayList<>();

  StringBuilder builder = new StringBuilder();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
    setContentView(R.layout.activity_main);

    output = findViewById(R.id.outputField);
    input = findViewById(R.id.inputField);
    enter = findViewById(R.id.enterButton);

    output.setMovementMethod(new ScrollingMovementMethod());
    output.setVerticalScrollBarEnabled(true);

    setOnClickListener();
    getRandomNumber();
    startTime = System.currentTimeMillis();
  }

  private void setOnClickListener() {
    enter.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        printResult(v);
      }
    });
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

  public void printResult(View v) {
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

      if (countA == 4) {
        long endTime = System.currentTimeMillis();
        builder.append("DONE.\n")
            .append("Time : ")
            .append((endTime - startTime) / 1000)
            .append(" Second.\n");
      }
      output.setText(builder.toString());
    }

    input.setText("");
  }
}
