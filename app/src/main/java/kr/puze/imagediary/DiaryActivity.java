package kr.puze.imagediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import kr.puze.imagediary.Util.PrefUtil;

public class DiaryActivity extends AppCompatActivity {

    PrefUtil prefUtil;
    ImageView buttonBack;
    ImageView buttonAdd;
    TextView textActionbar;
    TextView textDate;
    TextView textTitle;
    TextView textContent;
    ImageView imageDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        init();
    }

    private void init(){
        prefUtil = new PrefUtil(this);
        buttonBack = findViewById(R.id.button_back_diary);
        buttonAdd = findViewById(R.id.button_add_diary);
        textActionbar = findViewById(R.id.text_actionbar_diary);
        textDate = findViewById(R.id.text_date_diary);
        textTitle = findViewById(R.id.text_title_diary);
        textContent = findViewById(R.id.text_content_diary);
        imageDiary = findViewById(R.id.image_diary);

        buttonBack.setOnClickListener(view ->{ finish(); });
        buttonAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddDiaryActivity.class);
            startActivity(intent);
            finish();
        });

        getData();
    }

    private void getData(){
        Intent diaryIntent = getIntent();
        textActionbar.setText(diaryIntent.getStringExtra("title"));
        textDate.setText(diaryIntent.getStringExtra("date"));
        textTitle.setText(diaryIntent.getStringExtra("title"));
        textContent.setText(diaryIntent.getStringExtra("content"));
    }
}