
package com.iss.bubble.ejb;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@LoggingInterceptor
@Interceptor
public class LoggingInterceptorDetail {
    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        System.out.println("BEFORE: " + context.getParameters());
        Object result = context.proceed();
        System.out.println("AFTER: " + context.getMethod());

        return result;
    }
}
