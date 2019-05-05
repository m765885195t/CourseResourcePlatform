package com.motian.crp.intercepors;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Component
public class CrpInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("re" + response + "mo" + modelAndView + " ja" + handler);
        Map<String, Object> model = modelAndView.getModelMap();
        if (model.containsKey("data")) {
            model.put("count", ((List) model.get("data")).size());
        }
        model.put("code", 0);
    }
}
