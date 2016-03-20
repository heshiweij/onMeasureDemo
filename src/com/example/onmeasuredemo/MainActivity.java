package com.example.onmeasuredemo;

import com.example.onmeasuredemo.view.FlowLayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private FlowLayout mFlLayout;
    private String[] names = {"ÌÆÉ®","ËïÎò¿Õ","Öí°Ë½ä",
    		"É³Îò¾»","ÎÄÊâÆĞÈø","ÈçÀ´·ğ×æ",
    		"¹ÛÒôÆĞÈø","ÑÖÍõ","æÏ¶ğ","Ì«ÉÏÀÏ¾ı"};
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFlLayout = (FlowLayout) findViewById(R.id.fl_layout);
        mFlLayout.setOrientation(LinearLayout.VERTICAL);
        
        for (int i = 0; i < 10; i++){
        	TextView text = new TextView(this);
        	text.setBackgroundColor(Color.RED);
        	text.setText(names[i]);
        	mFlLayout.addView(text);
        }
    }
}
