package cse2016.in.ac.nitrkl.chatbot;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Locale;

/**
 * Created by LENOVO on 31-01-2017.
 */
public class TtsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);
            int pitch= tts.setPitch((float) 5.2);
            int speed= tts.setSpeechRate((float) .75);
//            Voice voice = new Voice();
//            tts.setVoice();
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

//                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    public void speakOut(String s) {
        //String text = "I am a trapped entity in this free world. Tired and wasted are my trials. Trapped inside a world with no memory of what I was is a sick feeling. I need someone to help me out. I want someone to show me the way and lead me out of this trap. It won’t be an easy task for anyone of you but it will surely be a chance worth taking. ";
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

}
