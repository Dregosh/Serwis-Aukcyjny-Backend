package com.sda.serwisaukcyjnybackend.domain.user;

import com.sda.serwisaukcyjnybackend.config.app.converters.AddressConverter;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "display_name")
    private String displayName;
    @Convert(converter = AddressConverter.class)
    private Address address;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus accountStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;
    @Version
    private Long version;
    @Column(name = "promoted_auctions_count")
    private int promotedAuctionsCount;
    @OneToMany(mappedBy = "buyer")
    private List<Purchase> purchases = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Bid> bids = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Observation> observations = new ArrayList<>();
    @OneToMany(mappedBy = "seller")
    private List<Auction> issuedAuctions = new ArrayList<>();

    public User(String email, String firstName,
                String lastName, String displayName,
                Address address, AccountStatus accountStatus,
                AccountType accountType) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.address = address;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
        this.version = 0L;
        this.createdAt = LocalDateTime.now();
    }

    public boolean canCreatePromotedAuction(int maxPromotedValue) {
        return accountType == AccountType.PREMIUM && promotedAuctionsCount < maxPromotedValue;
    }

    public void addPromotedAuction() {
        this.promotedAuctionsCount++;
    }

    public void removePromotedAuction() {
        this.promotedAuctionsCount--;
    }
}
