package kr.puze.imagediary.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kr.puze.imagediary.Data.DiaryData;

public class PrefUtil {
    String TAG = "LOGTAG/PREFUTIL";
    Context context;
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static final String DIARY_DATA = "diary_data";
    private static final String TEMP_DATA = "temp_data";

    public PrefUtil(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("ImageDiary", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setDiaryPref(DiaryData diary) {
        ArrayList<DiaryData> diaries = getDiaryPref();
        diaries.add(diary);
        Gson gson = new Gson();
        String json = gson.toJson(diaries);

        editor.putString(DIARY_DATA, json);
        editor.commit();
    }

    public ArrayList<DiaryData> getDiaryPref(){
        ArrayList<DiaryData> arrayItems = new ArrayList<>();
        String serializedObject = preferences.getString(DIARY_DATA, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<DiaryData>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

    public void removeDiaryPref(int position){
        ArrayList<DiaryData> diaries = getDiaryPref();
        diaries.remove(position);
        Gson gson = new Gson();
        String json = gson.toJson(diaries);

        editor.putString(DIARY_DATA, json);
        editor.commit();
    }

    public void setTempPref(DiaryData diary) {
        Gson gson = new Gson();
        String json = gson.toJson(diary);
        Log.d(TAG, json);
        editor.putString(TEMP_DATA, json);
        editor.commit();
    }

    public DiaryData getTempPref() {
        DiaryData diary = new DiaryData();
        String serializedObject = preferences.getString(TEMP_DATA, null);
        if (serializedObject != null) {
            Log.d(TAG, serializedObject);
            Gson gson = new Gson();
            Type type = new TypeToken<DiaryData>(){}.getType();
            diary = gson.fromJson(serializedObject, type);
        }
        return diary;
    }

    public void removeTempPref(){
        Gson gson = new Gson();
        String json = gson.toJson(new DiaryData());
        Log.d(TAG, json);

        editor.putString(TEMP_DATA, json);
        editor.commit();
    }

    private void resetDiaryPref(){
        editor.clear();
    }
}