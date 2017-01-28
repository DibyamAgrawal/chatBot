package cse2016.in.ac.nitrkl.chatbot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class CustomAnimationFragment extends AppCompatActivity {
    Timer timer;
    int page = 0;
    ViewPager viewpager;
    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }
    // this is an inner class...
    class RemindTask extends TimerTask {


        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 4) { // In my case the number of pages are 5
                        timer.cancel();
                        // Showing a toast for just testing purpose
                    } else {

                        viewpager.setCurrentItem(page++);
                        if (page == 5)
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
        pageSwitcher(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
