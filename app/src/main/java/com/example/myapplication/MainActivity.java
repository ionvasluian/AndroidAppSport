package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login, register;
        login = findViewById(R.id.signin);
        register = findViewById(R.id.register);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        register.setBackgroundResource(R.drawable.not_pressed_color);
        register.setTextColor(Color.argb(74,190,190,190));
        if (savedInstanceState == null) {
            transaction.replace(R.id.container1, LoginFragment.class,null).commitNow();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register.setBackgroundResource(R.drawable.not_pressed_color);
                register.setTextColor(Color.argb(74,190,190,190));
                login.setBackgroundResource(R.drawable.button_rectangle);
                login.setTextColor(Color.parseColor("#585DDB"));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container1, LoginFragment.class,null)
                        .commit();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register.setBackgroundResource(R.drawable.button_rectangle);
                register.setTextColor(Color.parseColor("#585DDB"));
                login.setBackgroundResource(R.drawable.not_pressed_color);
                login.setTextColor(Color.argb(74,190,190,190));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container1, RegisterFragment.class,null)
                        .commit();
            }
        });
    }
}