package cse2016.in.ac.nitrkl.chatbot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    public static final int DEFAULT = -1;

    private static ChatArrayAdapter chatArrayAdapter;
    private static ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;
    DBAdapter myDB = new DBAdapter(this);
    DBAdapter myDB2 = new DBAdapter(this);
    static DBAdapter myDB1;
    public static int id = -1;
    public int sid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat);
        myDB1 = new DBAdapter(this);
        myDB.open();
        final int recMemId = getIntent().getIntExtra("recMemId", -1);
        sid = recMemId;
        final String recMemName = getIntent().getStringExtra("recMemName");
        id = recMemId;
        buttonSend = (Button) findViewById(R.id.send);

        listView = (ListView) findViewById(R.id.msgview);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
//            listView.setAdapter(chatArrayAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        final int memId = sharedPreferences.getInt("memId", DEFAULT);
        update(recMemId);
/*            AsyncTask<String, String, String> jsonTask = new JSONTask(new JSONTask.AsyncResponse() {

                @Override
                public void processFinish(String output) {
                    Log.i("result",output);
                }
            }).execute(Query.REFRESH,""+memId );*/

        myDB2.open();
        Cursor cc = myDB2.searchRowConn2(recMemId);
        if (cc.getCount() > 0) {
            myDB2.updateRowConn(cc.getLong(DBAdapter.COL_ROWID), 0);
            cc.close();
        }
        myDB2.close();
        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (chatText.getText().toString().length() > 0) {

                }
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);


        //to scroll the list view to bottom on data change
//            chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
//                @Override
//                public void onChanged() {
//                    super.onChanged();
//                    listView.setSelection(chatArrayAdapter.getCount() - 1);
//                }
//            });
    }

    public static void update(int userId) {
        myDB1.open();
        if (myDB1.searchRowConn(userId)) {
            Cursor c = myDB1.getAllRows("chat_" + userId);
            int senderId;
            String msg;
//            chatArrayAdapter.clear();
            chatArrayAdapter.clear1();
            if (c.getCount() != 0) {
                do {
                    senderId = c.getInt(DBAdapter.COL_SENDER);
                    msg = c.getString(DBAdapter.COL_MESSAGE);
                    if (senderId == userId) {
                        chatArrayAdapter.add(new ChatMessage(false, msg));
                    } else {
                        chatArrayAdapter.add(new ChatMessage(true, msg));
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        myDB1.close();
    }

    private boolean sendChatMessage() {


        chatArrayAdapter.add(new ChatMessage(true, chatText.getText().toString()));
        chatText.setText("");

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB2.open();
        Cursor cc = myDB2.searchRowConn2(sid);
        if (cc.getCount() > 0) {
            myDB2.updateRowConn(cc.getLong(DBAdapter.COL_ROWID), 0);
            cc.close();
        }
        myDB2.close();
        myDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       // inflater.inflate(R.menu.item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
