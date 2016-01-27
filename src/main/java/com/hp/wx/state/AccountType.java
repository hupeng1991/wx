package com.hp.wx.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 账号类型
 * @author phu
 * 
 */
public class AccountType extends SysStateSupport {
 
	private static Map<String, SysStateSupport> stateMap = new HashMap<String, SysStateSupport>();

	public AccountType(String type_code, String short_desc) {
		this.type_code = type_code;
		this.short_desc = short_desc;
	}

	public static final AccountType SYS = new AccountType("sys", "系统管理员");
	public static final AccountType GUEST=new AccountType("gues","访问客户");
	static {
		stateMap.put(SYS.getType_code(), SYS);
		stateMap.put(GUEST.getType_code(), GUEST);
	}

	public static List<String[]> findStatesByAll() {
		return makeStatesList(stateMap);
	}

	public static AccountType codeToState(String type_code) {
		return (AccountType) stateMap.get(type_code);
	}

	public static String codeToDesc(String type_code) {
		return getDescOfCode(stateMap, type_code);
	}
}
