package com.example.warehouse.DO.track17;

import lombok.Data;

@Data
public class ProviderEventDo {

    private String time_iso;
    private String time_utc;
    private String description;
    private String location;
    private String stage;
    private Address address;

    class Address {
        private String country;
        private String state;
        private String city;
        private String street;
        private String postal_code;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPostal_code() {
            return postal_code;
        }

        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }
    }
}


