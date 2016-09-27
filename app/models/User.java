package models;

import java.util.UUID;

public class User {

    public String username;

    public String password;

    public static User authorize(String username, String password) {
        User user = new User();
        user.username = username;
        user.password = password;
        return user;
    }

    //napravi tu nesto pametnije :D
    public String createToken() {
        return this.username + "####token";
    }
}
