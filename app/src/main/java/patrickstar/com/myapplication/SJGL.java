package patrickstar.com.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.generator.ToMany;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import patrickstar.com.myapplication.db.DBOpenHelper;
import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.db.DBShopsmenu;
import patrickstar.com.myapplication.model.tb_shopsinfo;
import patrickstar.com.myapplication.model.tb_shopsmenu;
import patrickstar.com.myapplication.ViewHolder;


public class SJGL extends AppCompatActivity {
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
    private ImageView img;
    public List<tb_shopsmenu> data;
    public int j = 0;
    public ViewHolder holder = null;//viewholder里面装的有界面上有的所有控件对应的属性//viewholder里面装的有界面上有的所有控件对应的属性
    public LayoutInflater inflater;//解析xml文件  将xml文件转换成为view
    private List<tb_shopsmenu> listData;// 用于装数据用的
    private ListView listView;
    private Button deletebt;
    //private  TextView meid;
    public Long shopid;
    private int longflag = 0;//判断是否长按
    private int muflag = 0;//复选框不显示
    public Button cancel;
    public tb_shopsinfo tb;
    private DBShopsmenu pb;
    public String userid;

   public  Boolean isfirst = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjgl);
        Window window = SJGL.this.getWindow();
//        //取消设置透明状态栏，使contentview内容不再覆盖状态栏
       window.clearFlags((WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS));
       //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

       //设置状态栏颜色
        window.setStatusBarColor(SJGL.this.getResources().getColor(R.color.statusbar1));
        isfirst =true;

        ViewGroup mContentView = (ViewGroup)SJGL.this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if(mChildView != null){
            //注意不是设置ContentView 的FitsSystemWIndows，而是设置ContentView 的第一子View
            //预留出系统的View的空间。
            ViewCompat.setFitsSystemWindows(mChildView,true);
        }


//        tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("2"),"xl","123456","饺子馆","人和食堂","18212322222","img/a.jpeg","8:00 am","");
//
//        DBShopsinfo db = new DBShopsinfo(SJGL.this);
//        int i =  db.insert(shopsinfo);

