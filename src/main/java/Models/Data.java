package Models;

import com.example.deliveryservice_courseproject.User;

public class Data {
    private static final Data instance = new Data();

    private User user;

    public static Data getInstance(){
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
