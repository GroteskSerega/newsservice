package com.news.newsservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "news")
public class News {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @EqualsAndHashCode.Include
    private UUID id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

//    @BatchSize(size = 20)
//    @OneToMany(mappedBy = "news",
//            cascade = CascadeType.ALL)
//    @Builder.Default
//    private List<Comment> comments =
//            new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createAt;

    @UpdateTimestamp
    private Instant updateAt;

    @Formula("(SELECT COUNT(*) FROM comments c WHERE c.news_id = id)")
    private Integer countComments;
}
