package com.kural.delivermanager.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kural.delivermanager.R;
import com.kural.network.download.DownloadManager;
import com.kural.network.download.bean.DownloadInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDownloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadBtn = (Button) findViewById(R.id.btn_download);
        mDownloadBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                startDownload("http://dl.cm.ksmobile.com/static/res/93/a9/web.zip");
                break;
        }
    }

    private void startDownload (String url) {
        DownloadInfo downloadInfo = new DownloadInfo(url);
        DownloadManager.getInstance().startDownload(downloadInfo, true);
    }
}
