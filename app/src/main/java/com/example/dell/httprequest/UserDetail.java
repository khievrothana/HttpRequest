package com.example.dell.httprequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.httprequest.models.User;
import com.example.dell.httprequest.utils.ImageDownloaderTask;
import com.example.dell.httprequest.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserDetail extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    User user;
    ProgressDialog progressDialog;

    private static final String URL_API = "http://khmercode.comeze.com/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Intent i = getIntent();
        String id = i.getStringExtra("id");
        new LoadDetailUser().execute(id);
    }


    //this class use for load all users data
    class LoadDetailUser extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UserDetail.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... ids) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",ids[0]));
            JSONObject jsonObject= jsonParser.makeHttpRequest(URL_API,"GET",params);
            try{
                int success = jsonObject.getInt("success");
                if(success==1){
                    jsonObject = jsonObject.getJSONObject("user");
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String sex = jsonObject.getString("sex");
                    String password = jsonObject.getString("password");
                    String email = jsonObject.getString("email");
                    String phone = jsonObject.getString("phone");
                    int status = jsonObject.getInt("status");
                    String date = jsonObject.getString("date");
                    String photo = jsonObject.getString("photo");
                    user = new User(id,name,sex,password,email,phone,status,date,photo);
                }else{
                    Log.i("Error", jsonObject.getString("message"));
                }

            }catch (JSONException e){
                Log.i("Error", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            ImageView imageView = (ImageView) findViewById(R.id.photo);
            if(user.getPhoto()!="null" && user.getPhoto()!=null){
                new ImageDownloaderTask(imageView).execute(user.getPhoto());
            }else {
                imageView.setImageResource(R.drawable.photo);
            }
            TextView name = (TextView) findViewById(R.id.name);
            name.setText("Name        : "+user.getName());
            TextView mail = (TextView) findViewById(R.id.email);
            mail.setText("Email       : "+user.getEmail());
            TextView phone = (TextView) findViewById(R.id.phone);
            phone.setText("Tel        : "+user.getPhone());
            TextView sex = (TextView) findViewById(R.id.sex);
            sex.setText("Sex        : "+user.getSex());
            TextView status = (TextView) findViewById(R.id.status);
            status.setText("Status       : "+user.getStatus());
            TextView date = (TextView) findViewById(R.id.date);
            date.setText("Date       : "+user.getDate());

        }
    }
}
