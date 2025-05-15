package com.example.cesizen_mobile;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cesizen_mobile.api.ApiClient;
import com.example.cesizen_mobile.api.ApiService;
import com.example.cesizen_mobile.models.GroupedTrackerResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmotionTrackerListActivity extends AppCompatActivity {

    private LinearLayout trackerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_tracker_list);

        trackerContainer = findViewById(R.id.tracker_container);

        SharedPreferences prefs = getSharedPreferences("CesizenPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", "");

        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.getGroupedEmotionTrackers(token).enqueue(new Callback<List<GroupedTrackerResponse>>() {
            @Override
            public void onResponse(Call<List<GroupedTrackerResponse>> call, Response<List<GroupedTrackerResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (GroupedTrackerResponse tracker : response.body()) {
                        TextView title = new TextView(EmotionTrackerListActivity.this);
                        title.setText("📘 " + tracker.getName() + " – " + tracker.getCreatedAt());
                        title.setTextSize(18f);
                        title.setPadding(0, 20, 0, 10);
                        trackerContainer.addView(title);

                        TextView desc = new TextView(EmotionTrackerListActivity.this);
                        desc.setText(tracker.getDescription());
                        desc.setTextColor(getColor(android.R.color.darker_gray));
                        desc.setPadding(0, 0, 0, 10);
                        trackerContainer.addView(desc);

                        for (Map.Entry<String, List<String>> entry : tracker.getGroupedEmotions().entrySet()) {
                            TextView cat = new TextView(EmotionTrackerListActivity.this);
                            cat.setText("• " + entry.getKey());
                            cat.setTypeface(null, Typeface.BOLD);
                            cat.setPadding(0, 10, 0, 5);
                            trackerContainer.addView(cat);


                            for (String emotion : entry.getValue()) {
                                TextView e = new TextView(EmotionTrackerListActivity.this);
                                e.setText("   ↳ " + emotion);
                                trackerContainer.addView(e);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GroupedTrackerResponse>> call, Throwable t) {
                Toast.makeText(EmotionTrackerListActivity.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
