package patrickstar.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.model.tb_shopsinfo;

public class DishActivity extends AppCompatActivity {
    private List<tb_shopsinfo> listData;// 用于装数据用的
    private Button btnheader;
    private ListView  mListView;
    private Button btnclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        listData = getdata();

        final MyAdapter adapter=new MyAdapter(DishActivity.this,listData);
        mListView=(ListView)this.findViewById(R.id.listview1);
        mListView.setAdapter(adapter);

   /*    mListView=new ListView(this);
        MyAdapter adapter=new MyAdapter(MainActivity.this,listData);
        View one;
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        one = inflater.inflate(R.layout.headerlayout, null);
         mListView.addHeaderView(one);
       setListAdapter(adapter);*/

    }

    //在List前面添加一个按钮


    public List<tb_shopsinfo> getdata()
    {
        List<tb_shopsinfo> list=new ArrayList<tb_shopsinfo>();
        /*
        tb_shopsinfo person=new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","老昆明火锅","人和食堂","18212322222","img/a.jpeg","8:00 am","");
        list.add(person);

        person=new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","水煮鱼","人和食堂","18212322222","img/a.jpeg","8:00 am","");
        list.add(person);


        person=new tb_shopsinfo(Long.parseLong("1"),"ljm","123456","黄焖鸡","人和食堂","18212322222","img/a.jpeg","8:00 am","");
        list.add(person);

       Map<String,Object> map=new HashMap<String,Object>();
        map.put("title1","刘备");
        map.put("explain","刘备（161年－223年6月10日），字玄德，东汉末年幽州涿郡涿县（今河北省涿州市）人，西汉中山靖王刘胜的后代，三国时期蜀汉开国皇帝、政治家，史家又称他为先主");
        map.put("img",R.drawable.zf);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("title1","关羽");
        map.put("explain","关羽（？－220年），本字长生，后改字云长，河东郡解县（今山西运城）人，东汉末年名将，早期跟随刘备辗转各地，曾被曹操生擒
        ，于白马坡斩杀袁绍大将颜良，与张飞一同被称为万人敌。赤壁之战后，刘备助东吴周瑜攻打南郡曹仁，");
        map.put("img",R.drawable.gy);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("title1","貂蝉");
        map.put("explain","貂蝉是历史小说《三国演义》中的人物，是中国古代四大美女之一。在民间传说中她原名任红昌，是山西一村姑，也有人认为吕布部将秦宜禄前妻杜氏（杜秀娘）即是貂蝉。");
        map.put("img",R.drawable.gy);
        list.add(map);*/
        DBShopsinfo db = new DBShopsinfo(DishActivity.this);
        List<tb_shopsinfo> list1 = db.findAll();
        return list1;



    }
}
