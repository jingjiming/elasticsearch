package com.alpha.common.exceptions;

import com.alpha.common.beans.enums.ResponseCode;
import com.alpha.common.beans.response.IResult;
import com.alpha.common.beans.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 通用异常处理类
 * 异常采用统一的信息格式表示
 * Created by jiming.jing on 2020/4/2.
 */
@RestControllerAdvice
public class CommonExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    @ExceptionHandler(BizException.class)
    public IResult handleException(BizException e) {
        logger.error("BizException[errorCode:{}, message:{}]", e.getErrorCode(), e.getMessage(), e);
        return ResultBean.badRequest(e.getErrorCode(), ResponseCode.ERROR.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public IResult handleException(DataAccessException e) {
        logger.error("DataAccessException[errorCode:{}, message:{}]", ResponseCode.DATA_ACCESS_ERROR.getCode(), e.getMessage(), e);
        return ResultBean.badRequest(ResponseCode.DATA_ACCESS_ERROR.getCode(), ResponseCode.DATA_ACCESS_ERROR.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public IResult handleException(Throwable e) {
        logger.error("Throwable:[errorCode:{}, message:{}]", ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        return ResultBean.badRequest(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public IResult handleException(MethodArgumentNotValidException e) {
        logger.error("MethodArgumentNotValidException[errorCode:{}, message:{}]", e.getMessage());

        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder stringBuilder = new StringBuilder(ResponseCode.PARAM_VALID_ERROR.getMessage());
            errors.forEach(error -> {
                FieldError fieldError = (FieldError) error;
                stringBuilder.append(",").append(fieldError.getDefaultMessage());
                logger.error("参数校验异常，{},{},{}", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
            });
            return ResultBean.badRequest(ResponseCode.PARAM_VALID_ERROR.getCode(), stringBuilder.toString());
        }

        return ResultBean.badRequest(ResponseCode.PARAM_VALID_ERROR.getCode(), ResponseCode.PARAM_VALID_ERROR.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public IResult handleException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> items = e.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder(ResponseCode.PARAM_VALID_ERROR.getMessage());
        items.forEach( item -> stringBuilder.append(item.getMessage()));
        return ResultBean.badRequest(ResponseCode.PARAM_VALID_ERROR.getCode(), stringBuilder.toString());
    }

    @ExceptionHandler(Exception.class)
    public IResult handleException(Exception e) {
        logger.error("Exception[message:{}]", e.toString());
        e.printStackTrace();
        return ResultBean.badRequest(ResponseCode.ERROR.getMessage());
    }

}
