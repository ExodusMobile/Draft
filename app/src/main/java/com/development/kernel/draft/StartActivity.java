package com.development.kernel.draft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private StartThreadIntent startThreadIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        LinearLayout MainScreen = (LinearLayout) findViewById(R.id.activity_start);
        MainScreen.setOnClickListener(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_draft);
        ImageView draft_sticker = (ImageView) findViewById(R.id.draft);
        draft_sticker.startAnimation(animation);

         startThreadIntent = new StartThreadIntent(this);
        startThreadIntent.start();

    }

    @Override
    public void onClick(View view) {
        startThreadIntent.interrupt();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        this.finish();

    }

    class StartThreadIntent extends Thread {
        private StartActivity startActivity;

        StartThreadIntent(StartActivity startActivity){
            this.startActivity = startActivity;
        }
        @Override
        public void run() {
            try{
                Thread.sleep(5000);
            }
            catch (Exception ignored){

            }
            Intent intent = new Intent(startActivity, RegisterActivity.class);
            startActivity(intent);
            startActivity.finish();
        }
    }
}
