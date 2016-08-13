package com.example.dell.httprequest.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by DELL on 8/11/2016.
 */
public class JSONParser {

    private String json;
    private InputStream inputStream;
    private JSONObject jsonObject;


    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params){
        try{
            if(method=="POST"){
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
            }else  if(method=="GET"){
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String urlParams = URLEncodedUtils.format(params,"UTF-8");
                url += '?'+urlParams;
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line +"\n");
            }
            json = stringBuilder.toString();
            bufferedReader.close();
        }catch (Exception e){
            Log.d("Buffer Error", e.toString());
        }

        try {
            jsonObject = new JSONObject(json);
        }catch (JSONException e){
            Log.d("JsonError", e.toString());
        }

        return  jsonObject;
    }
}
