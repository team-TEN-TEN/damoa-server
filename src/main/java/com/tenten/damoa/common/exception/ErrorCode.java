package com.tenten.damoa.common.exception;

import static com.tenten.damoa.stat.domain.Period.MAX_DATE_BASED_DATE;
import static com.tenten.damoa.stat.domain.Period.MAX_DATE_BASED_HOUR;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 400 - Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다. 입력값을 확인하고 다시 시도해주세요."),
    ARGUMENT_TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "입력 값이 예상된 형식이나 유형과 일치하지 않습니다."),
    METHOD_ARGUMENT_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "입력된 값이 유효하지 않습니다. 각 파라미터의 조건을 확인해 주세요."),
    INVALID_FORMAT_EXCEPTION(HttpStatus.BAD_REQUEST, "요청된 데이터의 형식이 잘못되었습니다. 유효한 JSON 형식을 사용해 주세요."),
    MISSING_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, "필수 요청 값이 누락되었거나 잘못되었습니다."),


    TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "지원하지 않는 타입입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),

    INVALID_TIME_UNIT_EXCEPTION(HttpStatus.BAD_REQUEST, "입력된 시간 단위가 유효하지 않습니다."),
    INVALID_METRICS_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "입력된 지표 종류가 유효하지 않습니다."),

    INVALID_PERIOD_EXCEPTION(HttpStatus.BAD_REQUEST, "입력된 기간의 종료일이 시작일보다 빠릅니다."),
    PERIOD_HOUR_LIMIT_EXCEED_EXCEPTION(HttpStatus.BAD_REQUEST, String.format("시간단위 조회 기간의 제한범위를 초과했습니다. (%d일)", MAX_DATE_BASED_HOUR)),
    PERIOD_DATE_LIMIT_EXCEED_EXCEPTION(HttpStatus.BAD_REQUEST, String.format("날짜단위 조회 기간의 제한범위를 초과했습니다. (%d일)", MAX_DATE_BASED_DATE)),

    /**
     * 404 - Not Found
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."),
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "발급된 인증 코드가 존재하지 않습니다."),

    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "인증번호가 일치하지 않습니다"),
    ACCOUNT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "존재하지 않는 계정입니다."),
    PASSWORD_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "비밀번호를 잘못 입력했습니다."),
    LOGIN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인을 먼저 해주세요."),
    INVALID_TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    /**
     * 403 - Forbidden
     */
    PRE_MEMBER_FORBIDDEN(HttpStatus.FORBIDDEN, "서비스 회원이 아닙니다. 이메일 인증을 먼저 해주세요."),

    /**
     * 409 - Conflict
     */
    ACCOUNT_CONFLICT(HttpStatus.CONFLICT, "이미 사용중인 계정입니다."),

    /**
     * 500 - Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}