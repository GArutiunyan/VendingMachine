package MyMapDB;

public class User {
    private int userId, money;
    private String userName, password;

    private UserType userType;

    public enum UserType {
        CUSTOMER, OPERATOR
    }

    public User(int userId, String userName, String password, int money, UserType userType) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.money = money;
        this.userType = userType;
    }


    public int getUserId() {
        return userId;
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