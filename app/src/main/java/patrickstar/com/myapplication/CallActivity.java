package patrickstar.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/***
 * 拨打电话页面
 */
public class CallActivity extends AppCompatActivity {

    public Button btnguanduan;
    public Button btnjianpan;
    public TextView txtphone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);

        //接收来自商家详细页面的号码
        String value = getIntent().getStringExtra("call");
        txtphone = (TextView) findViewById(R.id.txtphone);
        txtphone.setText(value);

        btnguanduan = (Button) findViewById(R.id.btnguanduan);

        btnjianpan = (Button) findViewById(R.id.bntjianpan);
        //给挂断按钮添加点击事件
        btnguanduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(CallActivity.this,StoreActivity.class);
                startActivity(intent);
                finish();//关闭当前Activity

            }
        });

        //点击键盘，打开键盘
        btnjianpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

}
