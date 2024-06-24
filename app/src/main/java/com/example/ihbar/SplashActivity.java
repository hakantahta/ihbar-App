package com.example.ihbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 2000; // Splash screen süresi (milisaniye cinsinden)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // ImageView bağlama
        ImageView imageViewSplash = findViewById(R.id.imageViewSplash);

        // İlerleme çubuğu bağlama
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        // İlerleme çubuğunu 5 saniyede doldur
        final int totalProgressTime = 5000;
        final int jumpTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < 100) {
                    try {
                        sleep(jumpTime); // İlerleme hızı
                        progress += 5; // İlerleme yüzdesi
                        progressBar.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

        // Splash ekran süresi sonunda ana ekrana geçiş yapılacak
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_TIMEOUT);

    }
}
