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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import patrickstar.com.myapplication.db.DBShopsmenu;
import patrickstar.com.myapplication.model.tb_shopsinfo;
import patrickstar.com.myapplication.model.tb_shopsmenu;
import  patrickstar.com.myapplication.ViewHolder;


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
    private  ImageView  img;
    public List<tb_shopsmenu> data;
    public int j = 0;
    public ViewHolder holder = null;//viewholder里面装的有界面上有的所有控件对应的属性//viewholder里面装的有界面上有的所有控件对应的属性
    public LayoutInflater inflater;//解析xml文件  将xml文件转换成为view
    private List<tb_shopsmenu> listData;// 用于装数据用的
    private ListView listView;
    private  Button deletebt;
    //private  TextView meid;

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
            listData=getdata();

           final SJGLAdapter adapter= new SJGLAdapter(SJGL.this,listData);
           listView=(ListView) this.findViewById(R.id.listView);
           listView.setAdapter(adapter);
           listView.setItemsCanFocus(false);
           listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  SJGLViewHolder viewHolder =(SJGLViewHolder) view.getTag();
                  //在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值
                  viewHolder.cb.toggle();
                  SJGLAdapter.isSelected.put(i,viewHolder.cb.isChecked());
              }
       });

            //tb_shopsinfo shopsinfo = new tb_shopsinfo(Long.parseLong("2"),"xl","123456","饺子馆","人和食堂","18212322222","img/a.jpeg","8:00 am","");

            //DBShopsinfo db = new DBShopsinfo(SJGL.this);
            //int i =  db.insert(shopsinfo);

//           tb_shopsmenu shopmemu = new tb_shopsmenu(Long.parseLong("2"),"2","鱼香肉丝","img/b.jpeg","12元","优惠");
//           DBShopsmenu pb = new DBShopsmenu(SJGL.this);
//           int i =  pb.insert(shopmemu) ;
           // boolean a = db.deleteAll();
          // List li = db.findAll();
          // Toast.makeText(SJGL.this,li.size(),Toast.LENGTH_SHORT);

            DBShopsinfo db = new DBShopsinfo(SJGL.this);
            tb_shopsinfo tb = db.findbyUserid("xl");
            String str = tb.getUserid();
            final long id =tb.getId();
            name = (TextView)findViewById(R.id.name);
            name.setText(String.valueOf(tb.getSname()));

            image =(ImageView)findViewById(R.id.image) ;
            String a = tb.getPhoto();

//            final File files = new File(a);
//            Toast.makeText(SJGL.this,SJGL.this.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT).show();
//            if(files.exists()){
//                image.setImageURI(Uri.fromFile(files));
//            }


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
                Bitmap bm = BitmapFactory.decodeFile(path+"/img/b.jpeg");
                //将图片显示到ImageView中
                image.setImageBitmap(bm);
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
            //meid =(TextView)findViewById(R.id.meid);
            //meid.setText(String.valueOf(tb.getId()));
            deletebt = (Button)findViewById(R.id.delete);
            deletebt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  for(int i=0;i<listView.getCount();i++){
                      if(SJGLAdapter.isSelected.get(i)){
                          SJGLViewHolder viewHolder = (SJGLViewHolder)listView.getChildAt(i).getTag();

                          DBShopsmenu db = new DBShopsmenu(SJGL.this);
                          boolean shopsmenu = db.deleteById(viewHolder.id);

                          listData=getdata();
                          Toast.makeText(SJGL.this,viewHolder.title.getText(),Toast.LENGTH_SHORT).show();
                      }
                  }
                }
            });


    }

    public List<tb_shopsmenu> getdata()
    {
        DBShopsmenu sb = new DBShopsmenu(SJGL.this);
        List<tb_shopsmenu> list = sb.query();


        return list;


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
