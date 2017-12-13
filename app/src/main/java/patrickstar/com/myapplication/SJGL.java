package patrickstar.com.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import patrickstar.com.myapplication.db.DBOpenHelper;
import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.model.tb_shopsinfo;

public class SJGL extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView name;
    private ImageView image;
    private TextView address;
    private TextView mobile;
    private TextView time;
    private TextView remark;
    private Button modify;
    private ListView listimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjgl);
        initView();
    }

        //设置导航栏
        public  void initView(){
           toolbar = (Toolbar)findViewById(R.id.toolbar);//获取页面的工具栏

            toolbar.setTitle(R.string.app_name);
            toolbar.setTitleMarginStart(200);
            toolbar.setTitleMarginTop(10);
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.bac);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //返回首页
                    Intent intent = new Intent(SJGL.this,YGSCateAPP.class);
                    startActivity(intent);
                }
            });
            DBShopsinfo db = new DBShopsinfo(SJGL.this);
           // db.deleteAll();
          //  tb_shopsinfo tb = db.findbyUserid("ljm");
            //Log.d("userid",tb.getUserid());
          //  tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("2"),"ljm","123456","川菜馆","人和食堂","18212322222","img/a.jpeg","8:00 am","");

          //  DBShopsinfo db = new DBShopsinfo(SJGL.this);
            //int i =  db.insert(shopsinfo);
            tb_shopsinfo tb = db.findbyUserid("ljm");
            name = (TextView)findViewById(R.id.name);
            name.setText(String.valueOf(tb.getSname()));
            address =(TextView)findViewById(R.id.address);
            address.setText(String.valueOf(tb.getAddress()));
            mobile =(TextView)findViewById(R.id.mobile);
            mobile.setText(String.valueOf(tb.getTel()));
            time  =(TextView)findViewById(R.id.time );
            time.setText(String.valueOf(tb.getOpentime()));
            remark  =(TextView)findViewById(R.id.remark );
            remark .setText(String.valueOf(tb.getRemark()));

            modify = (Button)findViewById(R.id.modify);
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳转到修改界面
                    Intent intent = new Intent(SJGL.this,SJXG.class);
                    intent.putExtra("name",name.getText().toString());
                    intent.putExtra("address",address.getText().toString());
                    intent.putExtra("mobile",mobile.getText().toString());
                    intent.putExtra("time",time.getText().toString());
                    intent.putExtra("remark",remark.getText().toString());
                    SJGL.this.startActivity(intent);
                }
            });



            //像商家信息内容并显示
         /*   tb_shopsinfo tb = db.findbyUserid("ljm");
            Log.d("userid",tb.getUserid());*/
        }

        //创建数据库
        /*
         DBOpenHelper helpe=new DBOpenHelper(SJGL.this); //创建数据库
         helpe.getWritableDatabase();
         */

       /* //添加数据
           tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","川菜馆","人和食堂","18212322222","img/a.jpeg","8:00 am","");

        DBShopsinfo db = new DBShopsinfo(SJGL.this);
       int i =  db.insert(shopsinfo);

        //像商家信息内容并显示
       tb_shopsinfo tb = db.findbyUserid("ljm");
        Log.d("userid",tb.getUserid());
        */

}
