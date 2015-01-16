package vell.bibi.vvip.service;

public class StringService {
	private StringService(){}
	private static StringService service = null;
	public static StringService getInstance() {
		if(service == null)
			service = new StringService();
		return service;
	}
	
	private final String helpStr = 
		  "1)查询余额\n"
		+ "  1/ls/余额/ye\n"
		+ "2)查询历史账单\n"
		+ "  '2' 或 '2 YYYY/MM/DD' 或 '2 YYYY/MM/DD YYYY/MM/DD'\n"
		+ "  '2'可替换成'H'或'h'\n"
		+ "  例如：\n"
		+ "    2 2014/09/01 \n"
		+ "      表示查询从2014年9月1号到现在的所有历史记录 \n"
		+ "    2 2014/09/01 2014/09/02\n"
		+ "      表示查询从2014年9月1号到2014年9月2号的所有历史记录\n "
		+ "3)查询用户信息\n"
		+ "  3/i/info";
	
	private final String bindHelpStr = 
		  "请先绑定微信号\n"
		+ "  bind 账户id 名字 手机号 学号\n"
		+ "    账户id需要找管理员取得，名字里必须出现字符，学号为12位数字，手机号为11位数字。三个信息至少需要填写一个\n"
		+ "  例如：\n"
		+ "    bind 001 贺磊 18392605732 201106080120\n"
		+ "  绑定的所有信息必须完全对应，否则绑定失败，一个账户只能绑定一个微信号";
	
	private final String unBindHelpStr = 
		  "解除绑定\n"
		+ "  unbind 账户id\n"
		+ "  只能解绑当前微信号，账户id必须正确";
	
	private final String adminHelpStr = 
		  "1)查询余额\n"
		+ "  ls -a\n"
		+ "    查询全部用户余额信息\n"
		+ "  ls -A\n"
		+ "    查询总余额\n"
		+ "2)查询账户信息\n"
		+ "  3/i/info 学号/名字/手机号后四位\n"
		+ "  例如：\n"
		+ "    3 贺磊\n"
		+ "    info 5732\n"
		+ "3)添加记录\n"
		+ "  4/addrecord/ar 账户id +/-金额 备注\n"
		+ "    账户id可以查询账户信息获得\n"
		+ "  例如：\n"
		+ "    4 001 +100\n"
		+ "      表示给001账户充值100元人名币\n"
		+ "    ar 002 -100\n"
		+ "      表示002账户消费了100元人名币\n"
		+ "4)添加用户\n"
		+ "  adduser/au 名字 手机号 学号 \n"
		+ "    名字里必须出现字符，学号为12位数字，手机号为11位数字。三个信息至少需要填写一个\n"
		+ "  例如：\n"
		+ "    au 贺磊 18392605732 201106080120 \n"
		+ "5)冻结用户\n"
		+ "  freeze 账户id 备注\n"
		+ "  unfreeze 账户id 备注";
			
	public String getHelpStr() {
		return helpStr;
	}

	public String getUnBindHelpStr() {
		return unBindHelpStr;
	}

	public String getAdminHelpStr() {
		return adminHelpStr;
	}

	public String getBindHelpStr() {
		return bindHelpStr;
	}
}
