package com.onecoderspace.base.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.onecoderspace.base.filter.LoginFilter;

@Configuration
public class ShiroConfigration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfigration.class);

    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();


    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);//保存10天
        return simpleCookie;
    }

    /**
     * Cookie management object;
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        logger.debug("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("kPv59vyqzj00x11LXJZTjJ2UHW48jzHN"));
        return cookieRememberMeManager;
    }


    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy("shiroFilter");
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        proxy.setTargetFilterLifecycle(true);
        filterRegistration.setFilter(proxy);

        filterRegistration.setEnabled(true);
        //filterRegistration.addUrlPatterns("/*");// 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
        return filterRegistration;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myShiroRealm());
        manager.setRememberMeManager(rememberMeManager());
        manager.setCacheManager(ehCacheManager());
        return manager;
    }


    /**
      * ShiroFilterFactoryBean handles the problem of intercepting resource files.
      * Note: A single ShiroFilterFactoryBean configuration is either error or not, thinking that
      * Initiate ShiroFilterFactoryBean when injecting: SecurityManager
      * <p>
      * Filter Chain definition description
      * 1, a URL can be configured with multiple filters, separated by commas
      * 2. When multiple filters are set, all verifications are passed and are considered as passed.
      * 3, some filters can specify parameters, such as perms, roles
      */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        logger.debug("ShiroConfigration.getShiroFilterFactoryBean()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        HashMap<String, javax.servlet.Filter> loginFilter = new HashMap<>();
        loginFilter.put("loginFilter", new LoginFilter());
        shiroFilterFactoryBean.setFilters(loginFilter);


        filterChainDefinitionMap.put("/login/submit", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");

        //配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/", "user");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
        filterChainDefinitionMap.put("/**", "loginFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
      * shiro cache manager;
      * Need to be injected into the corresponding other entity class:
      * 1, security manager: securityManager
      * Visible securityManager is the core of the entire shiro;
      *
      * @return
      */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
