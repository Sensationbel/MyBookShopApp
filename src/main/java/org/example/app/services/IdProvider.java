package org.example.app.services;

import lombok.extern.log4j.Log4j;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Log4j
public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {
    public String provideId(Book book) {
        return this.hashCode() + "_" + book.hashCode();
    }

    private void initIdProvider() {
        log.info("provide INIT");
    }

    private void destroyIdProvider() {
        log.info("provide DESTROY");
    }

    private void defaultInit(){
        log.info("Default INIT in provided");
    }

    private void defaultDestroy(){
        log.info("Default DESTROY in provided");
    }

    @Override
    public void destroy() throws Exception {
        log.info("DisposableBean destroy invoked");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("provider afterPropertiesSet invoked");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("postProcessBeforeInitialization invoked by bean " + beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("postProcessAfterInitialization invoked by bean " + beanName);
        return null;
    }

    @PostConstruct
    public void postConstructIdProvider() {
        log.info("PostConstruct annotated method called");
    }

    @PreDestroy
    public void preDestroyIdProvider() {
        log.info("PreDestroy annotated method called");
    }

}
