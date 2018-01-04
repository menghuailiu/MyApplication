package patrickstar.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.renderscript.Sampler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import patrickstar.com.myapplication.db.DBShopsinfo;
import patrickstar.com.myapplication.model.tb_shopsinfo;

public class SJXG extends Activity {
    private  static final int PHOTO_REQUEST_CAREMA = 1;//相机
    private  static  final  int PHOTO_REQUEST_GALLERY = 2;//相册
    private static  final int PHOTO_REQUEST_CUT = 3 ;//剪切图片
    private Camera camera;//相机对象
    private  boolean isPreview = false;//是否为预览模式
    private  String imgpath="";
    private File tempFile;
    private EditText txtname;
    private EditText txtaddress;
    private EditText txtmobile;
    private EditText txttime;
    private EditText txtremark;
    private Button submit;
    private ImageView image;
    private Button load;
     long id;
    private static DBShopsinfo db ;
    private static tb_shopsinfo tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjxg);
        if (db == null){
            db = new DBShopsinfo(SJXG.this);
        }
        if (tb == null){
            Intent intent = getIntent();
            id = Long.parseLong(intent.getStringExtra("shopid"));
            tb = db.shopsinfoById(id);
        }
        initView();
       // initVIew();
    }
    public void initView()
    {
        txtname = (EditText)findViewById(R.id.txtname);
        txtaddress =(EditText)findViewById(R.id.txtaddress);
        txtmobile =(EditText)findViewById(R.id.txtmobile);
        txttime  =(EditText)findViewById(R.id.txttime);
        txtremark  =(EditText)findViewById(R.id.txtremark );

        txtname.setText(tb.getSname());
        txtaddress.setText(tb.getAddress());
        txtmobile.setText(tb.getTel());
        txttime.setText(tb.getOpentime());
        txtremark.setText(tb.getRemark());

        image =(ImageView)findViewById(R.id.image) ;
        String a = tb.getPhoto();
        imgpath =a;
        final File file = new File(tb.getPhoto());
       // Toast.makeText(SJXG.this,SJXG.this.getFilesDir().getPath().toString(),Toast.LENGTH_SHORT).show();
        if(file.exists()){
            image.setImageURI(Uri.fromFile(file));
        }

        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {

                tb.setId(id);
                tb.setSname(txtname.getText().toString());
                tb.setAddress(txtaddress.getText().toString());
                tb.setTel(txtmobile.getText().toString());
                tb.setOpentime(txttime.getText().toString());
                tb.setRemark(txtremark.getText().toString());
                tb.setPhoto(imgpath);

                boolean a =db.update(tb);
                List list  = db.findAll();
                if(a=true)
                {

                    Intent intent = new Intent(SJXG.this,SJGL.class);
                    intent.putExtra("shopid", String.valueOf(id));
                    startActivity(intent);

                }
            }
        });

        load = (Button)findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("img*//*");
                startActivityForResult(intent, 1);*/
                changeHeadIcon();
            }
        });
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

    //以下是点击图片选择相册
    private  void initVIew(){
        //绑定页面imageview控件，给图片设计点击事件
        image = (ImageView)findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeadIcon();
            }
        });
        changeTheme();
        String a = tb.getPhoto();
        File file = new File(a);
        if(file.exists()){
            image.setImageURI(Uri.fromFile(file));
        }
    }

    //设置初始界面图片
    private  void changeTheme(){
        try {
            Intent intent =  getIntent();
            String pic =  intent.getExtras().getString("picname");
            if (pic.length() < 1) {

            } else {
                //Toast.makeText(SJXG.this, "ohahha", Toast.LENGTH_SHORT).show();//弹出提示
                if (!android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(SJXG.this, "请安装SD卡", Toast.LENGTH_SHORT).show();//弹出提示
                }

                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/" + pic + ".jpg";
                imgpath=path;
                File file = new File(path);//创建一个文件对象

                if (file.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    //将图片显示到ImageView中
                    image.setImageBitmap(bm);
                }
            }
        }catch (Exception e) {
            Toast.makeText(SJXG.this, "bbugbug", Toast.LENGTH_SHORT).show();//弹出提示
            Calendar c = Calendar.getInstance();
            System.out.println(c.get(Calendar.HOUR_OF_DAY));//输出事件
            if ((c.get(Calendar.HOUR_OF_DAY)) < 18 && (c.get(Calendar.HOUR_OF_DAY)) >= 6) {
                image.setImageResource(R.drawable.g);
            }
        }
    }

    //设置弹出框的内容  修改图片
    private void changeHeadIcon(){
        final  CharSequence[] items = {"相册"};
        AlertDialog dlg = new AlertDialog.Builder(SJXG.this).setTitle("选择图片").
                setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //当点击相册时
                        if(i == 0){
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
                        }
                        else{
                            //跳转到拍照页面
                            Intent intent = new Intent(SJXG.this,CameraPic.class);
                            intent.putExtra("page","sjxg");
                            startActivity(intent);
                        }
                    }
                }).create();
        dlg.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                Log.e("图片路径？？", data.getData() + "");
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(SJXG.this, "未找到存储卡，无法存储照片！",
                        Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                final Bitmap bitmap = data.getParcelableExtra("data");
                image.setImageBitmap(bitmap);
                // 保存图片到internal storage
                FileOutputStream outputStream;
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();

                    //outputStream = Register.this.openFileOutput("_head_icon.jpg", Context.MODE_PRIVATE);
                    if (!android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(SJXG.this, "请安装SD卡", Toast.LENGTH_SHORT).show();//弹出提示
                    }
                    SimpleDateFormat sDateFormat    =   new    SimpleDateFormat("yyyyMMddhhmmss");
                    String    date    =    sDateFormat.format(new    java.util.Date());

                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication";
                    File f = new File(path);
                    if(f.exists()) {
                    }
                    else
                    {
                        f.mkdirs();
                    }
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/" + date+"r"+ ".jpg");//创建一个文件对象
                    imgpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/" + date+"r"+ ".jpg";
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    out.writeTo(fileOutputStream);
                    out.close();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                if (tempFile != null && tempFile.exists())
                    tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    //图片剪切跳转
    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

}
