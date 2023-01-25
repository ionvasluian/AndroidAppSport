package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.security.MessageDigest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    final Calendar calendar = Calendar.getInstance();

    Button register;
    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText email;
    EditText password;
    EditText phoneNumber;
    EditText birthday;

    String firstNameFieldValue;
    String lastNameFieldValue;
    String userNameFieldValue;
    String emailFieldValue;
    String passwordFieldValue;
    String phoneNumberFieldValue;
    String birthdayFieldValue;

    private static final String url = "http://52.86.7.191:443/createUser";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View contextView = view;

        register    = view.findViewById(R.id.signupbutton);
        firstName   = view.findViewById(R.id.first_name_signup);
        lastName    = view.findViewById(R.id.last_name_signup);
        userName    = view.findViewById(R.id.username_signup);
        email       = view.findViewById(R.id.emailsignup);
        password    = view.findViewById(R.id.passwordsignup);
        phoneNumber = view.findViewById(R.id.callphoneNumber);
        birthday    = view.findViewById(R.id.birthday_date);

        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateDateOnLabel(contextView);
            }
        };

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(
                        getActivity(),
                        R.style.DialogTheme,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstNameFieldValue   = firstName.getText().toString().trim();
                lastNameFieldValue    = lastName.getText().toString().trim();
                userNameFieldValue    = userName.getText().toString().trim();
                emailFieldValue       = email.getText().toString().trim();
                passwordFieldValue    = password.getText().toString().trim();
                phoneNumberFieldValue = phoneNumber.getText().toString().trim();
                birthdayFieldValue    = birthday.getText().toString().trim();

                if (firstNameFieldValue.isEmpty()   ||
                    lastNameFieldValue.isEmpty()    ||
                    userNameFieldValue.isEmpty()    ||
                    emailFieldValue.isEmpty()       ||
                    passwordFieldValue.isEmpty()    ||
                    phoneNumberFieldValue.isEmpty() ||
                    birthdayFieldValue.isEmpty())
                {
                    Toast.makeText(
                            view.getContext(),
                            "Complete all fields",
                            Toast.LENGTH_LONG
                    ).show();
                }else if(!isEmailValid(emailFieldValue)){
                    Toast.makeText(
                            view.getContext(),
                            "It should be a valid email",
                            Toast.LENGTH_LONG
                    ).show();
                }
                else
                {
                    // hashing password
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                        messageDigest.update(passwordFieldValue.getBytes());
                        passwordFieldValue = new String(messageDigest.digest());
                        char[] hashedPassword = passwordFieldValue.toCharArray();
                        Log.e("Debug", hashedPassword.toString());

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
                                    SharedPreferences sharedPreferences = getActivity()
                                            .getSharedPreferences(
                                                    MainActivity.PREFS_NAME,
                                                    0
                                            );
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isUserLoggedIn", true);
                                    try {
                                        editor.putString("userID", responseJson.getString("user_id"));
                                        editor.putString("userName", userNameFieldValue);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    editor.apply();
                                    Intent intent = new Intent(getActivity(), ViewEventActivity.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(
                                            view.getContext(),
                                            error.toString(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Log.e("Debugging", passwordFieldValue);
                            Log.e("all", firstNameFieldValue+","+lastNameFieldValue+","+userNameFieldValue+","+passwordFieldValue+","+emailFieldValue+","+phoneNumberFieldValue+","+birthdayFieldValue);
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("userFirstName", firstNameFieldValue);
                            params.put("userLastName", lastNameFieldValue);
                            params.put("userName", userNameFieldValue);
                            params.put("userPassword", passwordFieldValue);
                            params.put("userEmail", emailFieldValue);
                            params.put("userPhoneNumber", phoneNumberFieldValue);
                            params.put("userBirthday", birthdayFieldValue);
                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }

            }
        });



    }

    private void updateDateOnLabel(View view) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        birthday = view.findViewById(R.id.birthday_date);
        birthday.setText(String.format("%s", dateFormat.format(calendar.getTime())));
        birthdayFieldValue = birthday.getText().toString().trim();

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}