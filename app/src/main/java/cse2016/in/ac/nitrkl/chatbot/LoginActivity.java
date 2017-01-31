package cse2016.in.ac.nitrkl.chatbot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

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

}
