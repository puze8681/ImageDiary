package kr.puze.imagediary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kr.puze.imagediary.Data.DiaryData;
import kr.puze.imagediary.Util.PrefUtil;

@SuppressLint("SimpleDateFormat")
public class AddDiaryActivity extends AppCompatActivity {

    private enum Button {
        TEMP, SAVE;
    }

    String TAG = "LOGTAG/ADDDIARYACTIVITY";
    PrefUtil prefUtil;
    ImageView buttonBack;
    TextView textTitle;
    TextView buttonTemp;
    TextView buttonSave;
    EditText editTitle;
    EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        init();
    }

    private void init(){
        prefUtil = new PrefUtil(this);
        buttonBack = findViewById(R.id.button_back_add_diary);
        textTitle = findViewById(R.id.text_title_add_diary);
        buttonTemp = findViewById(R.id.button_temp_add_diary);
        buttonSave = findViewById(R.id.button_save_add_diary);
        editTitle = findViewById(R.id.edit_title_add_diary);
        editContent = findViewById(R.id.edit_content_add_diary);

        buttonBack.setOnClickListener(view -> {
            finish();
        });

        buttonTemp.setOnClickListener(view -> {
            button(Button.TEMP, "image123");
        });

        buttonSave.setOnClickListener(view -> {
            button(Button.SAVE, "image123");
        });

        getTemp();
    }

    private void getTemp(){
        DiaryData diary = prefUtil.getTempPref();
        Log.d(TAG, "getTemp ");
        Log.d(TAG, diary.getTitle());
        Log.d(TAG, diary.getContent());
        if(!diary.getTitle().equals("") && !diary.getContent().equals("")){
            editTitle.setText(diary.getTitle());
            editContent.setText(diary.getContent());
            Toast.makeText(getApplicationContext(), "Load Saved Diary", Toast.LENGTH_SHORT).show();
        }
    }

    private void button(Button button, String image){
        String titleTrim = editTitle.getText().toString().trim();
        String contentTrim = editContent.getText().toString().trim();

        if(!titleTrim.equals("") && !contentTrim.equals("")){
            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTimeInMillis());
            switch (button){
                case TEMP:
                    prefUtil.setTempPref(new DiaryData(date, image, titleTrim, contentTrim));
                    Toast.makeText(getApplicationContext(), "Temporary Save Diary", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case SAVE:
                    prefUtil.setDiaryPref(new DiaryData(date, image, titleTrim, contentTrim));
                    prefUtil.removeTempPref();
                    Toast.makeText(getApplicationContext(), "Save Diary", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
        else Toast.makeText(getApplicationContext(), "Please Enter All value", Toast.LENGTH_SHORT).show();
    }
}