package com.example.bbs.aspect;
import com.example.bbs.entity.Admin;
import com.example.bbs.entity.Information;
import com.example.bbs.entity.Plate;
import com.example.bbs.entity.User;
import com.example.bbs.service.AdminService;
import com.example.bbs.service.RoleService;
import com.example.bbs.service.UserService;
import com.example.bbs.utils.JjwtUtils;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class Authorization_backup {
    @Resource
    private  AdminService adminService;
    @Resource
    private   UserService userService;
    @Resource
    private   RoleService roleService;


    /**
     * 验证token与id是否一致
     */
    @Pointcut("@annotation(com.example.bbs.annotation.AuthChecker)  @args(Integer)")
    public void authCheckerPointcut(){}
    @Around(value = "authCheckerPointcut()")
    public Object checkUserAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request=((ServletRequestAttributes)(Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
        System.out.println("AuthChecker");

//        try {
////            boolean verify = verify(request, id);
////
////            if(!verify || id==null){
////                return Information.error(411,"非法操作");
////            }
////            return joinPoint.proceed();
////        } catch (Throwable e) {
////            e.printStackTrace();
////            return Information.error(500,"服务器错误");
////        }
        //System.out.println(joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }


    /**
     * 验证token与id是否一致等
     */
    @Pointcut("@annotation(com.example.bbs.annotation.RightChecker)&& args(id) ")
    public void rightCheckerPointcut(Integer id){}
    @Around(value = "rightCheckerPointcut(id)", argNames = "joinPoint,id")
    public Object checkRightAuth(ProceedingJoinPoint joinPoint,Integer id){
        HttpServletRequest request=((ServletRequestAttributes)(Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
        try {
            boolean isHost = verify(request, id);
            boolean isAdmin=verifyAdmin(request);
            boolean isManager=verifyManager(request);
            if(!isAdmin && !isHost && !isManager){
                return Information.error(411,"非法操作");
            }
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            return Information.error(500,"服务器错误");
        }
    }

    private  boolean verify(HttpServletRequest request,Integer id) throws Exception {
        Integer userId = getId(request);
        return userId != null && userId.equals(id);
    }


    private  boolean verifyAdmin(HttpServletRequest request) throws Exception {
        Integer userId=getId(request);
        if(userId==null){
            return false;
        }
        System.out.println(adminService);
        Admin admin = adminService.selectAdminById(userId);
        return admin!=null;
    }
    private   boolean verifyManager(HttpServletRequest request) throws Exception {
        Integer userId=getId(request);
        if(userId==null){
            return false;
        }
        List<Plate> plates = roleService.selectRoleByUserId(userId);
        return plates!=null && plates.size()>0;
    }
    private  boolean verifyUser(HttpServletRequest request)throws Exception{
        Integer userId=getId(request);
        if(userId==null){
            return false;
        }
        User user = userService.selectUserById(userId);
        return user!=null;
    }

    private  Integer getId(HttpServletRequest request)throws Exception{
        String token=request.getHeader("token");
        Integer id=null;
        if(token!=null){
            Claims claims = JjwtUtils.parseJWT(token);
            if(claims!=null){
                id= (Integer) claims.get("userId");
            }
        }
        return id;
    }
}
