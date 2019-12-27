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

public class ResultActivity extends AppCompatActivity {

    static ResultListAdapter listAdapter;
    static List<Result> resultList = new ArrayList<Result>();
    ListView resultListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultListView = (ListView)findViewById(R.id.resultListView);
        listAdapter = new ResultListAdapter();
        resultListView.setAdapter(listAdapter);
        loadResult();
        //戻るボタン
        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int ListPosition, long id) {
                Result result = resultList.get(ListPosition);
                int re_id = result.getRe_id();
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtra("re_id",re_id);
                startActivity(intent);
            }
        });

    }

    //日別結果読込
    protected void loadResult(){
        resultList.clear();

        // Read
        dbAdapter.open();
        Cursor c = dbAdapter.getResult();

        startManagingCursor(c);

        if(c.moveToFirst()){
            do {
                String users = "";
                for (int i=0;i<5;i++) {
                    String userID = "USER" + String.valueOf(i+1);
                    String name = dbAdapter.getUserName(c.getInt(c.getColumnIndex(userID)));
                    if (!(name.equals(""))){
                        if(i == 0){
                            users = users + name;
                        } else {
                            users = users + "," +name;
                        }
                    }
                }

                    Result result = new Result(
                            c.getInt(c.getColumnIndex(DBAdapter.COL_RE_ID)),
                            c.getString(c.getColumnIndex(DBAdapter.COL_DATE)),
                            c.getString(c.getColumnIndex(DBAdapter.COL_PLACE)),
                            users
                    );
                    resultList.add(result);


            } while(c.moveToNext());
        }

        stopManagingCursor(c);
        dbAdapter.close();

        listAdapter.notifyDataSetChanged();
    }

    private class ResultListAdapter extends BaseAdapter {
        TextView nameText;
        public TextView pointText;
        public TextView idText;

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public Object getItem(int position) {
            return resultList.get(position);
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
                v = inflater.inflate(R.layout.result_row, null);
            }
            final Result result = (Result) getItem(position);
            TextView re_idText = (TextView) v.findViewById(R.id.re_idText);
            TextView dateText = (TextView) v.findViewById(R.id.dateText);
            TextView placeText = (TextView) v.findViewById(R.id.placeText);
            TextView usersText = (TextView) v.findViewById(R.id.usersText);


            re_idText.setText(String.valueOf(result.getRe_id()));
            dateText.setText(result.getDate());
            placeText.setText(result.getPlace());
            usersText.setText(result.getUsers());

            v.setTag(result);

            return v;
        }
    }

}
