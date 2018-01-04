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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import patrickstar.com.myapplication.model.tb_shopsmenu;


/**
 * Created by ios23 on 2017/12/14.
 */

public class MyAdaptero extends BaseAdapter {
    public MyAdaptero() {
        super();

    }
    public List<tb_shopsmenu> data;
    public int j = 0;
    public ViewHoldero holder = null;//viewholder里面装的有界面上有的所有控件对应的属性//viewholder里面装的有界面上有的所有控件对应的属性

    public LayoutInflater inflater;//解析xml文件  将xml文件转换成为view


    public MyAdaptero(Context context, List<tb_shopsmenu> ListData) {
        this.inflater = LayoutInflater.from(context);//解析context转换成view  setcontentview也有调用到LayoutInflater
        data = ListData;
        Log.i("数据", String.valueOf(data.size()));

    }


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
            holder = new ViewHoldero();//实例化ViewHolder
            view = inflater.inflate(R.layout.storeitem, null);//解析activity_main 界面
            holder.imgcai = (ImageView) view.findViewById(R.id.imgcai); //使用ViewHOlder绑定view中的控件
            holder.txtcname = (TextView) view.findViewById(R.id.txtcname);
            holder.txtcprice = (TextView) view.findViewById(R.id.txtcprice);
            holder.txtcremark = (TextView)view.findViewById(R.id.txtcremack);

            //holder.cb = view.findViewById(R.id.cb);
            view.setTag(holder);//在view中使用tag标签存储

        } else {
            holder = (ViewHoldero) view.getTag();//如果是有值就直接从view中获取
        }


//        //从模拟器读取图片
//        final File file = new File(StoreDetail.this.getFilesDir(),"imgs/a.jpeg");
//        Toast.makeText(StoreDetail.this,StoreDetail.this.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT).show();
//        if(file.exists()){
//            imgPhoto.setImageURI(Uri.fromFile(file));
//        }
        //赋值
        //  holder.img.setBackgroundResource(data.get(i).getPhoto());
        String path = data.get(i).getPhoto();
        //imgpath=path;
        File file = new File(path);//创建一个文件对象

        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(path);
            //将图片显示到ImageView中
            holder.imgcai.setImageURI(Uri.fromFile(file));
        }


        holder.txtcname.setText(data.get(i).getDishname());
        holder.txtcprice.setText(data.get(i).getPrice());
        holder.txtcremark.setText(data.get(i).getRemark());

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
       // Log.i("数据", String.valueOf(view));

        return view;//赋值好的view返回
    }

}
