package com.rohanshrestha.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayoutControls;
    LinearLayout linearLayoutTimeCounter;
    LinearLayout linearLayoutTimeSelector;
    Button buttonStart;
    SeekBar seekBarHours;
    SeekBar seekBarMinutes;
    SeekBar seekBarSeconds;
    TextView timeCountHours;
    TextView timeCountMinutes;
    TextView timeCountSeconds;
    CountDownTimer countDownTimer;
    long remainingTime;
    Boolean counterIsActive = false;


    public void updateHours(int hoursLeft) {

        String hours;
        if (hoursLeft < 10) {
            hours = "0" + Integer.toString(hoursLeft);
        } else {
            hours = Integer.toString(hoursLeft);
        }

        timeCountHours.setText(hours);

    }



    public void updateMinutes(int minutesLeft) {

        String minutes;
        if (minutesLeft < 10) {
            minutes = "0" + Integer.toString(minutesLeft);
        } else {
            minutes = Integer.toString(minutesLeft);
        }

        timeCountMinutes.setText(minutes);

    }

    public void updateSeconds(int secondsLeft){

        String seconds;
        if(secondsLeft<10){
            seconds = "0"+Integer.toString(secondsLeft);
        } else {
            seconds = Integer.toString(secondsLeft);
        }

        timeCountSeconds.setText(seconds);

    }

    public void timerInitialize (int time) {

        countDownTimer = new CountDownTimer(time + 100, 1000) {

            @Override
            public void onTick(long l) {

                long hoursLeft = (long) l / 3600000;
                long minutesLeft = (long) (l - hoursLeft * 3600000) / 60000;
                long secondsLeft = (long) (l - minutesLeft * 60000) / 1000;

                updateHours((int) hoursLeft);
                updateMinutes((int) minutesLeft);
                updateSeconds((int) secondsLeft);

                remainingTime = l;
            }

            @Override
            public void onFinish() {

                updateHours(0);
                updateMinutes(0);
                updateSeconds(0);
                seekBarHours.setProgress(0);
                seekBarMinutes.setProgress(0);
                seekBarSeconds.setProgress(0);

                linearLayoutTimeSelector = (LinearLayout) findViewById(R.id.linearLayoutTimeSelector);
                linearLayoutTimeCounter = (LinearLayout) findViewById(R.id.linearLayoutTimeCounter);
                Button buttonStop = (Button) findViewById(R.id.buttonStop);

                linearLayoutTimeSelector.setVisibility(View.VISIBLE);
                linearLayoutTimeCounter.animate().translationYBy(400f).setDuration(500);
                linearLayoutControls.animate().translationYBy(400f).setDuration(500);
                linearLayoutControls.setVisibility(View.GONE);
                buttonStart.setVisibility(View.VISIBLE);

                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                mediaPlayer.start();

            }
        }.start();

    }

    public void timerStart(View view) {

        counterIsActive = true;
        int totalTime = seekBarHours.getProgress()*60*60*1000 + seekBarMinutes.getProgress()*60*1000 +
                seekBarSeconds.getProgress()*1000;

        linearLayoutTimeSelector  = (LinearLayout) findViewById(R.id.linearLayoutTimeSelector);
        linearLayoutTimeCounter = (LinearLayout) findViewById(R.id.linearLayoutTimeCounter);
        buttonStart = (Button) findViewById(R.id.buttonStart);

        if (totalTime > 0) {
            linearLayoutTimeSelector.setVisibility(View.INVISIBLE);
            linearLayoutTimeCounter.animate().translationYBy(-400f).setDuration(500);
            linearLayoutControls.setVisibility(View.VISIBLE);
            linearLayoutControls.animate().translationYBy(-400f).setDuration(500);
            buttonStart.setVisibility(View.GONE);

            timerInitialize(totalTime);
        }

    }

    public void timerPauseResume(View view) {

        Button buttonPauseResume = (Button) findViewById(R.id.buttonPauseResume);

        if(counterIsActive) {

            countDownTimer.cancel();
            buttonPauseResume.setText(R.string.resume);
            counterIsActive = false;

        } else {

            timerInitialize((int)remainingTime);
            buttonPauseResume.setText(R.string.pause);
            counterIsActive = true;

        }
    }

    public void timerStop(View view) {

        linearLayoutTimeSelector  = (LinearLayout) findViewById(R.id.linearLayoutTimeSelector);
        linearLayoutTimeCounter = (LinearLayout) findViewById(R.id.linearLayoutTimeCounter);
        Button buttonStop = (Button) findViewById(R.id.buttonStop);

        updateHours(0);
        updateMinutes(0);
        updateSeconds(0);
        seekBarHours.setProgress(0);
        seekBarMinutes.setProgress(0);
        seekBarSeconds.setProgress(0);
        countDownTimer.cancel();
        linearLayoutTimeSelector.setVisibility(View.VISIBLE);
        linearLayoutTimeCounter.animate().translationYBy(400f).setDuration(500);
        linearLayoutControls.animate().translationYBy(400f).setDuration(500);
        linearLayoutControls.setVisibility(View.GONE);
        buttonStart.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeCountHours = (TextView) findViewById(R.id.textViewTimerHours);
        timeCountMinutes = (TextView) findViewById(R.id.textViewTimerMinutes);
        timeCountSeconds = (TextView) findViewById(R.id.textViewTimerSeconds);

        seekBarHours = (SeekBar) findViewById(R.id.seekBarHours);
        seekBarHours.setMax(99);
        seekBarHours.setProgress(0);
        seekBarHours.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateHours(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarMinutes = (SeekBar) findViewById(R.id.seekBarMinutes);
        seekBarMinutes.setMax(59);
        seekBarMinutes.setProgress(0);
        seekBarMinutes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateMinutes(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSeconds = (SeekBar) findViewById(R.id.seekBarSeconds);
        seekBarSeconds.setMax(59);
        seekBarSeconds.setProgress(0);
        seekBarSeconds.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateSeconds(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        linearLayoutControls = (LinearLayout) findViewById(R.id.linearLayoutControls);
        linearLayoutControls.setVisibility(View.GONE);

    }
}
