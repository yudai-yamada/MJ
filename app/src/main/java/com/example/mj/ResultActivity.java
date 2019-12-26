package com.example.mj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    static ResultListAdapter listAdapter;
    static List<Result> userList = new ArrayList<Result>();
    ListView resultListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //戻るボタン
        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private class ResultListAdapter extends BaseAdapter {
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
