package com.softylogic.musicQar;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class findChordScale extends AppCompatActivity {
    private boolean scrolling = false;
    DialogFragment dialogue = new InfoPopUpDialogueFragment();
    private AdView adview;
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_chord_scale_layout);
        adview = (AdView) findViewById(R.id.adView);
        AdRequest adrequest = new AdRequest.Builder().build();
        adview.loadAd(adrequest);
        Bundle message = new Bundle();
        message.putString("message" , "Help/About" +
                "\n Chord/Scale Finder" +
                "\n Thousands of Chords and scales at your fingertips.\n" +
                "Just select your desired chord or scale from the lower wheels and upper wheels will show the notes of that particular chord or scale.\n");
        dialogue.setArguments(message);
        toolbar = (Toolbar) findViewById(R.id.toolbar_chordscalefinder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.btn_home);
        final String firstSelectionWheel[] = new String[] {"Scales", "Chords"}; //Polpulates firstSelectionWheel
        final String secondSelectionWheel[] = new String[] {"A", "A#" ,"B" , "C" ,"C#", "D", "D#" , "E" , "F"
                ,"F#" , "G" , "G#"}; // Populates second selection wheel--Calculates Chords by Formula
        final String[][] chordsNameByNumberOfNotes= new String[][]{
                new String[]{"5"},
                new String[]{"maj", "min","sus4","aug", "dim","sus9"},
                new String[]{"6", "min6","maj7", "7", "min7","min7b5","sus4 7","aug7","dim7","minmaj7","add9"},
                new String[]{"M7 9", "7 9", "7 b9","7 #9","sus4 7 9","min7 9","M7 #11","7 #11","min7 11","M7 13",
                        "7 13","7 b13"}//Categorizes Chords according to the number of notes--Used to Disable Unnecessary Wheels
        };
        final String thirdSelectionWheel_chords[] = new String[] {"maj","min", "6","min6" ,"sus4"
                ,"5" ,"aug", "dim","maj7", "7","min7","min7b5","sus4 7","aug7","dim7" ,"minmaj7"
                ,"add9" ,"sus9","M7 9","7 9" ,"7 b9", "7 #9" ,"sus4 7 9" ,"min7 9" ,"M7 #11","7 #11",
                "min7 11", "M7 13", "7 13" ,"7 b13"}; //Populates thirdSelectionWheel
        final String chordDetailName[] =
                {"Major",
                "Minor",
                "Major with a major sixth",
                "Minor with a major sixth",
                "Suspended 4",
                "Power Chord",
                "Augmented",
                "Diminished",
                "Major, major seventh",
                "Major, minor seventh",
                "Minor, minor seventh",
                "Minor with a diminished fifth and a minor seventh",
                "Suspended 4, minor seventh",
                "Augmented C, minor seventh",
                "Diminished C, diminished seventh",
                "Minor, major seventh",
                "Major, major ninth",
                "Major where the third is replaced by the major 9th",
                "Major, major seventh, major ninth",
                "Major, minor seventh, major ninth",
                "Major, minor seventh, minor ninth",
                "Major, minor seventh, augmented ninth",
                "Suspended 4, minor seventh, major ninth",
                "Minor, minor seventh, major ninth",
                "Major, major seventh, augmented eleventh",
                "Major, minor seventh, augmented eleventh",
                "Minor, minor seventh, perfect",
                "Major, major seventh, major thirteenth",
                "Major, minor seventh, major thirteenth",
                "Major, minor seventh, minor thirteenth"};

        final String [][] chordFormulae =  new String[][]{
                new String[]{"4" , "7"}, new String[]{"3", "7"}, new String[]{"4" , "7", "9"},
                new String[]{"3", "7" ,"9"}, new String[]{"5", "7"},new String[]{"7"},
                new String[]{"4", "8"},new String[]{"3" ,"6"},new String[]{"4", "7" , "11"},
                new String[]{"4", "7" , "10"},new String[]{"3", "7", "10"},new String[]{"3", "6","10"},
                new String[]{"5","7","10"},new String[]{"4","8","10"},new String[]{"3","6","9"},
                new String[]{"3","7","11"},new String[]{"4","7","2"},new String[]{"7","2"},
                new String[]{"4","7","11","2"},new String[]{"4","7","10","2"},new String[]{"4","7","10","1"},
                new String[]{"4","7","10","3"},new String[]{"5","7","10","2"},new String[]{"3","7","10","2"},
                new String[]{"4","7","11","6"},new String[]{"4","7","10","6"},new String[]{"3","7","10","5"},
                new String[]{"4","7","11","9"},new String[]{"4","7","10","9"},new String[]{"4","7","10","8"},
        };//Formulae for Chords Calculation
        final String thirdSelectionWheel_scales[] = new String[] {"Major","Minor", "Pentatonic" ,"Blues","Non Western"};
        final String[][] fourthSelectionWheel = new String[][]{
                new String[]{"Ionian","Dorian","Phrygian","Lydian","Mixolydian","Aeolian","Locrian"},
                new String[]{"Natural","Harmonic","Melodic"},
                new String[]{"Major","Minor","Hyojo Minor Pentatonic"},
                new String[]{"Major//Country","Minor"},
                new String[]{"Byzantine","Gypsy","Arabian","Persian","Jewish","Ethiopia","Pelog","Java","Indian",
                        "Oriental"}
        };
        final String [][] majorScaleFormulae =  new String[][]{
                new String[]{"3", "5", "6", "8" ,"10" ,"12"}, new String[]{"3","4", "6", "8" ,"10", "11"}, new String[]{"2", "4", "6" ,"8", "9" ,"10"},
                new String[]{"3", "5", "7", "8", "10" ,"12"}, new String[]{"3", "5" ,"6" ,"8", "10" ,"11"}, new String[]{"3", "4" ,"6", "8" ,"9" ,"11"},
                new String[]{"2", "4", "6" ,"7", "9", "11"}
        };
        final String [][] minorScaleFormulae =  new String[][]{
                new String[]{"3", "4", "6", "8" ,"9" ,"11"}, new String[]{"3","4", "6", "8" ,"9", "12"}, new String[]{"3", "4", "6" ,"8", "10" ,"12"},
        };
        final String [][] pentatonicScaleFormulae =  new String[][]{
                new String[]{"3", "5", "8", "10"}, new String[]{"4", "6", "8","11"}, new String[]{"3", "6", "8" ,"10"}
        };
        final String [][] bluesScaleFormulae =  new String[][]{
                new String[]{"3", "4", "5", "8", "10"}, new String[]{"4", "6","7", "8","11"},
        };
        final String [][] nonWesternScaleFormulae =  new String[][]{
                new String[]{"2", "5", "6", "8" ,"9" ,"12"}, new String[]{"3","4", "7", "8" ,"9", "11"}, new String[]{"3", "5", "6" ,"7", "9" ,"11"},
                new String[]{"2", "5", "6", "7", "9" ,"12"}, new String[]{"2", "5" ,"6" ,"8", "9" ,"11"}, new String[]{"3", "4" ,"6", "8" ,"9" ,"12"},
                new String[]{"2", "4", "6" ,"8", "9", "11"}, new String[]{"2", "4", "6" ,"8", "10", "11"}, new String[]{"3", "5", "6" ,"8", "9", "11"},
                new String[]{"2", "5", "6" ,"7", "10", "11"}
        };
        final LinkedList<String> emptyLinkedList =  new LinkedList<>();
        LinkedList<String> firstSelectionWheelLinkedList = new LinkedList<>(Arrays.asList(firstSelectionWheel));
        final LinkedList<String> secondSelectionWheelLinkedList = new LinkedList<>(Arrays.asList(secondSelectionWheel));
        LinkedList<String> thirdSelectionWheelLinkedList_chords = new LinkedList<>(Arrays.asList(thirdSelectionWheel_chords));
        LinkedList<String> thirdSelectionWheelLinkedList_scales = new LinkedList<>(Arrays.asList(thirdSelectionWheel_scales));
        LinkedList<String> fourthSelectionWheel_linkedList = new LinkedList<>(Arrays.asList(fourthSelectionWheel[0]));


        final WheelView slot1 = initWheel(R.id.slot_1, secondSelectionWheelLinkedList);
        final WheelView slot2 = initWheel(R.id.slot_2, secondSelectionWheelLinkedList);
        final WheelView slot3 = initWheel(R.id.slot_3, secondSelectionWheelLinkedList);
        final WheelView slot4 = initWheel(R.id.slot_4, secondSelectionWheelLinkedList);
        final WheelView slot5 = initWheel(R.id.slot_5, secondSelectionWheelLinkedList);
        final WheelView slot6 = initWheel(R.id.slot_6, secondSelectionWheelLinkedList);
        final WheelView slot7 = initWheel(R.id.slot_7, secondSelectionWheelLinkedList);
        final WheelView slot8 = initSelectionWheel(R.id.slot_8 , firstSelectionWheelLinkedList);
        final WheelView slot9 = initSelectionWheel(R.id.slot_9 , secondSelectionWheelLinkedList);
        final WheelView slot10 = (WheelView) (findViewById(R.id.slot_10));
        final WheelView slot11 = initSelectionWheel(R.id.slot_11 , fourthSelectionWheel_linkedList);
        slot8.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    showItemsInThirdWheel(slot8.getCurrentItem() ,slot9 , slot10 , secondSelectionWheel , thirdSelectionWheel_chords , thirdSelectionWheel_scales );
                    if(slot8.getCurrentItem() == 0){
                        showItemsInFourthWheel(fourthSelectionWheel ,slot11 , slot10.getCurrentItem());
                        slot11.setAlpha(1);
                        slot11.setEnabled(true);
                    }
                    else{
                        slot11.setAlpha(0.5f);
                        slot11.setEnabled(false);
                    }
                }
            }
        });

        slot8.addScrollingListener( new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                showItemsInThirdWheel(slot8.getCurrentItem() ,slot9 , slot10 , secondSelectionWheel , thirdSelectionWheel_chords , thirdSelectionWheel_scales );
                if(slot8.getCurrentItem() == 0){
                    showItemsInFourthWheel(fourthSelectionWheel ,slot11 , slot10.getCurrentItem());
                    slot11.setAlpha(1);
                    slot11.setEnabled(true);
                }
                else{
                    slot11.setAlpha(0.5f);
                    slot11.setEnabled(false);
                }
            }
        });
        slot9.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {

                    showItemsInThirdWheel(slot8.getCurrentItem() ,slot9 , slot10 , secondSelectionWheel , thirdSelectionWheel_chords , thirdSelectionWheel_scales );

                }
            }
        });

        slot9.addScrollingListener( new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;

                showItemsInThirdWheel(slot8.getCurrentItem() ,slot9 , slot10 , secondSelectionWheel , thirdSelectionWheel_chords , thirdSelectionWheel_scales );

            }
        });
        slot10.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    if(slot8.getCurrentItem()==0){
                        showItemsInFourthWheel(fourthSelectionWheel ,slot11 , slot10.getCurrentItem());
                    }
                    else{
                        int chordLabel = slot10.getCurrentItem();
                        ArrayList<String> list = calculateChords(secondSelectionWheelLinkedList , slot9.getCurrentItem() ,chordFormulae , slot10.getCurrentItem());
                        int listLength = list.size();
                        switch (listLength){
                            case 2:
                                slot1.setAlpha(1);
                                slot2.setAlpha(1);
                                mixWheel(R.id.slot_1 , secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 144 );
                                mixWheel(R.id.slot_2 , secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem()+ 240 ) ;
                                slot3.setAlpha(0.5f);
                                slot4.setAlpha(0.5f);
                                slot5.setAlpha(0.5f);
                                slot6.setAlpha(0.5f);
                                slot7.setAlpha(0.5f);

                                break;
                            case 3:
                                slot1.setAlpha(1);
                                slot2.setAlpha(1);
                                slot3.setAlpha(1);
                                mixWheel(R.id.slot_1 , secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 144 );
                                mixWheel(R.id.slot_2 , secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem() + 240 );
                                mixWheel(R.id.slot_3 , secondSelectionWheelLinkedList.indexOf(list.get(2)) - slot3.getCurrentItem() + 300 );
                                slot4.setAlpha(0.5f);
                                slot5.setAlpha(0.5f);
                                slot6.setAlpha(0.5f);
                                slot7.setAlpha(0.5f);
                                break;
                            case 4:
                                slot1.setAlpha(1);
                                slot2.setAlpha(1);
                                slot3.setAlpha(1);
                                slot4.setAlpha(1);
                                mixWheel(R.id.slot_1 , secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 144 );
                                mixWheel(R.id.slot_2 , secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem() + 240 );
                                mixWheel(R.id.slot_3 , secondSelectionWheelLinkedList.indexOf(list.get(2)) - slot3.getCurrentItem() + 300 );
                                mixWheel(R.id.slot_4 , secondSelectionWheelLinkedList.indexOf(list.get(3)) - slot4.getCurrentItem() + 360 );
                                slot5.setAlpha(0.5f);
                                slot6.setAlpha(0.5f);
                                slot7.setAlpha(0.5f);
                                break;

                            case 5:
                                slot1.setAlpha(1);
                                slot2.setAlpha(1);
                                slot3.setAlpha(1);
                                slot4.setAlpha(1);
                                slot5.setAlpha(1);
                                mixWheel(R.id.slot_1 , secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 144 );
                                mixWheel(R.id.slot_2 , secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem() + 240 );
                                mixWheel(R.id.slot_3 , secondSelectionWheelLinkedList.indexOf(list.get(2)) - slot3.getCurrentItem() + 300 );
                                mixWheel(R.id.slot_4 , secondSelectionWheelLinkedList.indexOf(list.get(3)) - slot4.getCurrentItem() + 360 );
                                mixWheel(R.id.slot_5 , secondSelectionWheelLinkedList.indexOf(list.get(4)) - slot5.getCurrentItem() + 420 );
                                slot6.setAlpha(0.5f);
                                slot7.setAlpha(0.5f);
                                break;
                        }
                    }
                }
            }
        });

        slot10.addScrollingListener( new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                if(slot8.getCurrentItem()==0){

                    showItemsInFourthWheel(fourthSelectionWheel ,slot11 , slot10.getCurrentItem());

                }
                else{
                    int chordLabel = slot10.getCurrentItem();
                    ArrayList<String> list = calculateChords(secondSelectionWheelLinkedList , slot9.getCurrentItem() ,chordFormulae , slot10.getCurrentItem());
                    int listLength = list.size();
                    switch (listLength){
                        case 2:
                            slot1.setAlpha(1);
                            slot2.setAlpha(1);
                            mixWheel(R.id.slot_1 , secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 144 );
                            mixWheel(R.id.slot_2 , secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem()+ 240 ) ;
                            slot3.setAlpha(0.5f);
                            slot4.setAlpha(0.5f);
                            slot5.setAlpha(0.5f);
                            slot6.setAlpha(0.5f);
                            slot7.setAlpha(0.5f);
                            break;
                        case 3:
                            slot1.setAlpha(1);
                            slot2.setAlpha(1);
                            slot3.setAlpha(1);
                            mixWheel(R.id.slot_1 , secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 144 );
                            mixWheel(R.id.slot_2 , secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem() + 240 );
                            mixWheel(R.id.slot_3 , secondSelectionWheelLinkedList.indexOf(list.get(2)) - slot3.getCurrentItem() + 300 );
                            slot4.setAlpha(0.5f);
                            slot5.setAlpha(0.5f);
                            slot6.setAlpha(0.5f);
                            slot7.setAlpha(0.5f);
                            break;
                        case 4:
                            slot1.setAlpha(1);
                            slot2.setAlpha(1);
                            slot3.setAlpha(1);
                            slot4.setAlpha(1);
                            mixWheel(R.id.slot_1 , secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 144 );
                            mixWheel(R.id.slot_2 , secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem() + 240 );
                            mixWheel(R.id.slot_3 , secondSelectionWheelLinkedList.indexOf(list.get(2)) - slot3.getCurrentItem() + 300 );
                            mixWheel(R.id.slot_4 , secondSelectionWheelLinkedList.indexOf(list.get(3)) - slot4.getCurrentItem() + 360 );
                            slot5.setAlpha(0.5f);
                            slot6.setAlpha(0.5f);
                            slot7.setAlpha(0.5f);
                            break;

                        case 5:
                            slot1.setAlpha(1);
                            slot2.setAlpha(1);
                            slot3.setAlpha(1);
                            slot4.setAlpha(1);
                            slot5.setAlpha(1);
                            mixWheel(R.id.slot_1 , secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 144 );
                            mixWheel(R.id.slot_2 , secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem() + 240 );
                            mixWheel(R.id.slot_3 , secondSelectionWheelLinkedList.indexOf(list.get(2)) - slot3.getCurrentItem() + 300 );
                            mixWheel(R.id.slot_4 , secondSelectionWheelLinkedList.indexOf(list.get(3)) - slot4.getCurrentItem() + 360 );
                            mixWheel(R.id.slot_5 , secondSelectionWheelLinkedList.indexOf(list.get(4)) - slot5.getCurrentItem() + 420 );
                            slot6.setAlpha(0.5f);
                            slot7.setAlpha(0.5f);
                            break;
                    }
                }
            }
        });
        slot11.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                   if(slot8.getCurrentItem() == 0){

                       int currentItemInSlot10 = slot10.getCurrentItem();
                       switch(currentItemInSlot10) {
                           case 0:
                               slot1.setAlpha(1f);
                               slot2.setAlpha(1f);
                               slot3.setAlpha(1f);
                               slot4.setAlpha(1f);
                               slot5.setAlpha(1f);
                               slot6.setAlpha(1f);
                               slot7.setAlpha(1f);
                               ArrayList<String> list = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), majorScaleFormulae);
                               mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 120);
                               mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem() + 144);
                               mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(list.get(2)) - slot3.getCurrentItem() + 180);
                               mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(list.get(3)) - slot4.getCurrentItem() + 180);
                               mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(list.get(4)) - slot5.getCurrentItem() + 180);
                               mixWheel(R.id.slot_6, secondSelectionWheelLinkedList.indexOf(list.get(5)) - slot6.getCurrentItem() + 180);
                               mixWheel(R.id.slot_7, secondSelectionWheelLinkedList.indexOf(list.get(6)) - slot7.getCurrentItem() + 180);
                               break;

                           case 1:
                               slot1.setAlpha(1f);
                               slot2.setAlpha(1f);
                               slot3.setAlpha(1f);
                               slot4.setAlpha(1f);
                               slot5.setAlpha(1f);
                               slot6.setAlpha(1f);
                               slot7.setAlpha(1f);
                               ArrayList<String> minorList = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), minorScaleFormulae);
                               mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(minorList.get(0)) - slot1.getCurrentItem() + 120);
                               mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(minorList.get(1)) - slot2.getCurrentItem() + 144);
                               mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(minorList.get(2)) - slot3.getCurrentItem() + 180);
                               mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(minorList.get(3)) - slot4.getCurrentItem() + 180);
                               mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(minorList.get(4)) - slot5.getCurrentItem() + 180);
                               mixWheel(R.id.slot_6, secondSelectionWheelLinkedList.indexOf(minorList.get(5)) - slot6.getCurrentItem() + 180);
                               mixWheel(R.id.slot_7, secondSelectionWheelLinkedList.indexOf(minorList.get(6)) - slot7.getCurrentItem() + 180);
                               break;
                           case 2:
                               slot1.setAlpha(1f);
                               slot2.setAlpha(1f);
                               slot3.setAlpha(1f);
                               slot4.setAlpha(1f);
                               slot5.setAlpha(1f);
                               ArrayList<String> penList = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), pentatonicScaleFormulae);
                               mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(penList.get(0)) - slot1.getCurrentItem() + 120);
                               mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(penList.get(1)) - slot2.getCurrentItem() + 144);
                               mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(penList.get(2)) - slot3.getCurrentItem() + 180);
                               mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(penList.get(3)) - slot4.getCurrentItem() + 180);
                               mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(penList.get(4)) - slot5.getCurrentItem() + 180);
                               slot6.setAlpha(0.5f);
                               slot7.setAlpha(0.5f);
                               break;
                           case 3:
                               slot1.setAlpha(1f);
                               slot2.setAlpha(1f);
                               slot3.setAlpha(1f);
                               slot4.setAlpha(1f);
                               slot5.setAlpha(1f);
                               slot6.setAlpha(1f);
                               ArrayList<String> bluesList = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), bluesScaleFormulae);
                               mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(bluesList.get(0)) - slot1.getCurrentItem() + 120);
                               mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(bluesList.get(1)) - slot2.getCurrentItem() + 144);
                               mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(bluesList.get(2)) - slot3.getCurrentItem() + 180);
                               mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(bluesList.get(3)) - slot4.getCurrentItem() + 180);
                               mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(bluesList.get(4)) - slot5.getCurrentItem() + 180);
                               mixWheel(R.id.slot_6, secondSelectionWheelLinkedList.indexOf(bluesList.get(5)) - slot6.getCurrentItem() + 180);
                               slot7.setAlpha(0.5f);
                               break;
                           case 4:
                               slot1.setAlpha(1f);
                               slot2.setAlpha(1f);
                               slot3.setAlpha(1f);
                               slot4.setAlpha(1f);
                               slot5.setAlpha(1f);
                               slot6.setAlpha(1f);
                               slot7.setAlpha(1f);
                               ArrayList<String> nonWesternList = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), nonWesternScaleFormulae);
                               mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(0)) - slot1.getCurrentItem() + 120);
                               mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(1)) - slot2.getCurrentItem() + 144);
                               mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(2)) - slot3.getCurrentItem() + 180);
                               mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(3)) - slot4.getCurrentItem() + 180);
                               mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(4)) - slot5.getCurrentItem() + 180);
                               mixWheel(R.id.slot_6, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(5)) - slot6.getCurrentItem() + 180);
                               mixWheel(R.id.slot_7, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(6)) - slot7.getCurrentItem() + 180);
                               break;
                       }
                   }


                }
            }
        });

        slot11.addScrollingListener( new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                if(slot8.getCurrentItem() == 0){
                    int currentItemInSlot10 = slot10.getCurrentItem();
                    switch(currentItemInSlot10) {
                        case 0:
                            slot1.setAlpha(1f);
                            slot2.setAlpha(1f);
                            slot3.setAlpha(1f);
                            slot4.setAlpha(1f);
                            slot5.setAlpha(1f);
                            slot6.setAlpha(1f);
                            slot7.setAlpha(1f);
                            ArrayList<String> list = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), majorScaleFormulae);
                            mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(list.get(0)) - slot1.getCurrentItem() + 120);
                            mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(list.get(1)) - slot2.getCurrentItem() + 144);
                            mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(list.get(2)) - slot3.getCurrentItem() + 180);
                            mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(list.get(3)) - slot4.getCurrentItem() + 180);
                            mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(list.get(4)) - slot5.getCurrentItem() + 180);
                            mixWheel(R.id.slot_6, secondSelectionWheelLinkedList.indexOf(list.get(5)) - slot6.getCurrentItem() + 180);
                            mixWheel(R.id.slot_7, secondSelectionWheelLinkedList.indexOf(list.get(6)) - slot7.getCurrentItem() + 180);
                            break;
                        case 1:
                            slot1.setAlpha(1f);
                            slot2.setAlpha(1f);
                            slot3.setAlpha(1f);
                            slot4.setAlpha(1f);
                            slot5.setAlpha(1f);
                            slot6.setAlpha(1f);
                            slot7.setAlpha(1f);
                            ArrayList<String> minorList = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), minorScaleFormulae);
                            mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(minorList.get(0)) - slot1.getCurrentItem() + 120);
                            mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(minorList.get(1)) - slot2.getCurrentItem() + 144);
                            mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(minorList.get(2)) - slot3.getCurrentItem() + 180);
                            mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(minorList.get(3)) - slot4.getCurrentItem() + 180);
                            mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(minorList.get(4)) - slot5.getCurrentItem() + 180);
                            mixWheel(R.id.slot_6, secondSelectionWheelLinkedList.indexOf(minorList.get(5)) - slot6.getCurrentItem() + 180);
                            mixWheel(R.id.slot_7, secondSelectionWheelLinkedList.indexOf(minorList.get(6)) - slot7.getCurrentItem() + 180);
                            break;
                        case 2:
                            slot1.setAlpha(1f);
                            slot2.setAlpha(1f);
                            slot3.setAlpha(1f);
                            slot4.setAlpha(1f);
                            slot5.setAlpha(1f);
                            ArrayList<String> penList = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), pentatonicScaleFormulae);
                            mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(penList.get(0)) - slot1.getCurrentItem() + 120);
                            mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(penList.get(1)) - slot2.getCurrentItem() + 144);
                            mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(penList.get(2)) - slot3.getCurrentItem() + 180);
                            mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(penList.get(3)) - slot4.getCurrentItem() + 180);
                            mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(penList.get(4)) - slot5.getCurrentItem() + 180);
                            slot6.setAlpha(0.5f);
                            slot7.setAlpha(0.5f);
                            break;
                        case 3:
                            slot1.setAlpha(1f);
                            slot2.setAlpha(1f);
                            slot3.setAlpha(1f);
                            slot4.setAlpha(1f);
                            slot5.setAlpha(1f);
                            slot6.setAlpha(1f);
                            ArrayList<String> bluesList = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), bluesScaleFormulae);
                            mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(bluesList.get(0)) - slot1.getCurrentItem() + 120);
                            mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(bluesList.get(1)) - slot2.getCurrentItem() + 144);
                            mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(bluesList.get(2)) - slot3.getCurrentItem() + 180);
                            mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(bluesList.get(3)) - slot4.getCurrentItem() + 180);
                            mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(bluesList.get(4)) - slot5.getCurrentItem() + 180);
                            mixWheel(R.id.slot_6, secondSelectionWheelLinkedList.indexOf(bluesList.get(5)) - slot6.getCurrentItem() + 180);
                            slot7.setAlpha(0.5f);
                            break;
                        case 4:
                            slot1.setAlpha(1f);
                            slot2.setAlpha(1f);
                            slot3.setAlpha(1f);
                            slot4.setAlpha(1f);
                            slot5.setAlpha(1f);
                            slot6.setAlpha(1f);
                            slot7.setAlpha(1f);
                            ArrayList<String> nonWesternList = calculateScale(secondSelectionWheelLinkedList, slot9.getCurrentItem(), slot11.getCurrentItem(), nonWesternScaleFormulae);
                            mixWheel(R.id.slot_1, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(0)) - slot1.getCurrentItem() + 120);
                            mixWheel(R.id.slot_2, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(1)) - slot2.getCurrentItem() + 144);
                            mixWheel(R.id.slot_3, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(2)) - slot3.getCurrentItem() + 180);
                            mixWheel(R.id.slot_4, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(3)) - slot4.getCurrentItem() + 180);
                            mixWheel(R.id.slot_5, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(4)) - slot5.getCurrentItem() + 180);
                            mixWheel(R.id.slot_6, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(5)) - slot6.getCurrentItem() + 180);
                            mixWheel(R.id.slot_7, secondSelectionWheelLinkedList.indexOf(nonWesternList.get(6)) - slot7.getCurrentItem() + 180);
                            break;
                    }
                }


            }
        });
        updateScalesInThirdWheel(slot10, thirdSelectionWheel_scales );
    }
    /**
     * Show Items in Fourth Selection Wheel
     * @param arrayOfScales 2D Array which contains Scales names
     * @param slot11 Wheel to Show Scales Names
     * @param currentItemOfSlot10 Index - Shows Which Scale Type is selected in the wheel
     */
    private void showItemsInFourthWheel(String [][] arrayOfScales, WheelView slot11, int currentItemOfSlot10){

        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList(arrayOfScales[currentItemOfSlot10]));
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, linkedList);
        adapter.setTextSize(18);
        slot11.setViewAdapter(adapter);
        slot11.setCurrentItem(0);
    }
    /**
     * Function ---> Shows appropriate list of items when items in first wheel are scrolled through
     * @param Index First wheel Currently Selected Item
     *  @param slot9 Wheel of Notes
     * @param slot10 Wheel That will show Chords or Scales
     * @param secondSelectionWheel Array of Notes
     *@param thirdSelectionWheel_chords Array of chords
     *@param thirdSelectionWheel_scales Array of Scales
     */
    private void showItemsInThirdWheel(int Index , WheelView slot9 , WheelView slot10 , String []secondSelectionWheel , String[] thirdSelectionWheel_chords , String [] thirdSelectionWheel_scales){
        if (Index==0){

            updateScalesInThirdWheel(slot10 ,thirdSelectionWheel_scales );
        }
        else{
            updateChordsInThirdWheel(slot9 ,slot10 , secondSelectionWheel , thirdSelectionWheel_chords );
        }
    }
    /**
     *
     * @param listOfNotes secondSelectionWheel Array containing 12 notes
     * @param currentNoteSelected Current Note Selected in Slot 9
     * @param chordFormulaeArray Array containing Chord Formulae
     * @param currentChordTypeSelected Current Chord Type Selected in Slot 10
     * @return
     */
    private ArrayList<String> calculateChords(LinkedList<String> listOfNotes, int currentNoteSelected, String chordFormulaeArray[][] , int currentChordTypeSelected){

        ListIterator<String> iterator = listOfNotes.listIterator(currentNoteSelected);
        ArrayList<String> list = new ArrayList<>();
        list.add(iterator.next());
        for(int i = 0 ; i < chordFormulaeArray[currentChordTypeSelected].length ; i++ ){
            iterator = listOfNotes.listIterator(currentNoteSelected);
            int counter = Integer.parseInt(chordFormulaeArray[currentChordTypeSelected][i]);
            for(int j = 1; j<=counter; j++){
                if (!iterator.hasNext()) {
                    iterator = listOfNotes.listIterator(0);
                }
                iterator.next();
            }

            if (!iterator.hasNext()) {
                list.add(listOfNotes.get(0));
            }
            else{
                list.add(iterator.next());
            }

        }
        return list;
    }
    private ArrayList<String> calculateScale(LinkedList<String> listOfNotes, int currentNoteSelected, int currentScale2ndTypeSelected , String [][] arrayOfScaleFormulae){

        ListIterator<String> iterator = listOfNotes.listIterator(currentNoteSelected);
        ArrayList<String> list = new ArrayList<>();
        list.add(iterator.next());
        for(int i = 0 ; i < arrayOfScaleFormulae[currentScale2ndTypeSelected].length ; i++ ){
            iterator = listOfNotes.listIterator(currentNoteSelected);
            int counter = Integer.parseInt(arrayOfScaleFormulae[currentScale2ndTypeSelected][i])-1;
            for(int j = 1; j<=counter; j++){
                if (!iterator.hasNext()) {
                    iterator = listOfNotes.listIterator(0);
                }
                iterator.next();
            }

            if (!iterator.hasNext()) {
                list.add(listOfNotes.get(0));
            }
            else{
                list.add(iterator.next());
            }

        }
        return list;
    }
    /**
     * Function ---> Makes Chords names by concatinating two strings
     * @param Index Notes wheel Currently Selected Item
     *  @param array1 Notes Array
     * @param array2 Chords Array
     */
    private String[] createArrayOfChords(int Index, String [] array1 , String [] array2){
        String []array = new String[30];
        for(int i = 0 ; i <array2.length ; i++){
            array[i] = array1[Index].concat(array2[i]);
        }

        return  array;
    }
    private void updateChordsInThirdWheel(WheelView wheelNotes , WheelView wheelChords, String arrayNotes[] , String arrayChords[])
    {
        String array[] = createArrayOfChords(wheelNotes.getCurrentItem() , arrayNotes , arrayChords);
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList(array));
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, linkedList);
        adapter.setTextSize(18);
        wheelChords.setViewAdapter(adapter);
        wheelChords.setCurrentItem(0);


    }
    private void updateScalesInThirdWheel(WheelView wheelScales, String arrayScales[])
    {
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList(arrayScales));
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, linkedList);
        adapter.setTextSize(18);
        wheelScales.setViewAdapter(adapter);
        wheelScales.setCurrentItem(0);


    }
    private boolean wheelScrolled = false;
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        @Override
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
        }
    };
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {
            }
        }
    };
    /**
     * Initializes wheel
     * @param id the wheel widget Id
     * @param array
     */
    private WheelView initWheel(int id, LinkedList<String> array) {
        WheelView wheel = getWheel(id);
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, array);
        adapter.setTextSize(18);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem(0);
        wheel.setCyclic(true);
        wheel.setEnabled(false);
        return wheel;
    }
    private WheelView initSelectionWheel(int id , LinkedList<String> array) {
        WheelView wheel = getWheel(id);
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, array);
        adapter.setTextSize(18);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem(0);
        return wheel;
    }
    /**
     * Returns wheel by Id
     * @param id the wheel Id
     * @return the wheel with passed Id
     */
    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }
    /**
     * Tests wheels
     * @return true
     */
    private boolean test() {
        int value = getWheel(R.id.slot_1).getCurrentItem();
        return testWheelValue(R.id.slot_2, value) && testWheelValue(R.id.slot_3, value);
    }
    /**
     * Tests wheel value
     * @param id the wheel Id
     * @param value the value to test
     * @return true if wheel value is equal to passed value
     */
    private boolean testWheelValue(int id, int value) {
        return getWheel(id).getCurrentItem() == value;
    }
    /**
     * Mixes wheel
     * @param id the wheel id
     */
    private void mixWheel(int id, int distance) {
        WheelView wheel = getWheel(id);
        wheel.scroll(distance, 2000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu , menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.info:
            dialogue.show(getFragmentManager(), "InfoPopUp");
            return true;


            default:

                return super.onOptionsItemSelected(item);
        }


    }
}
