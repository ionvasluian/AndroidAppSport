package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends Fragment {
    String url = "http://52.86.7.191:443/authenticateUser";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Context context = getActivity().getApplicationContext();

        EditText email = (EditText) view.findViewById(R.id.email);
        EditText password = (EditText) view.findViewById(R.id.password);

        Button signInButton = (Button) view.findViewById(R.id.signinbutton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailFieldValue = email.getText().toString().trim();
                String passwordFieldValue = password.getText().toString().trim();

                if (emailFieldValue.isEmpty() || passwordFieldValue.isEmpty()) {
                    Toast.makeText(
                            context,
                            "Complete both fields",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("true")) {
                                        Toast.makeText(
                                                context,
                                                "You logged in",
                                                Toast.LENGTH_LONG
                                        ).show();
                                        Intent intent = new Intent(context, ViewEventActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(
                                                context,
                                                "Wrong email or password",
                                                Toast.LENGTH_LONG
                                        ).show();
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
}