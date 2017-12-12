package patrickstar.com.myapplication.model;

/**
 * Created by sysadmin on 2017/12/11.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class tb_shopsinfo {
    @Id
    private Long id;
    private String userid;
    private String pwd;
    private String sname;
    private String address;
    private String tel;
    private String photo;
    private String opentime;
    private String remark;

    public Long getId() {
        return id;
    }

    public String getSname() {
        return sname;
    }

    public String getAddress() {
        return address;
    }

    public String getTel() {
        return tel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getOpentime() {
        return opentime;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserid() {
        return userid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Generated(hash = 1732132575)
    public tb_shopsinfo(Long id, String userid, String pwd, String sname, String address, String tel, String photo, String opentime, String remark) {
        this.id = id;
        this.userid = userid;
        this.pwd = pwd;
        this.sname = sname;
        this.address = address;
        this.tel = tel;
        this.photo = photo;
        this.opentime = opentime;
        this.remark = remark;
    }

    @Generated(hash = 563845243)
    public tb_shopsinfo() {
    }
}
