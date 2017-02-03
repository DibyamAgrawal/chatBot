package cse2016.in.ac.nitrkl.chatbot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by LENOVO on 31-01-2017.
 */
public class LoginActivity extends AppCompatActivity {

    String code,password="uc2017";
    EditText enterCode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String DEFAULT = "N/A";
    Button enterButton;
    DBAdapter2 myDB;

    //table 1 data
    public static String[] areas ={"area1","area2","area3","area4","area5","area6","area7","area8","area9","area10"};
    String[] questions ={"question1","question2","question3","question4","question5","question6","question7","question8","question9","question10"};
    String[] answers = {"answer1","answer2","answer3","answer4","answer5","answer6","answer7","answer8","answer9","answer10"};
    String[] stories = {"story1","story2","story3","story4","story5","story6","story7","story8","story9","story10"};


//    table 2 data
    public static String[] areas2 ={"area1","area2","area3","area4","area5","area6","area7","area8","area9","area10"};
    String[] questions2 ={"question11","question12","question13","question21","question22","question23","question31","question32","question33","question41","question42","question43","question51","question52","question53","question61","question62","question63","question71","question72","question73","question81","question82","question83","question91","question92","question93","question101","question102","question103"};
    String[] answers2 = {"answer11","answer12","answer13","answer21","answer22","answer23","answer31","answer32","answer33","answer41","answer42","answer43","answer51","answer52","answer53","answer61","answer62","answer63","answer71","answer72","answer73","answer81","answer82","answer83","answer91","answer92","answer93","answer101","answer102","answer103"};
    String[] finalAns2 = {"finalAns11","finalAns12","finalAns13","finalAns21","finalAns22","finalAns23","finalAns31","finalAns32","finalAns33","finalAns41","finalAns42","finalAns43","finalAns51","finalAns52","finalAns53","finalAns61","finalAns62","finalAns63","finalAns71","finalAns72","finalAns73","finalAns81","finalAns82","finalAns83","finalAns91","finalAns92","finalAns93","finalAns101","finalAns102","finalAns103"};
    String[] blno2 = {"blno11","blno12","blno13","blno21","blno22","blno23","blno31","blno32","blno33","blno41","blno42","blno43","blno51","blno52","blno53","blno61","blno62","blno63","blno71","blno72","blno73","blno81","blno82","blno83","blno91","blno92","blno93","blno101","blno102","blno103"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        myDB = new DBAdapter2(this);
        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String uname = sharedPreferences.getString("name", DEFAULT);
        if (uname.equals(password)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        enterButton = (Button)findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterCode= (EditText) findViewById(R.id.enter_game);
                code = enterCode.getText().toString();
                if(code.equals(password)) {
                    editor.putString("name", code);
                    editor.apply();
                    fillDatabase();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    enterCode.setText("");
                    Toast.makeText(getApplicationContext(),"Wrong code",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void fillDatabase() {
        myDB.open();
        for (int i=0;i<10;i++) {
            long l = myDB.insertRow(i,areas[i], questions[i], answers[i], stories[i]);
        }

        for (int i=0;i<30;i++) {
            long l = myDB.insertRow2(i, areas2[i / 3], (i % 3) + 1, questions2[i], answers2[i], blno2[i], finalAns2[i]);
            Log.i("log",l+"");
        }

        myDB.close();
    }

}
