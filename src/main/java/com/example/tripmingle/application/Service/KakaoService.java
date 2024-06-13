package com.example.tripmingle.application.Service;

import com.example.tripmingle.common.error.ErrorResponse;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.common.utils.KakaoProperties;
import com.example.tripmingle.dto.etc.KakaoUserInfo;
import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.res.KakaoLoginResDTO;
import com.example.tripmingle.dto.res.KakaoTokenResDTO;
import com.example.tripmingle.entity.Refresh;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.KakaoLoginFeignClientForAccessTokenPort;
import com.example.tripmingle.port.out.KakaoLoginFeignClientPort;
import com.example.tripmingle.port.out.RefreshPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.example.tripmingle.common.constants.JwtConstants.ACCESS_TOKEN;
import static com.example.tripmingle.common.constants.JwtConstants.REFRESH_TOKEN;
import static com.example.tripmingle.common.constants.LoginType.KAKAO;
import static com.example.tripmingle.common.error.ErrorCode.KAKAO_ALREADY_EXISTS_USER;
import static com.example.tripmingle.common.error.ErrorCode.KAKAO_NO_EXISTS_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class KakaoService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final KakaoProperties kakaoProperties;
    private final UserPersistPort userPersistPort;
    private final RefreshPort refreshPort;
    private final KakaoLoginFeignClientPort kakaoLoginFeignClientPort;
    private final KakaoLoginFeignClientForAccessTokenPort kakaoLoginFeignClientForAccessTokenPort;

    @Transactional
    public TokenDTO loginKakaoAccount(String kakaoAccessToken) {
        String redefineAccessToken = "Bearer " + kakaoAccessToken;
        KakaoLoginResDTO kakaoLoginResDTO = kakaoLoginFeignClientPort.getKakaoUserInfo(redefineAccessToken);

        User user = joinStateCheckAndReturnUser(kakaoLoginResDTO);
        userNullCheck(user);

        String accessToken = jwtUtils.createJwtToken(user.getEmail(), user.getRole(), user.getLoginType(), ACCESS_TOKEN.getMessage(), jwtUtils.getAccessTokenExpTime());
        String refreshToken = jwtUtils.createJwtToken(user.getEmail(), user.getRole(), user.getLoginType(), REFRESH_TOKEN.getMessage(), jwtUtils.getRefreshTokenExpTime());
        addRefreshEntity(user.getEmail(), refreshToken, jwtUtils.getRefreshTokenExpTimeByToken(refreshToken));

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // TODO 닉네임, 국가에 대한 입력을 어떻게 할지 논의 필요 -> 카카오 액세스 토큰을 주면서 해당 데이터를 같이 넘겨줄지,
    //  아니면 나중에 해당 유저 정보를 수정하는 걸로 할지에 대해서
    private User joinStateCheckAndReturnUser(KakaoLoginResDTO kakaoLoginResDTO) {
        KakaoUserInfo kakaoUserInfo = kakaoLoginResDTO.getKakaoUserInfo();
        User user = null;
        if (!userPersistPort.existsByEmail(kakaoUserInfo.getEmail())) {
            user = userPersistPort.save(User.builder()
                    .email(kakaoUserInfo.getEmail())
                    .password(passwordEncoder.encode("TripMingle " + kakaoUserInfo.getEmail() + kakaoLoginResDTO.getKakaoId()))
                    .role("ROLE_USER")
                    .loginType(KAKAO.getLoginType())
                    .oauthId(kakaoLoginResDTO.getKakaoId())
                    .nickName("default")
                    .ageRange(kakaoUserInfo.getAgeRange())
                    .gender(kakaoUserInfo.getGender())
                    .name(kakaoUserInfo.getName())
                    .nationality("default")
                    .phoneNumber(kakaoUserInfo.getPhoneNumber())
                    .build());
        } else {
            user = userPersistPort.findByEmail(kakaoUserInfo.getEmail()).orElseThrow(() -> new ErrorResponse(KAKAO_ALREADY_EXISTS_USER));
        }
        return user;
    }

    private void userNullCheck(User user) {
        if (user == null) {
            throw new ErrorResponse(KAKAO_NO_EXISTS_USER);
        }
    }

    private void addRefreshEntity(String email, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        Refresh refreshEntity = Refresh.builder()
                .email(email)
                .refreshToken(refresh)
                .expiration(date.toString())
                .build();
        refreshPort.save(refreshEntity);
    }

    public KakaoTokenResDTO getKakaoAccessToken(String code) {
        return kakaoLoginFeignClientForAccessTokenPort.getToken(kakaoProperties.getKakaoGrantType(), kakaoProperties.getKakaoClientId(), kakaoProperties.getKakaoSecretKey(), code);
    }
}
