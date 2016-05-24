package fr.opnf.androidtp5retrofit.Model;

/**
 * Created by OpenFieldMacMini on 24/05/2016.
 */
public class User {
    private int id;
    private String name;
    private String login;
    private int followers;
    private String avatar_url;
    private String html_url;

    public User(int id, String name, String login, int followers, String avatar_url, String html_url) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.followers = followers;
        this.avatar_url = avatar_url;
        this.html_url = html_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
