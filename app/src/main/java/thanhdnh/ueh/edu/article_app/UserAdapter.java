package thanhdnh.ueh.edu.article_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
  private final ArrayList<User> users; private final Context context;
  public UserAdapter(ArrayList<User> users, Context context) { this.users = users; this.context = context; }
  public int getCount() { return users.size(); }
  public Object getItem(int position) { return users.get(position); }
  public long getItemId(int position) { return users.get(position).getId(); }
  public View getView(int position, View convertView, ViewGroup parent) {
    MyView item;
    if (convertView == null) {
      item = new MyView();
      convertView = LayoutInflater.from(context).inflate(R.layout.user_disp_tpl, parent, false);
      item.photo = convertView.findViewById(R.id.imv_photo);
      item.name = convertView.findViewById(R.id.tv_title);
      convertView.setTag(item);
    } else item = (MyView) convertView.getTag();
    User user = users.get(position);
    Picasso.get().load(user.getUrlProfile()).resize(300, 400).centerCrop().into(item.photo);
    item.name.setText(user.getUname());
    return convertView;
  }
  private static class MyView { ImageView photo; TextView name; }
}
