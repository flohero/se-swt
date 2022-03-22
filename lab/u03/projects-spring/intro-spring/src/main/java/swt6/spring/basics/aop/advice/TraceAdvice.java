package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TraceAdvice {

    @Before("execution(public * swt6.spring.basics.aop.logic..*.*(..))")
    public void traceBefore(JoinPoint jp) {
        String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
        System.out.println("--> " + methodName);
    }

    @After("execution(public * swt6.spring.basics.aop.logic..*.*(..))")
    public void traceAfter(JoinPoint jp) {
        String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
        System.out.println("<-- " + methodName);
    }

    @Around("execution(public * swt6.spring.basics.aop.logic..*.find*ById(..))")
    public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName();

        System.out.println("==> " + methodName);
        Object result = pjp.proceed();
        System.out.println("<== " + methodName);

        return result;
    }

    @AfterThrowing(pointcut = "execution(public * swt6.spring.basics.aop.logic..*.*(..))", throwing = "exception")
    public void traceException(JoinPoint jp, Throwable exception) {
        String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();

        System.out.printf("##> %s\n\t threw %s\n", methodName, exception.getClass().getName());
    }
}
