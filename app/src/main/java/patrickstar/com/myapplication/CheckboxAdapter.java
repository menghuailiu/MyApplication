package patrickstar.com.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckboxAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, Object>> listData;
    //记录checkbox的状态
    HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();

    //构造函数
    public CheckboxAdapter(Context context,ArrayList<HashMap<String,Object>> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 重写View
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = LayoutInflater.from(context);
        convertView = mInflater.inflate(R.layout.menulist, null);
        ImageView image = (ImageView) convertView.findViewById(R.id.img);
        image.setBackgroundResource((Integer) listData.get(position).get("image"));
        TextView title = (TextView) convertView.findViewById(R.id.title1);
        title.setText((String) listData.get(position).get("title"));
        TextView explain = (TextView) convertView.findViewById(R.id.explain);
        explain.setText((String) listData.get(position).get("friend_id"));
        CheckBox check = (CheckBox) convertView.findViewById(R.id.cb);
        check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked)
            {
                if (isChecked) {
                    state.put(position, isChecked);
                } else {
                    state.remove(position);
                }
            }
        });
        check.setChecked((state.get(position) == null ? false : true));
        return convertView;
    }
}