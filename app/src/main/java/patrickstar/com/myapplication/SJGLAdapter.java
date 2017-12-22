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
import patrickstar.com.myapplication.model.tb_shopsmenu;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.CheckBox;

/**
 * Created by ios18 on 17/12/14.
 */

public class SJGLAdapter extends BaseAdapter {

    private  Context context1;
    public static Map<Integer, Boolean> isSelected;
    public List<tb_shopsmenu> data;
    public int j = 0;
    public SJGLViewHolder holder = null;//viewholder里面装的有界面上有的所有控件对应的属性//viewholder里面装的有界面上有的所有控件对应的属性

    public LayoutInflater inflater;//解析xml文件  将xml文件转换成为view
    public int flag = 0;//复选框默认不显示


    public SJGLAdapter(Context context, List<tb_shopsmenu> ListData,int flag) {
        this.flag =flag;
        this.inflater = LayoutInflater.from(context);//解析context转换成view  setcontentview也有调用到LayoutInflater
        data = ListData;
        Log.i("数据", String.valueOf(data.size()));
        context1 = context;
        //这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < data.size(); i++) {
            isSelected.put(i, false);
        }

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
            holder = new SJGLViewHolder();//实例化ViewHolder
            view = inflater.inflate(R.layout.menulist, null);//解析activity_main 界面
            holder.cb=(CheckBox)view.findViewById(R.id.cb);
            holder.img = (ImageView) view.findViewById(R.id.img); //使用ViewHOlder绑定view中的控件
            holder.explain = (TextView) view.findViewById(R.id.explain);
            holder.title = (TextView) view.findViewById(R.id.title);
            //holder.cb = view.findViewById(R.id.cb);
            view.setTag(holder);//在view中使用tag标签存储

        } else {
            holder = (SJGLViewHolder) view.getTag();//如果是有值就直接从view中获取
        }

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/" +data.get(i).getPhoto();
        //imgpath=path;
        File file = new File(path);//创建一个文件对象

        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(path);
            //将图片显示到ImageView中
            holder.img.setImageBitmap(bm);
        }
        //赋值
        holder.id = data.get(i).getId();
        holder.title.setText(data.get(i).getDishname());
        holder.explain.setText(data.get(i).getPrice());
        holder.cb.setChecked(false);
        if(flag==1) {
            holder.cb.setVisibility(View.VISIBLE);
        }
        Log.i("数据", String.valueOf(view));

        return view;//赋值好的view返回
    }
}