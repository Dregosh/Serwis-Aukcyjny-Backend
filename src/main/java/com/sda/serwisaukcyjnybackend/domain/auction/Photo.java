package com.sda.serwisaukcyjnybackend.domain.auction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    public Photo(String name, Auction auction) {
        this.name = name;
        this.auction = auction;
    }
}
