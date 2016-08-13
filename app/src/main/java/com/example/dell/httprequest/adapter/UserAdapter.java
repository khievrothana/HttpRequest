package com.example.dell.httprequest.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.httprequest.R;
import com.example.dell.httprequest.Users;
import com.example.dell.httprequest.models.User;
import com.example.dell.httprequest.utils.ImageDownloaderTask;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 8/11/2016.
 */
public class UserAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<User> users;
    private static LayoutInflater inflater = null;

    public  UserAdapter(Context activity, ArrayList<User> users){
        this.activity = activity;
        this.users = users;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = inflater.inflate(R.layout.row_user,null);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView mail = (TextView) view.findViewById(R.id.email);
            TextView phone = (TextView) view.findViewById(R.id.phone);
            TextView id = (TextView) view.findViewById(R.id.id);
            ImageView imageView = (ImageView) view.findViewById(R.id.photo);

            final User user = users.get(i);
            id.setText(user.getId());
            name.setText(user.getName());
            mail.setText("Email : "+ user.getEmail());
            phone.setText("Tel : " +user.getPhone());

            if(user.getPhoto()!="null" && user.getPhoto()!=null){
                new ImageDownloaderTask(imageView).execute(user.getPhoto());
            }else {
                imageView.setImageResource(R.drawable.photo);
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("URL");
                    builder.setMessage(user.getPhoto());
                    builder.show();
                }
            });

        }
        return view;
    }
}
