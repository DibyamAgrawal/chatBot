package cse2016.in.ac.nitrkl.chatbot;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by LENOVO on 23-01-2017.
 */
public class CarousalPagerAdapter extends PagerAdapter {

    private int mSize;

    public CarousalPagerAdapter() {
        mSize = 3;
    }

    public CarousalPagerAdapter(int count) {
        mSize = count;
    }

    @Override public int getCount() {
        return mSize;
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override public Object instantiateItem(ViewGroup view, int position) {
        ImageView imageView = new ImageView(view.getContext());
//        textView.setText(String.valueOf(position + 1));
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.WHITE);
//        textView.setTextSize(48);

        imageView.setImageResource(AreaImages.imageArea1[CustomAnimationFragment.area_num][position]);
        view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    public void addItem() {
        mSize++;
        notifyDataSetChanged();
    }

    public void removeItem() {
        mSize--;
        mSize = mSize < 0 ? 0 : mSize;

        notifyDataSetChanged();
    }
}
