package com.example.noteapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String noteContent;
    private String noteTitle;
    private String notebookID;

    public Note(String noteContent, String noteTitle, String notebookID) {
        this.noteContent = noteContent;
        this.noteTitle = noteTitle;
        this.notebookID = notebookID;
    }

    public Note(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        notebookID = data[0];
        noteTitle = data[1];
        noteContent = data[3];
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNotebookID() {
        return notebookID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] data = {notebookID, noteTitle, noteContent};
        dest.writeStringArray(data);
    }

    public static final Parcelable.Creator<Note> CREATOR
            = new Parcelable.Creator<Note>() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
