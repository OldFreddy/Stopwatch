package com.oldfredddy.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;


import java.util.Locale;

public class StopwatchActivity extends Activity {
    //Количество секунд на секундомере
    private int seconds = 0;
    //Секундомен работает?
    private boolean running;
    //Переменная для хранения информации о том, работал ли секундомер перед выховом метода onStop().
    private boolean wasRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);

    }

    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
    }
    //вызывается перед уничтожением активности
//    @Override
//    protected void onStop() {
//        super.onStop();
//        wasRunning = running;
//        running = false;
//    }
    //смотри методы жизненного цикла активности

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //вызывается после создания активности
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (wasRunning){
//            running = true;
//        }
//    }

    //смотри методы жизненного цикла активности
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;


    }

    //Reset the stopwatch when the Reset button in clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = (seconds % 3600) % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000); //используется для перезапуска метода Run через секунду
            }
        });


    }


}
