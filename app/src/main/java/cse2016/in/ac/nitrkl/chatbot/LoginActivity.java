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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LENOVO on 31-01-2017.
 */
public class LoginActivity extends AppCompatActivity {

    EditText name, email, phone;
    Button save;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String DEFAULT = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String uname = sharedPreferences.getString("name", DEFAULT);
        if (!uname.equals(DEFAULT)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        name = (EditText) findViewById(R.id.enter_game);
    }



    public void register(View view) {

        editor.putString("name", name.getText().toString());
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
