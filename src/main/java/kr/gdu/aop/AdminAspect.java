package kr.gdu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import kr.gdu.exception.ShopException;
import kr.gdu.logic.User;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class AdminAspect {
    
    @Around("execution(* kr.gdu.controller.Admin*.getUser*(..)) && args(..,session)")
    public Object adminIdCheck(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable {
        // 메서드 시그니처와 인자 확인
    	System.out.println("AdminAspect: Checking admin access for method "+ joinPoint.getSignature().getName());
        
        if (session == null) {
        	log.warn("AdminAspect: No HttpSession found in method arguments");
            throw new ShopException("[adminCheck] 세션이 필요합니다", "../user/login");
        }
        
        User loginUser = (User) session.getAttribute("loginUser");
        
        if (loginUser == null || !(loginUser instanceof User)) {
        	log.warn("AdminAspect: User not logged in");
            throw new ShopException("[adminCheck] 로그인이 필요합니다", "../user/login");
        }
        
        if (!loginUser.getUserid().equals("admin")) {
        	log.warn("AdminAspect: Non-admin user {} attempted access", loginUser.getUserid());
            throw new ShopException("[adminCheck] 관리자만 접근 가능합니다", "../item/list");
        }
        
        System.out.println("AdminAspect: Admin access granted for user "+ loginUser.getUserid());
        return joinPoint.proceed();
    }
}