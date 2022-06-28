package com.example.noteapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.noteapp.models.Note;

public class note_item_fragment extends Fragment {

    private TextView mNoteTitle, mNoteContents, mNotebook;

    private static final String USER_NAME = "userName";
    private static final String NOTE = "note";

    // TODO: Rename and change types of parameters
    private String mUserName;
    private Note mNote;

    public note_item_fragment() {
        // Required empty public constructor
    }

    public static note_item_fragment newInstance(String userName, Note note) {
        note_item_fragment fragment = new note_item_fragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, userName);
        args.putParcelable(NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    // Goes 2nd
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(USER_NAME);
            mNote = getArguments().getParcelable(NOTE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();

        mNoteTitle = activity.findViewById(R.id.txt_noteTitle);
        mNoteContents = activity.findViewById(R.id.txt_noteContents);
        mNotebook = activity.findViewById(R.id.txt_notebook);

        mNoteTitle.setText(mNote.getNoteTitle());
        mNoteContents.setText(mNote.getNoteContent());
        mNotebook.setText(mNote.getNotebookID());
    }

    // Goes 3rd
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_item, container, false);
    }

}