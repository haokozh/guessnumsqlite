package school.haokozh.advancedguessnum;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GameAdapter extends BaseAdapter {

  private Cursor cursor;

  public GameAdapter(Cursor cursor) {
    this.cursor = cursor;
  }

  @Override
  public int getCount() {
    return cursor.getCount();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    cursor.moveToPosition(position);
    return cursor.getInt(0);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return null;
  }
}
