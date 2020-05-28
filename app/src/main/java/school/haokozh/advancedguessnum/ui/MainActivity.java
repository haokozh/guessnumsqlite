package school.haokozh.advancedguessnum.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import school.haokozh.advancedguessnum.R;

public class MainActivity extends AppCompatActivity {

  private EditText userName;
  private Button buttonStart;
  private Button buttonExit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    userName = findViewById(R.id.user_name);
    buttonStart = findViewById(R.id.button_game_start);
    buttonExit = findViewById(R.id.button_exit);

    setOnclickListener();
  }

  private void setOnclickListener() {
    buttonStart.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        moveToMainActivity();
      }
    });

    buttonExit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        exitApp();
      }
    });
  }

  private void moveToMainActivity() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtra("userName", userName.getText().toString());
    startActivity(intent);
  }

  private void exitApp() {
    // Alert Dialog for exit the app
    AlertDialog.Builder alertDialogBuilder = new Builder(this);

    // set the title of the Alert Dialog
    alertDialogBuilder.setTitle("Exit");

    // set dialog message
    alertDialogBuilder
        .setMessage("Exit?")
        .setCancelable(false)
        .setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                // what to do if Yes is tapped
                finishAffinity();
                System.exit(0);
              }
            })
        .setNeutralButton("Cancel",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                // code to do on Cancel tapped
                dialog.cancel();
              }
            })
        .setNegativeButton("No",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                // code to do on No tapped
                dialog.cancel();
              }
            });

    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
  }
}
