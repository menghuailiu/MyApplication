package patrickstar.com.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.greendao.annotation.Id;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.db.DBShopsmenu;
import patrickstar.com.myapplication.model.tb_shopsinfo;
import patrickstar.com.myapplication.model.tb_shopsmenu;

/***
 *显示商家详细信息，菜单页面
 */
public class StoreActivity extends AppCompatActivity {

    public static final String FLAG = "id";//定义一个常量，用来作为请求码
    String strType = "";//创建字符串，记录类型


    ListView dList;//菜单
    TextView txtname;//商店名字
    TextView txtmoblie;//电话
    TextView txtaddress;//地址
    TextView txttime;//营业时间
    ImageView imgPhoto;//照片
    TextView txtremark;//备注
    Button btnCal;//拨打电话
    Toolbar toolbar;//导航栏


    //菜单list的控件定义
    private List<tb_shopsmenu> listData;// 用于装数据用的
    private Button btnheader;
    private ListView  menuListView;
    private Button btnclick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storedetail);


        //菜单list的内容

        listData = getdata();

        final MyAdaptero adapter=new MyAdaptero(StoreActivity.this,listData);
        menuListView=(ListView)this.findViewById(R.id.detaillist);
        menuListView.setAdapter(adapter);


        //给数据库添加商店信息
//        tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("8"),"al","124456","铁板烧","地址：亨特餐厅","订餐电话：152123298272","img/a.jpeg","营业时间：8:00 am - 10：00 pm","提供免费wifi");
//        DBShopsinfo db = new DBShopsinfo(StoreActivity.this);
//        int i =  db.insert(shopsinfo);
        // 给数据库添加商店信息
//        tb_shopsmenu cookinfo = new tb_shopsmenu(Long.parseLong("15"),"8","香米饭","img/m6.jpeg","3元/份","稻花香米独家烹制");
//        DBShopsmenu db = new DBShopsmenu(StoreActivity.this);
//        int i =  db.insert(cookinfo);

//        DBShopsinfo shopsinfo = new DBShopsinfo(StoreDetail.this);
        //tb_shopsinfo b = shopsinfo.deleteAll(Id);


        //调用DAO层方法，查询商店信息
        String value = getIntent().getStringExtra("id1");
        DBShopsinfo dstore = new DBShopsinfo(StoreActivity.this);
        tb_shopsinfo sb = dstore.find(Integer.valueOf(value));//定义一个变量接收查询到的数据



        dList = (ListView) findViewById(R.id.detaillist);
        txtname = (TextView) findViewById(R.id.storename);
        txtname.setText(String.valueOf(sb.getSname()));

        txtaddress = (TextView) findViewById(R.id.storeaddress);
        txtaddress.setText(String.valueOf(sb.getAddress()));

        txtmoblie = (TextView) findViewById(R.id.storemobile);
        txtmoblie.setText(sb.getTel());
        final String number = txtmoblie.getText().toString();


        txttime = (TextView) findViewById(R.id.storetime);
        txttime.setText(sb.getOpentime());

        txtremark = (TextView) findViewById(R.id.remark);
        txtremark.setText(sb.getRemark());

        imgPhoto =(ImageView)findViewById(R.id.storeimage) ;
        String a = sb.getPhoto();

        //读取数据库图片
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/" ;

        File f = new File(path+"/img");
        if(f.exists()) {
        }
        else
        {
            f.mkdirs();
        }
        File file = new File(path+"/img/a.jpeg");//创建一个文件对象

        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(path+"/img/a.jpeg");
            //将图片显示到ImageView中
            imgPhoto.setImageBitmap(bm);
        }
//        //从模拟器读取图片
//        final File file = new File(StoreActivity.this.getFilesDir(),"imgs/a.jpeg");
//        Toast.makeText(StoreActivity.this,StoreActivity.this.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT).show();
//        if(file.exists()){
//            imgPhoto.setImageURI(Uri.fromFile(file));
//        }

        initView();//调用导航栏

        //点击拨打电话，进入拨号页面
        btnCal = (Button) findViewById(R.id.btnCal);
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });//调用点击方法
    }

    //设置导航栏
    public void initView(){
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
                Intent intent = new Intent(StoreActivity.this,FirstActivity.class);
                startActivity(intent);
            }
        });

    }
    //菜单的list显示调用的getdata方法
    public List<tb_shopsmenu> getdata()
    {
        //Intent intent = this.getIntent();
        //  String value = getIntent().getStringExtra("id1");
        // txtphone = (TextView) findViewById(R.id.txtphone);
        // txtphone.setText(value);
        //String receiveName = _getIntent.getExtras().get(id);
        String value = getIntent().getStringExtra("id1");
        List<tb_shopsmenu> list=new ArrayList<tb_shopsmenu>();
        DBShopsmenu db = new DBShopsmenu(StoreActivity.this);
        List<tb_shopsmenu> listmenu = db.findDataBySHopid(Long.parseLong(value));
        Log.i("-----------------i",String.valueOf(list.size()));

        for(int j = 0 ; j<list.size();j++) {
            tb_shopsmenu t = (tb_shopsmenu) list.get(j);
            Toast.makeText(StoreActivity.this, t.getDishname(), Toast.LENGTH_SHORT);

        }
        //Log.i()
        return listmenu;

    }

}
