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
    public static String[] areas ={"area1","area2","area3","area4","area5","area6","area7","area8","area9","area10","area11","area12"};
    String[] questions ={"question1","question2","question3","question4","question5","question6","question7","question8","question9","question10","question11","question12"};
    String[] answers = {"answer1","answer2","answer3","answer4","answer5","answer6","answer7","answer8","answer9","answer10","answer11","answer12"};
    String[] stories = {"story1","story2","story3","story4","story5","story6","story7","story8","story9","story10","story11","story12"};


//    table 2 data
    public static String[] areas2 ={"area1","area2","area3","area4","area5","area6","area7","area8","area9","area10","area11","area12"};
    String[] questions2 ={"question1","question2","question3","question4","question5","question6","question7","question8","question9","question10","question11","question12","question1","question2","question3","question4","question5","question6","question7","question8","question9","question10","question11","question12","question1","question2","question3","question4","question5","question6","question7","question8","question9","question10","question11","question12"};
    String[] answers2 = {"answer1","answer2","answer3","answer4","answer5","answer6","answer7","answer8","answer9","answer10","answer11","answer12","answer1","answer2","answer3","answer4","answer5","answer6","answer7","answer8","answer9","answer10","answer11","answer12","answer1","answer2","answer3","answer4","answer5","answer6","answer7","answer8","answer9","answer10","answer11","answer12"};
    String[] finalAns2 = {"finalAns1","finalAns2","finalAns3","finalAns4","finalAns5","finalAns6","finalAns7","finalAns8","finalAns9","finalAns10","finalAns11","finalAns12","finalAns1","finalAns2","finalAns3","finalAns4","finalAns5","finalAns6","finalAns7","finalAns8","finalAns9","finalAns10","finalAns11","finalAns12","finalAns1","finalAns2","finalAns3","finalAns4","finalAns5","finalAns6","finalAns7","finalAns8","finalAns9","finalAns10","finalAns11","finalAns12"};
    String[] blno2 = {"blno1","blno2","blno3","blno4","blno5","blno6","blno7","blno8","blno9","blno10","blno11","blno12","blno1","blno2","blno3","blno4","blno5","blno6","blno7","blno8","blno9","blno10","blno11","blno12","blno1","blno2","blno3","blno4","blno5","blno6","blno7","blno8","blno9","blno10","blno11","blno12"};


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
        for (int i=0;i<12;i++) {
            long l = myDB.insertRow(i,areas[i], questions[i], answers[i], stories[i]);
        }

        for (int i=0;i<36;i++) {
            long l = myDB.insertRow2(i, areas2[i / 3], (i % 3) + 1, questions2[i], answers2[i], blno2[i], finalAns2[i]);
            Log.i("log",l+"");
        }

        myDB.close();
    }

}
