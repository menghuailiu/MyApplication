package patrickstar.com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by ios16 on 17/9/5.  人和食堂
 */

public class MyListView extends ListView {
    View one;
    public MyListView(Context context) {
        super(context);
        initHeaderView(context);
    }

    private void initHeaderView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        one = inflater.inflate(R.layout.headerlayout, null);
        addHeaderView(one);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //控制第一个头部布局的位置
        one.setPadding(0, -1 * one.getHeight()/2, 0, 0);
    }
}