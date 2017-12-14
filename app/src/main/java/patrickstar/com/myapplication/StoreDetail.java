package patrickstar.com.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.db.DBShopsmenu;
import patrickstar.com.myapplication.model.tb_shopsinfo;
import patrickstar.com.myapplication.model.tb_shopsmenu;

/***
 *显示商家详细信息，菜单页面
 */
public class StoreDetail extends AppCompatActivity {

    public static final String FLAG = "id";//定义一个常量，用来作为请求码
    String strType = "";//创建字符串，记录类型


    ListView dList;//菜单
    TextView txtname;//商店名字
    TextView txtmoblie;//电话
    TextView txtaddress;//地址
    TextView txttime;//营业时间
    ImageView imgPhoto;//照片
    TextView txtremark;//备注
    Button btnCall;//拨打电话
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


        //查询菜单
//        int iii=0;
//        DBShopsmenu db = new DBShopsmenu(StoreDetail.this);
//        List<tb_shopsmenu> list = db.findDataBySHopid(Long.parseLong("3"));
//        Log.i("-----------------i",String.valueOf(list.size()));
//
//        for(int j = 0 ; j<list.size();j++) {
//            tb_shopsmenu t = (tb_shopsmenu) list.get(j);
//            Toast.makeText(StoreDetail.this, t.getDishname(), Toast.LENGTH_SHORT);
//
//        }



        //菜单list的内容

        listData = getdata();

        final MyAdaptero adapter=new MyAdaptero(StoreDetail.this,listData);
        menuListView=(ListView)this.findViewById(R.id.detaillist);
        menuListView.setAdapter(adapter);







        //给数据库添加商店信息
       // tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("6"),"al","124456","麻辣小龙虾","地址：人和食堂","订餐电话：152123298272","img/a.jpeg","营业时间：8:00 am - 10：00 pm","提供免费wifi");
      //  DBShopsinfo db = new DBShopsinfo(StoreDetail.this);
       // int i =  db.insert(shopsinfo);
       // 给数据库添加商店信息
//        tb_shopsmenu cookinfo = new tb_shopsmenu(Long.parseLong("7"),"7","美味小面","img/m3.jpeg","8元/份","");
//        DBShopsmenu db = new DBShopsmenu(StoreDetail.this);
//        int i =  db.insert(cookinfo);
//        DBShopsinfo shopsinfo = new DBShopsinfo(StoreDetail.this);
//        //tb_shopsinfo b = shopsinfo.deleteAll(Id);


        //调用DAO层方法，查询商店信息
        DBShopsinfo dstore = new DBShopsinfo(StoreDetail.this);
        tb_shopsinfo sb = dstore.find(6);//定义一个变量接收查询到的数据



        dList = (ListView) findViewById(R.id.detaillist);
        txtname = (TextView) findViewById(R.id.storename);
        txtname.setText(String.valueOf(sb.getSname()));

        txtaddress = (TextView) findViewById(R.id.storeaddress);
        txtaddress.setText(String.valueOf(sb.getAddress()));

        txtmoblie = (TextView) findViewById(R.id.storemobile);
        txtmoblie.setText(sb.getTel());

        txttime = (TextView) findViewById(R.id.storetime);
        txttime.setText(sb.getOpentime());

        txtremark = (TextView) findViewById(R.id.remark);
        txtremark.setText(sb.getRemark());

        imgPhoto =(ImageView)findViewById(R.id.storeimage) ;
        String a = sb.getPhoto();

        //从模拟器读取图片
        final File file = new File(StoreDetail.this.getFilesDir(),"imgs/a.jpeg");
        Toast.makeText(StoreDetail.this,StoreDetail.this.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT).show();
        if(file.exists()){
            imgPhoto.setImageURI(Uri.fromFile(file));
        }

        initView();//调用导航栏
       // ShowInfo(R.id.detaillist);//调用ShowInfo方法，显示菜单信息

        //点击拨打电话，进入拨号页面
        btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(StoreDetail.this,CallActivity.class);
                startActivity(intent);
                finish();

            }
        });


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
                Intent intent = new Intent(StoreDetail.this,YGSCateAPP.class);
                startActivity(intent);
            }
        });

    }
    //定义ShowInfo方法，通过上一页面传来的商店id查询数据
//    private void ShowInfo(int storeid) {
//        String[] strInfos = null;//定义字符串数组，用来接存储商店详细信息
//        ArrayAdapter<String> arrayAdapter = null;
//        strType = "btninfo";//为strType赋值
//
//
//        //调用dao类，实例化对象
//        DBShopsmenu list = new DBShopsmenu(StoreDetail.this);
//
//        //获取所有菜单信息，并存储到List泛型集合中
//        List<tb_shopsmenu> listinfos = list.findDataBySHopid(Long.parseLong("5"));
//        strInfos = new String[listinfos.size()];
//        int m = 0;
//        for(tb_shopsmenu tb:listinfos)
//        {
//            //将菜单信息组合成一个字符串，存储到字符串数组的相应位置
//            strInfos[m] = tb.getPhoto()+"||"+tb.getDishname()+"|"+tb.getPrice()+"|"+tb.getRemark();
//            m++;
//
//        }
//
//        //使用字符串数组初始化ArrayAdaper对象
////        arrayAdapter = new ArrayAdapter<String>(this,strInfos,R.layout.storeitem,
////                new String[]{"","","",""},
////                new int[]{R.id.imgcai,R.id.txtcname,R.id.txtcprice,R.id.txtcremack});
//        arrayAdapter = new ArrayAdapter<String>(this,R.layout.storeitem,strInfos);
//        dList.setAdapter(arrayAdapter);//为ListView列表设置数据源
//
//
//        //点击指定项时，打开详细信息(点击订餐打开拨号界面)
//        dList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
  //  }


    //菜单的list显示调用的getdata方法

    public List<tb_shopsmenu> getdata()
    {
        List<tb_shopsmenu> list=new ArrayList<tb_shopsmenu>();
        DBShopsmenu db = new DBShopsmenu(StoreDetail.this);
        List<tb_shopsmenu> listmenu = db.findDataBySHopid(Long.parseLong("1"));
        Log.i("-----------------i",String.valueOf(list.size()));

        for(int j = 0 ; j<list.size();j++) {
            tb_shopsmenu t = (tb_shopsmenu) list.get(j);
            Toast.makeText(StoreDetail.this, t.getDishname(), Toast.LENGTH_SHORT);

        }
        //Log.i()
        return listmenu;

    }

}
