package com.ayushgupta2959.supernote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.ayushgupta2959.supernote.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.ayushgupta2959.supernote.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.ayushgupta2959.supernote.EXTRA_PRIORITY";
    public static final String EXTRA_IMAGE =
            "com.ayushgupta2959.supernote.EXTRA_IMAGE";

    private EditText editTextTitle,editTextDescription;
    private NumberPicker numberPickerPriority;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextDescription = findViewById(R.id.edit_text_description);
        editTextTitle = findViewById(R.id.edit_text_title);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        imageView = findViewById(R.id.imageView);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher_background);
        setTitle("Add Note");
    }
    private void saveNote(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        byte[] byteArray;
        if(imageView.getDrawable()==null){
            byteArray = null;
        }

        else{
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap image = drawable.getBitmap();
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byteArray = bStream.toByteArray();
        }

        if(title.trim().isEmpty()||description.trim().isEmpty()){
            Toast.makeText(this, "Please insert and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        data.putExtra(EXTRA_IMAGE,byteArray);
        setResult(RESULT_OK,data);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

