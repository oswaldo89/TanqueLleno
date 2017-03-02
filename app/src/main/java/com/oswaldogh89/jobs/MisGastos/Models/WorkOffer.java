package com.oswaldogh89.jobs.MisGastos.Models;

import com.oswaldogh89.jobs.Utils.AuditoriaModel;

/**
 * Created by oswaldogh89 on 12/02/17.
 */

public class WorkOffer extends AuditoriaModel{

    private double Amount;
    private int Likes;
    private String Telephone;
    private String UrlImage;
    private int Category;

    public int getCategory() {
        return Category;
    }

    public void setCategory(int category) {
        Category = category;
    }

    public String getUrlImage() {
        return UrlImage;
    }

    public void setUrlImage(String urlImage) {
        UrlImage = urlImage;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }
}
