package com.example.shivam_pc.googlebookssearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class BooksActivity extends AppCompatActivity {


    String tosearch = "maze runner";
    private  String google_api_url = "https://www.googleapis.com/books/v1/volumes?q={"+tosearch+"}";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
    }


    public void search(View view) {
        EditText searchbox= (EditText)findViewById(R.id.searchbox);
        tosearch=searchbox.getText().toString();
        google_api_url = "https://www.googleapis.com/books/v1/volumes?q={"+tosearch+"}";
        Intent intent = new Intent(BooksActivity.this,
                BooksActivity2.class);
       intent.putExtra("URL", google_api_url);
        startActivity(intent);


    }

}



