package com.example.proyectonavigation.model;

public class FoodActivities {

    String id;
    String name ;
    String latitud;
    String longitud;
    String phone;
    String price ;
    String photo;
    String horario_ini ;
    String horario_fin;

    public FoodActivities(String id, String name, String latitud, String longitud, String phone, String price, String photo, String horario_ini, String horario_fin) {
        this.id = id;
        this.name = name;
        this.latitud = latitud;
        this.longitud = longitud;
        this.phone = phone;
        this.price = price;
        this.photo = photo;
        this.horario_ini = horario_ini;
        this.horario_fin = horario_fin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getHorario_ini() {
        return horario_ini;
    }

    public void setHorario_ini(String horario_ini) {
        this.horario_ini = horario_ini;
    }

    public String getHorario_fin() {
        return horario_fin;
    }

    public void setHorario_fin(String horario_fin) {
        this.horario_fin = horario_fin;
    }

    @Override
    public String toString() {
        return "FoodActivities{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", phone='" + phone + '\'' +
                ", price='" + price + '\'' +
                ", photo='" + photo + '\'' +
                ", horario_ini='" + horario_ini + '\'' +
                ", horario_fin='" + horario_fin + '\'' +
                '}';
    }
}