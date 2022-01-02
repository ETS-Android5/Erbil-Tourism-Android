package com.example.assiment;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class Place {
    private int id;
    private String name;
    private String address;
    private byte[] image;
    private String idFB;



    public Place(String name, String address, byte[] image, int id , String idFB) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.id = id;
        this.idFB = idFB;
    }


    public String getIdFB() {
        return idFB;
    }

    public void setIdFB(String idFB) {
        this.idFB = idFB;
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

    public String getPrice() {
        return address;
    }

    public void setPrice(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
