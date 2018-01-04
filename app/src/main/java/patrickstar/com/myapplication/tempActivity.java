package patrickstar.com.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.db.DBShopsmenu;
import patrickstar.com.myapplication.model.tb_shopsmenu;

public class tempActivity extends Activity {
    public View layout;
    public ImageView pic;
    public EditText menuname, menuprice, menunote;
    public Intent intent;
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/myapplication/";
    private Bitmap bitmap;
    private Uri uri;
    public tb_shopsmenu shopmenu;
    public int flag = 0;//0表示修改，1表示新增
    public int photoflag= 0;//是否换过图片 0代表没有，1表示有

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopmenu = (tb_shopsmenu) getIntent().getSerializableExtra("shopmenu");
        if (shopmenu.getDishname() == null || shopmenu.getDishname().isEmpty()) {
            flag = 1;
        }
        Log.i("菜名：", shopmenu.getDishname());
        dialog();

       // newStart();
    }

    public void dialog() {
        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.activity_add_menu, null);
        pic = layout.findViewById(R.id.imageid);
        menuname = layout.findViewById(R.id.dishes);
        menuprice = layout.findViewById(R.id.price);
        menunote = layout.findViewById(R.id.notes);
        Button uplaod = layout.findViewById(R.id.uploadimage);
        String a = shopmenu.getPhoto();
        try {
            File file = new File(a);//创建一个文件对象
            if (file.exists()) {
               pic.setImageURI(uri.fromFile(new File(a)));
            }
        }
        catch (Exception ex){
             ex.printStackTrace();
        }
        menuname.setText(shopmenu.getDishname());
        shopmenu.setPhoto(shopmenu.getPhoto());
        menuprice.setText(shopmenu.getPrice());
        menunote.setText(shopmenu.getRemark());

        //上传图片
        uplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(tempActivity.this);

        builder.setTitle("添加商品");
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        save();
                        
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tempActivity.this.finish();
                    }
                });
        builder.create().show();
       // newStart();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            photoflag =1;
            uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                pic.setImageBitmap(bitmap);

            } catch (Exception e) {

            }
        }

    }

    //新增shopmenu
    public void save() {
        String imagename;
        if(flag ==1 || photoflag==1)//要么是新增，要么换了图片
        {
            imagename = saveImage();
        }
        else{
            imagename =shopmenu.getPhoto();
        }
        shopmenu.setDishname(menuname.getText().toString());
      //  shopmenu.setPhoto(imagename);  注释 罗
        shopmenu.setPhoto(ALBUM_PATH+imagename);
        shopmenu.setPrice(menuprice.getText().toString());
        shopmenu.setRemark(menunote.getText().toString());

        DBShopsmenu dbShopsmenu = new DBShopsmenu(this);
        if (flag == 1) {
            if (dbShopsmenu.insert(shopmenu) > 0) {
                Toast.makeText(tempActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                tempActivity.this.finish();
            } else {
                Toast.makeText(tempActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                tempActivity.this.finish();
            }
        } else {
            if (dbShopsmenu.update(shopmenu)) {
                tempActivity.this.finish();

            } else {
                Toast.makeText(tempActivity.this, "修改失败", Toast.LENGTH_LONG).show();
                tempActivity.this.finish();
            }
        }
    }

    //写入图片
    public String saveImage() {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String temp = uri.getPath();
        String imagename = temp.substring(temp.indexOf("document/") + 9, temp.length());
        File myCaptureFile = new File(ALBUM_PATH + imagename.replace(":", "") + ".jpg");
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imagename.replace(":", "") + ".jpg";
    }

    public void newStart() {
        Intent intent = new Intent(tempActivity.this,SJGL.class);
        intent.putExtra("shopid",shopmenu.getShopsinfoid());
        startActivity(intent);
    }

    private void cancel(){
        //newStart();
    }

    //以下代码是隐藏键盘  点击其它地方隐藏键盘
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
