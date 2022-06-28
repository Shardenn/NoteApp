package com.example.noteapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.noteapp.api.RetrofitClient;
import com.example.noteapp.models.Note;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserWorkspace extends AppCompatActivity implements View.OnClickListener {

    private String userId;
    ArrayList<Note> userNotes = new ArrayList<Note>();

    private ArrayList<Note> tempList = new ArrayList<Note>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_wsLogout) :
                goToLoginScreen();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_workspace);

        //SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        //userId = prefs.getInt(getString(R.string.last_user_id), 0);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getString(getString(R.string.last_user_id));
        }

        findViewById(R.id.btn_wsLogout).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (userId == null) {
            try {
                throw new Exception("Could not retrieve a valid user ID from preferences");
            } catch (Exception e) {
                e.printStackTrace();
                goToLoginScreen();
                return;
            }
        }

        getNotesById(userId);
    }


    private ArrayList<Note> getNotesById(String id) {
        Call<ArrayList<Note>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getNotesById(id);

        ArrayList<Note> list = new ArrayList<>();
        call.enqueue(new Callback<ArrayList<Note>>() {
            @Override
            public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                try {
                    tempList.addAll(response.body());
                    Log.v("getNotes", "LOGS " + tempList.size());
                    for (int i = 0; i < tempList.size(); i++) {
                        String notebookID = tempList.get(i).getNotebookID();
                        String noteTitle = tempList.get(i).getNoteTitle();
                        String noteText = tempList.get(i).getNoteContent();
                        list.add(new Note(noteText, noteTitle, notebookID));
                    }
                    userNotes = list;
                    Log.v("getNotes", "Resulting list " + list);

                    addNotesToView();

                } catch (Exception e) {
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Note>> call, Throwable t) {
                Log.d("getNotes", t.getMessage());
                Toast.makeText(UserWorkspace.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return list;
    }

    private void addNotesToView() {
        for (Note note : userNotes) {
            note_item_fragment note_item = note_item_fragment.newInstance("test",note);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.frag_notesHolder, note_item)
                    .commit();
        }
    }

    private void goToLoginScreen() { startActivity(new Intent(this, MainActivity.class)); }
}
