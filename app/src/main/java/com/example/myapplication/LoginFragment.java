package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends Fragment {
    String emailFieldValue;
    String passwordFieldValue;

    String url = "http://52.86.7.191:443/authenticateUser";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText email = (EditText) view.findViewById(R.id.email);
        EditText password = (EditText) view.findViewById(R.id.password);

        Button signInButton = (Button) view.findViewById(R.id.signinbutton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailFieldValue    = email.getText().toString().trim();
                passwordFieldValue = password.getText().toString().trim();

                if (emailFieldValue.isEmpty() || passwordFieldValue.isEmpty()) {
                    Toast.makeText(
                            view.getContext(),
                            "Complete both fields",
                            Toast.LENGTH_LONG
                    ).show();
                }else if(!isEmailValid(emailFieldValue)) {
                    Toast.makeText(
                            view.getContext(),
                            "Please insert a valid email",
                            Toast.LENGTH_LONG
                    ).show();
                }else
                 {
                    // hashing password
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                        messageDigest.update(passwordFieldValue.getBytes());
                        passwordFieldValue = new String(messageDigest.digest());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    RequestQueue queue = Volley.newRequestQueue(view.getContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject responseJsonObject = null;
                                    JSONObject responseJson = null;
                                    try {
                                        responseJsonObject = new JSONObject(response);
                                        responseJson = responseJsonObject.getJSONObject("response");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (responseJson.getString("auth").equals("success")) {
                                            SharedPreferences sharedPreferences = getActivity()
                                                    .getSharedPreferences(
                                                            MainActivity.PREFS_NAME,
                                                            0
                                                    );
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("isUserLoggedIn", true);
                                            editor.putString("userID", responseJson.getString("user_id"));
                                            editor.apply();

                                            Toast.makeText(
                                                    view.getContext(),
                                                    "You logged in",
                                                    Toast.LENGTH_LONG
                                            ).show();
                                            Intent intent = new Intent(getActivity(), ViewEventActivity.class);
                                            startActivity(intent);
                                        }
                                        else if (responseJson.getString("auth").equals("unsuccess")) {
                                            Toast.makeText(
                                                    view.getContext(),
                                                    "Wrong email or password",
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("userEmail", emailFieldValue);
                            params.put("userPassword", passwordFieldValue);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });

        return view;
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}