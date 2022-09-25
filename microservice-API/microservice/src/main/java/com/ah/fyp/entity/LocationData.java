package com.ah.fyp.entity;

import lombok.*;

import javax.persistence.*;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Table(name = "LOCATION")
    public class LocationData {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", nullable = false)
        private int id;

        @Column(name = "LATITUDE ", length = 50)
        public String latitude;

        @Column (name = "S_ID")
        private int sId;

        @Column (name = "NAME")
        private String  name;

        @Column(name = "LONGITUDE ", length = 50)
        public String longitude;

        @Column(name = "CRIME_TYPE ", length = 100)
        public String crimeType;

        @Column(name = "MONTH ")
        public String  month;
}
