package cse2016.in.ac.nitrkl.chatbot;

import android.content.Context;

/**
 * Created by dibya on 23-01-2017.
 */
public class ImagesUrls {
    DBAdapter2 myDB;

    public final static Integer[] imageUrls_left1 = new Integer[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f


    };

    public final static Integer[] imageUrls_left2 = new Integer[]{
            R.drawable.aa,
            R.drawable.bb,
            R.drawable.cc,
            R.drawable.dd,
            R.drawable.ee,
            R.drawable.ff
    };
    public final static Integer[] imageUrls_left3 = new Integer[]{
            R.drawable.aaa,
            R.drawable.bbb,
            R.drawable.ccc,
            R.drawable.ddd,
            R.drawable.eee,
            R.drawable.fff
    };

    public final static Integer[] imageUrls_right1 = new Integer[]{
            R.drawable.g,
            R.drawable.h,
            R.drawable.i,
            R.drawable.j

    };

    public final static Integer[] imageUrls_right2 = new Integer[]{
            R.drawable.gg,
            R.drawable.hh,
            R.drawable.ii,
            R.drawable.jj
    };

    public final static Integer[] imageUrls_right3 = new Integer[]{
            R.drawable.ggg,
            R.drawable.hhh,
            R.drawable.iii,
            R.drawable.jjj

    };

    public ImagesUrls(Context context) {
        super();
        myDB = new DBAdapter2(context);
        myDB.open();


        for(int i=0;i<10;i++){
            if(myDB.getRow(LoginActivity.areas[i]).getInt(myDB.COL_LOCK)==1){
                if (i < 6){
                    imageUrls_left1[i] = imageUrls_left2[i];
                }
                else{
                    imageUrls_right1[i-6] = imageUrls_right2[i-6];
                }
            }
            if(myDB.getRow(LoginActivity.areas[i]).getInt(myDB.COL_SOLVED)==1){
                if (i < 6){
                    imageUrls_left1[i] = imageUrls_left3[i];
                }
                else{
                    imageUrls_right1[i-6] = imageUrls_right3[i-6];
                }
            }
        }

        myDB.close();
    }



//    public final static String[] imageUrls_left = new String[]{
//            "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
//            "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
//            "https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg",
//            "https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s1024/Antelope%252520Butte.jpg",
//            "https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s1024/Antelope%252520Hallway.jpg",
//            "https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg",
//            "https://lh6.googleusercontent.com/-UBmLbPELvoQ/URqucCdv0kI/AAAAAAAAAbs/IdNhr2VQoQs/s1024/Apre%2525CC%252580s%252520la%252520Pluie.jpg"
//    };


//    public final static String[] imageUrls_right = new String[]{
//            "https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg",
//            "https://lh5.googleusercontent.com/-bvmif9a9YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s1024/Bee%252520and%252520Flower.jpg",
//            "https://lh5.googleusercontent.com/-n7mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s1024/Bonzai%252520Rock%252520Sunset.jpg",
//            "https://lh6.googleusercontent.com/-4CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s1024/Caterpillar.jpg",
//            "https://lh3.googleusercontent.com/-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s1024/Chess.jpg"
//
//    };




}
