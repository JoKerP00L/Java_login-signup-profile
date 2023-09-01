package com.example.project;

import static com.example.project.URL.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView name, email, fetchresult;
    SharedPreferences sharedPreferences;
    Button buttonLogout, buttonFetchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        fetchresult = findViewById(R.id.fetch_result);
        buttonLogout = findViewById(R.id.logout);
        buttonFetchUser = findViewById(R.id.fetch_data);

        sharedPreferences = getSharedPreferences("Login_User", MODE_PRIVATE);
        if (sharedPreferences.getString("logged", "false").equals("false")) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        name.setText(sharedPreferences.getString("name", ""));
        email.setText(sharedPreferences.getString("email", ""));


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//                String url = "http://192.168.0.108/PHP_auth_api_key/logout.php";
                String url = BASE_URL+"logout.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("logged", "");
                                    editor.putString("name", "");
                                    editor.putString("email", "");
                                    editor.putString("apiKey", "");
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                }else
                                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", sharedPreferences.getString("email", ""));
                        paramV.put("apiKey", sharedPreferences.getString("apiKey", ""));
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

        buttonFetchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//                String url = "http://192.168.0.108/PHP_auth_api_key/profile.php";
                String url = BASE_URL+"profile.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                fetchresult.setText(response);
                                fetchresult.setVisibility(View.VISIBLE);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", sharedPreferences.getString("email", ""));
                        paramV.put("apiKey", sharedPreferences.getString("apiKey", ""));
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}