package com.example.tripmingle.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //Example
    EXAMPLE(400, "EX001", "예시 에러코드입니다."),
    KAKAO_BAD_REQUEST(400, "O001", "카카오 소셜 로그인에 잘못된 요청입니다."),
    KAKAO_ALREADY_EXISTS_USER(400, "O002", "이미 트립밍글에 존재하는 카카오 유저입니다."),
    KAKAO_NO_EXISTS_USER(400, "O003", "트립밍글에 존재하지 않는 카카오 유저입니다."),
    KAKAO_SERVER_ERROR(500, "O002", "카카오 소셜 로그인 서버 오류입니다."),
    USER_NOT_FOUND(404, "U001", "해당 유저가 존재하지 않습니다.");

    final private int status;
    final private String ErrorCode;
    final private String message;
}
