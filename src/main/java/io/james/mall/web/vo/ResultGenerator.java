package io.james.mall.web.vo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 500;

    public Result genSuccessResult() {
        return generateInternalResult(RESULT_CODE_SUCCESS, DEFAULT_SUCCESS_MESSAGE, null);
    }

    public Result genSuccessResult(String message) {
        return generateInternalResult(RESULT_CODE_SUCCESS, message, null);
    }

    public Result genSuccessResult(Object data) {
        return generateInternalResult(RESULT_CODE_SUCCESS, DEFAULT_SUCCESS_MESSAGE, data);
    }

    public Result genFailResult(String message) {
        return generateInternalResult(RESULT_CODE_SERVER_ERROR,
                StringUtils.isEmpty(message) ? DEFAULT_FAIL_MESSAGE : message, null);
    }

    public Result genErrorResult(int code, String message) {
        return generateInternalResult(code, message, null);
    }

    private Result generateInternalResult(int code, String message, Object data) {
        return new Result(code, message, data);
    }
}
