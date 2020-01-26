package model;

import java.util.Objects;

public class Recipient {

    private String username;

    private String privateKey;

    public Recipient(String username, String privateKey) {
        this.username = username;
        this.privateKey = privateKey;
    }

    public String getUsername() {
        return username;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return username.equals(recipient.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
