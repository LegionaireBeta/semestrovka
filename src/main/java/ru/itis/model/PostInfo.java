package ru.itis.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PostInfo {
    private Long articleId;
    private Long userId;
    private String username;
    private String title;
    private String text;
    private String tag;
    private String date;
    private Long likes;
    private UUID uuid;
}
