package com.dist.ars.aop;

import com.dist.ars.exceptions.IllegalParameterException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-AOP：接口的方法入参验证
 */
@Slf4j
@Aspect
@Component
public class ValidaMethodArgs {

    // 参数校验器
    private static final ExecutableValidator executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();

    @Pointcut("execution(* com.dist.ars.service..*(..))")
    public void exceptionAspect() {
    }

    @Around(value = "exceptionAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        // 获取目标对象
        Object targetObject = point.getTarget();

        // 获取目标方法
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();

        // 获取参数列表
        Object[] args = point.getArgs();

        // 进行入参校验
        Set<ConstraintViolation<Object>> inputArgsErrors = executableValidator.validateParameters(targetObject, targetMethod, args);

        // 如果结果不为空，抛出入参异常
        if (!inputArgsErrors.isEmpty()) {
            throw new IllegalParameterException(this.getErrorMsg(inputArgsErrors));
        }

        return point.proceed();
    }

    /**
     * 获取错误消息
     *
     * @param errors 错误信息列表
     * @return 错误信息
     */
    private String getErrorMsg(Set<ConstraintViolation<Object>> errors) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<Object> error : errors) {
            sb.append("参数【").append(error.getPropertyPath().toString()).append("】").append(error.getMessage()).append("；");
        }
        return sb.toString();
    }

}
