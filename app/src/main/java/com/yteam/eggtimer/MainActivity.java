package com.yteam.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    SeekBar timeBar;
    TextView timeView;
    Button timeButton;
    MediaPlayer siren;
    CountDownTimer countDownTimer;

    boolean timerActive = false;

    public void startTimer(View view){

        if (timerActive){
            timeBar.setEnabled(true);
            timeButton.setText("Start");
            timeBar.setProgress(30000);
            countDownTimer.cancel();
            timerActive = false;

        }else {
            timerActive =true;
            timeBar.setEnabled(false);
            timeButton.setText("Stop");
            long time = timeBar.getProgress();
            countDownTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onFinish() {
                    timeBar.setEnabled(true);
                    timeBar.setProgress(30000);
                    siren.start();
                }

                @Override
                public void onTick(long l) {
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(l);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(minutes);

                    timeView.setText(String.format("%02d:%02d", minutes, seconds));
                }
            }.start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeButton = findViewById(R.id.startButton);
        timeBar = findViewById(R.id.timerSeekBar);
        timeView = findViewById(R.id.timeTextView);

        siren = MediaPlayer.create(this,R.raw.foghorn);

        timeBar.setMax(10*60*1000);
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(i);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(i) - TimeUnit.MINUTES.toSeconds(minutes);

                timeView.setText(String.format("%02d:%02d",minutes,seconds));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
