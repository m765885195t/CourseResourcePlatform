package com.motian.crp.intercepors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Component
public class CrpInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        log.info("preHandle::uri={}",request.getRequestURI());
        try {
            Boolean loginState = (Boolean) request.getSession().getAttribute(RESULT);
            if (loginState == null || !loginState) {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
