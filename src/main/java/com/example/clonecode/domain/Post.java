package com.example.clonecode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String postImgUrl;
    private String tag;
    private String text;

    @ManyToOne
    private User user;

    @Transient
    private long likesCount;

    private LocalDateTime createDate;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post")
    private List<Likes> likeList;

    // DB insert 시 함께 실행. jpa auditing 고려
    @PrePersist
    public void createDate(){ this.createDate = LocalDateTime.now();}

    @Builder
    public Post(String postImgUrl, String tag, String text, User user) {
        this.postImgUrl = postImgUrl;
        this.tag = tag;
        this.text = text;
        this.user = user;
    }

    public void update(String tag, String text) {
        this.tag = tag;
        this.text = text;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }
}
