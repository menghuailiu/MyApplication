package patrickstar.com.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class CameraPic extends AppCompatActivity {

    private Camera camera;//相机对象，
    private boolean isPreview;//是否为预览模式
    public static String picname;//设置图片名称


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置全屏显示，一定要写在setContentView之前。
        setContentView(R.layout.activity_camera_pic);

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
        Button btnPreview = (Button) findViewById(R.id.preview);
       btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断如果相机为非预览模式，则打开相机
                if (!isPreview) {
                    camera = Camera.open();//打开相机
                }
                try {
                    camera.setPreviewDisplay(sh);//设置用于显示预览的SurfaceView
                    Camera.Parameters parameters = camera.getParameters();//获取相机参数
                    parameters.setPictureSize(640, 480);//设置预览画面大小
                    parameters.setPictureFormat(PixelFormat.JPEG);//指定图片为JPEG格式
                    parameters.set("jpeg-quality", 80);//设置图片质量
                    camera.startPreview();//开始预览
                    camera.autoFocus(null);//设置自动对焦
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
             }
       });


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
                             Intent intent = new Intent(CameraPic.this, SJXG.class);
                             intent.putExtra("picname", picname);
                             startActivity(intent);
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
}