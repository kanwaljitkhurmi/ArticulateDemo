//package com.mb.demo.articulateDemo.controller;
//import java.util.Map;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//import com.amazonaws.xray.entities.Subsegment;
//import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
//
//@Aspect
//@Component
//public class XRayInspector extends AbstractXRayInterceptor {    
//    @Override    
//    protected Map<String, Map<String, Object>> generateMetadata(ProceedingJoinPoint proceedingJoinPoint, Subsegment subsegment) {      
//        return super.generateMetadata(proceedingJoinPoint, subsegment);    
//    }    
//  
//  @Override    
//  @Pointcut("execution(* com.mb.demo.articulateDemo.controller.ArticulateController.*(..))")    
//  public void xrayEnabledClasses() {}
//}