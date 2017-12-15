package model.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import patrickstar.com.myapplication.model.tb_shopsmenu;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TB_SHOPSMENU".
*/
public class tb_shopsmenuDao extends AbstractDao<tb_shopsmenu, Long> {

    public static final String TABLENAME = "TB_SHOPSMENU";

    /**
     * Properties of entity tb_shopsmenu.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Shopsinfoid = new Property(1, String.class, "shopsinfoid", false, "SHOPSINFOID");
        public final static Property Dishname = new Property(2, String.class, "dishname", false, "DISHNAME");
        public final static Property Photo = new Property(3, String.class, "photo", false, "PHOTO");
        public final static Property Price = new Property(4, String.class, "price", false, "PRICE");
        public final static Property Remark = new Property(5, String.class, "remark", false, "REMARK");
    };


    public tb_shopsmenuDao(DaoConfig config) {
        super(config);
    }
    
    public tb_shopsmenuDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TB_SHOPSMENU\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"SHOPSINFOID\" TEXT," + // 1: shopsinfoid
                "\"DISHNAME\" TEXT," + // 2: dishname
                "\"PHOTO\" TEXT," + // 3: photo
                "\"PRICE\" TEXT," + // 4: price
                "\"REMARK\" TEXT);"); // 5: remark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TB_SHOPSMENU\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, tb_shopsmenu entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String shopsinfoid = entity.getShopsinfoid();
        if (shopsinfoid != null) {
            stmt.bindString(2, shopsinfoid);
        }
 
        String dishname = entity.getDishname();
        if (dishname != null) {
            stmt.bindString(3, dishname);
        }
 
        String photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindString(4, photo);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(5, price);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(6, remark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, tb_shopsmenu entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String shopsinfoid = entity.getShopsinfoid();
        if (shopsinfoid != null) {
            stmt.bindString(2, shopsinfoid);
        }
 
        String dishname = entity.getDishname();
        if (dishname != null) {
            stmt.bindString(3, dishname);
        }
 
        String photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindString(4, photo);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(5, price);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(6, remark);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public tb_shopsmenu readEntity(Cursor cursor, int offset) {
        tb_shopsmenu entity = new tb_shopsmenu( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // shopsinfoid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // dishname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // photo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // price
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // remark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, tb_shopsmenu entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setShopsinfoid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDishname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPhoto(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPrice(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRemark(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(tb_shopsmenu entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(tb_shopsmenu entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}