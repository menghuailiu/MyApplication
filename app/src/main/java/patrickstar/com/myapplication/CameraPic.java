package patrickstar.com.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

/**
 * 可以用于拍照的有两个控件 ，Surface和texture  ，serfaceview 相当于重新创建一个window
 * 而⃣️texture是在view的基础上完成的，
 *
 *CameraPic类实现拍照，比并且把图片保存到sd卡。然后返回图片名称
 *
 */
public class CameraPic extends Activity implements  SurfaceHolder.Callback {

    private Camera camera;//相机对象，
    private boolean isPreview;//是否为预览模式
    public static String picname;//设置图片名称
    public   Button btnPreview;
    int mCameraId;//摄像头id
    private SurfaceHolder holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置全屏显示，一定要写在setContentView之前。
        setContentView(R.layout.activity_camera_pic);
        CameraPic.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        //设置手机屏幕朝向，一共有7种
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        //SCREEN_ORIENTATION_BEHIND： 继承Activity堆栈中当前Activity下面的那个Activity的方向
        //SCREEN_ORIENTATION_LANDSCAPE： 横屏(风景照) ，显示时宽度大于高度
        //SCREEN_ORIENTATION_PORTRAIT： 竖屏 (肖像照) ， 显示时高度大于宽度
        //SCREEN_ORIENTATION_SENSOR  由重力感应器来决定屏幕的朝向,它取决于用户如何持有设备,当设备被旋转时方向会随之在横屏与竖屏之间变化
        //SCREEN_ORIENTATION_NOSENSOR： 忽略物理感应器——即显示方向与物理感应器无关，不管用户如何旋转设备显示方向都不会随着改变("unspecified"设置除外)
        //SCREEN_ORIENTATION_UNSPECIFIED： 未指定，此为默认值，由Android系统自己选择适当的方向，选择策略视具体设备的配置情况而定，因此不同的设备会有不同的方向选择
        //SCREEN_ORIENTATION_USER： 用户当前的首选方向


