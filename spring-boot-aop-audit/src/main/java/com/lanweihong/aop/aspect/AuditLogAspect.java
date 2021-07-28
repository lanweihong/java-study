package com.lanweihong.aop.aspect;

import cn.hutool.json.JSONUtil;
import com.lanweihong.aop.annotation.AuditLog;
import com.lanweihong.aop.entity.AuditLogDO;
import com.lanweihong.aop.service.IAuditLogService;
import com.lanweihong.aop.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 审计日志注解切面处理
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 13:35
 */
@Aspect
@Component
@Slf4j
public class AuditLogAspect {

    private final IAuditLogService operationLogService;

    @Autowired
    public AuditLogAspect(IAuditLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    /**
     * 定义注解切点，注解拦截
     */
    @Pointcut("@annotation(com.lanweihong.aop.annotation.AuditLog)")
    public void auditLog() {
    }

    /**
     * 前置通知，方法调用前被调用
     * 除非抛出一个异常，否则这个通知不能阻止连接点之前的执行流程
     */
    @Before("auditLog()")
    public void doBefore() {
        log.info("进入 @Before......");
    }

    /**
     * 环绕增强
     * 先执行 pjp.proceed() 然后进入 @Before，然后执行主方法，回到 @Around 的返回值
     * @param pjp ProceedingJoinPoint
     * @return
     */
    @Around("auditLog()")
    public Object doAround(ProceedingJoinPoint pjp) {
        log.info("进入 @Around......");
        Object result = null;
        try {
            result = pjp.proceed();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info("退出 @Around......");
        return result;
    }

    /**
     * 后置通知，如果切入点抛出异常，则不会执行
     */
    @AfterReturning(pointcut = "auditLog()", returning = "keys")
    public void doAfterReturning(JoinPoint joinPoint, Object keys) {
        log.info("进入 @AfterReturning......");
        // 获取 RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "获取 RequestAttributes 为空");
        // 获取 HttpServletRequest
        HttpServletRequest request = (HttpServletRequest)requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 获取注解参数信息
        AuditLogDO operationLogDO = new AuditLogDO();
        // 从切入点通过反射获取切入点方法
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 获取执行方法，e.g. public void com.lanweihong.aop.controller.UserController.test()
        Method method = signature.getMethod();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();

        // 获取注解
        AuditLog operationLog = method.getAnnotation(AuditLog.class);
        if (null != operationLog) {
            operationLogDO.setModule(operationLog.module().getModuleCode());
            operationLogDO.setOperationType(operationLog.type().getTypeCode());
            // 解析表达式
            String desc = parseDescriptionExpression(operationLog.description(), args);
            operationLogDO.setDescription(desc);
        }

        // TODO 请自己获取用户信息
        operationLogDO.setUserId(null)
                .setUserName("");

        // 获取执行方法，e.g. test
        String methodName = method.getName();
        // 获取请求类名，e.g. com.lanweihong.aop.controller.UserController
        String className = joinPoint.getTarget().getClass().getName();
        Assert.notNull(request, "获取 HttpServletRequest 为空");
        // 获取请求参数
        Map<String, String> parameters = convertParameterMap(request.getParameterMap());

        log.info("请求类名与方法：" + className + "." + methodName + "()");
        log.info("请求参数：" + JSONUtil.toJsonStr(parameters));

        // 获取 IP
        String ip = RequestUtils.getIpAddr(request);
        String userAgent = request.getHeader("user-agent");
        operationLogDO.setIp(ip)
                .setUserAgent(userAgent);
        operationLogService.addOperationLog(operationLogDO);
    }

    /**
     * 异常返回通知，切入点抛出异常后执行
     * @param joinPoint 切入点
     * @param e 异常
     */
    @AfterThrowing(pointcut = "auditLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        // 异常可在这里处理......
        log.error("进入 @AfterThrowing...错误：" + e.getMessage());
    }

    @After(value = "auditLog()")
    public void doAfter() {
        log.info("进入 @After......");
    }

    private Map<String, String> convertParameterMap(Map<String, String[]> map) {
        Map<String, String> result = new HashMap<>(2);
        for (String key : map.keySet()) {
            result.put(key, map.get(key)[0]);
        }
        return result;
    }

    /**
     * 解析 SpEL 表达式，从参数中获取数据并生成描述
     * @param descriptionExpression 描述表达式（使用 SpEL 表达式）
     * @param args 参数
     * @return
     */
    private String parseDescriptionExpression(String descriptionExpression, Object[] args) {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        Expression expression = spelExpressionParser.parseExpression(descriptionExpression, new TemplateParserContext());
        return expression.getValue(new StandardEvaluationContext(args), String.class);
    }
}
