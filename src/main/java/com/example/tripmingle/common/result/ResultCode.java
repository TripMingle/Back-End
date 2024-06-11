package com.example.tripmingle.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    EXAMPLE(200, "EX001", "예시 결과코드입니다.");
    private final int status;
    private final String code;
    private final String message;
}