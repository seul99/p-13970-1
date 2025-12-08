package com.back.global.rsData;

import com.back.domain.post.postComment.dto.PostCommentDto;

public record RsData<T>(String resultCode, String formatted, T data) {
    public RsData(String resultCode, String formatted){
        this(resultCode, formatted, null);
    }
}
