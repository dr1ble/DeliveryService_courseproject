package Models;

import com.example.deliveryservice_courseproject.Client;
import com.example.deliveryservice_courseproject.Courier;
import com.example.deliveryservice_courseproject.User;

public class Data {
    private static final Data instance = new Data();

    private User user;
    private Client client;
    private Courier courier;

    public static Data getInstance(){
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
