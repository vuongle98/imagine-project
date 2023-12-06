package com.vuongle.imaginepg.application.queries;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostFilter implements Serializable {

    private String likeTitle;

    private String likeContent;

    private boolean featured;
}
