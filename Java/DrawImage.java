package com.example.test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DrawImage extends AppCompatActivity
    {

        private PaintView paintView;
        private Bitmap loadedBitmap = null;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_image);

            Intent intent = getIntent();
            if(intent.getIntExtra("LOAD_IMAGE",-1) != -1) {

                int loadNum = intent.getIntExtra("LOAD_IMAGE", -1);
                String path = Environment.getExternalStoragePublicDirectory("PaintIt").getAbsolutePath();
                String fileName = "img" + loadNum + ".png";
                System.out.println("\nLoading bitmap file from: "+path+File.separator+fileName);
                loadedBitmap = BitmapFactory.decodeFile(path+File.separator+fileName);
                loadedBitmap = loadedBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }


        paintView = findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics, loadedBitmap);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mode_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.normal:
                paintView.normal();
                return true;
            case R.id.emboss:
                paintView.emboss();
                return true;
            case R.id.blur:
                paintView.blur();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
            case R.id.brushColor:
                showPickColorDialog();
                return true;
            case R.id.brushSize:
                showBrushSizeDialog();
                return true;
            case R.id.Save:
                showSaveImageDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

        private void showSaveImageDialog() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save Image");

            View customLayout = getLayoutInflater().inflate(R.layout.save_image_dialog, null);
            builder.setView(customLayout);

            Button saveSd = customLayout.findViewById(R.id.sbutton);
            Button save = customLayout.findViewById(R.id.sbutton_ph);

            final AlertDialog dialog = builder.create();

            saveSd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveBitmapSD();
                    dialog.dismiss();
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveBitmap();
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        public void showPickColorDialog() {
        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Brush Color");

            View colorPickerLayout = getLayoutInflater().inflate(R.layout.pick_color_dialog, null);
            builder.setView(colorPickerLayout);

            Button red = colorPickerLayout.findViewById(R.id.btnRed);
            Button green = colorPickerLayout.findViewById(R.id.btnGrn);
            Button blue = colorPickerLayout.findViewById(R.id.btnBlu);
            Button yellow = colorPickerLayout.findViewById(R.id.btnYlw);
            Button black = colorPickerLayout.findViewById(R.id.btnBlk);
            Button white = colorPickerLayout.findViewById(R.id.btnWht);

// create and show the alert dialog
        final AlertDialog dialog = builder.create();

            red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paintView.currentColor = Color.RED;
                    dialog.dismiss();
                }
            });

            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paintView.currentColor = Color.GREEN;
                    dialog.dismiss();
                }
            });

            blue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paintView.currentColor = Color.BLUE;
                    dialog.dismiss();
                }
            });

            yellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paintView.currentColor = Color.YELLOW;
                    dialog.dismiss();
                }
            });

            black.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paintView.currentColor = Color.BLACK;
                    dialog.dismiss();
                }
            });

            white.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paintView.currentColor = Color.WHITE;
                    dialog.dismiss();
                }
            });

        dialog.show();
    }

    //BRUSH SIZE
    public void showBrushSizeDialog() {
        // create an alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Brush Size");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.brush_size_dialog, null);
        builder.setView(customLayout);
        //Seekbar-progressbar-text
        final TextView textView1;
        final TextView textView2;
            final SeekBar sb = customLayout.findViewById(R.id.sizeBar);
            textView1= customLayout.findViewById(R.id.textView2);
            textView2=customLayout.findViewById(R.id.textView3);
            final ProgressBar prg=customLayout.findViewById(R.id.progressBar2);
            //Gia na exun timi meta tin deuteri emfanisi tou dialog
            sb.setProgress(paintView.strokeWidth);
            prg.setProgress(sb.getProgress());
            textView1.setText(""+sb.getProgress()+"%");

            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressi;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressi=progress;
                    textView1.setText(""+progressi+"%");
                    textView2.setText("Changing Size");
                    prg.setProgress(progressi);
                }

             
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    textView2.setText("Changing Size Stopped");
                }
            });

        
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                 paintView.strokeWidth = sb.getProgress();

             if(paintView.strokeWidth ==0){
                 Toast.makeText(getApplicationContext(), "Brush Size set to 2% for safety " , Toast.LENGTH_LONG).show();
             }else {
                 Toast.makeText(getApplicationContext(), "Brush Size= " +  paintView.strokeWidth+ "%", Toast.LENGTH_LONG).show();
             }
         }});

           
        AlertDialog dialog = builder.create();
        dialog.show();
    }

      

        public void saveBitmap() {
            try{
                int count = 1;
                File file;
                String imgName = "img" + count + ".png";
                file = new File(this.getDir(Environment.DIRECTORY_PICTURES,Context.MODE_WORLD_READABLE), imgName);
                String path = file.getAbsolutePath();

                while (file.exists()) {
                    count++;
                    imgName = "img" + count + ".png";
                    file = new File(path, imgName);
                }

                if (!file.exists())
                    new File(path).mkdirs();

                Bitmap savedBitmap = paintView.mBitmap;
                String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), savedBitmap, "img"+count , "Made with PaintIt!");
                System.out.println("\nImage saved to internal storage: "+imgSaved + "\tAt: "+path);
            Toast.makeText(getApplicationContext(),"Image Saved!", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error: Image was not saved",Toast.LENGTH_LONG).show();
            }
        }

        public void saveBitmapSD(){


            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            paintView.mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            File exStorageDir = Environment.getExternalStoragePublicDirectory("PaintIt");

            int count = 1;
            String filename = "img"+count+".png";
            String path = exStorageDir.getAbsolutePath();

            System.out.println("Ext storage Path: "+path);
            File file = new File(path+File.separator+filename);

            while(file.exists()){
                count++;
                filename = "img"+count+".png";
                System.out.println("File to save:"+path+File.separator+filename);
                file = new File(path+File.separator+filename);
            }

            if(!file.exists()) {
                new File(path).mkdirs();
            }

            FileOutputStream fOut = null;
            try{
                file.createNewFile();
                fOut = new FileOutputStream(file);
                fOut.write(bytes.toByteArray());

                Toast.makeText(getApplicationContext(),"Image Saved!", Toast.LENGTH_LONG).show();
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error Image not Saved",Toast.LENGTH_LONG).show();
            }finally{
                if(fOut != null){
                    try{
                        fOut.close();
                        bytes.close();
                    }catch (IOException e){
                        System.out.println("\n===========\nOutput Stream didn't close!\n");
                        e.printStackTrace();
                    }
                }
            }

        }
    }