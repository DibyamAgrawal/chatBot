package cse2016.in.ac.nitrkl.chatbot;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonElement;
import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AIListener, ListBuddiesLayout.OnBuddyItemClickListener {

    public TextView resultTextView;
    AIDataService aiDataService;
    AIRequest aiRequest;
    private TextToSpeech tts;
    final Context context = this;
    String res,ques ;
    DBAdapter2 mydb;
    int pos,flag;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //boolean to_prompt = false;
    String[] questions ={"1","2","3","4","5","6","7","8","9","10","11","12"};
    String[] answers = {"area1","area2","area3","area4","area5","area6","area7","area8","area9","area10","area11","area12"};
//    boolean[] to_prompt = {false,false,false,false,false,false,false,false,false,false,false,false,};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        tts = new TextToSpeech(this, this);
//        speakOut("Deepika");
        mydb = new DBAdapter2(this);
        mydb.open();
        final AIConfiguration config = new AIConfiguration("6063deb9df104b4a8da4f80367fc9826",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiDataService = new AIDataService(config);
        aiRequest = new AIRequest();
        aiRequest.setQuery("Give me a clue");

        ListBuddiesLayout listBuddies = (ListBuddiesLayout) findViewById(R.id.listbuddies);
        CircularAdapter adapter = new CircularAdapter(this, getResources().getDimensionPixelSize(R.dimen.image_size1), Arrays.asList(ImagesUrls.imageUrls_left));
        CircularAdapter adapter2 = new CircularAdapter(this, getResources().getDimensionPixelSize(R.dimen.image_size2), Arrays.asList(ImagesUrls.imageUrls_right));
        listBuddies.setAdapters(adapter, adapter2);

        listBuddies.setOnItemClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void chatx() {

        Intent intent1 = new Intent(this, ChatHeadService.class);
        startService(intent1);
        Intent intent = new Intent(this, BOT.class);
        startActivity(intent);

    }

    public int REQUEST_CODE = 3;

    @TargetApi(Build.VERSION_CODES.M)
    public void chat(View v) {
        /** check if we already  have permission to draw over other apps */
        if (!Settings.canDrawOverlays(this)) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            chatx();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */
        if (requestCode == REQUEST_CODE) {
//             if so check once again if we have permission
            if (Settings.canDrawOverlays(this)) {
                // continue here - permission was granted
                chatx();
            }
        }

    }

    public void refresh(View view) {
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
                resultTextView.setText("Speech: " + result.getFulfillment().getSpeech());
            }
        }.execute(aiRequest);
    }


    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {
        resultTextView.setText(error.toString());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cse2016.in.ac.nitrkl.chatbot/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cse2016.in.ac.nitrkl.chatbot/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    int i;

    @Override
    public void onBuddyItemClicked(AdapterView<?> parent, View view, int buddy, int position, long id) {
        pos=position;
        if(buddy==0) {
            i = position;
            flag=0;
            res = answers[i];
            ques = questions[i];
        }
        else {
            flag=1;
            i = 7 + position;
            res = answers[i];
            ques = questions[i];
        }

        if (mydb.getRow(res).getInt(mydb.COL_LOCK) == 1) {
            Toast.makeText(this, "buddy:" + buddy + " position:" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CustomAnimationFragment.class);
            startActivity(intent);
        } else {

            alertDialog1();
        }

    }

    public void alertDialog1() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final TextView message = (TextView)promptsView.findViewById(R.id.textView1);
        message.setText(ques);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                               // result.setText(userInput.getText());
                                String input = userInput.getText().toString();

                                if(input.equals(res)){
//                                    to_prompt[i]=true;
                                    if(flag==0)
                                        ImagesUrls.imageUrls_left[pos]= R.drawable.ic_action_send_now;
                                    else
                                        ImagesUrls.imageUrls_right[pos]= R.drawable.ic_action_send_now;

                                    mydb.updateLock(res,1);
                                    Intent intent = new Intent(MainActivity.this, CustomAnimationFragment.class);
                                    startActivity(intent);
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mydb.close();
    }
}



