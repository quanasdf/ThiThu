package com.example.thithu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để mở màn hình chính
                Intent intent = new Intent(SplashScreen.this, SignIn.class);
                startActivity(intent);
                finish(); // Đóng SplashActivity sau khi chuyển đến màn hình chính
            }
        }, SPLASH_DISPLAY_TIME);
    }
}