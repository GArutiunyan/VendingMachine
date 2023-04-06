package dtos;


public class UserDTO {
    private Integer id, money;
    private String username, password;

    private UserType userType;

    public enum UserType {
        CUSTOMER, OPERATOR
    }

    public UserDTO(Integer id, Integer money, String username, String password, UserType userType) {
        this.id = id;
        this.money = money;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public UserDTO() {
        this.id = null;
        this.money = null;
        this.username = null;
        this.userType = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
