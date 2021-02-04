package kr.puze.imagediary;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import kr.puze.imagediary.Adapter.DiaryRecyclerAdapter;
import kr.puze.imagediary.CustomView.DividerItemDecoration;
import kr.puze.imagediary.Data.DiaryData;
import kr.puze.imagediary.Util.PrefUtil;

public class MainActivity extends AppCompatActivity implements DiaryRecyclerAdapter.OnListItemSelectedInterface, DiaryRecyclerAdapter.OnListItemLongSelectedInterface {

    private enum Page{
        DIARY, ADD;
    }

    private String TAG = "LOGTAG/MAINACTIVITY";
    private PrefUtil prefUtil;
    private DiaryRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonMain;
    private ArrayList<DiaryData> diaries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        prefUtil = new PrefUtil(this);
        recyclerView = findViewById(R.id.recycler_main);
        buttonMain = findViewById(R.id.button_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DiaryRecyclerAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.line_divider));
        buttonMain.setOnClickListener(view -> { goToPage(Page.ADD, null); });
    }

    private void getData(){
        Log.d(TAG, "getData");
        adapter.resetItem();
        diaries = prefUtil.getDiaryPref();
        for(int i = 0; i < diaries.size(); i++) adapter.addItem(diaries.get(i));
        adapter.notifyDataSetChanged();
    }

    private void goToPage(Page page, DiaryData diary){
        Intent intent;
        switch (page){
            case DIARY:
                intent = new Intent(getApplicationContext(), DiaryActivity.class)
                        .putExtra("date", diary.getDate())
                        .putExtra("image", diary.getImage())
                        .putExtra("title", diary.getTitle())
                        .putExtra("content", diary.getContent());
                break;
            case ADD:
                intent = new Intent(getApplicationContext(), AddDiaryActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + page);
        }
        startActivity(intent);
    }

    @Override
    public void onItemLongSelected(View v, int position) {
        prefUtil.removeDiaryPref(position);
        Toast.makeText(getApplicationContext(), "Remove Diary", Toast.LENGTH_SHORT).show();
        getData();
    }

    @Override
    public void onItemSelected(View v, int position) {
        goToPage(Page.DIARY, diaries.get(position));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}