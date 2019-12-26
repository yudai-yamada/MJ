package com.example.mj;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;


public class MenuActivity extends AppCompatActivity{

    static  ViewGroup vg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // TableLayoutのグループを取得
        vg = (ViewGroup)findViewById(R.id.tableLayout);

        //新規登録ボタン
        Button editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
        //日別結果編集ボタン
        Button resultButton = findViewById(R.id.day_button);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ResultActivity.class);
                startActivity(intent);
            }
        });
        //年別結果編集ボタン
        Button yearButton = findViewById(R.id.year_button);
        yearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), YearActivity.class);
                startActivity(intent);
            }
        });
        //生涯結果編集ボタン
        Button totalButton = findViewById(R.id.total_button);
        totalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), TotalActivity.class);
                intent.putExtra("year","");
                startActivity(intent);
            }
        });

        //ユーザ編集ボタン
        Button saveButton = findViewById(R.id.user_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), UserActivity.class);
                startActivity(intent);
            }
        });



    }

}