        SimpleDateFormat sDateFormat    =   new    SimpleDateFormat("yyyyMMddhhmmss");
        String    date    =    sDateFormat.format(new    java.util.Date());
        //Toast.makeText(CameraPic.this,date ,Toast.LENGTH_SHORT).show();
        picname=date;
        /**
         * 判断是否安装SD卡。
         */
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(CameraPic.this, "请安装SD卡", Toast.LENGTH_SHORT);//弹出提示
        }

        SurfaceView surface = (SurfaceView) findViewById(R.id.surfaceView1);//获取SurfaceView组件，用哪个与显示相机预览
        final SurfaceHolder sh=surface.getHolder();

       //预览按钮事件处理
        btnPreview = (Button) findViewById(R.id.preview);
        btnPreview.setVisibility(View.INVISIBLE);
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断如果相机为非预览模式，则打开相机
                if (!isPreview) {
                    camera = Camera.open(0);//打开相机
                    camera.setDisplayOrientation(90);// 旋转镜头
                }
                try {
                    camera.setPreviewDisplay(sh);//设置用于显示预览的SurfaceView
                    Camera.Parameters parameters = camera.getParameters();//获取相机参数
                    parameters.setPictureSize(640, 400);//设置预览画面大小
                    parameters.setPictureFormat(PixelFormat.JPEG);//指定图片为JPEG格式
                    parameters.set("jpeg-quality", 80);//设置图片质量
                    camera.startPreview();//开始预览
                    camera.autoFocus(null);//设置自动对焦
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
             }
       });


        //在最后调用  设置在控件创建的时候打开相机
        sh.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    btnPreview.performClick();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });//添加回调


        //实现拍照的回调接口
        final Camera.PictureCallback jpeg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                final Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);//取出数据到bm里面
                //加载layout/save.xml文件对应的布局资源。
                View saveView = getLayoutInflater().inflate(R.layout.saveimg, null);//加载布局
               // final EditText photoName = (EditText) saveView.findViewById(R.id.edphototname);
                //获取对话框上面的ImageView组件
                ImageView imageView = (ImageView) saveView.findViewById(R.id.imageView1);
                imageView.setImageBitmap(bm);//显示刚刚拍照的图片
                camera.stopPreview();//停止预览
                isPreview = false;
                //使用对话框显示saveDialog组件
                new AlertDialog.Builder(CameraPic.this).setView(saveView).setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication";
                     File f = new File(path);
                     if(f.exists()) {
                     }
                     else
                     {
                         f.mkdirs();
                     }
                         //将文件保存到本地
                         File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapplication/" + picname.toString() + ".jpg");//创建一个文件对象
                         try {
                             file.createNewFile();// 创建一个新的文件
                             //创建一个文件输出流对象
                             FileOutputStream fileOutputStream = new FileOutputStream(file);
                             //将图片内容压缩为JPEG格式输出到输出流对象中
                             bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                             fileOutputStream.flush();//将缓冲区的数据全部写出输出流中
                             fileOutputStream.close();//关闭文件输出流对象
                             resetCamera();
                             isPreview = true;
                             try{
                              Intent inte =   getIntent();
                              String page = inte.getStringExtra("page");
                              if(page == null){
                                  /*Intent intent = new Intent(CameraPic.this, Register.class);
                                  intent.putExtra("picname", picname);
                                  startActivity(intent);*/
                                  String userid="",pwd="",sname="",addr="",tel="",opentime="",remark="";


                                  try {
                                      //获取界面数据和传递数据
                                      Intent intent1 = getIntent();
                                      userid = intent1.getStringExtra("userid");
                                      pwd = intent1.getStringExtra("pwd");
                                      sname = intent1.getStringExtra("sname");
                                      addr = intent1.getStringExtra("addr");
                                      tel = intent1.getStringExtra("tel");
                                      opentime = intent1.getStringExtra("opentime");
                                      remark =intent1.getStringExtra("remark");
                                  }catch (Exception e){
                                      e.printStackTrace();
                                  }
                                  Intent intent = new Intent(CameraPic.this, Register.class);
                                  if(userid==null){

                                  } else{
                                      intent.putExtra("userid",userid);
                                  }

                                  if(pwd==null){

                                  } else{
                                      intent.putExtra("pwd",pwd);
                                  }

                                  if (sname==null){

                                  }else{
                                      intent.putExtra("sname",sname);
                                  }

                                  if(addr==null){

                                  }else{
                                      intent.putExtra("addr",addr);
                                  }

                                  if (tel.length()<1){

                                  }else{
                                      intent.putExtra("tel",tel);
                                  }

                                  if(opentime==null){

                                  }else{
                                      intent.putExtra("opentime",opentime);
                                  }


                                  if(remark==null){

                                  }else{
                                      intent.putExtra("remark",remark);
                                  }

                                  intent.putExtra("picname", picname);
                                  startActivity(intent);
                              }
                              else{
                                  Intent intent = new Intent(CameraPic.this, SJXG.class);
                                  intent.putExtra("picname", picname);
                                  startActivity(intent);
                              }
                             }catch (Exception ex){
                                 Log.i("camera error ","----------------------------------------");
                                 ex.printStackTrace();
                                 String userid="",pwd="",sname="",addr="",tel="",opentime="",remark="";


                                 try {
                                 //获取界面数据和传递数据
                                 Intent intent1 = getIntent();
                                  userid = intent1.getStringExtra("userid");
                                  pwd = intent1.getStringExtra("pwd");
                                  sname = intent1.getStringExtra("sname");
                                  addr = intent1.getStringExtra("addr");
                                  tel = intent1.getStringExtra("tel");
                                  opentime = intent1.getStringExtra("opentime");
                                  remark =intent1.getStringExtra("remark");
                                 }catch (Exception e){
                                     e.printStackTrace();
                                 }
                                 Intent intent = new Intent(CameraPic.this, Register.class);
                                 if(userid==null){

                                 } else{
                                     intent.putExtra("userid",userid);
                                 }

                                 if(pwd==null){

                                 } else{
                                     intent.putExtra("pwd",pwd);
                                 }

                                 if (sname==null){

                                 }else{
                                     intent.putExtra("sname",sname);
                                 }

                                 if(addr==null){

                                 }else{
                                     intent.putExtra("addr",addr);
                                 }

                                 if (tel==null){

                                 }else{
                                     intent.putExtra("tel",tel);
                                 }

                                 if(opentime==null){

                                 }else{
                                     intent.putExtra("opentime",opentime);
                                 }


                                 if(remark==null){

                                 }else{
                                     intent.putExtra("remark",remark);
                                 }

                                 intent.putExtra("picname", picname);
                                 startActivity(intent);
                             }


                         } catch (Exception ex) {
                             ex.printStackTrace();

                         }


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isPreview = true;
                        resetCamera();
                    }
                }).show();
            }
        };
        //拍照按钮事件处理
        Button btnTakePicture = (Button) findViewById(R.id.btntakephoto);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断相机对象是否为空，不为空再进行拍照
                if (camera != null) {
                    camera.takePicture(null, null, jpeg);//进行拍照  jpeg是处理拍照的图片的事件
                }
            }
        });




    }

    /**
     * 重新预览
     */
    public void resetCamera() {
        if (isPreview) {
            camera.startPreview();//开启预览
        }
    }

    /**
     * OnPause方法用于暂停Activity时，停止预览并释放相机资源
     */
    @Override
    protected void onPause() {
        if (camera != null) {
            camera.stopPreview();//停止预览
            camera.release();//释放资源
        }
        super.onPause();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //当surfaceview创建时开启相机
        if(camera == null) {
            camera = Camera.open();
            try {
               // camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                camera.startPreview();//开始预览
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //当surfaceview关闭时，关闭预览并释放资源
        camera.stopPreview();
        camera.release();
        camera = null;
        holder = null;
      //  surface = null;
    }
}