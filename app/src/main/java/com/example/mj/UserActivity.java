package com.example.mj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.mj.MainActivity.dbAdapter;

public class UserActivity  extends AppCompatActivity {
    static UserListAdapter listAdapter;
    static List<User> userList = new ArrayList<User>();
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        userListView = (ListView)findViewById(R.id.userListView);
        listAdapter = new UserListAdapter();
        userListView.setAdapter(listAdapter);
        loadUser();

        //結果保存ボタン
        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //日別結果読込
    protected void loadUser(){
        userList.clear();

        // Read
        dbAdapter.open();
        Cursor c = dbAdapter.getUserList();

        startManagingCursor(c);

        if(c.moveToFirst()){
            do {
                Cursor C2 = dbAdapter.getResultByUser(String.valueOf(c.getInt(c.getColumnIndex(DBAdapter.COL_USER_ID))),"");
                C2.moveToFirst();
                User user = new User(
                        c.getInt(c.getColumnIndex(DBAdapter.COL_USER_ID)),
                        c.getString(c.getColumnIndex(DBAdapter.COL_USER_NAME)),
                        String.valueOf(C2.getInt(C2.getColumnIndex("POINT"))),
                        0
                );
                userList.add(user);
            } while(c.moveToNext());
        }

        stopManagingCursor(c);
        dbAdapter.close();

        listAdapter.notifyDataSetChanged();
    }

    private class UserListAdapter extends BaseAdapter{
        EditText nameText;
        public TextView pointText;
        public TextView idText;

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public Object getItem(int position) {
            return userList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if(v==null){
                LayoutInflater inflater =
                        (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.user_row, null);
            }
            final User user = (User) getItem(position);
            nameText = (EditText)v.findViewById(R.id.UserNameText);
            pointText = (TextView)v.findViewById(R.id.pointText);
            idText = (TextView)v.findViewById(R.id.idText);
            nameText.setText(user.getName());
            pointText.setText(user.getPoint());
            idText.setText(String.valueOf(user.getID()));
            v.setTag(R.id.pointText, user);

            Button saveButton = (Button)v.findViewById(R.id.save_button);

            saveButton.setOnClickListener(new SaveListener());
            return v;
        }
        class SaveListener implements View.OnClickListener{
            public void onClick(View v){
                View parentView = (View) v.getParent();
                nameText = (EditText)parentView.findViewById(R.id.UserNameText);
                idText = (TextView)parentView.findViewById(R.id.idText);
                dbAdapter.open();
                dbAdapter.updateUserName(Integer.valueOf(idText.getText().toString()),nameText.getText().toString());
                dbAdapter.close();
                new AlertDialog.Builder(UserActivity.this)
                        .setTitle("変更完了")
                        .setMessage("名前の変更が完了しました")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
    }
}

