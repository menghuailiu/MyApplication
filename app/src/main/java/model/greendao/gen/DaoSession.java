package model.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import patrickstar.com.myapplication.model.tb_shopsinfo;
import patrickstar.com.myapplication.model.tb_shopsmenu;

import model.greendao.gen.tb_shopsinfoDao;
import model.greendao.gen.tb_shopsmenuDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tb_shopsinfoDaoConfig;
    private final DaoConfig tb_shopsmenuDaoConfig;

    private final tb_shopsinfoDao tb_shopsinfoDao;
    private final tb_shopsmenuDao tb_shopsmenuDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tb_shopsinfoDaoConfig = daoConfigMap.get(tb_shopsinfoDao.class).clone();
        tb_shopsinfoDaoConfig.initIdentityScope(type);

        tb_shopsmenuDaoConfig = daoConfigMap.get(tb_shopsmenuDao.class).clone();
        tb_shopsmenuDaoConfig.initIdentityScope(type);

        tb_shopsinfoDao = new tb_shopsinfoDao(tb_shopsinfoDaoConfig, this);
        tb_shopsmenuDao = new tb_shopsmenuDao(tb_shopsmenuDaoConfig, this);

        registerDao(tb_shopsinfo.class, tb_shopsinfoDao);
        registerDao(tb_shopsmenu.class, tb_shopsmenuDao);
    }
    
    public void clear() {
        tb_shopsinfoDaoConfig.getIdentityScope().clear();
        tb_shopsmenuDaoConfig.getIdentityScope().clear();
    }

    public tb_shopsinfoDao getTb_shopsinfoDao() {
        return tb_shopsinfoDao;
    }

    public tb_shopsmenuDao getTb_shopsmenuDao() {
        return tb_shopsmenuDao;
    }

}