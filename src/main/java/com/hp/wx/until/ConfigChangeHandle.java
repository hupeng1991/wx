package com.hp.wx.until;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sd4324530.fastweixin.api.config.ConfigChangeNotice;
import com.github.sd4324530.fastweixin.handle.AbstractApiConfigChangeHandle;

public class ConfigChangeHandle extends AbstractApiConfigChangeHandle {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigChangeHandle.class);
    
    @Override
    public void configChange(ConfigChangeNotice notice) {
        LOG.debug("收到通知.....");
        LOG.debug(notice.toJsonString());
    }
}
