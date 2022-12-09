package com.example.sharedpreferenceshomework;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "key";
    private SharedPreferences sharedPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences("My Preferences", MODE_PRIVATE);

        final ArrayList<UserNote> userNotes = new ArrayList<>();
        final NotesAdapter notesAdapter = new NotesAdapter(userNotes);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(notesAdapter);

        String savedNotes = sharedPref.getString(KEY, null);
        if (savedNotes == null || savedNotes.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Type type = new TypeToken<ArrayList<UserNote>>() {
                }.getType();
                ArrayList<UserNote> fromJson = new GsonBuilder().create().fromJson(savedNotes,type);
               notesAdapter.setNewData(fromJson);
               userNotes.addAll(fromJson); // Home work task
            } catch (JsonSyntaxException e) {
                Toast.makeText(this, "Ошибка трансформации", Toast.LENGTH_SHORT).show();
            }
        }

        findViewById(R.id.fab).setOnClickListener(view -> {
            int c = userNotes.size(); // Home work task
            userNotes.add(new UserNote("New note " + (c + 1), new Date(),// Home work task
                    "Description " + (c + 1)));
           notesAdapter.setNewData(userNotes);
            String jsonNotes = new GsonBuilder().create().toJson(userNotes);
            sharedPref.edit().putString(KEY, jsonNotes).apply();
        });
    }
}
