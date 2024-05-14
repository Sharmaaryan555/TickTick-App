package com.example.ticktick;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "my_notes")
public class Note {
    private String title;
    private String disc;

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public Note(String title, String disc) {
        this.title = title;
        this.disc = disc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }
}
