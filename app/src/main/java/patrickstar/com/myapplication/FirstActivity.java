package patrickstar.com.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import patrickstar.com.myapplication.db.DBOpenHelper;
import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.model.tb_shopsinfo;

public class FirstActivity extends AppCompatActivity {
    public static final String FLAG = "id";//定义一个常量用来作为请求码，传值给下一个页面

    //list控件的定义
    private List<tb_shopsinfo> listData;// 用于装数据用的
    private Button btnheader;
    private ListView  mListViewt;
    private Button btnclick;


    //模糊搜索
    private String[] mStrs = {"aaa","fhfh","u7tut"};
    private SearchView mSearchView;
    private ListView mListView;

    //菜品陈列


    //下拉搜
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    //图片轮播
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.dish2,
            R.drawable.dish3,
            R.drawable.dish4,
            R.drawable.dish5,
            R.drawable.dish6
    };
    //存放图片的标题
    private String[]  titles = new String[]{
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
  //  private ImageView pic;
  //  private  TextView name;
  //  private TextView optime;

    //数据列


  //  List personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        /*tb_shopsinfo shopsinfo1 = new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","会珍堂","人和食堂","18212322222","img/a.jpeg","8:00 am","");

        DBShopsinfo db = new DBShopsinfo(FirstActivity.this);
        int n =  db.insert(shopsinfo1);
        tb_shopsinfo tb = db.findbyUserid("ljm");*/
         //Log.i("userid",tb.getUserid());



        listData=getdata();

      final MyAdapter adapter1=new MyAdapter(FirstActivity.this,listData);
        mListViewt=(ListView)this.findViewById(R.id.listviewd);
        mListViewt.setAdapter(adapter1);


        //点击商店信息，进入到商店详细页面
        mListViewt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String shopsInfo = String.valueOf(((TextView)view).getText());//记录商店信息
                String shopsid = shopsInfo.substring(0,shopsInfo.indexOf('|'));//截取商店id
                Toast.makeText(FirstActivity.this,shopsid,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FirstActivity.this,StoreDetail.class);
                intent.putExtra(FLAG,new String(shopsid));
                startActivity(intent);
            }
        });



/*
        personList = new ArrayList();


        //把数据库的数据查询出来
        //在这里写成this，是由于在MyOpenHelper的super构造器中，已经写死了另外三个参数；
        DBOpenHelper oh = new DBOpenHelper(this);
        SQLiteDatabase db =  oh.getWritableDatabase();
        Cursor cursor = db.query("tb_shopsinfo", null, null, null, null, null, null, null,null);
        while(cursor.moveToNext()){
            String _id = cursor.getString(0);
            String name = cursor.getString(1);
            String salary = cursor.getString(2);
            String phone = cursor.getString(3);

            //把这几个值封装在一个类中，这种思想要学会；由于p在这里是一局部变量，所以定义了
            //一个List的全局变量的容器去存放Person类型的变量p;关键学会别人的这种思想；
            tb_shopsinfo p = new tb_shopsinfo(_id, name, phone, salary);
            personList.add(p);
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        //把数据显示至屏幕
        for (tb_shopsinfo p : personList) {
            //注意，TextView除了在layout里边布局之外，也可以单独new出来，
            //因为其也是一个类，是View类下边的一个子类，只是此时的TextView
            //和layout还没有关联起来，所以记得加上第3步
            //1.集合中每有一条元素，就new一个textView
            TextView tv = new TextView(this);
            //2.把人物的信息设置为文本框的内容
            tv.setText(p.toString());
            tv.setTextSize(18);
            //设置完上述两条语句并不会把TextView显示在界面上，
            //所以需要第三步，将其与layout关联起来；
            //3.把textView设置为线性布局的子节点
            ll.addView(tv);
        }

*/
/*

       int iii=0;
       DBShopsinfo db = new DBShopsinfo(FirstActivity.this);
        List<tb_shopsinfo> list = db.findAll();
       Log.i("-----------------i",String.valueOf(list.size()));

        for(int j = 0 ; j<list.size();j++){
            tb_shopsinfo t = (tb_shopsinfo) list.get(j);
            Toast.makeText(FirstActivity.this,t.getUserid(),Toast.LENGTH_SHORT);
            String st= t.getPwd();*/

           /* ImageView pic = (ImageView)findViewById(R.id.pic);
           TextView name =(TextView)findViewById(R.id.name);
            TextView optime = (TextView)findViewById(R.id.optime);
            name.setText(t.getSname().toString());
            optime.setText(t.getOpentime().toString());*/

       // }

       // LinearLayout item_good_count0=(LinearLayout) findViewById(R.id.item_good_count0);
        //把数据显示到屏幕
        /*for(tb_shopsinfo p:list)
        {
            //1.集合中每有一条数据，就new一个TextView
            TextView tv=new TextView(this);
            //2.把人物的信息设置为文本的内容
            tv.setText(p.toString());

            tv.setTextSize(18);
            //3.把TextView设置成线性布局的子节点
            item_good_count0.addView(tv);
            for(int m = 0;m <=list.size();m++){
                TextView name =(TextView)findViewById(R.id.name);
                TextView optime = (TextView)findViewById(R.id.optime);
                name.setText(p.getSname().toString());
                optime.setText(p.getOpentime().toString());
            }
        }*/

        //下拉
        spinner = (Spinner) findViewById(R.id.spinner);

        //数据
        data_list = new ArrayList<String>();
        data_list.add("北京");
        data_list.add("上海");
        data_list.add("广州");
        data_list.add("深圳");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);

        //模糊搜索
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<String >(this, android.R.layout.simple_list_item_1, mStrs));
        mListView.setTextFilterEnabled(true);

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    mListView.setFilterText(newText);
                }else{
                    mListView.clearTextFilter();
                }
                return false;
            }
        });




        //图片轮播
        mViewPaper = (ViewPager) findViewById(R.id.vp);

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        title = (TextView) findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dish2);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dish3);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     * 自定义Adapter
     * @author liuyazhuang
     *
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_first,menu);
        return true;
    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }







    }

    public List<tb_shopsinfo> getdata()
    {
        /*List<tb_shopsinfo> list=new ArrayList<tb_shopsinfo>();
        tb_shopsinfo person=new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","老昆明火锅","人和食堂","18212322222","img/a.jpeg","8:00 am","");
        list.add(person);

        person=new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","水煮鱼","人和食堂","18212322222","img/a.jpeg","8:00 am","");
        list.add(person);


        person=new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","黄焖鸡","人和食堂","18212322222","img/a.jpeg","8:00 am","");
        list.add(person);*/


//        //从模拟器读取图片
//        final File file = new File(FirstActivity.this.getFilesDir(),"imgs/a.jpeg");
//        Toast.makeText(FirstActivity.this,FirstActivity.this.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT).show();
//        if(file.exists()){
//            imgPhoto.setImageURI(Uri.fromFile(file));
//        }

        DBShopsinfo db = new DBShopsinfo(FirstActivity.this);
        List<tb_shopsinfo> list = db.findAll();
        return list;

    }
}
