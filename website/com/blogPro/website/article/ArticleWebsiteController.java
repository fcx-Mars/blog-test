package com.blogPro.website.article;

import java.util.Date;

import com.jfinal.core.Controller;

import cn.jbolt.common.model.Article;
import cn.jbolt.common.model.Noluser;

public class ArticleWebsiteController extends Controller {
	static  ArticleWebsiteService service = ArticleWebsiteService.me;
	public void index() {
//		String nolname = getSessionAttr("noluser.nolname").toString();
//		System.out.println(nolname);
		if(getSessionAttr("noluser.nolname")==null) {
			redirect("login");
		}else {
			Noluser noluser = Noluser.dao.findFirst("select * from noluser where nolname = '"+getSessionAttr("noluser.nolname")+"'");
//			setAttr("state", true);
			setAttr("icon", noluser.getIcon());
			setAttr("userid", noluser.getId());
			render("index.html");
		}
	}
//	退出登录
	public void logout() {
		setSessionAttr("noluser.nolname", null);
		redirect("/website/homePage");
	}
	public void submit() {
		Article article = getModel(Article.class, "article");
		String author = getSessionAttr("author", "xiaoxiao");
		article.setAuthor(author);
		article.setCreatTime(new Date());
		//article.setIsPermit(true);
		boolean success = service.doSubmit(article);
		if(success) {
			redirect("/website/user");
//			render("/WEB-INF/view/admin/common/success.html");
		}else {
			render("/WEB-INF/view/admin/common/error.html");
		}
	}
}
