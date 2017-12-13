package patrickstar.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SJXG extends AppCompatActivity {
    private TextView txtname;
    private TextView txtaddress;
    private TextView txtmobile;
    private TextView txttime;
    private TextView txtremark;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjxg);
        initView();
    }
    public void initView()

    {
       txtname = (TextView)findViewById(R.id.txtname);
        txtaddress =(TextView)findViewById(R.id.txtaddress);
        txtmobile =(TextView)findViewById(R.id.txtmobile);
        txttime  =(TextView)findViewById(R.id.txttime);
        txtremark  =(TextView)findViewById(R.id.txtremark );
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String mobile = intent.getStringExtra("mobile");
        String time = intent.getStringExtra("time");
        String remark = intent.getStringExtra("remark");

        txtname.setText(name);
        txtaddress.setText(address);
        txtmobile.setText(mobile);
        txttime.setText(time);
        txtremark.setText(remark);



    }
}
