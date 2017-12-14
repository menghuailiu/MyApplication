package patrickstar.com.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import patrickstar.com.myapplication.db.DBOpenHelper;
import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.model.tb_shopsinfo;

public class SJGL extends AppCompatActivity {
    private  Toolbar toolbar;
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
        }

        //创建数据库
        /*
         DBOpenHelper helpe=new DBOpenHelper(SJGL.this); //创建数据库
         helpe.getWritableDatabase();
         */

        //添加数据
           tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","老昆明火锅","人和食堂","18212322222","img/a.jpeg","8:00 am","");

        DBShopsinfo db = new DBShopsinfo(SJGL.this);
       int i =  db.insert(shopsinfo);

        //像商家信息内容并显示
       tb_shopsinfo tb = db.findbyUserid("ljm");
       // Log.i("userid",tb.getUserid());


}
