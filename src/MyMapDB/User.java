package MyMapDB;

import java.io.Serializable;

public class User implements Serializable {
    static final long SerialVersionUID = 4862342411707L;
    private int id, money;
    private String userName, password;

    private UserType userType;

    public enum UserType {
        CUSTOMER, OPERATOR
    }

    public User(int id, String userName, String password, int money, UserType userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.money = money;
        this.userType = userType;
    }


    public int getUserId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public UserType getUserType() {
        return userType;
    }
}