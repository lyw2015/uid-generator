package com.baidu.fsg.uid.controller;

import com.baidu.fsg.uid.core.UidGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ProjectName uid-generator
 * @Author Laiyw
 * @CreateTime 2022/5/6 10:36
 * @Description TODO
 */

@RestController
public class GeneratorController {

    @Resource(name = "cachedUidGenerator")
    private UidGenerator generator;

    @GetMapping("/generator")
    public Long generator() {
        return generator.getUID();
    }
}
