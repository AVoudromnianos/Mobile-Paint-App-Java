package com.example.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class gameMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
    }

    public void newImage(View view)
    {
        Intent intent = new Intent(this, DrawImage.class);
        startActivity(intent);
    }

    public void loadImage(View view)
    {
        Intent intent = new Intent(this, imageList.class);
        startActivity(intent);
    }
}
