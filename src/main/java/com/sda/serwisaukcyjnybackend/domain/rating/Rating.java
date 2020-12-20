package com.sda.serwisaukcyjnybackend.domain.rating;

import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @Column(name = "sellers_rating")
    private Integer sellersRating;

    @Column(name = "sellers_comment")
    private String sellersComment;

    @Column(name = "sellers_rating_date")
    private LocalDateTime sellersRatingDate;

    @Column(name = "buyers_rating")
    private Integer buyersRating;

    @Column(name = "buyers_comment")
    private String buyersComment;

    @Column(name = "buyers_rating_date")
    private LocalDateTime buyersRatingDate;

    public Rating(Purchase purchase) {
        this.purchase = purchase;
    }
}
