package com.example.project;

import static com.example.project.URL.BASE_URL;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Login extends AppCompatActivity{

    TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp , errorText;
    ProgressBar progressBar;
    String name, email, password, apiKey;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alart_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button button = dialog.findViewById(R.id.btn_retry);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
            dialog.show();
        }else {

            textInputEditTextEmail = findViewById(R.id.email);
            textInputEditTextPassword = findViewById(R.id.password);
            buttonLogin = findViewById(R.id.buttonLogin);
            textViewSignUp = findViewById(R.id.signupText);
            progressBar = findViewById(R.id.progress);
            errorText = findViewById(R.id.error);
            sharedPreferences = getSharedPreferences("Login_User", MODE_PRIVATE);

            if(sharedPreferences.getString("logged", "false").equals("true")){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            textViewSignUp.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SignUP.class);
                    startActivity(intent);
                    finish();
                }
            });
            buttonLogin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    errorText.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    email = String.valueOf(textInputEditTextEmail.getText());
                    password = String.valueOf(textInputEditTextPassword.getText());
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = BASE_URL+"new_login.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String status = jsonObject.getString("status");
                                        String message = jsonObject.getString("message");
                                        if (status.equals("success")){
                                            name = jsonObject.getString("name");
                                            email = jsonObject.getString("email");
                                            apiKey = jsonObject.getString("apiKey");
                                            System.out.println("apiKey");
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("logged", "true");
                                            editor.putString("name", name);
                                            editor.putString("email", email);
                                            editor.putString("apiKey", apiKey);
                                            editor.apply();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                                        } else {
                                            errorText.setText(message);
                                            errorText.setVisibility(View.VISIBLE);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            errorText.setText(error.getLocalizedMessage());
                            errorText.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();

                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("email", email);
                            paramV.put("password", password);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
            });

        }

    }
}
