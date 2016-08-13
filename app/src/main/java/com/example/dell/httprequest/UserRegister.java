package com.example.dell.httprequest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.httprequest.models.User;
import com.example.dell.httprequest.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRegister extends AppCompatActivity {

    Button bntRegister, bntSelectImage;
    ProgressDialog progressDialog;
    private static final String URL_API = "http://khmercode.comeze.com/api/post.php";
    JSONParser parser = new JSONParser();
    ImageView display_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        display_image = (ImageView) findViewById(R.id.image_display);
        bntRegister = (Button) findViewById(R.id.btnregister);
        bntRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Register().execute();
            }
        });

        bntSelectImage = (Button) findViewById(R.id.btnImage);
        bntSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
            }});
    }

    class Register extends AsyncTask<String, String, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UserRegister.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name","Niko"));
            params.add(new BasicNameValuePair("sex","Male"));
            params.add(new BasicNameValuePair("phone","012 345 677"));
            params.add(new BasicNameValuePair("email","niko@gamil.com"));
            params.add(new BasicNameValuePair("date","10/12/1993"));
            JSONObject jsonObject = parser.makeHttpRequest(URL_API,"POST",params);
            try{
                int success = jsonObject.getInt("success");
                if(success==1){
                    return  true;
                }
            }catch (Exception e){
                Log.e("GetJsonError", e.getMessage().toString());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            AlertDialog.Builder alter = new AlertDialog.Builder(UserRegister.this);
            alter.setTitle("Register");
            if(s==true){
                alter.setMessage("Completed");
            }else{
                alter.setMessage("Error");
            }
            alter.show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            display_image.setImageBitmap(bmp);

        }


    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
