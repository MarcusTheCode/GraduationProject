package dk.bm.fido.auth.models;

public class UserAccount {

    private String name;
    private String username;
    private String email;
    private String id;

    public UserAccount(String name, String username, String email, String id) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
