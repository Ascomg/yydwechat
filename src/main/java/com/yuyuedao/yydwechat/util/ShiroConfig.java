package com.yuyuedao.yydwechat.util;



import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

    //@Configuration
    public class ShiroConfig {
        //将自己的验证方式加入容器
//        @Bean
//        MyShiroRealm myShiroRealm() {
//            MyShiroRealm myShiroRealm = new MyShiroRealm();
//            myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//            return myShiroRealm;
//        }
//
//        //权限管理，配置主要是Realm的管理认证
//        @Bean
//        DefaultWebSecurityManager securityManager() {
//            DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//            manager.setRealm(myShiroRealm());
//            return manager;
//        }
//
//        //凭证匹配器（密码校验交给Shiro的SimpleAuthenticationInfo进行处理)
//        @Bean
//        public HashedCredentialsMatcher hashedCredentialsMatcher(){
//            HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//            hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//            hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
//            return hashedCredentialsMatcher;
//        }
//
//        // Filter工厂，设置对应的过滤条件和跳转条件
//        @Bean
//        ShiroFilterFactoryBean shiroFilterFactoryBean() {
//            ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
//            bean.setSecurityManager(securityManager());
//            Map<String, String> filterMap = new HashMap<String, String>();
//            // 登出
//            filterMap.put("/logout", "logout");
//            // swagger
//            filterMap.put("/swagger**/**", "anon");
//            filterMap.put("/webjars/**", "anon");
//            filterMap.put("/v2/**", "anon");
//            // 对所有用户认证
//            filterMap.put("/**", "authc");
//            // 登录
//            bean.setLoginUrl("/login");
//            // 首页
//            bean.setSuccessUrl("/index");
//            // 未授权页面，认证不通过跳转
//            bean.setUnauthorizedUrl("/403");
//            bean.setFilterChainDefinitionMap(filterMap);
//            return bean;
//        }
//
//        //开启shiro aop注解支持.
//        @Bean
//        public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
//            AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//            authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
//            return authorizationAttributeSourceAdvisor;
//        }
//
//        //shiro注解模式下，登录失败或者是没有权限都是抛出异常，并且默认的没有对异常做处理，配置一个异常处理
//        @Bean(name="simpleMappingExceptionResolver")
//        public SimpleMappingExceptionResolver
//        createSimpleMappingExceptionResolver() {
//            SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//            Properties mappings = new Properties();
//            mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
//            mappings.setProperty("UnauthorizedException","/403");
//            r.setExceptionMappings(mappings);  // None by default
//            r.setDefaultErrorView("error");    // No default
//            r.setExceptionAttribute("exception");     // Default is "exception"
//            return r;
//        }
//    }
}
