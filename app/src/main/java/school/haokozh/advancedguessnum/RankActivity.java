package school.haokozh.advancedguessnum;

import android.database.Cursor;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import school.haokozh.advancedguessnum.R;
import school.haokozh.advancedguessnum.GameDatabase;

public class RankActivity extends AppCompatActivity {

  private Button buttonQuery;
  private Button buttonDelete;
  private Button buttonBack;

  private ListView output;

  private GameDatabase db;
  private Cursor cursor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rank);

    buttonBack = findViewById(R.id.button_back);
    buttonQuery = findViewById(R.id.button_query);
    buttonDelete = findViewById(R.id.button_delete);
    output = findViewById(R.id.outputField);

    db.open();
    cursor = db.selectAll();
    updateListView(cursor);
    output.setSelector(android.R.drawable.alert_light_frame);
  }

  private void updateListView(Cursor cursor) {
    GameAdapter adapter = new GameAdapter(cursor);
    output.setAdapter(adapter);
  }
}
