package com.example.MyBookShopAPP.security.blackList;

import com.example.MyBookShopAPP.config.RedisCacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BlackListingService {

    @CachePut(RedisCacheConfig.BLACKLIST_CACHE_NAME)
    public String blackListJwt(String jwt) {
        return jwt;
    }

    @Cacheable(value = RedisCacheConfig.BLACKLIST_CACHE_NAME, unless = "#result == null")
    public String getJwtBlackList(String jwt) {
        return null;
    }
}
