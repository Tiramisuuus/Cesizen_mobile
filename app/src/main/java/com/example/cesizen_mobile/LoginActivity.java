package com.example.cesizen_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cesizen_mobile.api.ApiClient;
import com.example.cesizen_mobile.api.ApiService;
import com.example.cesizen_mobile.models.AuthRequest;
import com.example.cesizen_mobile.models.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            AuthRequest request = new AuthRequest(email, password);

            ApiService api = ApiClient.getClient().create(ApiService.class);
            api.login(request).enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> res) {
                    if (res.isSuccessful() && res.body() != null) {
                        String token = res.body().getToken();

                        // 🔐 Enregistrer le token localement
                        getSharedPreferences("CesizenPrefs", MODE_PRIVATE)
                                .edit()
                                .putString("token", token)
                                .apply();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); //  évite de revenir en arrière sur l'écran de login

                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur d'identifiants", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Log.e("API_ERROR", "Échec connexion", t);
                    Toast.makeText(LoginActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
                }

            });
        });
    }
}
