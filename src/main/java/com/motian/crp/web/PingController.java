package com.motian.crp.web;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@RestController
public class PingController {

    @PostMapping(value = "/ping")
    public Map<String, Object> registered() {
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, true);
        return model;
    }
}
