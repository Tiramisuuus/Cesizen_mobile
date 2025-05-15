package com.example.cesizen_mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cesizen_mobile.api.ApiClient;
import com.example.cesizen_mobile.api.ApiService;
import com.example.cesizen_mobile.models.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextView textName, textEmail;
    String token; // À récupérer depuis SharedPreferences ou un système maison

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);

        SharedPreferences prefs = getSharedPreferences("CesizenPrefs", MODE_PRIVATE);
        token = prefs.getString("token", null);


        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getProfile(token).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfile user = response.body();
                    textName.setText(user.getUsername());
                    textEmail.setText(user.getEmail());
                } else {
                    Toast.makeText(ProfileActivity.this, "Erreur lors de la récupération du profil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
