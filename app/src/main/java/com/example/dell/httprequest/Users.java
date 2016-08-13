package com.example.dell.httprequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.httprequest.adapter.UserAdapter;
import com.example.dell.httprequest.models.User;
import com.example.dell.httprequest.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Users extends AppCompatActivity {

    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();
    ArrayList<User> userList;
    JSONArray users = null;
    ListView listView ;
    Context context = this;
    private static final String URL_API = "http://khmercode.comeze.com/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        userList = new ArrayList<User>();
        listView = (ListView) findViewById(R.id.listusers);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                TextView id = (TextView) view.findViewById(R.id.id);
                Intent i = new Intent(getApplicationContext(), UserDetail.class);
                i.putExtra("id", id.getText());
                startActivity(i);
            }
        });
        new LoadAllProduct().execute();


    }

    class LoadAllProduct extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Users.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject jsonObject = jsonParser.makeHttpRequest(URL_API,"GET",params);
            try{
                int success = jsonObject.getInt("success");
                if(success==1){
                    users = jsonObject.getJSONArray("users");
                    for (int i = 0 ;i<= users.length();i++){
                        JSONObject object = users.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String sex = object.getString("sex");
                        String password = object.getString("password");
                        String email = object.getString("email");
                        String phone = object.getString("phone");
                        int status = object.getInt("status");
                        String date = object.getString("date");
                        String photo = object.getString("photo");
                        User user = new User(id,name,sex,password,email,phone,status,date,photo);
                        userList.add(user);
                    }
                }else {
                    Log.e("Error", jsonObject.getString("message"));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    UserAdapter adapter = new UserAdapter(context ,userList);
                    listView.setAdapter(adapter);
                }
            });
        }
    }

}
