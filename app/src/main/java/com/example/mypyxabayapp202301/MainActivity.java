package com.example.mypyxabayapp202301;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /** VARS GLOBALES **/
    private RecyclerView recyclerView;
    private AdapterRecycler adapterRecycler;
    private ArrayList<ModelItem> itemArrayList;
    private RequestQueue requestQueue;
    private String search;

    public void init(){
        // Lien design Java
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
    }

    private void parseJSON(){
        search = "moto";
        String pixaKey = "24175925-f2016e765d25a20f1cb0a6989";

        // https://pixabay.com/api/?key=24175925-f2016e765d25a20f1cb0a6989&q=yellow+flowers&image_type=photo&pretty=true
        String urlJSONFile = "https://pixabay.com/api/"
                + "?key=" + pixaKey
                + "&q=" + search
                + "&image_type=photo"
                + "&orientation=horizontal"
                + "&pretty=true";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlJSONFile, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // On récupère le tableau de données JSON à partir de notre objet JsonObjectRequest
                            // dans un try and catch ajouter en auto en corrigeant l'erreur
                            JSONArray jsonArray = response.getJSONArray("hits");

                            // On récupère dans un premier temps toutes les données présentent dans le Array avec une boucle for
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                // Puis on sélectionne celles dn on à besoin soit user - likes - webformatURL
                                String creator = hit.getString("user");
                                int likes = hit.getInt("likes");
                                String imageUrl = hit.getString("webformatURL");

                                // On ajoute les données à notre tableau en utilisant son model
                                itemArrayList.add(new ModelItem(imageUrl, creator, likes));
                            }
                            //
                            adapterRecycler = new AdapterRecycler(MainActivity.this, itemArrayList); // Noter MainActivity.this car nous sommes dans une classe interne

                            // Puis on lie l'adpter au Recycler
                            recyclerView.setAdapter(adapterRecycler);

                            /** #10.3 On peut alors ajouter le listener **/
//                            adapterRecycler.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        /** #9.1 On rempli la request avec les données récupérées **/
        requestQueue.add(request);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        parseJSON();
    }
}