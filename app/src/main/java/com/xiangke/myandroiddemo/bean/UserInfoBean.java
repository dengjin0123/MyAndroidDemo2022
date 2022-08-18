package com.xiangke.myandroiddemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserInfoBean {
    /**
     *     "user": {
     *         "createBy": null,
     *         "searchValue": null,
     *         "createTime": "2021-12-06 14:16:39",
     *         "updateBy": null,
     *         "updateTime": null,
     *         "params": {},
     *         "remark": null,
     *         "userId": 1,
     *         "accountId": "123456789",
     *         "deptId": null,
     *         "sysType": "PC",
     *         "userName": "admin",
     *         "nickName": "管理员",
     *         "email": null,
     *         "phonenumber": "17673455833",
     *         "sex": "",
     *         "avatar": null,
     *         "faceId": null,
     *         "status": "1",
     *         "delFlag": "0",
     *         "loginIp": "10.10.10.7",
     *         "loginDate": 1645596721000,
     *         "dept": null,
     *         "roles": [{
     *             "createBy": null,
     *             "searchValue": null,
     *             "createTime": null,
     *             "updateBy": null,
     *             "updateTime": null,
     *             "params": {},
     *             "remark": null,
     *             "roleId": 1,
     *             "roleName": "超级管理员",
     *             "roleKey": "admin",
     *             "roleSort": "1",
     *             "dataScope": "1",
     *             "menuCheckStrictly": false,
     *             "deptCheckStrictly": false,
     *             "status": "1",
     *             "delFlag": null,
     *             "flag": false,
     *             "menuIds": null,
     *             "deptIds": null,
     *             "admin": true,
     *             "guest": false
     *         }],
     *         "roleIds": null,
     *         "postIds": null,
     *         "roleId": null,
     *         "realName": "admin",
     *         "fingerprintTwp": null,
     *         "fingerprintOne": null,
     *         "admin": true
     *     }
     */

    private int userId;
    private String accountId;
    private int deptId;
    private String sysType;
    private String userName;
    private String nickName;
    private String email;
    private String phonenumber;
    private String sex;
    private String avatar;
    private String faceId;
    private String status;
    private String delFlag;
    private String loginIp;
    private String loginDate;
    private DeptBean dept;
    private String roleIds;
    private String postIds;
    private String roleId;
    private String realName;
    private String fingerprintTwp;
    private String fingerprintOne;
    private boolean admin;
    private List<RolesBean> roles;

    public UserInfoBean(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public DeptBean getDept() {
        return dept;
    }

    public void setDept(DeptBean dept) {
        this.dept = dept;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFingerprintTwp() {
        return fingerprintTwp;
    }

    public void setFingerprintTwp(String fingerprintTwp) {
        this.fingerprintTwp = fingerprintTwp;
    }

    public String getFingerprintOne() {
        return fingerprintOne;
    }

    public void setFingerprintOne(String fingerprintOne) {
        this.fingerprintOne = fingerprintOne;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<RolesBean> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesBean> roles) {
        this.roles = roles;
    }

    public static class DeptBean implements Parcelable {
        /**
         * searchValue :
         * createBy :
         * createTime :
         * updateBy :
         * updateTime :
         * remark :
         * deptId : 103
         * parentId : 101
         * ancestors :
         * deptName : java后台
         * orderNum : 1
         * leader : zhangjl
         * phone :
         * email :
         * status : 0
         * delFlag :
         * parentName :
         */

        private String searchValue;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private int deptId;
        private int parentId;
        private String ancestors;
        private String deptName;
        private String orderNum;
        private String leader;
        private String phone;
        private String email;
        private String status;
        private String delFlag;
        private String parentName;

        public String getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(String searchValue) {
            this.searchValue = searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getAncestors() {
            return ancestors;
        }

        public void setAncestors(String ancestors) {
            this.ancestors = ancestors;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getLeader() {
            return leader;
        }

        public void setLeader(String leader) {
            this.leader = leader;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.searchValue);
            dest.writeString(this.createBy);
            dest.writeString(this.createTime);
            dest.writeString(this.updateBy);
            dest.writeString(this.updateTime);
            dest.writeString(this.remark);
            dest.writeInt(this.deptId);
            dest.writeInt(this.parentId);
            dest.writeString(this.ancestors);
            dest.writeString(this.deptName);
            dest.writeString(this.orderNum);
            dest.writeString(this.leader);
            dest.writeString(this.phone);
            dest.writeString(this.email);
            dest.writeString(this.status);
            dest.writeString(this.delFlag);
            dest.writeString(this.parentName);
        }

        public void readFromParcel(Parcel source) {
            this.searchValue = source.readString();
            this.createBy = source.readString();
            this.createTime = source.readString();
            this.updateBy = source.readString();
            this.updateTime = source.readString();
            this.remark = source.readString();
            this.deptId = source.readInt();
            this.parentId = source.readInt();
            this.ancestors = source.readString();
            this.deptName = source.readString();
            this.orderNum = source.readString();
            this.leader = source.readString();
            this.phone = source.readString();
            this.email = source.readString();
            this.status = source.readString();
            this.delFlag = source.readString();
            this.parentName = source.readString();
        }

        public DeptBean() {
        }

        protected DeptBean(Parcel in) {
            this.searchValue = in.readString();
            this.createBy = in.readString();
            this.createTime = in.readString();
            this.updateBy = in.readString();
            this.updateTime = in.readString();
            this.remark = in.readString();
            this.deptId = in.readInt();
            this.parentId = in.readInt();
            this.ancestors = in.readString();
            this.deptName = in.readString();
            this.orderNum = in.readString();
            this.leader = in.readString();
            this.phone = in.readString();
            this.email = in.readString();
            this.status = in.readString();
            this.delFlag = in.readString();
            this.parentName = in.readString();
        }

        public static final Creator<DeptBean> CREATOR = new Creator<DeptBean>() {
            @Override
            public DeptBean createFromParcel(Parcel source) {
                return new DeptBean(source);
            }

            @Override
            public DeptBean[] newArray(int size) {
                return new DeptBean[size];
            }
        };
    }

    public static class RolesBean implements Parcelable{
        /**
         *  "createBy": null,
         *  "searchValue": null,
         *  "createTime": null,
         *  "updateBy": null,
         *  "updateTime": null,
         *  "params": {},
         *  "remark": null,
         *  "roleId": 1,
         *  "roleName": "超级管理员",
         *  "roleKey": "admin",
         *  "roleSort": "1",
         *  "dataScope": "1",
         *  "menuCheckStrictly": false,
         *  "deptCheckStrictly": false,
         *  "status": "1",
         *  "delFlag": null,
         *  "flag": false,
         *  "menuIds": null,
         *  "deptIds": null,
         *  "admin": true,
         *  "guest": false
         */

        private String searchValue;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private int roleId;
        private String roleName;
        private String roleKey;
        private String roleSort;
        private String dataScope;
        private boolean menuCheckStrictly;
        private boolean deptCheckStrictly;
        private String status;
        private String delFlag;
        private boolean flag;
        private String menuIds;
        private String deptIds;
        private boolean admin;
        private boolean guest;

        public String getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(String searchValue) {
            this.searchValue = searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleKey() {
            return roleKey;
        }

        public void setRoleKey(String roleKey) {
            this.roleKey = roleKey;
        }

        public String getRoleSort() {
            return roleSort;
        }

        public void setRoleSort(String roleSort) {
            this.roleSort = roleSort;
        }

        public String getDataScope() {
            return dataScope;
        }

        public void setDataScope(String dataScope) {
            this.dataScope = dataScope;
        }

        public boolean isMenuCheckStrictly() {
            return menuCheckStrictly;
        }

        public void setMenuCheckStrictly(boolean menuCheckStrictly) {
            this.menuCheckStrictly = menuCheckStrictly;
        }

        public boolean isDeptCheckStrictly() {
            return deptCheckStrictly;
        }

        public void setDeptCheckStrictly(boolean deptCheckStrictly) {
            this.deptCheckStrictly = deptCheckStrictly;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public String getMenuIds() {
            return menuIds;
        }

        public void setMenuIds(String menuIds) {
            this.menuIds = menuIds;
        }

        public String getDeptIds() {
            return deptIds;
        }

        public void setDeptIds(String deptIds) {
            this.deptIds = deptIds;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public boolean isGuest() {
            return guest;
        }

        public void setGuest(boolean guest) {
            this.guest = guest;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.searchValue);
            dest.writeString(this.createBy);
            dest.writeString(this.createTime);
            dest.writeString(this.updateBy);
            dest.writeString(this.updateTime);
            dest.writeString(this.remark);
            dest.writeInt(this.roleId);
            dest.writeString(this.roleName);
            dest.writeString(this.roleKey);
            dest.writeString(this.roleSort);
            dest.writeString(this.dataScope);
            dest.writeByte(this.menuCheckStrictly ? (byte) 1 : (byte) 0);
            dest.writeByte(this.deptCheckStrictly ? (byte) 1 : (byte) 0);
            dest.writeString(this.status);
            dest.writeString(this.delFlag);
            dest.writeByte(this.flag ? (byte) 1 : (byte) 0);
            dest.writeString(this.menuIds);
            dest.writeString(this.deptIds);
            dest.writeByte(this.admin ? (byte) 1 : (byte) 0);
            dest.writeByte(this.guest ? (byte) 1 : (byte) 0);
        }

        public void readFromParcel(Parcel source) {
            this.searchValue = source.readString();
            this.createBy = source.readString();
            this.createTime = source.readString();
            this.updateBy = source.readString();
            this.updateTime = source.readString();
            this.remark = source.readString();
            this.roleId = source.readInt();
            this.roleName = source.readString();
            this.roleKey = source.readString();
            this.roleSort = source.readString();
            this.dataScope = source.readString();
            this.menuCheckStrictly = source.readByte() != 0;
            this.deptCheckStrictly = source.readByte() != 0;
            this.status = source.readString();
            this.delFlag = source.readString();
            this.flag = source.readByte() != 0;
            this.menuIds = source.readString();
            this.deptIds = source.readString();
            this.admin = source.readByte() != 0;
            this.guest = source.readByte() != 0;
        }

        public RolesBean() {
        }

        protected RolesBean(Parcel in) {
            this.searchValue = in.readString();
            this.createBy = in.readString();
            this.createTime = in.readString();
            this.updateBy = in.readString();
            this.updateTime = in.readString();
            this.remark = in.readString();
            this.roleId = in.readInt();
            this.roleName = in.readString();
            this.roleKey = in.readString();
            this.roleSort = in.readString();
            this.dataScope = in.readString();
            this.menuCheckStrictly = in.readByte() != 0;
            this.deptCheckStrictly = in.readByte() != 0;
            this.status = in.readString();
            this.delFlag = in.readString();
            this.flag = in.readByte() != 0;
            this.menuIds = in.readString();
            this.deptIds = in.readString();
            this.admin = in.readByte() != 0;
            this.guest = in.readByte() != 0;
        }

        public static final Creator<RolesBean> CREATOR = new Creator<RolesBean>() {
            @Override
            public RolesBean createFromParcel(Parcel source) {
                return new RolesBean(source);
            }

            @Override
            public RolesBean[] newArray(int size) {
                return new RolesBean[size];
            }
        };
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "userId=" + userId +
                ", accountId='" + accountId + '\'' +
                ", deptId=" + deptId +
                ", sysType='" + sysType + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", faceId='" + faceId + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginDate='" + loginDate + '\'' +
                ", dept=" + dept +
                ", roleIds='" + roleIds + '\'' +
                ", postIds='" + postIds + '\'' +
                ", roleId='" + roleId + '\'' +
                ", realName='" + realName + '\'' +
                ", fingerprintTwp='" + fingerprintTwp + '\'' +
                ", fingerprintOne='" + fingerprintOne + '\'' +
                ", admin=" + admin +
                ", roles=" + roles +
                '}';
    }
}
