package com.example.mj;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.mj.MainActivity.dbAdapter;

public class YearActivity extends AppCompatActivity {
    static UserListAdapter listAdapter;
    static List<User> yearList = new ArrayList<User>();
    ListView yearListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_year);
        yearListView = (ListView)findViewById(R.id.yearListView);
        listAdapter = new UserListAdapter();
        yearListView.setAdapter(listAdapter);
        loadYear();

        yearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int ListPosition, long id) {
                User user = yearList.get(ListPosition);
                String year = user.getName();
                Intent intent = new Intent(getApplication(), TotalActivity.class);
                intent.putExtra("year",year);
                startActivity(intent);
            }
        });

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
    protected void loadYear(){
        yearList.clear();

        // Read
        dbAdapter.open();
        Cursor c = dbAdapter.getYearList();

        startManagingCursor(c);

        if(c.moveToFirst()){
            do {
                User user = new User(
                        0,
                        c.getString(c.getColumnIndex("DATES")),
                        "0",
                        c.getInt(c.getColumnIndex("COUNT"))

                );
                yearList.add(user);
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
            return yearList.size();
        }

        @Override
        public Object getItem(int position) {
            return yearList.get(position);
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
                v = inflater.inflate(R.layout.year_row, null);
            }
            final User user = (User) getItem(position);
            nameText = (TextView) v.findViewById(R.id.yearText);
            TextView countText = (TextView) v.findViewById(R.id.countText);
            nameText.setText(user.getName() + " 年");
            countText.setText(String.valueOf(user.getCount()));
            v.setTag(R.id.nameText, user);
            return v;
        }
    }
}


