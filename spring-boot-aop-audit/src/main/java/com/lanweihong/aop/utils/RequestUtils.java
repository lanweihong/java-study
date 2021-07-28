package com.lanweihong.aop.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/28 12:06
 */
public class RequestUtils {

    /**
     * 定义获取 ip 的 header
     */
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

    /**
     * 获取 IP 地址
     * @param request HttpServletRequest
     * @return IP
     */
    public static String getIpAddr(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (null != ip && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        String ip = request.getRemoteAddr();
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
