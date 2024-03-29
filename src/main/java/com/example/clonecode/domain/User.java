package com.example.clonecode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
//@Table(name="user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private String phone;
    private String name;
    private String title;
    private String website;
    private String profileImgUrl;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<Post> postList;

    @Builder
    public User(String email, String password, String phone, String name, String title, String website, String profileImgUrl) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.title = title;
        this.website = website;
        this.profileImgUrl = profileImgUrl;
    }

    public void update(String password, String phone, String name, String title, String website) {
        this.password=password;
        this.phone=phone;
        this.name=name;
        this.title=title;
        this.website=website;
    }

    public void updateProfileImgUrl(String profileImgUrl){ this.profileImgUrl = profileImgUrl; }
}
