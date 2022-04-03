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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties({"postList"})
    private User user;

    @Transient
    private long likesCount;

    @Transient
    private boolean likesState;

    private LocalDateTime createDate;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Likes> likesList;

    @OrderBy("id")
    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    // DB insert 시 함께 실행. jpa auditing 고려
    @PrePersist
    public void createDate(){ this.createDate = LocalDateTime.now();}

    @Builder
    public Post(String postImgUrl, String tag, String text, User user, long likesCount) {
        this.postImgUrl = postImgUrl;
        this.tag = tag;
        this.text = text;
        this.user = user;
        this.likesCount = likesCount;
    }

    public void update(String tag, String text) {
        this.tag = tag;
        this.text = text;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public void updateLikesState(boolean likesState) { this.likesState = likesState; }
}
