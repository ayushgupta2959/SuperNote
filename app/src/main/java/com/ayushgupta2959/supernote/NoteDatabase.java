package com.ayushgupta2959.supernote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.ByteArrayOutputStream;

@Database(entities = {Note.class}, version=1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    private static Context myApplicationContext;
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        myApplicationContext = context;
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids){
            BitmapDrawable drawable = (BitmapDrawable) myApplicationContext.getResources().getDrawable(R.drawable.b);
            Bitmap image = drawable.getBitmap();
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byte[] byteArray = bStream.toByteArray();
            noteDao.insert(new Note("Title 1","Desc 1",1,byteArray));
            noteDao.insert(new Note("Title 2","Desc 2",2,byteArray));
            noteDao.insert(new Note("Title 3","Desc 3",3,null));
            noteDao.insert(new Note("Title 4","Desc 4",4,byteArray));
            noteDao.insert(new Note("Title 5","Desc 5",5,byteArray));
            noteDao.insert(new Note("Title 6","Desc 6",5,null));
            return null;
        }
    }
}
