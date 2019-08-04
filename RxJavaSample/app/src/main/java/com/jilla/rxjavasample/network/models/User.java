package com.jilla.rxjavasample.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class User {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("address")
    @Expose
    public Address address;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("website")
    @Expose
    public String website;

    @SerializedName("company")
    @Expose
    public Company company;

    public User() {
    }

    public User(Integer id, String name, String username, String email, Address address, String phone, String website, Company company) {
        super();
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    public class Address {

        @SerializedName("street")
        @Expose
        public String street;

        @SerializedName("suite")
        @Expose
        public String suite;

        @SerializedName("city")
        @Expose
        public String city;

        @SerializedName("zipcode")
        @Expose
        public String zipcode;

        @SerializedName("geo")
        @Expose
        public Geo geo;

        public Address() {
        }

        public Address(String street, String suite, String city, String zipcode, Geo geo) {
            super();
            this.street = street;
            this.suite = suite;
            this.city = city;
            this.zipcode = zipcode;
            this.geo = geo;
        }

        public class Geo {
            @SerializedName("lat")
            @Expose
            public String lat;

            @SerializedName("lng")
            @Expose
            public String lng;

            public Geo() {
            }

            public Geo(String lat, String lng) {
                super();
                this.lat = lat;
                this.lng = lng;
            }
        }
    }

    public class Company {

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("catchPhrase")
        @Expose
        public String catchPhrase;

        @SerializedName("bs")
        @Expose
        public String bs;

        public Company() {
        }

        public Company(String name, String catchPhrase, String bs) {
            super();
            this.name = name;
            this.catchPhrase = catchPhrase;
            this.bs = bs;
        }
    }
}



