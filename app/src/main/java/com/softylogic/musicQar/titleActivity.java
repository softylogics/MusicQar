package com.softylogic.musicQar;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class titleActivity extends AppCompatActivity {
    Button metronome;
    Button chordScaleFinder;
    DialogFragment dialogue = new InfoPopUpDialogueFragment();
    Bundle message = new Bundle();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        message.putString("message", "Application Name : MusiQar\n" +
                "Version : 1.0.0 ( Initial Release)\n" +
                "Developed by : Softy Logics\n" +
                "Contact: softylogics@gmail.com\n");
        dialogue.setArguments(message);
        metronome = (Button) findViewById(R.id.metronomebutton);
        chordScaleFinder = (Button) findViewById(R.id.chordscalebutton);
        metronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent metronome = new Intent(getApplicationContext(), Metronome.class);
                startActivity(metronome);
            }
        });
        chordScaleFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findChordScale = new Intent(getApplicationContext(), findChordScale.class);
                startActivity(findChordScale);
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

        switch (item.getItemId()){

            case R.id.info:
                dialogue.show(getFragmentManager() , "Message");

            default:
            return super.onOptionsItemSelected(item);
        }


    }
}
