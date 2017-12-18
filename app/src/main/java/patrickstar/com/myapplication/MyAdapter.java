package patrickstar.com.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import patrickstar.com.myapplication.db.DBOpenHelper;
import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.model.tb_shopsinfo;

import java.io.File;
import java.util.List;

/**
 * Created by ios18 on 17/12/14.
 */

public class MyAdapter extends BaseAdapter {

private  Context context1;


    public List<tb_shopsinfo> data;
    public int j = 0;
    public ViewHolder holder = null;//viewholder里面装的有界面上有的所有控件对应的属性//viewholder里面装的有界面上有的所有控件对应的属性

    public LayoutInflater inflater;//解析xml文件  将xml文件转换成为view


    public MyAdapter(Context context, List<tb_shopsinfo> ListData) {
        this.inflater = LayoutInflater.from(context);//解析context转换成view  setcontentview也有调用到LayoutInflater
        data = ListData;
        Log.i("数据", String.valueOf(data.size()));
        context1 = context;
//digh
    }
//dsgcjs

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //这是将数据绑定到view上面的方法  如果有循环，那就是一条记录调用一回
        //ViewHolder holder=null;//viewholder里面装的有界面上有的所有控件对应的属性
        if (view == null)//因为方法可能被重复调用，如果重复调用，那view 里面就有了viewholder的模版，不需要重新再定义了，可以提高效率
        {
            holder = new ViewHolder();//实例化ViewHolder
            view = inflater.inflate(R.layout.itemtest, null);//解析activity_main 界面
            //  holder.cb=(CheckBox)view.findViewById(R.id.cb);
            holder.img = (ImageView) view.findViewById(R.id.img); //使用ViewHOlder绑定view中的控件
            holder.explain = (TextView) view.findViewById(R.id.explain);
            holder.title1 = (TextView) view.findViewById(R.id.title1);
           // holder.cb = view.findViewById(R.id.cb);
            holder.shopid = view.findViewById(R.id.shopid);
            holder.id = view.findViewById(R.id.id);
            view.setTag(holder);//在view中使用tag标签存储

        } else {
            holder = (ViewHolder) view.getTag();//如果是有值就直接从view中获取
        }


        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/" +data.get(i).getPhoto();
        //imgpath=path;
        File file = new File(path);//创建一个文件对象

        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(path);
            //将图片显示到ImageView中
            holder.img.setImageBitmap(bm);
        }

//        //从模拟器读取图片getFilesDir()
//        final File file = new File(MyAdapter.this.context1.getFilesDir(),"imgs/a.jpg");
//        Toast.makeText(context1,MyAdapter.this.context1.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT);
//        if(file.exists()){
//            holder.img.setImageURI(Uri.fromFile(file));
//        }
        //赋值
      //  holder.img.setBackgroundResource(data.get(i).getPhoto());
        holder.title1.setText(data.get(i).getSname());
        holder.explain.setText(data.get(i).getAddress());
        holder.shopid.setText(data.get(i).getUserid());
        holder.id.setText(data.get(i).getId().toString());




    //    TextView text1= (TextView)this.f(R.id.shopid);
     //   Log.i("数据",""+text1);
//        final String userid = text1.getText().toString();//调用这个方法就可以获得这个textView的内容了
//
//        //点击商店信息，进入到商店详细页面
//        mListViewt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                String shopsInfo = String.valueOf(((TextView)view).getText());//记录商店信息
//
//                String shopsid = shopsInfo.substring(0,shopsInfo.indexOf(userid));//截取商店name
//                Toast.makeText(FirstActivity.this,shopsid,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(FirstActivity.this,StoreDetail.class);
//                intent.putExtra(FLAG,new String(shopsid));
//                startActivity(intent);
//            }
//        });



        //holder.cb.setChecked(data.get(i).getOpentime());

        //如果某个复选框被选中  ，修改状态
      /*  holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                holder.cb.setChecked(b);
            }
        });*/


     /*   j=i;
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(inflater.getContext(),data.get(j).get("title1").toString(),Toast.LENGTH_LONG);
            }
        });*/
        Log.i("数据", String.valueOf(view));

        return view;//赋值好的view返回
    }
}