package cse2016.in.ac.nitrkl.chatbot;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

public class MainActivity extends TtsActivity implements AIListener, ListBuddiesLayout.OnBuddyItemClickListener {

    public TextView resultTextView;
    AIDataService aiDataService;
    AIRequest aiRequest;
    private TextToSpeech tts;
    final Context context = this;
    String res, ques;
    DBAdapter2 mydb;
    int pos, flag;
    ListBuddiesLayout listBuddies;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //boolean to_prompt = false;
    String[] questions = {"It is a bachelor's degree in commerce and business administration. The degree is conferred after four years of full-time study in one or more areas of business concentrations.",
            "Instruments that record, analyse, summarise, organise, debate and explain information; that are illustrated, non-illustrated, hardbound, paperback, jacketed, non-jacketed; with foreword, introduction, table of contents, index; that are indented for the enlightenment, understanding, enrichment, enhancement and education of the human brain through sensory route of vision - sometimes touch are kept here.",
            "The art or practice of designing and constructing buildings.",
            "A stomping ground on the national days. A colourful battle front during the fests.",
            "Try and try till you don’t fly high. Or else jump and jump till you don’t hit the bump. All these are bogus things. All you need to do is find the synonym for Hamper and make sure it fits a 2D Sphere.",
            "There is a thing, which has taken the statement, “Slow and Steady” too seriously and hasn’t moved an inch since long. It stays while people go past it all the while. When I was this little kid, I used to play around it. Which is this thing?",
            "High and high, I deal with a sigh. Restricted and barriered this place speaks of tales those are not lies. A high reach tower like it stands and guards the whole of NIT.",
            "Come back home.",
            "I went to one of the best Spring Fest of Eastern India. I was astonished by its infrastructure but I have forgotten about one of its founders. Now when I see NITR, I get some vague memories of that person. He was the principal of Old NITR too.",
            "Physics and Maths coexist. Chemistry and Life Science co survive. Civil being the helping hand in both the case."};
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------
    String[] answers = {"bba", "library", "architecture", "ncc", "basketball", "train", "watertank", "sac", "rajendramishra", "mainbuilding"};
//------------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    @TargetApi(Build.VERSION_CODES.M)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Log.i("valval",Build.VERSION.SDK_INT+":"+Build.VERSION_CODES.LOLLIPOP);
            if (!Settings.canDrawOverlays(this)) {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivity(intent);
            }
        }

        aiDataService = new AIDataService(config);
        aiRequest = new AIRequest();
        aiRequest.setQuery("Give me a clue");
//        ImagesUrls imagesUrls = new ImagesUrls(this);
//        ListBuddiesLayout listBuddies = (ListBuddiesLayout) findViewById(R.id.listbuddies);
//        CircularAdapter adapter = new CircularAdapter(this, getResources().getDimensionPixelSize(R.dimen.image_size1), Arrays.asList(imagesUrls.imageUrls_left1));
//        CircularAdapter adapter2 = new CircularAdapter(this, getResources().getDimensionPixelSize(R.dimen.image_size2), Arrays.asList(imagesUrls.imageUrls_right1));
//        listBuddies.setAdapters(adapter, adapter2);

//        listBuddies.setOnItemClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImagesUrls imagesUrls = new ImagesUrls(this);
        listBuddies = (ListBuddiesLayout) findViewById(R.id.listbuddies);
        CircularAdapter adapter = new CircularAdapter(this, getResources().getDimensionPixelSize(R.dimen.image_size1), Arrays.asList(imagesUrls.imageUrls_left1));
        CircularAdapter adapter2 = new CircularAdapter(this, getResources().getDimensionPixelSize(R.dimen.image_size2), Arrays.asList(imagesUrls.imageUrls_right1));
        listBuddies.setAdapters(adapter, adapter2);
        listBuddies.setOnItemClickListener(this);
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
        if (id == R.id.score) {
            int score = 0;
            int level = 0;
            //calculare score
            Cursor cursor = mydb.getAllRows();
            do {
                level = cursor.getInt(mydb.COL_LEVEL) - 1;
                if (level < 4) {
                    score += level * 20;
                }
                if (level == 4) {
                    score += 100;
                }
            } while (cursor.moveToNext());

            LayoutInflater li = LayoutInflater.from(context);
            View scoreView = li.inflate(R.layout.score, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);


            alertDialogBuilder.setView(scoreView);
            final TextView message = (TextView) scoreView.findViewById(R.id.score);
            message.setText(score + "");

            // set dialog message

            alertDialogBuilder
                    .setCancelable(true)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            return true;
        }
        if (id == R.id.bot) {
            chat();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void chat() {

        Intent intent1 = new Intent(this, ChatHeadService.class);
        startService(intent1);
        Intent intent = new Intent(this, BOT.class);
        startActivity(intent);

    }

//    public int REQUEST_CODE = 3;

//    @TargetApi(Build.VERSION_CODES.M)
//    public void chat(View v) {
//        /** check if we already  have permission to draw over other apps */
//        if (!Settings.canDrawOverlays(this)) {
//            /** if not construct intent to request permission */
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                    Uri.parse("package:" + getPackageName()));
//            /** request permission via start activity for result */
//            startActivityForResult(intent, REQUEST_CODE);
//        } else {
//            chatx();
//        }
//    }

//    @TargetApi(Build.VERSION_CODES.M)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        /** check if received result code
//         is equal our requested code for draw permission  */
//        if (requestCode == REQUEST_CODE) {
////             if so check once again if we have permission
//            if (Settings.canDrawOverlays(this)) {
//                // continue here - permission was granted
//                chatx();
//            }
//        }
//
//    }

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
        pos = position;
        if (buddy == 0) {
            i = position;
            flag = 0;
            res = answers[i];
            ques = questions[i];
        } else {
            flag = 1;
            i = 6 + position;
            res = answers[i];
            ques = questions[i];
        }

        if (mydb.getRow(res).getInt(mydb.COL_LOCK) == 1) {
//            Toast.makeText(this, "buddy:" + buddy + " position:" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CustomAnimationFragment.class);
            intent.putExtra("areaId", i);
            intent.putExtra("area", res);
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
        final TextView message = (TextView) promptsView.findViewById(R.id.textView1);
        message.setText(ques);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message

        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                // result.setText(userInput.getText());
                                String input = userInput.getText().toString();


                                if (input.equals(res)) {
//                                    to_prompt[i]=true;
                                    if (flag == 0)
                                        ImagesUrls.imageUrls_left1[pos] = ImagesUrls.imageUrls_left2[pos];
                                    else
                                        ImagesUrls.imageUrls_right1[pos] = ImagesUrls.imageUrls_right2[pos];

                                    Intent intent = new Intent(MainActivity.this, CustomAnimationFragment.class);
                                    intent.putExtra("areaId", i);
                                    intent.putExtra("area", res);
                                    startActivity(intent);
                                    String botMsg = mydb.getRow2(res, 1).getString(mydb.COL_QUESTION2);


                                    Intent intent2 = new Intent(MainActivity.this, ChatHeadService.class);
                                    startService(intent2);
                                    Intent intent3 = new Intent(MainActivity.this, BOT.class);
                                    intent3.putExtra("botMsg", botMsg);
                                    intent3.putExtra("level", 1);
                                    Log.i("botMsg", botMsg);
                                    startActivity(intent3);
                                    //speakOut(botMsg,1);

//                                    BOT.generateLevel1(botMsg);

                                    mydb.updateLock(res, 1);

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



