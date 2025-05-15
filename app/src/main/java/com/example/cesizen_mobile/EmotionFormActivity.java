package com.example.cesizen_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cesizen_mobile.api.ApiClient;
import com.example.cesizen_mobile.api.ApiService;
import com.example.cesizen_mobile.models.ApiResponse;
import com.example.cesizen_mobile.models.EmotionTrackerRequest;
import com.example.cesizen_mobile.models.SecondaryEmotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmotionFormActivity extends AppCompatActivity {

    private EditText inputTitle, inputDescription;
    private LinearLayout emotionListLayout;
    private Button submitButton;

    private final List<Integer> selectedEmotionIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_form);

        inputTitle = findViewById(R.id.input_title);
        inputDescription = findViewById(R.id.input_description);
        emotionListLayout = findViewById(R.id.emotion_list);
        submitButton = findViewById(R.id.btn_submit);

        SharedPreferences prefs = getSharedPreferences("CesizenPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", "");

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getGroupedEmotions(token).enqueue(new Callback<Map<String, List<SecondaryEmotion>>>() {
            @Override
            public void onResponse(Call<Map<String, List<SecondaryEmotion>>> call, Response<Map<String, List<SecondaryEmotion>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, List<SecondaryEmotion>> grouped = response.body();

                    for (Map.Entry<String, List<SecondaryEmotion>> entry : grouped.entrySet()) {
                        String primary = entry.getKey();
                        List<SecondaryEmotion> emotions = entry.getValue();

                        TextView groupLabel = new TextView(EmotionFormActivity.this);
                        groupLabel.setText(primary);
                        groupLabel.setTextSize(18f);
                        groupLabel.setTextColor(getResources().getColor(android.R.color.holo_purple));
                        groupLabel.setPadding(0, 20, 0, 10);
                        emotionListLayout.addView(groupLabel);

                        for (SecondaryEmotion emotion : emotions) {
                            CheckBox cb = new CheckBox(EmotionFormActivity.this);
                            cb.setText(emotion.getName());
                            cb.setTextSize(16f);
                            cb.setTextColor(getResources().getColor(android.R.color.black));

                            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                if (isChecked) {
                                    selectedEmotionIds.add(emotion.getId());
                                } else {
                                    selectedEmotionIds.remove((Integer) emotion.getId());
                                }
                            });

                            emotionListLayout.addView(cb);
                        }
                    }
                } else {
                    Toast.makeText(EmotionFormActivity.this, "Erreur de chargement des émotions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, List<SecondaryEmotion>>> call, Throwable t) {
                Toast.makeText(EmotionFormActivity.this, "Échec réseau", Toast.LENGTH_SHORT).show();
            }
        });

        submitButton.setOnClickListener(v -> {
            EmotionTrackerRequest body = new EmotionTrackerRequest(
                    inputTitle.getText().toString().trim(),
                    inputDescription.getText().toString().trim(),
                    selectedEmotionIds
            );

            api.addEmotionTracker(body, token).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Émotion enregistrée", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EmotionFormActivity.this, EmotionTrackerListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Erreur d'envoi", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erreur réseau", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
