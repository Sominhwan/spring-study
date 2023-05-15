package com.jojoldu.book.springboot.domain.posts;

import org.springframework.stereotype.Indexed;

import jakarta.annotation.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private String author;

    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
