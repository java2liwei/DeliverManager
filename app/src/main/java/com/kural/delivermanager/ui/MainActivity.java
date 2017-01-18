package com.kural.delivermanager.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kural.delivermanager.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String mUrl = "http://dl.cm.ksmobile.com/static/res/93/a9/web.zip";

    private Button mDownloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadBtn = (Button) findViewById(R.id.btn_download);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_resume).setOnClickListener(this);
        mDownloadBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {


    }

}
