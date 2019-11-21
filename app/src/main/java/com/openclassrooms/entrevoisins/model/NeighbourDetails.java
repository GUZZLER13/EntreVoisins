package com.openclassrooms.entrevoisins.model;

public class NeighbourDetails {

    private String address;
    private String mail;
    private String phone;
    private String description;
    private String descriptionTitle;


    public NeighbourDetails(String address, String mail, String phone, String description, String descriptionTitle) {
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.description = description;
        this.descriptionTitle = descriptionTitle;
    }

    public String getAddress() {
        return address;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionTitle() {
        return descriptionTitle;
    }

}
