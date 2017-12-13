package util;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;;
/***
 * Dwr反向推送类
 * @author liaot
 * @time 2017/12/09
 */
public class DwrPush {
	// 记录所有在线的ScriptSession
	private final static List<ScriptSession> SESSIONS = new ArrayList<ScriptSession>();
	static {
		// 得到DWR容器
		Container container = ServerContextFactory.get().getContainer();
		// 从DWR中得到会话管理器
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		// 创建一个会话监听器
		ScriptSessionListener ssl = new ScriptSessionListener() {

			@Override
			public void sessionCreated(ScriptSessionEvent e) {
				SESSIONS.add(e.getSession());
				System.out.println("user login " + SESSIONS.size());
			}

			@Override
			public void sessionDestroyed(ScriptSessionEvent e) {
				SESSIONS.remove(e.getSession());
				System.out.println("user exit " + SESSIONS.size());
			}
		};
		// 给管理器添加监听器
		manager.addScriptSessionListener(ssl);
	}

	public void push(String msg) {
		// 得到当前用户的ScriptSession
		ScriptSession seft = WebContextFactory.get().getScriptSession();
		System.out.println(seft);
		for (ScriptSession session : SESSIONS) {
			// 创建脚本 缓存 执行指定function 传递msg参数
			ScriptBuffer sb = new ScriptBuffer();
			//调用 jsp页面上 callback 方法
			sb.appendCall("callback", seft.getCreationTime() + " : " + msg);
			//将脚本添加到回话中
			session.addScript(sb);
		}
	}
}