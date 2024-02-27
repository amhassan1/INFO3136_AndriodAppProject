package com.example.abdulazizrayanproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private final String NAME_PREFS = "name";

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;

    private Fragment bookFragment;
    private Fragment movieFragment;
    private Fragment tvShowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        {
            bookFragment = BookFragment.newInstance();
            movieFragment = MovieFragment.newInstance();
            tvShowFragment = TvShowFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, bookFragment);
            transaction.commit();
        }

        Button bookBtn = findViewById(R.id.book_button);
        Button movieBtn = findViewById(R.id.movie_button);
        Button tvShowBtn = findViewById(R.id.tv_button);
        Button logoutBtn = findViewById(R.id.logout_button);

        ListView entriesList = findViewById(R.id.entries_list);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrayList);

        entriesList.setAdapter(arrayAdapter);

        bookBtn.setOnClickListener(this::swapBookFragment);
        movieBtn.setOnClickListener(this::swapMovieFragment);
        tvShowBtn.setOnClickListener(this::swapTVFragment);

        logoutBtn.setOnClickListener(view -> {
            SharedPreferences namePreference = getSharedPreferences(NAME_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = namePreference.edit();
            editor.remove("name");
            editor.apply();
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveFile();
    }

    @Override
    protected void onStart() {
        super.onStart();
        readFile();
    }

    private void readFile() {
        arrayList.clear();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new InputStreamReader(openFileInput("entries.txt")));
            String line = reader.readLine();

            while (line != null) {
                addEntry(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        try {
            FileOutputStream outputStream = openFileOutput("entries.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            for(int i = 0; i < arrayList.size(); i++) {
                outputStreamWriter.write(arrayList.get(i)+ "\n");
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void swapBookFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, bookFragment);
        transaction.commit();
    }

    public void swapMovieFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, movieFragment);
        transaction.commit();
    }

    public void swapTVFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, tvShowFragment);
        transaction.commit();
    }

    public void addEntry(String data) {
        arrayList.add(data);
        arrayAdapter.notifyDataSetChanged();
    }
}