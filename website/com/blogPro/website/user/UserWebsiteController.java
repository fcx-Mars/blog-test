package com.blogPro.website.user;

import java.io.Console;
import java.util.List;

import com.blogPro.website.user.UserWebsiteService;
import com.jfinal.core.Controller;
import com.jfinal.json.JFinalJson;
import com.jfinal.upload.UploadFile;

import cn.jbolt.common.model.Article;
import cn.jbolt.common.model.Noluser;



public class UserWebsiteController extends Controller {
	static UserWebsiteService service=UserWebsiteService.me;
	
	public static int myid = 0;
	public void index() {
		//String picUrl = getSessionAttr("picUrl");
		islog();
		render("index.html");
	}
	public void islog() {
		if(getSessionAttr("noluser.nolname")==null) {
			setAttr("state", false);
			System.out.println("未得到session");
		}else {
			Noluser noluser = Noluser.dao.findFirst("select * from noluser where nolname = '"+getSessionAttr("noluser.nolname")+"'");
			myid = noluser.getId();
			setAttr("state", true);
			setAttr("icon", noluser.getIcon());
			System.out.println("得到session");
			System.out.println(myid);
		}
	}
	public void art1() {
		setAttr("p",getPara("title_id"));
		render("article.html");
	}
	public void logout() {
		setSessionAttr("noluser.nolname", null);
		redirect("/website/homePage");
	}
	
	 //index中用的edit方法，修改头像
    public void edit() {
 
   	 render("headpicform.html");
    }
    
    //headpicform中用的ajaxForm方法提交头像
    public void ajaxForm() {
    	//render("common/success.html");
    	UploadFile file=getFile("file");
    	setAttr("filepath",file.getUploadPath()+"\\"+file.getFileName());
    	setAttr("picUrl","upload/temp/"+file.getFileName());
    	String photourl = "upload/temp/"+file.getFileName();
    	//setAttr("pic","file");
    	//setSessionAttr("picUrl","upload/temp/"+file.getFileName());
    	Noluser user = Noluser.dao.findFirst("select * from noluser where id = '"+myid+"'");
    	user.setIcon(photourl);
    	boolean success = service.doSubmit(user);
    	if(success) {
    		System.out.println("写入数据库成功");
    		render("common/success.html");
    		
    	}
    	renderJson() ;  
    	}
    
    
   //index中用的editmsg方法修改个人信息
    public void editmsg() {
    	 int id = getParaToInt(0);
    	 Noluser user  = Noluser.dao.findById(id);
    	 setAttr("user",user);
      	 render("msgform.html");
       }

  //msgform中用的submit方法，提交form修改的个人信息数据
    public void submit() {
   	 Noluser user=getModel(Noluser.class,"user");
   	 boolean success=service.doSubmit(user);//调用Service中的doSubmit方法
   	 if(success) {
   		 render("common/success.html");
   		 
   	 }else {
   		 render("common/error.html");
   	 }
    }
    
    //index中渲染主页网名等调用的homemsg方法
    public void homemsg() {
    	getResponse().addHeader("Access-Control-Allow-Origin", "*");
    	String nolname = getSessionAttr("noluser.nolname");
    	List<Noluser> user = Noluser.dao.find("select * from noluser where nolname = '"+nolname+"' limit 1");
    	renderJson("{\"userData\":"+JFinalJson.getJson().toJson(user)+"}"); 
    	
	}
    
    //index中渲染文章调用的homearticles方法
    public void homearticles() {
    	getResponse().addHeader("Access-Control-Allow-Origin", "*");
    //	String callback=getRequest().getParameter("callback");
		List<Article> ifaces = Article.dao.find("select * from article");
   // 	String jsonp=callback+"("+JFinalJson.getJson().toJson(ifaces)+")";
   // 	renderJson(jsonp);
	//	String jsonData=JFinalJson.getJson().toJson(ifaces);
	//	renderJson(jsonData);
		renderJson("{\"jsonData\":"+JFinalJson.getJson().toJson(ifaces)+"}"); 
    	
	}	
    
    //article中调用的article方法，渲染文章详情页面
    public void detailarticle() {
		List<Article> article = Article.dao.find("select * from article where id = "+getPara("title_id")+"");
		renderJson("{\"artData\":"+JFinalJson.getJson().toJson(article)+"}"); 
	}
}
