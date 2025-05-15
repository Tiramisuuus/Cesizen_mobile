package com.example.cesizen_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences("CesizenPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        isConnected = token != null && !token.isEmpty();

        // Initialisation des blocs principaux
        LinearLayout cardConnexion = findViewById(R.id.card_connexion);
        LinearLayout cardInscription = findViewById(R.id.card_inscription);
        LinearLayout cardRessources = findViewById(R.id.card_ressources);
        LinearLayout cardProfil = findViewById(R.id.card_profil);
        LinearLayout cardLogout = findViewById(R.id.card_deconnexion);
        Button btnTracker = findViewById(R.id.btn_tracker);

        // Visibilité selon l'état de connexion
        cardConnexion.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        cardInscription.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        cardProfil.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        cardLogout.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        btnTracker.setVisibility(isConnected ? View.VISIBLE : View.GONE);

        // Redirections vers les écrans
        cardConnexion.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        cardInscription.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        cardRessources.setOnClickListener(v -> startActivity(new Intent(this, ResourcesActivity.class)));
        cardProfil.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        btnTracker.setOnClickListener(v -> startActivity(new Intent(this, EmotionFormActivity.class)));

        // Déconnexion
        cardLogout.setOnClickListener(v -> {
            prefs.edit().remove("token").apply();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        // Navigation Footer
        setupFooterNavigation();
    }

    private void setupFooterNavigation() {
        View navHome = findViewById(R.id.nav_home);
        View navTracker = findViewById(R.id.nav_tracker);
        View navProfile = findViewById(R.id.nav_profile);
        View navRessources = findViewById(R.id.nav_ressources);

        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                // Tu es déjà sur la page Home
            });
        }

        if (navTracker != null) {
            navTracker.setOnClickListener(v -> {
                if (isConnected) {
                    startActivity(new Intent(this, EmotionFormActivity.class));
                    finish();
                }
            });
        }

        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                if (isConnected) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    finish();
                }
            });
        }

        if (navRessources != null) {
            navRessources.setOnClickListener(v -> {
                startActivity(new Intent(this, ResourcesActivity.class));
                finish();
            });
        }
    }
}
