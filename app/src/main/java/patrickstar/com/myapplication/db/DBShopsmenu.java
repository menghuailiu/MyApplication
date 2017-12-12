package patrickstar.com.myapplication.db;

import android.content.Context;

import java.util.List;

import model.greendao.gen.DaoMaster;
import model.greendao.gen.DaoSession;
import model.greendao.gen.tb_shopsmenuDao;
import patrickstar.com.myapplication.model.tb_shopsmenu;

/**
 * Created by sysadmin on 2017/12/11.
 */

public class DBShopsmenu {
    /**
     * 与greendao数据操作相关的几个类
     */

    public tb_shopsmenuDao shopsmenuDao;
    public Context context;
    public tb_shopsmenu shopsmenu;



    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    public void initDb(){
        helper = new DaoMaster.DevOpenHelper(context, "account.db", null);
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        shopsmenuDao=session.getTb_shopsmenuDao();

    }
    public DBShopsmenu(Context context1){
        context=context1;
        initDb();
    }
    /**
     * 新增便签信息
     * 需要传入一个 flad对象
     *
     * @return  int 编号
     */
    public int insert(tb_shopsmenu flag)
    {
        long flagid=shopsmenuDao.insert(flag);
        return Integer.parseInt(String.valueOf(flagid));
    }


    /**
     * 根据id删除便签
     * @return boolean类型的数据
     */
    public  boolean deleteById(Long id)
    {
        boolean bo=true;
        try {
            shopsmenuDao.deleteByKey(id);
        }catch (Exception ex)
        {
            bo=false;
        }
        return bo;
    }


    /**
     * 删除所有数据
     * @return boolean 类型的数据
     */
    public boolean deleteAll()
    {
        boolean bo=true;
        try {
            shopsmenuDao.deleteAll();
        }catch (Exception ex)
        {
            bo=false;
        }
        return bo;
    }

    /**
     * 修改便签
     * @param tbflag  便签对象
     * @return boolean 类型的数据
     */
    public boolean update(tb_shopsmenu tbflag){

        if(tbflag == null){
            return false;
        }
        shopsmenuDao.update(tbflag);
        return true;
    }

    /**
     * 查询所有便签信息
     * @return tb_flag的list的集合
     */
    public List<tb_shopsmenu> query(){
        List<tb_shopsmenu> flagList = shopsmenuDao.loadAll();
        return flagList;
    }


    /**
     * 根据ID查询数据
     * @return tb_flag 便签对象
     */
    public tb_shopsmenu find(int id)
    {
        tb_shopsmenu bu=null;
        try {

            bu = shopsmenuDao.queryBuilder().where(tb_shopsmenuDao.Properties.Id.eq(id)).build().list().get(0);
  /*      tb_flag tb=null;*/

            // tb=(tb_flag) bu.list().get(0);
        }
        catch (Exception ex)
        {
            return null;
        }
        return bu;
    }

    /**
     * 获取最大数据
     * @return
     */
    public  int getMax(){
        int i=0;
        try{
            i=shopsmenuDao.loadAll().size();
            List<tb_shopsmenu> tb=shopsmenuDao.loadAll();
            int j=tb.size();
            tb_shopsmenu tbi=tb.get(j-1);
            i=Integer.parseInt(String.valueOf(tbi.getId()));

        }catch (Exception ex){
            i=0;
        }
        return  i;
    }


    /**
     * 相等查询,where参数中可以添加多个相等的条件
     *
     */
/*    public void queryEq(){
        tb_flag user = flagDao.queryBuilder()
                .where(flagDao.Properties.Flag.eq("admin")).unique();
    }*/


/*    public void queryLike(){
        List<UserInfo> userList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.RealName.like("%lihy%")).list();
    }*/

/*    public void queryBetween(){
        //List<UserInfo> userList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Age.between(0, 10)).list();
        List<UserInfo> userList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Age.gt(10)).list();
        //gt:大于 lt:小于 ge:大于等于 le:小于等于

    }*/

}
