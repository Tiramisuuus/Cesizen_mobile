package com.example.cesizen_mobile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cesizen_mobile.api.ApiClient;
import com.example.cesizen_mobile.api.ApiService;
import com.example.cesizen_mobile.models.Resource;
import com.example.cesizen_mobile.models.RessourceAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourcesActivity extends AppCompatActivity {

    private ListView listView;
    private RessourceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ressources);

        listView = findViewById(R.id.resourceListView);

        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.getResources().enqueue(new Callback<List<Resource>>() {
            @Override
            public void onResponse(Call<List<Resource>> call, Response<List<Resource>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new RessourceAdapter(ResourcesActivity.this, response.body());
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(ResourcesActivity.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Resource>> call, Throwable t) {
                Log.e("API", "Erreur: ", t);
                Toast.makeText(ResourcesActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
