package com.ayushgupta2959.supernote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.ayushgupta2959.supernote.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.ayushgupta2959.supernote.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.ayushgupta2959.supernote.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.ayushgupta2959.supernote.EXTRA_PRIORITY";
    public static final String EXTRA_IMAGE =
            "com.ayushgupta2959.supernote.EXTRA_IMAGE";
    public static final int DELETE_NOTE = 3;

    final static int PICK_IMAGE =2;

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
        numberPickerPriority.setMaxValue(5);

        FloatingActionButton save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        FloatingActionButton addImage = findViewById(R.id.add_new_image);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher_background);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            imageView.setBackgroundResource(R.drawable.image_background);
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
            byte[] byteArray = intent.getByteArrayExtra(EXTRA_IMAGE);
            if(byteArray!=null) {
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageView.setImageBitmap(image);
            }
        }
        else{
            setTitle("Add Note");
        }
    }
    private void saveNote(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        byte[] byteArray;
        if(imageView.getDrawable()==null){
            BitmapDrawable drawable = (BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.b);
            Bitmap image = drawable.getBitmap();
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byteArray = bStream.toByteArray();
        }

        else{
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap image = drawable.getBitmap();
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byteArray = bStream.toByteArray();
        }

        if(title.trim().isEmpty() && description.trim().isEmpty()){
            Toast.makeText(this, "Please fill Title and Description", Toast.LENGTH_SHORT).show();
            return;
        }

        if(title.trim().isEmpty()&& !description.trim().isEmpty()){
            Toast.makeText(this, "Please fill the Title", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!title.trim().isEmpty() && description.trim().isEmpty()){
            Toast.makeText(this, "Please fill the Description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            data.putExtra(EXTRA_ID,id);
        }
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
            case R.id.delete_note:
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                int priority = numberPickerPriority.getValue();
                byte[] byteArray;
                if(imageView.getDrawable()==null){
                    BitmapDrawable drawable = (BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.b);
                    Bitmap image = drawable.getBitmap();
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byteArray = bStream.toByteArray();
                }

                else{
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap image = drawable.getBitmap();
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byteArray = bStream.toByteArray();
                }
                Intent data = new Intent();

                int id = getIntent().getIntExtra(EXTRA_ID,-1);
                if(id!=-1){
                    data.putExtra(EXTRA_ID,id);
                }
                data.putExtra(EXTRA_TITLE,title);
                data.putExtra(EXTRA_DESCRIPTION,description);
                data.putExtra(EXTRA_PRIORITY,priority);
                data.putExtra(EXTRA_IMAGE,byteArray);
                setResult(DELETE_NOTE,data);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageView.setBackgroundResource(R.drawable.image_background);
            Uri selectedImageUri = data.getData();
            Picasso.get().load(selectedImageUri).fit().centerInside().into(imageView);

        }

    }
}

