package com.voldy.myblog.utils;

import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * Bean验证异常处理
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
public class ConstraintViolationExceptionHandler {
    /**
     * 获取批量异常信息
     * @param e
     * @return
     */
    public static String getMessage(ConstraintViolationException e) {
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msgList.add(constraintViolation.getMessage());
        }
        return StringUtils.join(msgList.toArray(), ";");
    }
}
