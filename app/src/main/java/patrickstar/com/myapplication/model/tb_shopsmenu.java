package patrickstar.com.myapplication.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by sysadmin on 2017/12/11.
 */

//_id integer primary key ,shopsinfoid integer,dishname varchar(100),photo varchar(500),price decimal,remark varchar(300)
@Entity
public class tb_shopsmenu implements Serializable{
    @Id
    private Long id;
    private String shopsinfoid;
    private String dishname;
    private String photo;
    private String price;
    private String remark;

    public Long getId() {
        return id;
    }

    public String getShopsinfoid() {
        return shopsinfoid;
    }

    public String getDishname() {
        return dishname;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPrice() {
        return price;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShopsinfoid(String shopsinfoid) {
        this.shopsinfoid = shopsinfoid;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public tb_shopsmenu() {
        super();
    }

    @Generated(hash = 371255)
    public tb_shopsmenu(Long id, String shopsinfoid, String dishname, String photo, String price, String remark) {
        this.id = id;
        this.shopsinfoid = shopsinfoid;
        this.dishname = dishname;
        this.photo = photo;
        this.price = price;
        this.remark = remark;
    }

}
