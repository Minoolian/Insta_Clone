package com.example.clonecode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    @ManyToOne
    @JsonIgnoreProperties({"postList"})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() { this.createDate = LocalDateTime.now(); }

    @Builder
    public Comment(String text, User user, Post post) {
        this.text = text;
        this.user = user;
        this.post = post;
    }
}
