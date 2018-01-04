package patrickstar.com.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sysadmin on 2017/12/11.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;//设置版本号
    private static final String DBNAME="Cate.db";//定义数据库名称

    public DBOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

  /*  public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }*/

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //创建数据库，第一次执行的时候运行
        //创建商家信息表
        //sname商家名称，opentime营业时间，remark备注
        //
/*        String str1="create table tb_shopsinfo(_id integer primarykey key,userid varchar(50),pwd varchar(30),sname varchar(20),address varchar(50),tel varchar(20),photo varchar(500),opentime varchar(20),remark varchar(300))";

        //创建商家菜单表
        //shopsinfoid商家信息ID，dishname菜名，remark备注
        String str2=" create table tb_shopsmenu(_id integer primary key ,shopsinfoid integer,dishname varchar(100),photo varchar(500),price decimal,remark varchar(300))";

        sqLiteDatabase.execSQL(str1);
        sqLiteDatabase.execSQL(str2);*/

    }

    //覆写基类的onUpdate方法，以便数据库版本更新
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//这是如果已经创建了数据库，那就把要修改数据库的代码写在这里
        //sname商家名称，opentime营业时间，remark备注
//        String str1="create table tb_shopsinfo(_id integer primarykey key,userid varchar(50),pwd varchar(30),sname varchar(20),address varchar(50),tel varchar(20),photo varchar(500),opentime varchar(20),remark varchar(300))";
//
//        sqLiteDatabase.execSQL(str1);
    }
}
