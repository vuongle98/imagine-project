package com.vuongle.imaginepg.application.queries;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PostFilter implements Serializable {

    private String likeTitle;

    private String likeContent;

    private boolean featured;

    private UUID categoryId;
}
