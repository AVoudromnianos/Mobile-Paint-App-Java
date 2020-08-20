package com.example.test1;

        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.AnimationDrawable;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ImageView;
        import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
                AnimationDrawable wifianimation;
        private static int SPLASH_TIME_OUT = 1000;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_splash_screen);
             

                final ProgressBar prg = findViewById(R.id.progressBar);
                prg.setMax(100);
                prg.setProgress(0);


        new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                final Thread thread =new Thread(){
                        @Override
                        public void run() {
                                try{
                                        for(int i=0; i<110; i++){
                                                prg.setProgress(i);
                                                sleep(10);
                                        }
                                }catch(Exception e){
                                        e.printStackTrace();
                                }finally {
                                        Intent intent=new Intent(getApplicationContext(),gameMenu.class);
                                        startActivity(intent);
                                        finish();
                                }
                        }
                };
                thread.start();



        }
        },SPLASH_TIME_OUT);
       
     }
       
 }