package com.example.rachitagarwal.fashionapp;

/**
 * Created by Rachit on 7/22/2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class LoginActivity extends Activity implements OnClickListener {
    EditText user, pass;
    private Button bLogin;
    private Button bRegister;
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://192.168.43.47/isro/login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        bLogin = (Button)findViewById(R.id.login);
        bRegister= (Button) findViewById(R.id.register);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                new AttemptLogin().execute();
                break;
            case R.id.register:
                Intent i=new Intent(getBaseContext(),RegisterActivity.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }
    class AttemptLogin extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // here Check for success tag
            int success;

            String user_name = "";
            String user_email = "";

            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // checking  log for json response
                Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Successfully Login!", json.toString());
                    user_name = json.getString("user_name");
                    user_email = json.getString("user_email");

                    //SAVE
                    SharedPreferences ui = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    SharedPreferences.Editor edUi = ui.edit();
                    edUi.putString("user_name", user_name);
                    edUi.putString("user_email", user_email);
                    edUi.commit();

                    Intent ii = new Intent(LoginActivity.this,MainActivity.class);
                    finish();
                    startActivity(ii);
                    return json.getString(TAG_MESSAGE);
                }else{

                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * **/
        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onCancelled() {
        }
    }

}