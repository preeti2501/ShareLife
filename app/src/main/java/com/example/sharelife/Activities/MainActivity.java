package com.example.sharelife.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sharelife.Adapters.RequestAdapter;
import com.example.sharelife.DataModels.RequestDataModel;
import com.example.sharelife.R;
import com.example.sharelife.Utils.Endpoints;
import com.example.sharelife.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RequestDataModel> requestDataModels;
    private RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView make_request_button=findViewById(R.id.make_request_button);
        make_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MakeRequestActivity.class));
            }
        });
        requestDataModels=new ArrayList<>();
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.search_button)
                {
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                }
                return false;
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        requestAdapter=new RequestAdapter(requestDataModels, this);
        recyclerView.setAdapter(requestAdapter);
        populateHomePage();
        TextView pick_location=findViewById(R.id.pick_location);
        String location=PreferenceManager.getDefaultSharedPreferences(this).getString("city", "no_city_found");
        if(!location.equals("no_city_found")){
            pick_location.setText(location);
        }

    }
    private void populateHomePage(){
        final String city=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("city", "no_city");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Endpoints.get_request_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<RequestDataModel>>(){}.getType();
                List<RequestDataModel> dataModels=gson.fromJson(response, type);
                requestDataModels.addAll(dataModels);
                requestAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("city", city);
                return params;
            }

        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}