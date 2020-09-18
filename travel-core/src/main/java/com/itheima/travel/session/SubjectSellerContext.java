package com.itheima.travel.session;

import com.itheima.travel.constant.Constant;
import com.itheima.travel.utils.EmptyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description：当前后台对象登录会话处理
 */
@Log4j2
@Component
public class SubjectSellerContext {

    //可以从spring容器中拿到当前session对象
    @Autowired
    HttpSession session;

    //可以从spring容器中拿到当前request对象
    @Autowired
    HttpServletRequest request;

    //是否是测试环境
    @Value("${context.test}")
    String isTest;

    /**
     * @Description 全局对象获得
     */
    public SubjectSeller getSubject() {
        String accessToken = null;
        if (Boolean.valueOf(isTest)){
            accessToken ="subjectSeller10101";
        }else {
            accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        }
        Object subject = session.getAttribute(accessToken);
        SubjectSeller subjectSeller = null;
        if (!EmptyUtil.isNullOrEmpty(subject)&&subject instanceof SubjectSeller){
            subjectSeller = (SubjectSeller) subject;
        }
        log.info("【获得】sessionId:"+session.getId());
        if (Boolean.valueOf(isTest)&&!EmptyUtil.isNullOrEmpty(subjectSeller)){
            subjectSeller.setToken("subjectSeller10101");
        }
        return subjectSeller;
    }

    /**
     * @Description 全局对象放入session
     */
    public  void createdSubject(String key,Object Principal) {
        if (Boolean.valueOf(isTest)){
            session.setAttribute("subjectSeller10101", Principal);
        }else {
            session.setAttribute(key, Principal);
        }
        log.info("【创建】sessionId:"+session.getId());
    }

    /**
     * @Description 全局对象session清理
     */
    public  void deleteSubject() {
        String accessToken = null;
        if (Boolean.valueOf(isTest)){
            accessToken ="subjectSeller10101";
        }else {
            accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        }
        session.removeAttribute(accessToken);
        log.info("【删除】sessionId:"+session.getId());
    }

    /**
     * @Description 是否全局对象存在
     */
    public  boolean existSubject(){
        SubjectSeller subjectSeller = getSubject();
        if (EmptyUtil.isNullOrEmpty(subjectSeller)){
            return false;
        }
        return true;
    }

}