//           tb_shopsmenu shopmemu = new tb_shopsmenu(Long.parseLong("2"),"2","鱼香肉丝","img/b.jpeg","12元","优惠");
//           DBShopsmenu pb = new DBShopsmenu(SJGL.this);
//           int i =  pb.insert(shopmemu) ;
        // boolean a = db.deleteAll();
        // List li = db.findAll();
        // Toast.makeText(SJGL.this,li.size(),Toast.LENGTH_SHORT);
        initView();
    }

    public void initView() {
        //设置导航栏
        DBShopsinfo db = new DBShopsinfo(SJGL.this);
        pb = new DBShopsmenu(SJGL.this);

       try {
           Intent inte  = getIntent();
           if(inte.getStringExtra("userid")==null){

           }else{
               userid = getIntent().getStringExtra("userid");
               tb = db.findbyUserid(userid);
               String str = tb.getUserid();
               shopid = tb.getId();
           }

           if(inte.getStringExtra("shopid")==null){}
           else{
               shopid =Long.parseLong(getIntent().getStringExtra("shopid"));
               tb = db.shopsinfoById(shopid);
           }

       }catch (Exception ex){
           ex.printStackTrace();
       }
        final long id = shopid;


        toolbar = (Toolbar) findViewById(R.id.toolbar);//获取页面的工具栏
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleMarginStart(170);
        toolbar.setTitleMarginTop(10);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.allback);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回首页
                Intent intent = new Intent(SJGL.this, Login.class);
                startActivity(intent);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                addShopmenu(item);
                return true;
            }
        });



        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        listData = getdata();
        Adapter();

        //长按列表项
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SJGLViewHolder viewHolder = (SJGLViewHolder) view.getTag();
                viewHolder.cb.toggle();
                //在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值
                longflag = 1;
                muflag = 1;
                deletebt.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                Adapter();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SJGLViewHolder viewHolder = (SJGLViewHolder) view.getTag();
                if (longflag == 0) {
                    editShopmenu(viewHolder.id);
                } else {
                    viewHolder.cb.toggle();
                    SJGLAdapter.isSelected.put(i, viewHolder.cb.isChecked());
                }
            }
        });


        // shopid = Long.parseLong(getIntent().getStringExtra("shopid"));

        name = (TextView) findViewById(R.id.name);
        name.setText(String.valueOf(tb.getSname()));

        image = (ImageView) findViewById(R.id.image);
        String a = tb.getPhoto();
        //读取数据库图片
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/";
//
//        File f = new File(path);
//        if (f.exists()) {
//        } else {
//            f.mkdirs();
//        }
//        File file = new File(tb.getPhoto());//创建一个文件对象
//
//
//        if (file.exists()) {
//            Bitmap bm = BitmapFactory.decodeFile(path +tb.getPhoto());
//            //将图片显示到ImageView中
//            image.setImageBitmap(bm);
//        }

        // image.setImageURI(uri.fromFile(new File(tb.getPhoto()))); //注释  罗金美

        String path =tb.getPhoto();
        File file = new File(path);//创建一个文件对象

        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(path);
            //将图片显示到ImageView中
            image.setImageBitmap(bm);
        }
        else{
            Toast.makeText(SJGL.this,"没有图片",Toast.LENGTH_LONG).show();
        }

        address = (TextView) findViewById(R.id.address);
        address.setText(String.valueOf(tb.getAddress()));
        mobile = (TextView) findViewById(R.id.mobile);
        mobile.setText(String.valueOf(tb.getTel()));
        time = (TextView) findViewById(R.id.time);
        time.setText(String.valueOf(tb.getOpentime()));
        remark = (TextView) findViewById(R.id.remark);
        remark.setText(String.valueOf(tb.getRemark()));

        modify = (Button) findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到修改界面

            }
        });
        //meid =(TextView)findViewById(R.id.meid);
        //meid.setText(String.valueOf(tb.getId()));
        deletebt = (Button) findViewById(R.id.delete);
        deletebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < listView.getCount(); i++) {
                    if (SJGLAdapter.isSelected.get(i)) {
                        SJGLViewHolder viewHolder = (SJGLViewHolder) listView.getChildAt(i).getTag();

                        DBShopsmenu db = new DBShopsmenu(SJGL.this);
                        boolean shopsmenu = db.deleteById(viewHolder.id);
                        Intent intent = new Intent(SJGL.this, SJGL.class);
                        intent.putExtra("userid",userid);
                        startActivity(intent);


                        //listData = getdata();
                    }
                }
            }
        });


    }

    public void Adapter() {
        final SJGLAdapter adapter = new SJGLAdapter(SJGL.this, listData, muflag);
        listView = (ListView) this.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public List<tb_shopsmenu> getdata() {
        DBShopsmenu sb = new DBShopsmenu(SJGL.this);
        List<tb_shopsmenu> list = sb.findDataBySHopid(shopid);
        return list;
    }

    //fl新增删除
    private void addShopmenu(MenuItem item) {
        if (item.getTitle().equals("编辑")) {
            Intent intent = new Intent(SJGL.this, SJXG.class);
            intent.putExtra("shopid", String.valueOf(shopid));
            SJGL.this.startActivity(intent);
        } else {
            long id = pb.getMax()+1;
            tb_shopsmenu shopmemu = new tb_shopsmenu(id,shopid.toString(), "", "", "", "");
            Intent intent = new Intent(SJGL.this, tempActivity.class);
            intent.putExtra("shopmenu", shopmemu);
            startActivity(intent);
        }

    }

    //修改菜单
    private void editShopmenu(Long menuid) {
        tb_shopsmenu shopsmenu = pb.find(menuid.intValue());
        Intent intent = new Intent(SJGL.this, tempActivity.class);
        intent.putExtra("shopmenu", shopsmenu);
        startActivity(intent);
    }

    private void cancel() {
        muflag = 0;
        longflag = 0;
        deletebt.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        Adapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop, menu);
         return true;
    }


    //关闭编辑、修改窗口可以刷新数据
    public void onResume(){
        super.onResume();
       if(isfirst = true) {
           listData = getdata();
           // Toast.makeText(SJGL.this,"resume",Toast.LENGTH_LONG).show();
            final SJGLAdapter adapterad = new SJGLAdapter(SJGL.this, listData, muflag);
            listView.setAdapter(adapterad);
            listData = getdata();
            isfirst = false;
            initView();
        }else{

        }
    }

}






