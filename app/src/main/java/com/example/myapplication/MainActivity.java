package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private static final String TAG = "Works";
    private boolean showOneTapUI = true;

    public static String PREFS_NAME = "isLoggedIn";

    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        boolean isUserLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false);
        String userID = sharedPreferences.getString("userID", "-1");

        if (isUserLoggedIn) {
            Intent intent = new Intent(MainActivity.this, ViewEventActivity.class);
            startActivity(intent);
        } else {

            login = findViewById(R.id.signin);
            register = findViewById(R.id.register);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setReorderingAllowed(true);

            register.setBackgroundResource(R.drawable.not_pressed_color);
            register.setTextColor(Color.argb(74, 190, 190, 190));
            if (savedInstanceState == null) {
                transaction.replace(R.id.container1, LoginFragment.class, null).commitNow();
            }

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    register.setBackgroundResource(R.drawable.not_pressed_color);
                    register.setTextColor(Color.argb(74, 190, 190, 190));
                    login.setBackgroundResource(R.drawable.button_rectangle);
                    login.setTextColor(Color.parseColor("#585DDB"));
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container1, LoginFragment.class, null)
                            .commit();
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    register.setBackgroundResource(R.drawable.button_rectangle);
                    register.setTextColor(Color.parseColor("#585DDB"));
                    login.setBackgroundResource(R.drawable.not_pressed_color);
                    login.setTextColor(Color.argb(74, 190, 190, 190));
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container1, RegisterFragment.class, null)
                            .commit();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            Intent intent = new Intent(MainActivity.this, ViewEventActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
