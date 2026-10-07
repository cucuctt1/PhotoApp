package thanhdnh.ueh.edu.article_app;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ViewUserActivity extends AppCompatActivity {
  ImageView iv_detail;
  TextView tv_detail_title, tv_detail_description;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_article);
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }

    iv_detail = findViewById(R.id.iv_detail);
    tv_detail_title = findViewById(R.id.tv_detail_title);
    tv_detail_description = findViewById(R.id.tv_detail_description);

    int id = (int) getIntent().getLongExtra("id", 0);

    User user = UserData.getUserFromId(id);
    if (user != null) {
      Picasso.get().load(user.getUrlProfile()).resize(400, 500).centerCrop().into(iv_detail);
      tv_detail_title.setText(user.getUname());
      tv_detail_description.setText(user.getShortBio());
    }
  }
}
