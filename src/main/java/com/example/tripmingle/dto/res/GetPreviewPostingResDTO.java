package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPreviewPostingResDTO {

    private String title;
    private String content;
    private String userNickName;
    private String userAgeRange;
    private String userGender;
    private String userNationality;

}
