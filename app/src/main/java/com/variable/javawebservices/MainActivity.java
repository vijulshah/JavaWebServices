package com.variable.javawebservices;

import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_email;
    private EditText edt_password;
    private Button btn_login;
    private Button btn_signUp;
    private ProgressBar progressBar_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        btn_login.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);
    }

    private void init() {

        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_password = (EditText)findViewById(R.id.edt_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_signUp = (Button)findViewById(R.id.btn_signUp);
        progressBar_Login = (ProgressBar)findViewById(R.id.progressBar_Login);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login:
            {
                progressBar_Login.setVisibility(View.VISIBLE);
                String email_id = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                if(!email_id.trim().equals(null)&&email_id.trim().contains("@"))
                {
                    if(password.trim().length()>8)
                    {
                        new ValidateLogin().execute(email_id,password);
                    }
                    else
                    {
                        Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                        progressBar_Login.setVisibility(View.GONE);
                    }
                }
                else
                {
                    Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
                    progressBar_Login.setVisibility(View.GONE);
                }
            }
            break;

            case R.id.btn_signUp:
            {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
                finish();
            }
            break;
        }
    }

    class ValidateLogin extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            String response = postDataToServlet(strings);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar_Login.setVisibility(View.GONE);
        }
    }

    private String postDataToServlet(String[] paramValues) {

        String response="";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://192.168.0.102:8080/WebServices/Login");

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("email", paramValues[0]));
            list.add(new BasicNameValuePair("password",paramValues[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse =  httpClient.execute(httpPost);

            response = readResponse(httpResponse);
            edt_email.setText("");
            edt_password.setText("");
            final String finalResponse = response;
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(MainActivity.this,""+ finalResponse, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}

        return response;
    }

    public String readResponse(HttpResponse res) {

        InputStream inputStream=null;
        String return_text="";
        try {
            inputStream=res.getEntity().getContent();

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            StringBuffer sb=new StringBuffer("");
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text=sb.toString();

        } catch (Exception e)
        {

        }
        return return_text;

    }
}

