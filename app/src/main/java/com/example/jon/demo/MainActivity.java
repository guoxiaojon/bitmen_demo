package com.example.jon.demo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jon.demo.NetUtils.NetUtils;
import com.example.jon.demo.TextUtils.TextUtils;

public class MainActivity extends AppCompatActivity {

    private Button open_Button;
    private Button save_Button;
    private CheckBox bold_italic_button;
    private TextView textView;
    private EditText editText;
    private Handler mHandler = new Handler();
    private String save = "&save&" ;
    private boolean isBold = false;
    private boolean isBoldstart = false;
    private int bold_start;
    private int bold_end;
    private int[][] bold = new int[1024][2];
    private int bold_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = (EditText)findViewById(R.id.edit);

        open_Button = (Button)findViewById(R.id.open_button);
        save_Button = (Button)findViewById(R.id.save_button);
        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetUtils.write2Service(save);
            }
        });
        bold_italic_button = (CheckBox)findViewById(R.id.bold_italic_button);
        textView = (TextView)findViewById(R.id.text);
        open_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String s = NetUtils.getFromService("save");


                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //显示文件
                                TextUtils.parseText(textView, s);
                            }
                        });
                    }
                }).start();
            }
        });

        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBold){
                    save = save + "</@>";
                    bold_italic_button.setChecked(false);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetUtils.write2Service(save);
                    }
                }).start();


            }
        });

        bold_italic_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isBold = isChecked;
                if (isChecked) {
                    bold_count++;
                    isBoldstart = true;

                    save = save + "<@>";

                } else {
                    save = save + "</@>";

                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isBold){
                    if(isBoldstart){
                        bold_start = start;
                        bold[bold_count][0] = bold_start;
                        isBoldstart = false;

                    }

                    bold_end = start+count;
                    bold[bold_count][1] = bold_end;

                }

                save = save + s.subSequence(start,count+start);
                Log.d("data","s--->"+s);
                Log.d("data","start--->"+start);
                Log.d("data","before--->"+before);
                Log.d("data","count--->"+count);

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i = 0;i<bold_count;i++){
                    s.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),bold[i+1][0],bold[i+1][1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }
        });




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
