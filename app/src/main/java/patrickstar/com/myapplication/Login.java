package patrickstar.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import patrickstar.com.myapplication.db.DBOpenHelper;
import  patrickstar.com.myapplication.db.DBShopsinfo;

//4365e6   4659c5  3c50c1
public class Login extends AppCompatActivity {
    public  Toolbar toolbar;
    public Button btnLogin;
    public Button btnRegister;
    public EditText txtuserid;
    public  EditText txtpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initView();
        initclick();
    }

    //处理按钮点击事件
    public  void initclick(){
        btnLogin = (Button)findViewById(R.id.btnlogin);
        btnRegister = (Button)findViewById(R.id.btnregister);
        txtuserid  = (EditText)findViewById(R.id.txtuserid);
        txtpwd = (EditText)findViewById(R.id.txtpwd);

        //设置登录事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtuserid.getText().toString().length()<1){
                    Toast.makeText(Login.this,"请输入用户名",Toast.LENGTH_LONG).show();
                }
                else if (txtpwd.getText().toString().length()<1){
                    Toast.makeText(Login.this,"请输入密码",Toast.LENGTH_LONG).show();
                }
                else {
                    DBShopsinfo info = new DBShopsinfo(Login.this);
                    boolean b = info.Login(txtuserid.getText().toString(), txtpwd.getText().toString());
                    TextView text=(TextView)findViewById(R.id.txtuserid);
                    text.getText();//调用这个方法就可以获得这个textView的内容了
                    String userid = text.getText().toString();
                    Intent intent = new Intent(Login.this,SJGL.class);
                    intent.putExtra("userid",new String(userid));
                    startActivity(intent);
                }

            }
        });

        //设置注册按钮点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

    }

    //设置导航栏
    public  void initView(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);//获取页面的工具栏

        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleMarginStart(200);
        toolbar.setTitleMarginTop(10);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.bac);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回首页
                Intent intent = new Intent(Login.this,YGSCateAPP.class);
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
}
