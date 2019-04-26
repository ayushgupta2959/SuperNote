package com.ayushgupta2959.supernote;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note){
        repository.insert(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }
    public void update(Note note){
        repository.update(note);
    }
    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }
    public  LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

}
