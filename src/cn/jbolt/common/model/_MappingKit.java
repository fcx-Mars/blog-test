package cn.jbolt.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("admuser", "id", Admuser.class);
		arp.addMapping("article", "id", Article.class);
		arp.addMapping("click", "id", Click.class);
		arp.addMapping("column", "id", Column.class);
		arp.addMapping("comment", "id", Comment.class);
		arp.addMapping("noluser", "id", Noluser.class);
		arp.addMapping("user_collect", "id", UserCollect.class);
		arp.addMapping("user_column", "id", UserColumn.class);
		arp.addMapping("user_comment", "id", UserComment.class);
		arp.addMapping("user_focus", "id", UserFocus.class);
	}
}

