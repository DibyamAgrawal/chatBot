package cse2016.in.ac.nitrkl.chatbot;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
    private TextToSpeech tts;
    private static ChatArrayAdapter chatArrayAdapter;
    private static ListView listView;
    private EditText chatText;
    private ImageButton buttonSend;
    AIDataService aiDataService;
    AIRequest aiRequest;
    DBAdapter myDB;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        myDB = new DBAdapter(this);
        myDB.open();

        chatText = (EditText) findViewById(R.id.msg);
        buttonSend = (ImageButton) findViewById(R.id.send);
        listView = (ListView) findViewById(R.id.msgview);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        update();

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

//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView = (TextView) view.findViewById(R.id.msgr);
//                String text = textView.getText().toString();
//                speakOut(text);
//            }
//        });
    }

    private boolean sendChatMessage() {
        final String userMsg = chatText.getText().toString();
        aiRequest.setQuery(userMsg);
        chatArrayAdapter.add(new ChatMessage(true, userMsg));
        chatText.setText("");

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
                speakOut(botMsg);

                chatArrayAdapter.add(new ChatMessage(false, botMsg));
                myDB.insertRow(userMsg, botMsg);
            }
        }.execute(aiRequest);

        return true;
    }

    public void update() {
        Cursor c = myDB.getAllRows();
        String userMsg, botMsg;
        chatArrayAdapter.clear1();
        if (c.getCount() != 0) {
            do {
                userMsg = c.getString(DBAdapter.COL_USER);
                botMsg = c.getString(DBAdapter.COL_BOT);
                chatArrayAdapter.add(new ChatMessage(true, userMsg));
                chatArrayAdapter.add(new ChatMessage(false, botMsg));
            } while (c.moveToNext());
        }
        c.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }
}
