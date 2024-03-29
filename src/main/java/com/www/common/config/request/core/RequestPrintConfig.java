package com.www.common.config.request.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.www.common.config.request.RequestPrintProperties;
import com.www.common.data.constant.CharConstant;
import com.www.common.data.response.Result;
import com.www.common.utils.HttpUtils;
import com.www.common.utils.NumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * <p>@Description 请求响应报文打印配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 20:08 </p>
 */
@Slf4j
@Aspect
public class RequestPrintConfig {
    /** 请求响应报文打印AOP拦截自动配置属性类 **/
    @Autowired
    private RequestPrintProperties requestPrintProperties;
    /**
     * <p>@Description 构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 19:50 </p>
     */
    public RequestPrintConfig(){
        log.info("启动加载>>>开启controller请求响应报文打印配置");
    }
    /**
     * <p>@Description 设置controller切入点 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 21:22 </p>
     * @return void
     */
    @Pointcut(value="execution(public * com.www..*.controller..*.*(..))") // 切入点表达式
    public void pointcut() {}
    /**
     * <p>@Description 环绕切入，打印请求信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 21:22 </p>
     * @param pjd 方法切入点
     * @return 响应报文
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjd) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //获取当前请求
        HttpServletRequest request = HttpUtils.getRequest();
        String traceId = HttpUtils.getTraceId();
        Logger log = LoggerFactory.getLogger(pjd.getSignature().getClass().getName());// 初始化日志打印
        //获取方法全量名
        String controllerMethod = pjd.getSignature().toString();
        //处理方法返回的对象，输出为json数据
        String requestText = this.handleRequestParamToJson(pjd);
        try {
            Object result = pjd.proceed();// 执行目标方法
            if(result != null && result instanceof Result){
                ((Result)result).setTraceId(traceId);
            }
            stopWatch.stop();
            log.info("请求:{} 调用{}方法执行耗时:{}秒。请求报文:{}，响应报文:{}",request.getRequestURI(),
                    controllerMethod, NumberUtils.format3(stopWatch.getTotalTimeSeconds()),requestText,this.handleResultToJson(result));
            return result;
        }catch (Exception e){
            log.error("请求:{} 调用{}方法发生异常。请求报文:{}，异常信息:",request.getRequestURI(), controllerMethod,requestText,e);
            throw e;
        }
    }
    /**
     * <p>@Description 请求或返回数据中字符串长度大于length时替换成replaceContent的内容 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/26 22:56 </p>
     * @param value 大数据字段
     * @return java.lang.String 替换的成指定字符串
     */
    private String handleBigDataReplace(String value){
        return StringUtils.length(value) > requestPrintProperties.getLength() ? requestPrintProperties.getContent() : value;
    }
    /**
     * <p>@Description 处理方法返回的对象，输出为json数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/26 22:59 </p>
     * @param result 返回数据
     * @return java.lang.String json数据
     */
    public String handleResultToJson(Object result){
        if(BooleanUtils.isNotFalse(requestPrintProperties.getReplace())){
            Map<String,Object> resultMap = JSONObject.parseObject(JSON.toJSONString(result));
            resultMap = handleResultMap(resultMap);
            String resultJson = JSONObject.toJSONString(resultMap);
            return resultJson;
        }else {
            return JSON.toJSONString(result);
        }
    }
    /**
     * <p>@Description 处理返回的数据中字符串长度大于length时替换成replaceContent的内容 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/29 11:58 </p>
     * @param resultMap
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    private Map<String,Object> handleResultMap(Map<String,Object> resultMap){
        if(MapUtils.isEmpty(resultMap)){
            return null;
        }
        for (String key : resultMap.keySet()){
            Object value = resultMap.get(key);
            if(value != null && value instanceof JSONArray){
                JSONArray arr = (JSONArray)value;
                List<Object> list = arr.subList(0,arr.size());
                for (Object obj : list){
                    if(obj != null && obj instanceof JSONObject){
                        this.handleResultMap(((JSONObject) obj).getInnerMap());
                    }
                }
            }else if(value != null && value instanceof JSONObject){
                this.handleResultMap(((JSONObject) value).getInnerMap());
            }else if(value != null && value instanceof String) {
                value = this.handleBigDataReplace((String) value);
                resultMap.put(key,value);
            }
        }
        return resultMap;
    }
    /**
     * <p>@Description 处理请求方法的入参，转为json数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/26 23:04 </p>
     * @param pjd 方法切入点
     * @return java.lang.String json数据
     */
    private String handleRequestParamToJson(ProceedingJoinPoint pjd){
        Method targetMethod = ((MethodSignature) (pjd.getSignature())).getMethod();
        //获取方法参数名称
        Parameter[] paramName = targetMethod.getParameters();
        Class<?>[] paramType = targetMethod.getParameterTypes();
        Object[] paramValue = pjd.getArgs();// 获取方法参数值
        //获取请求参数集合并进行遍历拼接
        String requestText = CharConstant.LEFT_BRACE;
        for (int i = 0; i < paramName.length && i < paramValue.length && i < paramType.length;  i++) {
            String value = "";
            if(paramValue[i] != null && paramValue[i] instanceof  MultipartFile){
                value = paramValue[i] != null ? ((MultipartFile) paramValue[i]).getOriginalFilename() : CharConstant.EMPTY;

            }else {
                value = JSON.toJSONString(paramValue[i]);
            }
            value = this.handleBigDataReplace(value);
            if(i != paramName.length -1){
                requestText += paramName[i].getName() + CharConstant.EQUAL + value + CharConstant.COMMA;
            }else {
                requestText += paramName[i].getName() + CharConstant.EQUAL + value ;
            }
        }
        requestText += CharConstant.RIGHT_BRACE;
        return requestText;
    }
}
