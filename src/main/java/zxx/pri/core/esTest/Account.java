package zxx.pri.core.esTest;

/**
 * @author zxx
 * @desc
 * @createTime 2019-10-16-下午 12:19
 */
public class Account {
    private Integer account_number;
    private Integer balance;
    private String firstname;
    private String lastname;
    private Integer age;
    private Character gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;

    public Account() {
    }

    public Account(Integer account_number, Integer balance, String firstname, String lastname, Integer age, Character gender, String address, String employer, String email, String city, String state) {
        this.account_number = account_number;
        this.balance = balance;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.employer = employer;
        this.email = email;
        this.city = city;
        this.state = state;
    }

    public Integer getAccount_number() {
        return account_number;
    }

    public void setAccount_number(Integer account_number) {
        this.account_number = account_number;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
