package patrickstar.com.myapplication.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import model.greendao.gen.DaoMaster;
import model.greendao.gen.DaoSession;
import model.greendao.gen.tb_shopsinfoDao;
import patrickstar.com.myapplication.model.tb_shopsinfo;


/**
 * Created by sysadmin on 2017/12/11.
 */

public class DBShopsinfo {
    /**
     * 与greendao数据操作相关的几个类
     */

    public tb_shopsinfoDao shopsinfoDao;
    public Context context;
    public tb_shopsinfo shopsinfo;


    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;

    public void initDb() {
        helper = new DaoMaster.DevOpenHelper(context, "account.db", null);
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        shopsinfoDao = session.getTb_shopsinfoDao();

    }

    public DBShopsinfo(Context context1) {
        context = context1;
        initDb();
    }

    /**
     * 新增便签信息
     * 需要传入一个 flad对象
     *
     * @return int 编号
     */
    public int insert(tb_shopsinfo flag) {
        long flagid = shopsinfoDao.insert(flag);
        return Integer.parseInt(String.valueOf(flagid));
    }


    /**
     * 根据id删除便签
     *
     * @return boolean类型的数据
     */
    public boolean deleteById(Long id) {
        boolean bo = true;
        try {
            shopsinfoDao.deleteByKey(id);
        } catch (Exception ex) {
            bo = false;
        }
        return bo;
    }


    /**
     * 删除所有数据
     *
     * @return boolean 类型的数据
     */
    public boolean deleteAll() {
        boolean bo = true;
        try {
            shopsinfoDao.deleteAll();
        } catch (Exception ex) {
            bo = false;
        }
        return bo;
    }

    /**
     * 修改便签
     *
     * @param tbflag 便签对象
     * @return boolean 类型的数据
     */
    public boolean update(tb_shopsinfo tbflag) {

        if (tbflag == null) {
            return false;
        }
        shopsinfoDao.update(tbflag);
        return true;
    }

    /**
     * 查询所有便签信息
     *
     * @return tb_flag的list的集合
     */
    public List<tb_shopsinfo> query() {
        List<tb_shopsinfo> flagList = shopsinfoDao.loadAll();
        return flagList;
    }


    /**
     * 根据ID查询数据
     *
     * @return tb_flag 便签对象
     */
    public tb_shopsinfo find(int id) {
        tb_shopsinfo bu = null;
        try {

            bu = shopsinfoDao.queryBuilder().where(tb_shopsinfoDao.Properties.Id.eq(id)).build().list().get(0);
  /*      tb_flag tb=null;*/

            // tb=(tb_flag) bu.list().get(0);
        } catch (Exception ex) {
            return null;
        }
        return bu;
    }

    /**
     * 获取最大数据
     *
     * @return
     */
    public int getMax() {
        int i = 0;
        try {
            i = shopsinfoDao.loadAll().size();
            List<tb_shopsinfo> tb = shopsinfoDao.loadAll();
            int j = tb.size();
            tb_shopsinfo tbi = tb.get(j - 1);
            i = Integer.parseInt(String.valueOf(tbi.getId()));

        } catch (Exception ex) {
            i = 0;
        }
        return i;
    }

    /*
    *
    * 查询所有数据
    */
    public List findAll() {
        List<tb_shopsinfo> bu = null;
        try {

            bu = shopsinfoDao.queryBuilder().list();
        } catch (Exception ex) {
            return null;
        }
        return bu;
    }

    /*
    * 根据用户名查询数据
    */
    public tb_shopsinfo findbyUserid(String userid) {
        tb_shopsinfo bu = null;
        try {

            bu = shopsinfoDao.queryBuilder().where(tb_shopsinfoDao.Properties.Userid.eq(userid)).build().list().get(0);
        } catch (Exception ex) {
            return null;
        }
        return bu;
    }

    /*
      * 根据地址查询数据
      */
    public tb_shopsinfo findbyAddress(String address) {
        tb_shopsinfo bu = null;
        try {

            bu = shopsinfoDao.queryBuilder().where(tb_shopsinfoDao.Properties.Address.eq(address)).build().list().get(0);
        } catch (Exception ex) {
            return null;
        }
        return bu;
    }

    /*
 * 根据店名获取商家信息
 */
    public List getDataBySname(String tj){
        List<tb_shopsinfo> bu=null;
        try {

            bu = shopsinfoDao.queryBuilder().where(tb_shopsinfoDao.Properties.Sname.like(tj)).list();
        }
        catch (Exception ex)
        {
            return null;
        }
        return bu;
    }

    /*
    * 用户登录
   */
    public boolean Login(String userid, String pwd) {
        tb_shopsinfo bu = null;
        try {

            bu = shopsinfoDao.queryBuilder().where(tb_shopsinfoDao.Properties.Userid.eq(userid), tb_shopsinfoDao.Properties.Pwd.eq(pwd)).list().get(0);
        } catch (Exception ex) {
            return false;
        }
        if (bu.getId() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    public tb_shopsinfo shopsinfoById(Long id){
    tb_shopsinfo bu = null;
    try {

        bu = shopsinfoDao.queryBuilder().where(tb_shopsinfoDao.Properties.Id.eq(id)).list().get(0);
    } catch (Exception ex) {
        return null;
    }
    return  bu;

}

    /*
* 获取地址
*/
    public List getAddress(){
        List<String> bu=null;
        try {
            String[] str ={"address"};
            List list = shopsinfoDao.loadAll();
            List<String> addr =new ArrayList<String>();
            for (int i =0; i<=list.size()-1;i++){
                tb_shopsinfo tb = (tb_shopsinfo)list.get(i);
                addr.add(tb.getAddress());
            }

            for ( int i = 0 ; i < addr.size() - 1 ; i ++ ) {
                for ( int j = addr.size() - 1 ; j > i; j -- ) {
                    if (addr.get(j).equals(addr.get(i))) {
                        addr.remove(j);
                    }
                }
            }
            bu = addr;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return bu;
    }
    /*
 * 根据地址获取数据
 */
    public List getshopByAddress(String tj){
        List<tb_shopsinfo> bu=null;
        try {

            bu = shopsinfoDao.queryBuilder().where(tb_shopsinfoDao.Properties.Address.like("%"+tj+"%")).list();
        }
        catch (Exception ex)
        {
            return null;
        }
        return bu;
    }


    /*
* 根据名字获取id
*/
    public Long getidBysname(String tj){
        List<tb_shopsinfo> bu=null;
        try {

            bu = shopsinfoDao.queryBuilder().where(tb_shopsinfoDao.Properties.Sname.eq(tj)).list();
            if(bu==null){
                return  Long.parseLong("0");
            }
            else{
                tb_shopsinfo t = (tb_shopsinfo)bu.get(0);
                return  t.getId();
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return  Long.parseLong("0");
        }

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
