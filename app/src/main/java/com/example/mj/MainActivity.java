package com.example.mj;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mj.MultiTextWatcher.TextWatcherWithInstance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private int RE_ID;
    private ViewGroup vg ;
    private TextView tableRowNumText;
    //チップ
    private EditText CPRateText;
    private EditText CPText1;
    private EditText CPText2;
    private EditText CPText3;
    private EditText CPText4;
    private EditText CPText5;

    //結果（金額）
    private EditText RateText;
    private TextView amtText1;
    private TextView amtText2;
    private TextView amtText3;
    private TextView amtText4;
    private TextView amtText5;

    //結果（点数）
    private TextView totalText1;
    private TextView totalText2;
    private TextView totalText3;
    private TextView totalText4;
    private TextView totalText5;
    private ViewGroup totalvg ;

    //レート
    private  int Rate;
    private  int CPRate;

    private EditText dayText;
    static DBAdapter dbAdapter;
    private  Button saveButton;
    private EditText userText1;
    private EditText userText2;
    private EditText userText3;
    private EditText userText4;
    private EditText userText5;

    private EditText placeText;
    private EditText umaText;
    private CheckBox tonCheck;
    private CheckBox ariariCheck;
    private CheckBox redCheck;
    private CheckBox okaCheck;
    private EditText costText;

    private Cursor oldCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        RE_ID = intent.getIntExtra("re_id",0);
        if(RE_ID == 0){
            setScreenMain(true);
        }else {
            setScreenMain(false);
        }

    }

    private void setScreenMain(boolean newFlg){
        setContentView(R.layout.activity_edit);
        dbAdapter = new DBAdapter(this);
        // TableLayoutのグループを取得
        vg = (ViewGroup)findViewById(R.id.tableLayout);
        totalvg = (ViewGroup)findViewById(R.id.totalTableLayout);
        Date date = new Date(); // 今日の日付
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = dateFormat.format(date);


        dayText = findViewById(R.id.dayEditText);
        dayText.setText(strDate);
        placeText = findViewById(R.id.holeEditText);
        umaText = findViewById(R.id.umaEditText);
        tonCheck =  findViewById(R.id.checkbox_ton);
        ariariCheck =  findViewById(R.id.checkbox_ariari);
        redCheck =  findViewById(R.id.checkbox_red);
        okaCheck =  findViewById(R.id.checkbox_oka);
        costText = findViewById(R.id.badaiEditText);
        RateText = findViewById(R.id.rateEditText);
        CPRateText = findViewById(R.id.cRateEditText);

        userText1 = findViewById(R.id.EditText1);
        userText2 = findViewById(R.id.EditText2);
        userText3 = findViewById(R.id.EditText3);
        userText4 = findViewById(R.id.EditText4);
        userText5 = findViewById(R.id.EditText5);

        CPText1 = findViewById(R.id.CPEditText1);
        CPText2 = findViewById(R.id.CPEditText2);
        CPText3 = findViewById(R.id.CPEditText3);
        CPText4 = findViewById(R.id.CPEditText4);
        CPText5 = findViewById(R.id.CPEditText5);

        //結果(金額)
        amtText1 = findViewById(R.id.amt1_TextView);
        amtText2 = findViewById(R.id.amt2_TextView);
        amtText3 = findViewById(R.id.amt3_TextView);
        amtText4 = findViewById(R.id.amt4_TextView);
        amtText5 = findViewById(R.id.amt5_TextView);

        dbAdapter.open();
        if (newFlg) {
            RE_ID = getLastID();
        } else {
            oldCursor = dbAdapter.getResultByID(RE_ID);
            oldCursor.moveToFirst();

            dayText.setText(oldCursor.getString(oldCursor.getColumnIndex(DBAdapter.COL_DATE)));
            placeText.setText(oldCursor.getString(oldCursor.getColumnIndex(DBAdapter.COL_PLACE)));
            RateText.setText(String.valueOf(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_RATE))));
            CPRateText.setText(String.valueOf(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_CHIP_RATE))));
            umaText.setText(oldCursor.getString(oldCursor.getColumnIndex(DBAdapter.COL_UMA)));
            costText.setText(String.valueOf(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_COST))));

            if(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_TON)) == 1){tonCheck.setChecked(true);}else{tonCheck.setChecked(false);}
            if(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_ARIARI)) == 1){ariariCheck.setChecked(true);}else{ariariCheck.setChecked(false);}
            if(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_RED)) == 1){redCheck.setChecked(true);}else{redCheck.setChecked(false);}
            if(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_OKA)) == 1){okaCheck.setChecked(true);}else{okaCheck.setChecked(false);}

            CPText1.setText(String.valueOf(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_CHIP1))));
            CPText2.setText(String.valueOf(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_CHIP2))));
            CPText3.setText(String.valueOf(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_CHIP3))));
            CPText4.setText(String.valueOf(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_CHIP4))));
            CPText5.setText(String.valueOf(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_CHIP5))));

            userText1.setText(dbAdapter.getUserName(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_USER1))));
            userText2.setText(dbAdapter.getUserName(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_USER2))));
            userText3.setText(dbAdapter.getUserName(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_USER3))));
            userText4.setText(dbAdapter.getUserName(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_USER4))));
            userText5.setText(dbAdapter.getUserName(oldCursor.getInt(oldCursor.getColumnIndex(DBAdapter.COL_USER5))));

            Cursor rowC = dbAdapter.getResultRowByID(RE_ID);
            int i = 0;
            if(rowC.moveToFirst()){
                do {
                    // 行を追加
                    getLayoutInflater().inflate(R.layout.table_row, vg);
                    tableRowNumText = findViewById(R.id.rowNumTextView);
                    // 文字設定
                    TableRow tr = (TableRow)vg.getChildAt(i+4);
                    ((TextView)(tr.getChildAt(0))).setText(String.valueOf(i+1));

                    ((TextView)(tr.getChildAt(1))).setText(String.valueOf(rowC.getInt(rowC.getColumnIndex(DBAdapter.COL_POINT1))));
                    ((TextView)(tr.getChildAt(2))).setText(String.valueOf(rowC.getInt(rowC.getColumnIndex(DBAdapter.COL_POINT2))));
                    ((TextView)(tr.getChildAt(3))).setText(String.valueOf(rowC.getInt(rowC.getColumnIndex(DBAdapter.COL_POINT3))));
                    ((TextView)(tr.getChildAt(4))).setText(String.valueOf(rowC.getInt(rowC.getColumnIndex(DBAdapter.COL_POINT4))));
                    ((TextView)(tr.getChildAt(5))).setText(String.valueOf(rowC.getInt(rowC.getColumnIndex(DBAdapter.COL_POINT5))));

                    new MultiTextWatcher()
                            .registerEditText( ((EditText)(tr.getChildAt(1))))
                            .registerEditText( ((EditText)(tr.getChildAt(2))))
                            .registerEditText( ((EditText)(tr.getChildAt(3))))
                            .registerEditText( ((EditText)(tr.getChildAt(4))))
                            .registerEditText( ((EditText)(tr.getChildAt(5))))
                            .setCallback(new TextWatcherWithInstance() {
                                @Override
                                public void beforeTextChanged(EditText editText, CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
                                }

                                @Override
                                public void afterTextChanged(EditText editText, Editable editable) {
                                    culRow(editable,(TableRow) editText.getParent());
                                    switch (editText.getId()){
                                        case R.id.EditText1:
                                            setAmt1(editable);
                                            break;
                                        case R.id.EditText2:
                                            setAmt2(editable);
                                            break;
                                        case R.id.EditText3:
                                            setAmt3(editable);
                                            break;
                                        case R.id.EditText4:
                                            setAmt4(editable);
                                            break;
                                        case R.id.EditText5:
                                            setAmt5(editable);
                                            break;
                                    }
                                }
                            });
                    i++;
                }while(rowC.moveToNext());
            }
            setAmt1(RateText.getText());
            setAmt2(RateText.getText());
            setAmt3(RateText.getText());
            setAmt4(RateText.getText());
            setAmt5(RateText.getText());

        }

        dbAdapter.close();


        //チップレート計算
        CPRateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(CPRateText.getText().toString().equals("")){
                    CPRate = 0;
                    return;
                } else{
                    CPRate = Integer.valueOf(CPRateText.getText().toString());
                    setAmt1(CPRateText.getText());
                    setAmt2(CPRateText.getText());
                    setAmt3(CPRateText.getText());
                    setAmt4(CPRateText.getText());
                    setAmt5(CPRateText.getText());
                }

            }
        });
        //レート計算
        RateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(RateText.getText().toString().equals("")){
                    Rate = 0;
                    return;
                } else{
                    Rate = Integer.valueOf(RateText.getText().toString()) * 10;
                    setAmt1(RateText.getText());
                    setAmt2(RateText.getText());
                    setAmt3(RateText.getText());
                    setAmt4(RateText.getText());
                    setAmt5(RateText.getText());
                }
            }
        });


        //チップ計算

        CPText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                setAmt1(s);
            }
        });

        CPText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                setAmt2(s);
            }
        });

        CPText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                setAmt3(s);
            }
        });

        CPText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                setAmt4(s);
            }
        });

        CPText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                setAmt5(s);
            }
        });

        if(newFlg){
            for (int i=0; i<1; i++) {
                // 行を追加
                getLayoutInflater().inflate(R.layout.table_row, vg);
                tableRowNumText = findViewById(R.id.rowNumTextView);
                // 文字設定
                TableRow tr = (TableRow)vg.getChildAt(i+4);
                ((TextView)(tr.getChildAt(0))).setText(String.valueOf(i+1));
                new MultiTextWatcher()
                        .registerEditText( ((EditText)(tr.getChildAt(1))))
                        .registerEditText( ((EditText)(tr.getChildAt(2))))
                        .registerEditText( ((EditText)(tr.getChildAt(3))))
                        .registerEditText( ((EditText)(tr.getChildAt(4))))
                        .registerEditText( ((EditText)(tr.getChildAt(5))))
                        .setCallback(new TextWatcherWithInstance() {
                            @Override
                            public void beforeTextChanged(EditText editText, CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(EditText editText, Editable editable) {
                                culRow(editable,(TableRow) editText.getParent());
                                switch (editText.getId()){
                                    case R.id.EditText1:
                                        setAmt1(editable);
                                        break;
                                    case R.id.EditText2:
                                        setAmt2(editable);
                                        break;
                                    case R.id.EditText3:
                                        setAmt3(editable);
                                        break;
                                    case R.id.EditText4:
                                        setAmt4(editable);
                                        break;
                                    case R.id.EditText5:
                                        setAmt5(editable);
                                        break;
                                }
                            }
                        });
            }
        }


        //ユーザ1検索ボタン
        Button tablerow1_button = findViewById(R.id.tablerow1_button);
        tablerow1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editView = new EditText(MainActivity.this);
                dbAdapter.open();
                Cursor Cr = dbAdapter.getUserList();
                int i = Cr.getCount();

                if(Cr.getCount() > 0){

                    final String[] items = new String[Cr.getCount()];

                    if(Cr.moveToFirst()) {
                        do {
                            int j = Cr.getPosition();
                            items[Cr.getPosition()] = Cr.getString(Cr.getColumnIndex(DBAdapter.COL_USER_NAME));
                        } while (Cr.moveToNext());
                    }

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("名前入力ダイアログ")
                            .setItems(items, new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // whichには選択したリスト項目の順番が入っているので、それを使用して値を取得
                                    String selectedVal = items[which];

                                    // MainActivityのインスタンスを取得
                                    userText1.setText(selectedVal);
                                }
                            })
                            .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }

                dbAdapter.close();
            }
        });

        //ユーザ2検索ボタン
        Button tablerow2_button = findViewById(R.id.tablerow2_button);
        tablerow2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editView = new EditText(MainActivity.this);
                dbAdapter.open();
                Cursor Cr = dbAdapter.getUserList();
                int i = Cr.getCount();

                if(Cr.getCount() > 0){

                    final String[] items = new String[Cr.getCount()];

                    if(Cr.moveToFirst()) {
                        do {
                            int j = Cr.getPosition();
                            items[Cr.getPosition()] = Cr.getString(Cr.getColumnIndex(DBAdapter.COL_USER_NAME));
                        } while (Cr.moveToNext());
                    }

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("名前入力ダイアログ")
                            .setItems(items, new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // whichには選択したリスト項目の順番が入っているので、それを使用して値を取得
                                    String selectedVal = items[which];

                                    // MainActivityのインスタンスを取得
                                    userText2.setText(selectedVal);
                                }
                            })
                            .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }

                dbAdapter.close();
            }
        });
        //ユーザ3検索ボタン
        Button tablerow3_button = findViewById(R.id.tablerow3_button);
        tablerow3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editView = new EditText(MainActivity.this);
                dbAdapter.open();
                Cursor Cr = dbAdapter.getUserList();
                int i = Cr.getCount();

                if(Cr.getCount() > 0){

                    final String[] items = new String[Cr.getCount()];

                    if(Cr.moveToFirst()) {
                        do {
                            int j = Cr.getPosition();
                            items[Cr.getPosition()] = Cr.getString(Cr.getColumnIndex(DBAdapter.COL_USER_NAME));
                        } while (Cr.moveToNext());
                    }

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("名前入力ダイアログ")
                            .setItems(items, new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // whichには選択したリスト項目の順番が入っているので、それを使用して値を取得
                                    String selectedVal = items[which];

                                    // MainActivityのインスタンスを取得
                                    userText3.setText(selectedVal);
                                }
                            })
                            .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }

                dbAdapter.close();
            }
        });
        //ユーザ4検索ボタン
        Button tablerow4_button = findViewById(R.id.tablerow4_button);
        tablerow4_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editView = new EditText(MainActivity.this);
                dbAdapter.open();
                Cursor Cr = dbAdapter.getUserList();
                int i = Cr.getCount();

                if(Cr.getCount() > 0){

                    final String[] items = new String[Cr.getCount()];

                    if(Cr.moveToFirst()) {
                        do {
                            int j = Cr.getPosition();
                            items[Cr.getPosition()] = Cr.getString(Cr.getColumnIndex(DBAdapter.COL_USER_NAME));
                        } while (Cr.moveToNext());
                    }

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("名前入力ダイアログ")
                            .setItems(items, new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // whichには選択したリスト項目の順番が入っているので、それを使用して値を取得
                                    String selectedVal = items[which];

                                    // MainActivityのインスタンスを取得
                                    userText4.setText(selectedVal);
                                }
                            })
                            .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }

                dbAdapter.close();
            }
        });
        //ユーザ5検索ボタン
        Button tablerow5_button = findViewById(R.id.tablerow5_button);
        tablerow5_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editView = new EditText(MainActivity.this);
                dbAdapter.open();
                Cursor Cr = dbAdapter.getUserList();
                int i = Cr.getCount();

                if(Cr.getCount() > 0){

                    final String[] items = new String[Cr.getCount()];

                    if(Cr.moveToFirst()) {
                        do {
                            int j = Cr.getPosition();
                            items[Cr.getPosition()] = Cr.getString(Cr.getColumnIndex(DBAdapter.COL_USER_NAME));
                        } while (Cr.moveToNext());
                    }

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("名前入力ダイアログ")
                            .setItems(items, new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // whichには選択したリスト項目の順番が入っているので、それを使用して値を取得
                                    String selectedVal = items[which];

                                    // MainActivityのインスタンスを取得
                                    userText5.setText(selectedVal);
                                }
                            })
                            .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }

                dbAdapter.close();
            }
        });

        //メニューボタン
        Button returnButton = findViewById(R.id.menu_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //アラートダイアログ表示
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("結果の保存")
                        .setMessage("保存していない結果は破棄されます。\n移動しますか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplication(), MenuActivity.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        //結果保存ボタン
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //アラートダイアログ表示
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("結果の保存")
                        .setMessage("この結果を保存しますか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveResult();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLayoutInflater().inflate(R.layout.table_row, vg);
                TableRow tr = (TableRow)vg.getChildAt(vg.getChildCount() - 1);
                ((TextView)(tr.getChildAt(0))).setText(String.valueOf(vg.getChildCount() - 4));

                new MultiTextWatcher()
                        .registerEditText( ((EditText)(tr.getChildAt(1))))
                        .registerEditText( ((EditText)(tr.getChildAt(2))))
                        .registerEditText( ((EditText)(tr.getChildAt(3))))
                        .registerEditText( ((EditText)(tr.getChildAt(4))))
                        .registerEditText( ((EditText)(tr.getChildAt(5))))
                        .setCallback(new TextWatcherWithInstance() {
                            @Override
                            public void beforeTextChanged(EditText editText, CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(EditText editText, Editable editable) {
                                culRow(editable,(TableRow) editText.getParent());
                                switch (editText.getId()){
                                    case R.id.EditText1:
                                        setAmt1(editable);
                                        break;
                                    case R.id.EditText2:
                                        setAmt2(editable);
                                        break;
                                    case R.id.EditText3:
                                        setAmt3(editable);
                                        break;
                                    case R.id.EditText4:
                                        setAmt4(editable);
                                        break;
                                    case R.id.EditText5:
                                        setAmt5(editable);
                                        break;
                                }

                            }
                        });

            }
        });
    }

    protected  int getLastID(){
        int id = dbAdapter.getLastRe_ID() + 1;
        return id;

    }


    protected void saveResult(){
        ArrayList<String> userArray = new ArrayList<>();
        if (!userText1.getText().toString().equals("")){userArray.add(userText1.getText().toString());}
        if (!userText2.getText().toString().equals("")){userArray.add(userText2.getText().toString());}
        if (!userText3.getText().toString().equals("")){userArray.add(userText3.getText().toString());}
        if (!userText4.getText().toString().equals("")){userArray.add(userText4.getText().toString());}
        if (!userText5.getText().toString().equals("")){userArray.add(userText5.getText().toString());}

        ArrayList<Integer> chipArray = new ArrayList<>();
        if (!CPText1.getText().toString().equals("")){ chipArray.add(Integer.valueOf(CPText1.getText().toString()));}
        if (!CPText2.getText().toString().equals("")){chipArray.add(Integer.valueOf(CPText2.getText().toString()));}
        if (!CPText3.getText().toString().equals("")){chipArray.add(Integer.valueOf(CPText3.getText().toString()));}
        if (!CPText4.getText().toString().equals("")){chipArray.add(Integer.valueOf(CPText4.getText().toString()));}
        if (!CPText5.getText().toString().equals("")){chipArray.add(Integer.valueOf(CPText5.getText().toString()));}
        int ton = 0;
        int ariari = 0;
        int red = 0;
        int oka = 0;

        if (tonCheck.isChecked()) { ton = 1;}
        if (ariariCheck.isChecked()) { ariari = 1;}
        if (redCheck.isChecked()) { red = 1;}
        if (okaCheck.isChecked()) { oka = 1;}
        dbAdapter.open();
        dbAdapter.db.beginTransaction();
        dbAdapter.saveResult(
                userArray,                        //日付r

                dayText.getText().toString(),                      //機種名
                placeText.getText().toString(),   //投資
                Integer.parseInt(RateText.getText().toString()),   //回収
                Integer.parseInt(CPRateText.getText().toString()),
                chipArray,
                ton,
                ariari,
                red,
                oka,
                umaText.getText().toString(),
                Integer.valueOf(costText.getText().toString())
        );
        dbAdapter.db.setTransactionSuccessful();
        dbAdapter.db.endTransaction();

        int Count = vg.getChildCount() - 4;
        String P1="";
        String P2="";
        String P3="";
        String P4="";
        String P5="";
        dbAdapter.db.beginTransaction();
        for (int i = 0; i < Count; i++){
            TableRow tr = (TableRow)vg.getChildAt(i+4);
            String result =((TextView)(tr.getChildAt(1))).getText().toString() ;
            if(!(result.equals(""))) {P1 = result; }
            result =((TextView)(tr.getChildAt(2))).getText().toString() ;
            if(!(result.equals(""))) {P2 = result; }
            result =((TextView)(tr.getChildAt(3))).getText().toString() ;
            if(!(result.equals(""))) {P3 = result; }
            result =((TextView)(tr.getChildAt(4))).getText().toString() ;
            if(!(result.equals(""))) {P4 = result; }
            result =((TextView)(tr.getChildAt(5))).getText().toString() ;
            if(!(result.equals(""))) {P5 = result; }
            dbAdapter.saveRowResult(RE_ID,i+1,P1,P2,P3,P4,P5);
            P1 = "";
            P2 = "";
            P3 = "";
            P4 = "";
            P5 = "";
        }
        dbAdapter.db.setTransactionSuccessful();
        dbAdapter.db.endTransaction();
        dbAdapter.close();

    }

    //User1の成績計算
    private void setAmt1(Editable s){
            try {
                Integer.parseInt(s.toString());
            }catch (NumberFormatException e) {
                return;
            }
        int amtResult;
        int Count = vg.getChildCount() - 4;
        int totalResult = 0;

        //チップ計算
        if (CPRateText.getText().toString().equals("") ||
                CPText1.getText().toString().equals("") ||
                CPText1.getText().toString().equals("+") ||
                CPText1.getText().toString().equals("-")){
            amtResult = 0;
        } else {
            amtResult = CPRate * Integer.valueOf(CPText1.getText().toString());
        }
        //点数計算
        for (int i = 0; i < Count; i++){
            TableRow tr = (TableRow)vg.getChildAt(i+4);
            String result =((TextView)(tr.getChildAt(1))).getText().toString() ;
            if(result.equals("")){

            }else{
                amtResult += Rate * Integer.valueOf(result);
                totalResult += Integer.valueOf(result);
            }
        }

        amtText1.setText(String.valueOf(amtResult));
        TableRow tr = (TableRow)totalvg.getChildAt(0);
        ((TextView)(tr.getChildAt(1))).setText(String.valueOf(totalResult));
    }
    //User2の成績計算
    private void setAmt2(Editable s){
        try {
            Integer.parseInt(s.toString());
        }catch (NumberFormatException e) {
            return;
        }
        int amtResult;
        int Count = vg.getChildCount() - 4;
        int totalResult = 0;

        if (CPRateText.getText().toString().equals("") ||
                CPText2.getText().toString().equals("") ||
                CPText2.getText().toString().equals("+") ||
                CPText2.getText().toString().equals("-")){
            amtResult = 0;
        } else {
            amtResult = CPRate * Integer.valueOf(CPText2.getText().toString());
        }
        for (int i = 0; i < Count; i++){
            TableRow tr = (TableRow)vg.getChildAt(i+4);
            String result =((TextView)(tr.getChildAt(2))).getText().toString() ;
            if(result.equals("")){

            }else{
                amtResult += Rate * Integer.valueOf(result);
                totalResult += Integer.valueOf(result);
            }
        }
        amtText2.setText(String.valueOf(amtResult));
        TableRow tr = (TableRow)totalvg.getChildAt(0);
        ((TextView)(tr.getChildAt(2))).setText(String.valueOf(totalResult));
    }
    //User3の成績計算
    private void setAmt3(Editable s){
        try {
            Integer.parseInt(s.toString());
        }catch (NumberFormatException e) {
            return;
        }
        int amtResult;
        int Count = vg.getChildCount() - 4;
        int totalResult = 0;

        if (CPRateText.getText().toString().equals("") ||
                CPText3.getText().toString().equals("") ||
                CPText3.getText().toString().equals("+") ||
                CPText3.getText().toString().equals("-")){
            amtResult = 0;
        } else {
            amtResult = CPRate * Integer.valueOf(CPText3.getText().toString());
        }
        for (int i = 0; i < Count; i++){
            TableRow tr = (TableRow)vg.getChildAt(i+4);
            String result =((TextView)(tr.getChildAt(3))).getText().toString() ;
            if(result.equals("")){

            }else{
                amtResult += Rate * Integer.valueOf(result);
                totalResult += Integer.valueOf(result);
            }
        }
        amtText3.setText(String.valueOf(amtResult));
        TableRow tr = (TableRow)totalvg.getChildAt(0);
        ((TextView)(tr.getChildAt(3))).setText(String.valueOf(totalResult));
    }
    //User4の成績計算
    private void setAmt4(Editable s){
        try {
            Integer.parseInt(s.toString());
        }catch (NumberFormatException e) {
            return;
        }
        int amtResult;
        int Count = vg.getChildCount() - 4;
        int totalResult = 0;

        if (CPRateText.getText().equals("") ||
                CPText4.getText().toString().equals("") ||
                CPText4.getText().toString().equals("+") ||
                CPText4.getText().toString().equals("-")){
            amtResult = 0;
        } else {
            amtResult = CPRate * Integer.valueOf(CPText4.getText().toString());
        }
        for (int i = 0; i < Count; i++){
            TableRow tr = (TableRow)vg.getChildAt(i+4);
            String result =((TextView)(tr.getChildAt(4))).getText().toString() ;
            if(result.equals("")){

            }else{
                amtResult += Rate * Integer.valueOf(result);
                totalResult += Integer.valueOf(result);
            }
        }
        amtText4.setText(String.valueOf(amtResult));
        TableRow tr = (TableRow)totalvg.getChildAt(0);
        ((TextView)(tr.getChildAt(4))).setText(String.valueOf(totalResult));
    }
    //User5の成績計算
    private void setAmt5(Editable s){
        try {
            Integer.parseInt(s.toString());
        }catch (NumberFormatException e) {
            return;
        }
        int amtResult;
        int Count = vg.getChildCount() - 4;
        int totalResult = 0;

        if (CPRateText.getText().toString().equals("") ||
                CPText5.getText().toString().equals("") ||
                CPText5.getText().toString().equals("+") ||
                CPText5.getText().toString().equals("-")){
            amtResult = 0;
        } else {
            amtResult = CPRate * Integer.valueOf(CPText5.getText().toString());
        }
        for (int i = 0; i < Count; i++){
            TableRow tr = (TableRow)vg.getChildAt(i+4);
            String result =((TextView)(tr.getChildAt(5))).getText().toString() ;
            if(result.equals("")){

            }else{
                amtResult += Rate * Integer.valueOf(result);
                totalResult += Integer.valueOf(result);
            }
        }
        amtText5.setText(String.valueOf(amtResult));
        TableRow tr = (TableRow)totalvg.getChildAt(0);
        ((TextView)(tr.getChildAt(5))).setText(String.valueOf(totalResult));
    }
    //行の合計計算
    private void culRow(Editable s,TableRow tr){
        try {
            Integer.parseInt(s.toString());
        }catch (NumberFormatException e) {
            return;
        }
        int total = 0;
      /*  TableRow tr = (TableRow)vg.getChildAt(row);*/

        for (int i = 1; i < 6; i++){
            String result =((TextView)(tr.getChildAt(i))).getText().toString() ;
            if(result.equals("")){
            }else{
                total += Integer.valueOf(((TextView)(tr.getChildAt(i))).getText().toString() );
            }
        }
        Resources res = getResources();
        if (total != 0){
            tr.setBackgroundColor(Color.RED);
        }else{
            tr.setBackgroundColor(res.getColor(R.color.whiteGreen));
        }


    }

}

