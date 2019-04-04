package dk.easv.friendsv2.Model;

import android.location.Location;

import java.io.Serializable;

public class BEFriend implements Serializable {

    private int m_id;
    private String m_name;
    private String m_phone;
    private Boolean m_isFavorite;
    private String m_email;
    private String m_url;
    private Location m_location;
    private byte[] m_image;

    public BEFriend(int id, String name, String phone, Boolean isFavorite, String email, String url) {
        m_id = id;
        m_name = name;
        m_phone = phone;
        m_isFavorite = isFavorite;
        m_email = email;
        m_url = url;
    }

    public BEFriend(int id, String name, String phone) {
        this(id, name, phone, false, null, null);
    }

    public BEFriend(int id, String name, String phone, Boolean isFavorite) {
        this(id, name, phone, isFavorite, null, null);
    }

    public String getPhone() {
        return m_phone;
    }

    public String getName() {
        return m_name;
    }

    public Boolean isFavorite() {
        return m_isFavorite;
    }

    public String getEmail() {
        return m_email;
    }

    public String getUrl() {
        return m_url;
    }

    public byte[] getImage() {
        return m_image;
    }

    public int getId() {
        return m_id;
    }

    public Location getLocation() {
        return m_location;
    }

    public void setName(String m_name) {
        this.m_name = m_name;
    }

    public void setPhone(String m_phone) {
        this.m_phone = m_phone;
    }

    public void setFavorite(Boolean m_isFavorite) {
        this.m_isFavorite = m_isFavorite;
    }

    public void setEmail(String m_email) {
        this.m_email = m_email;
    }

    public void setUrl(String m_url) {
        this.m_url = m_url;
    }

    public void setLocation(Location m_location) {
        this.m_location = m_location;
    }

    public void setImage(byte[] m_image) {
        this.m_image = m_image;
    }
}
