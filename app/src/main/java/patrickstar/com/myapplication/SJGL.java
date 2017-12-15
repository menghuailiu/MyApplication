package patrickstar.com.myapplication;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import patrickstar.com.myapplication.db.DBOpenHelper;
import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.model.tb_shopsinfo;


public class SJGL extends Activity {
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/myapplication/";
    private Bitmap bitmap;
    private Uri uri;
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

        public  void initView(){
            //设置导航栏
           toolbar = (Toolbar)findViewById(R.id.toolbar);//获取页面的工具栏

            toolbar.setTitle(R.string.app_name);
            toolbar.setTitleMarginStart(200);
            toolbar.setTitleMarginTop(10);
          //  setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.bac);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //返回首页
                    Intent intent = new Intent(SJGL.this,YGSCateAPP.class);
                    startActivity(intent);
                }
            });
            //tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("2"),"xl","123456","饺子馆","人和食堂","18212322222","img/a.jpeg","8:00 am","");

            //DBShopsinfo db = new DBShopsinfo(SJGL.this);
            //int i =  db.insert(shopsinfo);
            DBShopsinfo db = new DBShopsinfo(SJGL.this);
           // boolean a = db.deleteAll();
          // List li = db.findAll();
          // Toast.makeText(SJGL.this,li.size(),Toast.LENGTH_SHORT);


            tb_shopsinfo tb = db.findbyUserid("xl");
            String str = tb.getUserid();
            final long id =tb.getId();
            name = (TextView)findViewById(R.id.name);
            name.setText(String.valueOf(tb.getSname()));

            image =(ImageView)findViewById(R.id.image) ;
            String a = tb.getPhoto();
           // image.setImageURI(uri.fromFile(new File(ALBUM_PATH + a)));
            final File file = new File(SJGL.this.getFilesDir(),"a.jpeg");
            Toast.makeText(SJGL.this,SJGL.this.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT).show();
            if(file.exists()){
                image.setImageURI(Uri.fromFile(file));
            }


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
                    intent.putExtra("shopid",String.valueOf(id));
                    SJGL.this.startActivity(intent);
                }
            });

    }
    //创建数据库
        /*
         DBOpenHelper helpe=new DBOpenHelper(SJGL.this); //创建数据库
         helpe.getWritableDatabase();
         */

        //添加数据
        /*   tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","川菜馆","人和食堂","18212322222","img/a.jpeg","8:00 am","");

        DBShopsinfo db = new DBShopsinfo(SJGL.this);
       int i =  db.insert(shopsinfo);

        //像商家信息内容并显示
       tb_shopsinfo tb = db.findbyUserid("ljm");
        Log.i("userid",tb.getUserid());
        */


}
