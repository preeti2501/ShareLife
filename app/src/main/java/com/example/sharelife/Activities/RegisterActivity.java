package com.example.sharelife.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sharelife.R;
import com.example.sharelife.Utils.Endpoints;
import com.example.sharelife.Utils.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText bloodGroupEt, nameEt, stateEt, cityEt, mobileEt, passwordEt;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bloodGroupEt = findViewById(R.id.blood_group);
        nameEt = findViewById(R.id.name);
        stateEt = findViewById(R.id.state);
        cityEt = findViewById(R.id.city);
        mobileEt = findViewById(R.id.number);
        passwordEt = findViewById(R.id.password);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bloodGroup, name, state, city, mobile, password;
                bloodGroup = bloodGroupEt.getText().toString();
                name = nameEt.getText().toString();
                state = stateEt.getText().toString();
                city = cityEt.getText().toString();
                mobile = mobileEt.getText().toString();
                password = passwordEt.getText().toString();
                if (isValid(bloodGroup, name, state, city, mobile, password)) {
                    register(bloodGroup, name, state, city, mobile, password);
                }
            }

        });

    }
    private  void register(String blood_group, String name, String state, String city, String mobile, String password){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Endpoints.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Great Success!")){
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("city", city).apply();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params=new HashMap<>();
                params.put("blood_group", blood_group);
                params.put("name", name);
                params.put("state", state);
                params.put("city", city);
                params.put("number", mobile);
                params.put("password", password);
                return params;
            }

        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private boolean isValid(String blood_group, String name, String state, String city, String mobile, String password)
    {
        List<String> valid_blood_group=new ArrayList<>();
        valid_blood_group.add("A+");
        valid_blood_group.add("A-");
        valid_blood_group.add("B+");
        valid_blood_group.add("B-");
        valid_blood_group.add("AB+");
        valid_blood_group.add("AB-");
        valid_blood_group.add("O+");
        valid_blood_group.add("O-");
        if(name.isEmpty()){
            showMessage("Name is required");
            return false;
        }
        else if(city.isEmpty())
        {
            showMessage("City is required");
            return false;
        }
        else if(!valid_blood_group.contains(blood_group)){
            showMessage("Blood group invalid choose from"+valid_blood_group);
            return  false;
        }
        else if(mobile.length() != 10||mobile.isEmpty())
        {
            showMessage("Invalid mobile number");
            return false;
        }



        return true;
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}