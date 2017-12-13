package patrickstar.com.myapplication;

import android.content.ContentResolver;
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
import android.view.View;
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

public class tempActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopmenu = (tb_shopsmenu) getIntent().getSerializableExtra("shopmenu");
        if (shopmenu.getDishname()==null||shopmenu.getDishname().isEmpty())
        {
            flag = 1;
        }
        Log.i("菜名：", shopmenu.getDishname());
        dialog();
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
        pic.setImageURI(uri.fromFile(new File(ALBUM_PATH + a)));
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

        new AlertDialog.Builder(this)
                .setTitle("添加商品")
                .setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        save();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
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
        String imagename = saveImage();
        shopmenu.setDishname(menuname.getText().toString());
        shopmenu.setPhoto(imagename);
        shopmenu.setPrice(menuprice.getText().toString());
        shopmenu.setRemark(menunote.getText().toString());

        DBShopsmenu dbShopsmenu = new DBShopsmenu(this);
        if (flag == 1) {
            if (dbShopsmenu.insert(shopmenu) > 0) {
                Toast.makeText(tempActivity.this, "添加成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(tempActivity.this, "添加失败", Toast.LENGTH_LONG).show();
            }
        } else {
            if (dbShopsmenu.update(shopmenu)) {
                Toast.makeText(tempActivity.this, "修改成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(tempActivity.this, "修改失败", Toast.LENGTH_LONG).show();
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

}
