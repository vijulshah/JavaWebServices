package com.variable.javawebservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_name_new;
    private EditText edt_email_new;
    private EditText edt_password_new;
    private EditText edt_confirm_password;
    private Button btn_signUp;
    private Button btn_login;
    private ProgressBar progressBar_SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
        btn_signUp.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void init() {

        edt_name_new = (EditText)findViewById(R.id.edt_name_new);
        edt_email_new = (EditText)findViewById(R.id.edt_email_new);
        edt_password_new = (EditText)findViewById(R.id.edt_password_new);
        edt_confirm_password = (EditText)findViewById(R.id.edt_confirm_password);
        btn_signUp = (Button)findViewById(R.id.btn_signUp);
        btn_login = (Button)findViewById(R.id.btn_login);
        progressBar_SignUp = (ProgressBar)findViewById(R.id.progressBar_SignUp);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login:
            {
                Intent intent = new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            break;

            case R.id.btn_signUp:
            {
                progressBar_SignUp.setVisibility(View.VISIBLE);
                String name = edt_name_new.getText().toString();
                String email_id = edt_email_new.getText().toString();
                String password = edt_password_new.getText().toString();
                String confirmPassword = edt_confirm_password.getText().toString();

                if(!name.trim().equals(null)&&!email_id.equals(null)&&
                 password.length()>=8&&confirmPassword.equals(password))
                {
                    new RegisterUser().execute(name,email_id,password);
                }
                else
                {
                    Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                    progressBar_SignUp.setVisibility(View.GONE);
                }
            }
            break;
        }
    }

    class RegisterUser extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            String response = postDataToServlet(strings);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar_SignUp.setVisibility(View.GONE);
        }
    }

    public String postDataToServlet(String[] values){

        String response="";
        try{

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://IP_ADDRESS:8080/WebServices/SignUp");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("name",values[0]));
            list.add(new BasicNameValuePair("email_id",values[1]));
            list.add(new BasicNameValuePair("password",values[2]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse =  httpClient.execute(httpPost);
            response = readResponse(httpResponse);

            edt_name_new.setText("");
            edt_email_new.setText("");
            edt_password_new.setText("");
            edt_confirm_password.setText("");
            final String finalResponse = response;

            SignUp.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(SignUp.this,""+ finalResponse, Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception e){}

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
