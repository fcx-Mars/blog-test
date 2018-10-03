package com.blogPro.website.login;

import java.util.List;
import cn.jbolt.common.model.Noluser;
import com.jfinal.core.Controller;

public class LoginWebsiteController extends Controller {

	public void index() {
		render("login.html");

	}
	public void formlog() {
		boolean result = validateCaptcha("captcha");
		List<Noluser> logins1 = Noluser.dao.findByNameAndPwd(getPara("noluser.nolname"), getPara("noluser.psw"));
		List<Noluser> logins2 = Noluser.dao.findByPhoneAndPwd(getPara("noluser.nolname"), getPara("noluser.psw"));
		List<Noluser> logins3 = Noluser.dao.findByEmailAndPwd(getPara("noluser.nolname"), getPara("noluser.psw"));
		System.out.println(logins1);
		if (!result) {
			System.out.println(result);
			render("faillogin.html");
		} else {
			if( logins1.size() > 0||logins2.size()> 0||logins3.size() >0){
				setSessionAttr("noluser.nolname", logins1.get(0).getNolname());
				setSessionAttr("loginInfo1", logins1);
				setSessionAttr("loginInfo2", logins2);
				setSessionAttr("loginInfo3", logins3);
				redirect("/website/homePage");
			}else{
				render("faillogin.html");
			}
		}
	}

	public void img() {
		renderCaptcha();
	}

}
