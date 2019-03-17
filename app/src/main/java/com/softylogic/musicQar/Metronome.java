package com.softylogic.musicQar;
import android.app.DialogFragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class Metronome extends AppCompatActivity {
    DialogFragment dialogue = new InfoPopUpDialogueFragment();
    private static final String TAG = "Metronome";
    private AdView adview;
    InterstitialAd interstitialAd;
    public static PlayMetronome pm = null;
    float tempo;
    int sound , sound1 , sound2, sound3;
    SoundPool soundpool;
    Chronometer chronometer;
    SeekBar sb;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    Toolbar toolbar;

    ImageButton playPausebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);
        adview = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("23FEAE86B93C663F6B9A887DF747DFD4").build();
        adview.loadAd(adRequest);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                AdRequest adRequest = new AdRequest.Builder().addTestDevice("23FEAE86B93C663F6B9A887DF747DFD4").build();
                interstitialAd.loadAd(adRequest);
            }
        });
        Bundle message = new Bundle();
        message.putString("message" , "Help/About\n" +
                "Metronome\n" +
                "Now be a Pro at Rhythm and Beats with this metronome.\n" +
                "You can select three optional sounds for the metronome. \n" +
                "Increase or decrease the tempo/BPM using Seekbar or the Increment/Decrement Buttons.\n" +
                "Timer shows how long have you practiced thus far.\n");
        dialogue.setArguments(message);
        toolbar = (Toolbar) findViewById(R.id.toolbar_metronome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.btn_home);
        soundpool = new SoundPool(3 , AudioManager.STREAM_MUSIC, 0);
        sound1 = soundpool.load(this , R.raw.beep ,1);
        sound2 = soundpool.load(this , R.raw.sound2 ,1);
        sound3 = soundpool.load(this, R.raw.sound4, 1);
        sound = sound1;
        chronometer = (Chronometer) findViewById(R.id.chronometer1);
        radioButton1 = (RadioButton) findViewById(R.id.sound1);
        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((isChecked)){
                    sound = sound1;
                }
            }
        });

        radioButton2 = (RadioButton) findViewById(R.id.sound2);
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sound = sound2;
                }
            }
        });
        radioButton3 = (RadioButton) findViewById(R.id.sound3);
        radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sound = sound3;
                }
            }
        });
        radioButton1.toggle();
        Button increment = (Button) findViewById(R.id.increment);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setProgress(sb.getProgress() + 1);
            }
        });
        Button decrement = (Button) findViewById(R.id.decrement);
        decrement.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setProgress(sb.getProgress() - 1);
            }
        });
        playPausebutton = (ImageButton) findViewById(R.id.playbutton);
        playPausebutton.setImageResource(R.drawable.media_play);
        if (playPausebutton != null) {
            playPausebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playPausebutton.getDrawable() == getResources().getDrawable(R.drawable.ic_media_pause)) {
                        pm.cancel();
                        playPausebutton.setImageResource(R.drawable.media_play);
                        //animatePausetoPlay();
                        radioButton1.setEnabled(true);
                        radioButton2.setEnabled(true);
                        radioButton3.setEnabled(true);
                        chronometer.stop();
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    } else {
                        pm = new PlayMetronome();
                        pm.execute();
                        playPausebutton.setImageResource(R.drawable.ic_media_pause);
                        //animatePlaytoPause();
                        radioButton1.setEnabled(false);
                        radioButton2.setEnabled(false);
                        radioButton3.setEnabled(false);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                    }
                }
            });
        }
        final TextView tv = (TextView) findViewById(R.id.progressShowTextView);
        tv.setText("15");
        sb = (SeekBar) findViewById(R.id.seekBar);
        tempo = sb.getProgress() + 15;
        sb.setAlpha(0.60f);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tempo = progress + 15;
                tv.setText(tempo + " ");
                    pm = new PlayMetronome();
                    pm.execute();
                    chronometer.stop();
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setAlpha(1f);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setAlpha(0.600f);
            }

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.info:

            dialogue.show(getFragmentManager() , "Message");

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        super.onStop();
            pm.cancel();
            radioButton1.setEnabled(true);
            radioButton2.setEnabled(true);
            radioButton3.setEnabled(true);
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            playPausebutton.setImageResource(R.drawable.media_play);
            if(interstitialAd.isLoaded()){
                interstitialAd.show();
        }

    }



    class PlayMetronome extends AsyncTask {
        long n = (long) (60 / tempo);
        long interval = n * 1000;
        private boolean running = true;

        @Override
        protected Object doInBackground(Object[] params) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                while(running){
                    soundpool.play(sound, 1, 1, 1, 0, 1);
                }


            }
        }, interval);

            return null;
        }

        public void cancel(){
             running = false;
        }
                                    }

                                            }
//class PlayMetronome extends Thread {
//    private boolean running = true;
//    public PlayMetronome() {
//    }
//    @Override
//    public void run() {
//        float n = 60 / tempo;
//        float interval = n * 1000;
//        while(running) {
//            soundpool.play(sound, 1, 1, 1, 0, 1);
//            try {
//                Thread.sleep((long) interval);
//            } catch (InterruptedException e) {
//                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
//            }
//        }
//    }
//    public void cancel() {
//        running = false;
//    }
//}