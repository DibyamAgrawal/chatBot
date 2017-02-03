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

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class CustomAnimationFragment extends TtsActivity {
    Timer timer;
    int page = 0;
    ViewPager viewpager;
    DBAdapter2 myDB2;
    static int area_num;

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
        String area = getIntent().getStringExtra("area");
         area_num = myDB2.getRow(area).getInt(myDB2.COL_ROWID);

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
