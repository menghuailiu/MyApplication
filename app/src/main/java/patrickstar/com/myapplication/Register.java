package patrickstar.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import  patrickstar.com.myapplication.model.tb_shopsinfo;
import  patrickstar.com.myapplication.db.DBShopsinfo;

public class Register extends Activity {
    private  static final int PHOTO_REQUEST_CAREMA = 1;//相机
    private  static  final  int PHOTO_REQUEST_GALLERY = 2;//相册
    private static  final int PHOTO_REQUEST_CUT = 3 ;//剪切图片
    /*    private  static  final  String PHOTO_FILE_NAME = "a.jpeg";*/
    private File tempFile;
    private ImageView headIcon;
    public  Toolbar toolbar;

    private Camera camera;//相机对象
    private  boolean isPreview = false;//是否为预览模式
    private  String imgpath=null;

    private  EditText txtuserid;
    private  EditText txtpwd;
    private  EditText txtsname;
    private  EditText txtaddr;
    private  EditText txttel;
    private  EditText txtopentime;
    private  EditText txtremark;
    private  Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initVIew();

        //绑定控件，
        txtuserid = (EditText)findViewById(R.id.txtuserid);
        txtpwd = (EditText)findViewById(R.id.txtpwd);
        txtsname = (EditText)findViewById(R.id.txtsname);
        txtaddr = (EditText)findViewById(R.id.txtaddr);

        txttel = (EditText)findViewById(R.id.txttel);
        txtopentime = (EditText)findViewById(R.id.txtopentime);
        txtremark = (EditText)findViewById(R.id.txtremark);
        btnsave = (Button)findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtuserid.getText().toString().length()<1){
                    Toast.makeText(Register.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                else if(txtpwd.getText().toString().length()<1){
                    Toast.makeText(Register.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else if (txtsname.getText().toString().length()<1){
                    Toast.makeText(Register.this,"请输入店名",Toast.LENGTH_SHORT).show();
                }
                else if(txtaddr.getText().toString().length()<1){
                    Toast.makeText(Register.this,"请输入地址",Toast.LENGTH_SHORT).show();
                }
                else  if (txttel.getText().toString().length()<1){
                    Toast.makeText(Register.this,"请输入电话号码",Toast.LENGTH_SHORT).show();
                }
                else  if(txtopentime.getText().toString().length()<1){
                    Toast.makeText(Register.this,"请输入营业时间",Toast.LENGTH_SHORT).show();
                }
                else  if(txtremark.getText().toString().length()<1){
                    Toast.makeText(Register.this,"请输入备注",Toast.LENGTH_SHORT).show();
                }
                else if(imgpath == null){
                    Toast.makeText(Register.this,"请选择图片",Toast.LENGTH_SHORT).show();
                }
                else {
                    //获取最大id
                    DBShopsinfo db = new DBShopsinfo(Register.this);
                    int id = db.getMax()+1;//获取最大id
                    tb_shopsinfo tb = new tb_shopsinfo(Long.parseLong(String.valueOf(id)),txtuserid.getText().toString(),txtpwd.getText().toString(),txtsname.getText().toString(),txtaddr.getText().toString(),txttel.getText().toString(),
                            imgpath, txtopentime.getText().toString(),txtremark.getText().toString());
                    db.insert(tb);
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                }
            }
        });
    }

    //设置导航栏
    public  void initView(){
        toolbar = (Toolbar)findViewById(R.id.toolbar1);//获取页面的工具栏

        toolbar.setTitle("用户注册");
        toolbar.setTitleMarginStart(370);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleMarginTop(10);
        // setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.bac);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回首页
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
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
        headIcon = (ImageView)findViewById(R.id.headIcon);
        headIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeadIcon();
            }
        });
        changeTheme();
        File file = new File(Register.this.getFilesDir(),"a.jpeg");
        if(file.exists()){
            headIcon.setImageURI(Uri.fromFile(file));
        }
    }

    //设置初始界面图片
    private  void changeTheme(){
        try {
            Intent intent =  getIntent();
            String pic =  intent.getExtras().getString("picname");
            if (pic.length() < 1) {

            } else {
                Toast.makeText(Register.this, "ohahha", Toast.LENGTH_SHORT).show();//弹出提示
                if (!android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(Register.this, "请安装SD卡", Toast.LENGTH_SHORT).show();//弹出提示
                }

                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/" + pic + ".jpg";
                imgpath=path;
                File file = new File(path);//创建一个文件对象

                if (file.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    //将图片显示到ImageView中
                    headIcon.setImageBitmap(bm);
                }
            }
        }catch (Exception e) {
            Toast.makeText(Register.this, "bbugbug", Toast.LENGTH_SHORT).show();//弹出提示
            Calendar c = Calendar.getInstance();
            System.out.println(c.get(Calendar.HOUR_OF_DAY));//输出事件
            if ((c.get(Calendar.HOUR_OF_DAY)) < 18 && (c.get(Calendar.HOUR_OF_DAY)) >= 6) {
                headIcon.setImageResource(R.drawable.g);
            }
        }
    }

    //设置弹出框的内容  修改图片
    private void changeHeadIcon(){
        final  CharSequence[] items = {"相册","拍照"};
        AlertDialog dlg = new AlertDialog.Builder(Register.this).setTitle("选择图片").
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
                            Intent intent = new Intent(Register.this,CameraPic.class);
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
                Toast.makeText(Register.this, "未找到存储卡，无法存储照片！",
                        Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                final Bitmap bitmap = data.getParcelableExtra("data");
                headIcon.setImageBitmap(bitmap);
                // 保存图片到internal storage
                FileOutputStream outputStream;
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();

                    //outputStream = Register.this.openFileOutput("_head_icon.jpg", Context.MODE_PRIVATE);
                    if (!android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(Register.this, "请安装SD卡", Toast.LENGTH_SHORT).show();//弹出提示
                    }
                    SimpleDateFormat sDateFormat    =   new    SimpleDateFormat("yyyyMMddhhmmss");
                    String    date    =    sDateFormat.format(new    java.util.Date());
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
