package com.example.bbs.annotation;

import java.lang.annotation.*;

/**
 * 判断token与id是否统一
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthChecker {
}
