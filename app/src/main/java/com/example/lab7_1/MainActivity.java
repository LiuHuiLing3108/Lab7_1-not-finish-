package com.example.lab7_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int rabprogress = 0, turprogress = 0;

    private SeekBar seekBar, seekBar2;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btn_start.setEnabled(false);
                rabprogress = 0;
                turprogress = 0;
                seekBar.setProgress(0);
                seekBar2.setProgress(0);

                runThread();
                runAsyncTask();
            }
        });
    }
}

    private void runThread()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (rabprogress<=100 && turprogress<=100){
                    try {
                        Thread.sleep(100);
                        rabprogress += (int)(Math.random()*3);
                        Message msg = new Message();
                        msg.what = 1;
                        mHandle.sendMessage(msg);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler mHandle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    seekBar.setProgress(rabprogress);
                    break;
            }
            if (rabprogress>=100 && turprogress < 100){
                Toast.makeText(MainActivity.this,"兔子勝利",Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }
            return false;
        }
    });

    private void runAsyncTask()
    {
        new AsyncTask<Void, Integer, Boolean>(){
            @Override
            protected Boolean doInBackground(Void){
                while (turprogress<=100 && rabprogress<100){
                    try {
                        Thread.sleep(100);
                        turprogress += (int)(Math.random()*3);
                        publishProgress(turprogress);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            return true;
        }
    }




    /* while (turprogress<=100 && rabprogress<100){
        try {
        Thread.sleep(100);
        turprogress += (int)(Math.random()*3);
        publishProgress(turprogress);
        } catch (InterruptedException e){
        e.printStackTrace();
        } */

       /* @Override
        protected void onProgressUpdate(Integer) {
            super.onProgressUpdate(value[0]);
        }
        @Override
        protected void onPostExecute(Boolean){
            super.onPostExecute(Boolean aBoolean){
        if (turprogress>=100 && rabprogress<100){
            Toast.makeText(MainActivity.this,"烏龜勝利",Toast.LENGTH_SHORT).show();
            btn_start.setEnabled(true);
        }
            }
        }.execute();
    }*/