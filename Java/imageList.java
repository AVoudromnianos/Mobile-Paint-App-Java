package com.example.test1;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class imageList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        try {
            String path = Environment.getExternalStoragePublicDirectory("PaintIt").getAbsolutePath();
            File file = new File(path);
            File[] list = file.listFiles();
            String[] images = new String[list.length];
            for (int i = 0; i < list.length; i++) {
                images[i] = list[i].getName();
                System.out.println(list[i].getName());
            }


            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_layout, images);
            ListView listView = (ListView) findViewById(R.id.imageList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setNum(position + 1);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No images found!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, gameMenu.class);
            startActivity(intent);
        }
    }

    public void setNum(int num) {
        Intent intent = new Intent(this, DrawImage.class);
        intent.putExtra("LOAD_IMAGE", num);
        startActivity(intent);
    }
}
