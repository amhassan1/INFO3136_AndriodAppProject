package com.example.abdulazizrayanproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String NAME_PREFS = "name";
    private EditText nameField;
    private TextView welcomeBackView;

    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.login_button);
        nameField = findViewById(R.id.name_edit_text);
        welcomeBackView = findViewById(R.id.welcome_back_view);

        loginBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, HomeActivity.class);
            user_name = nameField.getText().toString();
            if(user_name.isEmpty()) {
                Toast.makeText(this, R.string.name_required, Toast.LENGTH_SHORT).show();
            }else {
                SharedPreferences name = getSharedPreferences(NAME_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = name.edit();
                editor.putString("name", nameField.getText().toString());
                editor.apply();
                startActivity(intent);
                nameField.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences namePreference = getSharedPreferences(NAME_PREFS, MODE_PRIVATE);
        user_name = namePreference.getString("name", "");

        if(user_name.isEmpty()) {
            nameField.setVisibility(View.VISIBLE);
            welcomeBackView.setVisibility(View.GONE);
        }else {
            nameField.setText(user_name);
            nameField.setVisibility(View.GONE);
            welcomeBackView.setVisibility(View.VISIBLE);
            welcomeBackView.setText(getString(R.string.welcome_back, user_name));
        }
    }


}