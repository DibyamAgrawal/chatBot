package cse2016.in.ac.nitrkl.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class CustomAnimationFragment extends TtsActivity {
    Timer timer;
    int page = 0;
    ViewPager viewpager;
    DBAdapter2 myDB2;
    static int area_num;
    String[] hint = {"This is the first of my memory that is still afresh. Childhood memories are always the fondest and they stay longer than other memories.",
            "This area also has a part of my childhood hidden in it. I always had an inclination towards books, people, writings, quotes, words and a lot of things.",
            "I don’t have any good vibes of this place. I am not sure why. Maybe, some incident had happened sometime. I don’t want to remember any part of it. But it’s necessary for decoding the key.",
            "This is one of my favourite places. Some of my favourite memories are stored.",
            "This is one of my favourite hunting grounds. I used to play along this area. Another reason why I remember this is, it was a unique way of someone telling me something.",
            "A play spot when I was a little kid. It was scenic to me then. Just like the ones I used to see in cartoon. I used to climb upon it and used to act out as a driver.",
            "Atop the ground so high, like a Gamla in the sky.",
            "A home to all the people during the fests and other curricular activities.",
            "A pretty place where all the exhibitions took place. I have fond memories of arts and exhibitions from this place.",
            "This is the last step of our journey. Hope we make through it. We enter the administrative area this whole set up. Let’s make it a good one."
    };
    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
    }
    // this is an inner class...
    class RemindTask extends TimerTask {


        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 2) { // In my case the number of pages are 5
                        timer.cancel();
                        // Showing a toast for just testing purpose
                    } else {

                        viewpager.setCurrentItem(page++);
                        if (page == 3)
                            page = 0;
                    }
                }
            });
        }
    }
//    @Nullable @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//            @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_sample_custom_animation, container, false);
//    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
//        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
//        viewpager.setAdapter(new CarousalPagerAdapter());
//        indicator.setViewPager(viewpager);
//        pageSwitcher(2);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sample_custom_animation);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        viewpager.setAdapter(new CarousalPagerAdapter());
        indicator.setViewPager(viewpager);
        myDB2 = new DBAdapter2(this);
        myDB2.open();
        int areaId = getIntent().getIntExtra("areaId",-1);
        String area = getIntent().getStringExtra("area");
        area_num = myDB2.getRow(area).getInt(myDB2.COL_ROWID);
        TextView textView = (TextView) findViewById(R.id.hint);
        textView.setText(hint[areaId]);
        pageSwitcher(1);
    }

    public void status(View view){
        String area = getIntent().getStringExtra("area");


        String botMsg = "";

        if(myDB2.getRow(area).getInt(myDB2.COL_LEVEL)<4){
            if(myDB2.getRow2(area,myDB2.getRow(area).getInt(myDB2.COL_LEVEL)).getInt(myDB2.COL_CORRECT2)==0){
                botMsg = myDB2.getRow2(area,myDB2.getRow(area).getInt(myDB2.COL_LEVEL)).getString(myDB2.COL_QUESTION2);
            }
            else if(myDB2.getRow2(area,myDB2.getRow(area).getInt(myDB2.COL_LEVEL)).getInt(myDB2.COL_SOLVED2)==0){
                botMsg = myDB2.getRow2(area,myDB2.getRow(area).getInt(myDB2.COL_LEVEL)).getString(myDB2.COL_BLNO2);
            }
        }
        else if(myDB2.getRow(area).getInt(myDB2.COL_LEVEL)==4){
            botMsg = myDB2.getRow(area).getString(myDB2.COL_QUESTION);
        }
        else if(myDB2.getRow(area).getInt(myDB2.COL_LEVEL)==5){
            botMsg = myDB2.getRow(area).getString(myDB2.COL_STORY);
        }

        speakOut(botMsg,1);
        Intent intent2 = new Intent(CustomAnimationFragment.this,ChatHeadService.class);
        startService(intent2);

        Intent intent3 = new Intent(CustomAnimationFragment.this,BOT.class);
        intent3.putExtra("botMsg",botMsg);
        intent3.putExtra("level", 1);
        startActivity(intent3);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        myDB2.close();
    }
}
