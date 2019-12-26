package com.example.mj;

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

public class TotalActivity extends AppCompatActivity {
    static UserListAdapter listAdapter;
    static List<User> userList = new ArrayList<User>();
    ListView userListView;
    String year = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_total);
        userListView = (ListView)findViewById(R.id.totalListView);
        listAdapter = new UserListAdapter();
        userListView.setAdapter(listAdapter);
        Intent intent = getIntent();
        year = intent.getStringExtra("year");
        loadUser();

        //戻るボタン
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
                Cursor C2 = dbAdapter.getResultByUser(String.valueOf(c.getInt(c.getColumnIndex(DBAdapter.COL_USER_ID))),year);
                int count = dbAdapter.getCountByUser(String.valueOf(c.getInt(c.getColumnIndex(DBAdapter.COL_USER_ID))),year);
                C2.moveToFirst();
                if (count != 0) {
                    User user = new User(
                            c.getInt(c.getColumnIndex(DBAdapter.COL_USER_ID)),
                            c.getString(c.getColumnIndex(DBAdapter.COL_USER_NAME)),
                            String.valueOf(C2.getInt(C2.getColumnIndex("POINT"))),
                            count

                    );
                    userList.add(user);
                }

            } while(c.moveToNext());
        }

        stopManagingCursor(c);
        dbAdapter.close();

        listAdapter.notifyDataSetChanged();
    }

    private class UserListAdapter extends BaseAdapter {
        TextView nameText;
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
            if (v == null) {
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.total_row, null);
            }
            final User user = (User) getItem(position);
            nameText = (TextView) v.findViewById(R.id.nameText);
            pointText = (TextView) v.findViewById(R.id.pointText);
            idText = (TextView) v.findViewById(R.id.idText);
            TextView countText = (TextView) v.findViewById(R.id.countText);
            nameText.setText(user.getName());
            pointText.setText(user.getPoint());
            idText.setText(String.valueOf(user.getID()));
            countText.setText(String.valueOf(user.getCount()));
            v.setTag(R.id.pointText, user);

            return v;
        }
    }
}
