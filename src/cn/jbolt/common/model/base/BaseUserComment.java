package cn.jbolt.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUserComment<M extends BaseUserComment<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setUserId(java.lang.Integer userId) {
		set("user_id", userId);
		return (M)this;
	}
	
	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

	public M setCommentId(java.lang.Integer commentId) {
		set("comment_id", commentId);
		return (M)this;
	}
	
	public java.lang.Integer getCommentId() {
		return getInt("comment_id");
	}

	public M setYes(java.lang.Boolean yes) {
		set("yes", yes);
		return (M)this;
	}
	
	public java.lang.Boolean getYes() {
		return get("yes");
	}

}