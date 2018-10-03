package com.blogPro.website.homePage;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.json.JFinalJson;
import com.jfinal.plugin.activerecord.Db;

import cn.jbolt.common.model.Click;
import cn.jbolt.common.model.Comment;
import cn.jbolt.common.model.Noluser;
import cn.jbolt.common.model.Article;
import cn.jbolt.common.model.UserComment;


public class BlogWebsiteController extends Controller {
	public void index() {
		islog();
		render("index.html");
	}
	
	public void art1() {
		islog();
		setAttr("mystr",getPara("title_id"));
		render("art1.html");
	}
//    判断是否登录
	public void islog() {
		if(getSessionAttr("noluser.nolname")==null) {
			setAttr("state", false);
		}else {
			Noluser noluser = Noluser.dao.findFirst("select * from noluser where nolname = '"+getSessionAttr("noluser.nolname")+"'");
			setAttr("state", true);
			setAttr("icon", noluser.getIcon());
		}
	}
//	退出登录方法
	public void logout() { 
		setSessionAttr("noluser.nolname", null);
		index();
	}
	public void art() {
		List<Click> clicks = Click.dao.find("select * from click where id = "+getPara("title_id")+"");
		System.out.println(clicks.isEmpty());
		if(clicks.isEmpty()) {                     
			new Click().set("id", getPara("title_id")).set("clickN", 1).set("comN",0).save();
		}else {
			Db.update("update click set clickN=clickN+1 where id = "+getPara("title_id")+"");
		}
		List<Article> article = Article.dao.find("select * from Article where id = "+getPara("title_id")+"");
		renderJson("{\"artData\":"+JFinalJson.getJson().toJson(article)+"}"); 
	}
	
	public void list() {
		List<Article> ifaces = Article.dao.find("select * from Article");
		renderJson("{\"jsonData\":"+JFinalJson.getJson().toJson(ifaces)+"}"); 	
	}	
	
	public void com_submit() {
		new Comment().set("comc",getPara("comc")).set("comt",getPara("comt")).set("comac", getPara("comac"))
		.set("comGN",0).set("comBN",0).save();
		Db.update("update click set comN="+ getPara("comN")+" where id = "+getPara("title_id")+"");
		renderJson(); 
	}
	
	public void com_list() {
		List<Comment> coms = Comment.dao.find("select * from comment where comac = "+getPara("title_id") +"");
		setAttr("comN",coms.size());
		renderJson("{\"comData\":"+JFinalJson.getJson().toJson(coms)+"}");
	}
	
	public void butGN() {
		int id = getParaToInt("id");
		List<UserComment> uc = UserComment.dao.find("select * from user_comment where user_id = 2 and comment_id =  "+getPara("id") +"");
		if(uc.isEmpty()) {
			Comment comment = Comment.dao.findById(id);
			int comGN = comment.getComGN();
			comGN =comGN + 1;
			comment.setComGN(comGN).update();
			new UserComment().set("user_id", 2).set("comment_id", id).set("yes",1).save();
		}	
		renderJson();
	}
	public void butBN() {
		int id = getParaToInt("id");
		List<UserComment> uc = UserComment.dao.find("select * from user_comment where user_id = 2 and comment_id =  "+getPara("id") +"");
		if(uc.isEmpty()) {
		Comment comment = Comment.dao.findById(id);
		int comBN = comment.getComBN();
		comBN =comBN + 1;
		comment.setComBN(comBN).update();
		new UserComment().set("user_id", 2).set("comment_id", id).set("yes",0).save();
		}
		renderJson();
	}
	
	public void uandc() {
		List<UserComment> uc = UserComment.dao.find("select * from user_comment where user_id = 2 and comment_id =  "+getPara("id") +"");
		List<UserComment> ucs = UserComment.dao.find("select * from user_comment where Yes=1 and comment_id =  "+getPara("id") +"");		
		List<UserComment> ucss = UserComment.dao.find("select * from user_comment where Yes=0 and comment_id =  "+getPara("id") +"");
		if(uc.isEmpty()) {
			renderJson("{\"comO\":1,\"numgn\":"+ucs.size()+",\"numbn\":"+ucss.size()+"}");
		}else {
			renderJson("{\"comO\":0,\"Yes\":"+uc.get(0).getYes()+",\"numgn\":"+ucs.size()+",\"numbn\":"+ucss.size()+"}");
		}
	}
}
