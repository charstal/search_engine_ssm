package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.common.utils.RedisTest;
import cn.edu.zucc.caviar.searchengine.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class SpellCheckServiceImpl {

    @Autowired
    private RedisTest redisUtil;
}
