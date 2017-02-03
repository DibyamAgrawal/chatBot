package cse2016.in.ac.nitrkl.chatbot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

/**
 * Created by dibya on 18-01-2017.
 */
public class BOT extends TtsActivity {

    private static ChatArrayAdapter chatArrayAdapter;
    private static ListView listView;
    private EditText chatText;
    private ImageButton buttonSend;
    AIDataService aiDataService;
    AIRequest aiRequest;
    static DBAdapter myDB;
    static DBAdapter2 myDB2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        IntentFilter filter = new IntentFilter();
        filter.addAction("cse2016.in.ac.nitrkl.chatbot");
        registerReceiver(receiver, filter);

        myDB = new DBAdapter(this);
        myDB.open();
        myDB2 = new DBAdapter2(this);
        myDB2.open();

        chatText = (EditText) findViewById(R.id.msg);
        buttonSend = (ImageButton) findViewById(R.id.send);
        listView = (ListView) findViewById(R.id.msgview);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        update();

        if(getIntent().getIntExtra("level",0) == 1) {
            String botMsg = getIntent().getStringExtra("botMsg");
            Log.i("botMessage2", botMsg);
            generateLevel1(botMsg);
        }

        final AIConfiguration config = new AIConfiguration("6063deb9df104b4a8da4f80367fc9826",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiDataService = new AIDataService(config);
        aiRequest = new AIRequest();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (chatText.getText().toString().length() > 0) {
                    sendChatMessage();
                }
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView = (TextView) view.findViewById(R.id.msgr);
                String text = textView.getText().toString();
                speakOut(text,1);
            }
        });
    }

    private boolean sendChatMessage() {
        final String userMsg = chatText.getText().toString();
        chatArrayAdapter.add(new ChatMessage(true, userMsg));
        chatText.setText("");

        if(userMsg.toCharArray()[0] == '#'){
            getAnswer(userMsg);
            return true;
        }

        aiRequest.setQuery(userMsg);
        new AsyncTask<AIRequest, Void, AIResponse>() {
            @Override
            protected AIResponse doInBackground(AIRequest... requests) {
                final AIRequest request = requests[0];
                try {
                    final AIResponse response = aiDataService.request(request);
                    return response;
                } catch (AIServiceException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {

                if (aiResponse != null) {
                    // process aiResponse here
                }
                Result result = aiResponse.getResult();
                // Show results in TextView.
                String botMsg = result.getFulfillment().getSpeech();
                speakOut(botMsg,1);

                chatArrayAdapter.add(new ChatMessage(false, botMsg));
                myDB.insertRow(userMsg, botMsg);
            }
        }.execute(aiRequest);

        return true;
    }

    private void getAnswer(String userMsgs) {
        int count=0,total=0;
        String userMsg = userMsgs.substring(1,userMsgs.length());

        Cursor c = myDB2.getAllRows2();
        do {
            total++;
            if(c.getString(myDB2.COL_ANS2).equals(userMsg) && myDB2.getRow(c.getString(myDB2.COL_AREA2)).getInt(myDB2.COL_LOCK)==1 && myDB2.getRow(c.getString(myDB2.COL_AREA2)).getInt(myDB2.COL_LEVEL)==c.getInt(myDB2.COL_LEVEL) && c.getInt(myDB2.COL_CORRECT2)==0){
                myDB2.updateCorrect2(c.getString(myDB2.COL_AREA2), c.getInt(myDB2.COL_LEVEL2), 1);
                String message = c.getString(myDB2.COL_BLNO2);
                botMessage(userMsgs,message);
                break;
            }
            else if(c.getString(myDB2.COL_FINALANS2).equals(userMsg) && c.getInt(myDB2.COL_CORRECT2)==1 && c.getInt(myDB2.COL_SOLVED2)==0){
                String time ="update";
                String message ="";
                myDB2.updateSolved2(c.getString(myDB2.COL_AREA2), c.getInt(myDB2.COL_LEVEL2),1,time);
                if(c.getInt(myDB2.COL_LEVEL2)==3) {
                    message = myDB2.getRow(c.getString(myDB2.COL_AREA2)).getString(myDB2.COL_QUESTION);
                }
                if(c.getInt(myDB2.COL_LEVEL2)<3){
                    message = myDB2.getRow2(c.getString(myDB2.COL_AREA2),c.getInt(myDB2.COL_LEVEL2)+1).getString(myDB2.COL_QUESTION2);
                }
                myDB2.updateLevel(c.getString(myDB2.COL_AREA2), myDB2.getRow(c.getString(myDB2.COL_AREA2)).getInt(myDB2.COL_LEVEL) + 1);
                botMessage(userMsgs,message);
                break;
            }
            else {
                count++;
            }
        }while (c.moveToNext());

        int total2=0;
        Cursor cursor = myDB2.getAllRows();
        do {
            total2++;
            if(cursor.getString(myDB2.COL_FINALANS).equals(userMsg) && cursor.getInt(myDB2.COL_LEVEL)==4 && cursor.getInt(myDB2.COL_SOLVED)==0){
                String time = "update";
                myDB2.updateSolved(cursor.getString(myDB2.COL_AREA),1,time);
                myDB2.updateLevel(cursor.getString(myDB2.COL_AREA), cursor.getInt(myDB2.COL_LEVEL) + 1);
                String message = cursor.getString(myDB2.COL_STORY);
                botMessage(userMsgs,message);
                break;
            }
            else{
                count++;
            }
        }while(cursor.moveToNext());

        if(count==total+total2){
            botMessage(userMsgs,"Try Again");
        }

    }

    private void botMessage(String userMsg,String botMsg) {
        speakOut(botMsg, 1);
        chatArrayAdapter.add(new ChatMessage(false, botMsg));
        myDB.insertRow(userMsg, botMsg);
    }

    public void update() {
        Cursor c = myDB.getAllRows();
        String userMsg, botMsg;
        chatArrayAdapter.clear1();
        if (c.getCount() != 0) {
            do {
                userMsg = c.getString(DBAdapter.COL_USER);
                botMsg = c.getString(DBAdapter.COL_BOT);
                if(userMsg.length()>0) {
                    chatArrayAdapter.add(new ChatMessage(true, userMsg));
                }
                if (botMsg.length()>0) {
                    chatArrayAdapter.add(new ChatMessage(false, botMsg));
                }
            } while (c.moveToNext());
        }
        c.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
        myDB2.close();
    }

    public static boolean isInFront;
    @Override
    public void onResume() {
        super.onResume();
        isInFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isInFront = false;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();

        }
    };

    public void finish() {
        super.finish();
    };

    public void generateLevel1(String botMsg) {
        speakOut(botMsg, 1);
        Log.i("bot",botMsg);
        chatArrayAdapter.add(new ChatMessage(false, botMsg));
        myDB.insertRow("", botMsg);
    }
}
