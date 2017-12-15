package patrickstar.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.model.tb_shopsinfo;

public class SJXG extends Activity {
    private EditText txtname;
    private EditText txtaddress;
    private EditText txtmobile;
    private EditText txttime;
    private EditText txtremark;
    private Button submit;
    private ImageView image;
    private Button load;
     long id;
    private static DBShopsinfo db ;
    private static tb_shopsinfo tb;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjxg);
        if (db == null){
            db = new DBShopsinfo(SJXG.this);
        }
        if (tb == null){
            Intent intent = getIntent();
            id = Long.parseLong(intent.getStringExtra("shopid"));
            tb = db.shopsinfoById(id);
        }
        initView();
    }
    public void initView()

    {
        txtname = (EditText)findViewById(R.id.txtname);
        txtaddress =(EditText)findViewById(R.id.txtaddress);
        txtmobile =(EditText)findViewById(R.id.txtmobile);
        txttime  =(EditText)findViewById(R.id.txttime);
        txtremark  =(EditText)findViewById(R.id.txtremark );
        image =(ImageView)findViewById(R.id.image);

        txtname.setText(tb.getSname());
        txtaddress.setText(tb.getAddress());
        txtmobile.setText(tb.getTel());
        txttime.setText(tb.getOpentime());
        txtremark.setText(tb.getRemark());

        image =(ImageView)findViewById(R.id.image) ;
        String a = tb.getPhoto();
        // image.setImageURI(uri.fromFile(new File(ALBUM_PATH + a)));
        final File file = new File(SJXG.this.getFilesDir(),"a.jpeg");
        Toast.makeText(SJXG.this,SJXG.this.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT).show();
        if(file.exists()){
            image.setImageURI(Uri.fromFile(file));
        }

        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {

                tb.setId(id);
                tb.setSname(txtname.getText().toString());
                tb.setAddress(txtaddress.getText().toString());
                tb.setTel(txtmobile.getText().toString());
                tb.setOpentime(txttime.getText().toString());
                tb.setRemark(txtremark.getText().toString());
                boolean a =db.update(tb);
                if(a=true)
                {
                    Intent intent = new Intent(SJXG.this,YGSCateAPP.class);
                    startActivity(intent);

                }
            }
        });

        load = (Button)findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("img/*");
                startActivityForResult(intent, 1);
            }
        });
    }
}
