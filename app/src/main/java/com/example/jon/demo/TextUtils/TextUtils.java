package com.example.jon.demo.TextUtils;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jon on 2016/2/19.
 */
public class TextUtils {
    public static void parseText(TextView textView,String input){
        textView.setText("");
        String boldStyleRegex = "\\<\\@\\>(.)*\\<\\/\\@\\>";

        String[] retVal = {};
        Pattern pattern = Pattern.compile(boldStyleRegex);
        Matcher matcher = pattern.matcher(input);
        Log.d("data","zhe");
        if(matcher.find()){
            Log.d("data","hhhhhh");
            retVal = input.split("\\<\\/\\@\\>");
            for(int i=0;i<retVal.length;i++){
                if(retVal[i].contains("<@>")) {
                    String[] retVal_1 = {};
                    retVal_1 = retVal[i].split("\\<\\@\\>");
                    textView.append( retVal_1[0]);
                    SpannableString ss = new SpannableString(retVal_1[1]);
                    ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.append(ss);
                }else {
                    textView.append(retVal[i]);
                }
                Log.d("data",retVal[i]);
            }

        }else {
            textView.append(input);
        }


    }
}
