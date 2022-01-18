package com.example.callofdutybestbuilds.Objects;

public class Weapon_meta {
    private String weapon_name;
    private String att1;
    private String att2;
    private String id;

    public Weapon_meta(String weapon_name, String att1, String att2, String id) {
        this.weapon_name = weapon_name;
        this.att1 = att1;
        this.att2 = att2;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeapon_name() {
        return weapon_name;
    }

    public String getAtt1() {
        return att1;
    }

    public String getAtt2() {
        return att2;
    }

    public void setWeapon_name(String weapon_name) {
        this.weapon_name = weapon_name;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }
}
