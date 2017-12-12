package patrickstar.com.myapplication;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjgl);
        //创建数据库
         /*DBOpenHelper helpe=new DBOpenHelper(SJGL.this); //创建数据库
         helpe.getWritableDatabase();
*/
        //添加数据
           tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","川菜馆","人和食堂","18212322222","img/a.jpeg","8:00 am","");

        DBShopsinfo db = new DBShopsinfo(SJGL.this);
       int i =  db.insert(shopsinfo);

        //像商家信息内容并显示
       tb_shopsinfo tb = db.findbyUserid("ljm");
        Log.d("userid",tb.getUserid());
    }

}
