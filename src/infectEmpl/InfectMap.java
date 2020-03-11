package infectEmpl;

import java.util.*;
import java.io.*;
import java.text.Collator;

public class InfectMap extends InfectStatistic {
	
	

	public static ArrayList<String> oneStatistic (String province)
	{
		String shuru = "D:/log/";
		String shuchu = "D:/output.txt";
		ArrayList<String> array = new ArrayList<String> ();
		String[] str = {"list","-log", shuru , "-out", shuchu};
		readList(str);
		readDirect();
		array.add(statistic.get(province + "感染患者"));
		array.add(statistic.get(province + "疑似患者"));
		array.add(statistic.get(province + "治愈"));
		array.add(statistic.get(province + "死亡"));
		return array;
	}
	
	public static Map<String , String> allStatistic (String type)
	{
		String shuru = "D:/log/";
		String shuchu = "D:/output.txt";
		String[] str = {"list","-log", shuru , "-out", shuchu};
		readList(str);
		readDirect();
		String allProvin[] = {"北京","天津","河北","辽宁","吉林","黑龙江","山东","江苏","上海","浙江","安徽","福建",
						"江西","广东","广西","海南","河南","湖南","湖北","山西","内蒙古","宁夏","青海","陕西","甘肃",
						"新疆","四川","贵州","云南","重庆","西藏","香港","澳门","台湾"};
		for(int i = 0 ; i < allProvin.length ; i++)
		{
			if(!statistic.containsKey(allProvin[i] + "疑似患者"))  //检查哈希表中是否已经存在该省份的数据了
			{
				initStatistic(allProvin[i]);
			}
		}
		Map<String , String> map = statistic;
		return map;
	}
}
