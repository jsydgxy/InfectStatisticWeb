package infectEmpl;
import java.util.*;
import java.io.*;
import java.text.Collator;

public class InfectMap extends InfectStatistic {
	
	public static File filename;
	public static String date;
	
	public static void judgeDate(String date) 
	{
		String str = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
	}
	
	public static ArrayList<String> getDate()
	{
		String shuru = "D:/log/";
		String shuchu = "D:/output.txt";
		String[] str = {"list","-log", shuru , "-out", shuchu};
		readList(str);
		readDirect();
		ArrayList<String> array = new ArrayList<String> ();	
		for(int i = fileArray.length - 7 ; i < fileArray.length; i++)
		{
			if(i<0) continue;
			else array.add(fileArray[i].getName().substring(0,10));
		}
		return array;
	}
	
	public static String latestDate()
	{
		String shuru = "D:/log/";
		String shuchu = "D:/output.txt";
		String[] str = {"list","-log", shuru , "-out", shuchu};
		readList(str);
		readDirect();
		return fileArray[fileArray.length - 1].getName().substring(0,10);
	}
	
	public static void reading()
	{
		String shuru = "D:/log/";
		String shuchu = "D:/output.txt";
		String[] str = {"list","-log", shuru , "-out", shuchu};
		readList(str);
		if(date != null)
			dateTime = date;
		readDirect();
	}

	public static ArrayList<String> oneStatistic (String province)
	{
		reading();
		ArrayList<String> array = new ArrayList<String> ();
		array.add(statistic.get(province + "感染患者"));
		array.add(statistic.get(province + "疑似患者"));
		array.add(statistic.get(province + "治愈"));
		array.add(statistic.get(province + "死亡"));
		return array;
	}
	
	public static Map<String, String> allStatistic (String date)
	{
		reading();
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
		sortMap = sortHashkey();
		countryStatic();
		for(String key : statistic.keySet())
		{
				System.out.println(key+" "+statistic.get(key));  //避免在最后多出一行			
		}
		return statistic;
		
	}
	
	public static ArrayList<String> compare(String province,String date) throws FileNotFoundException
	{
		reading();
		ArrayList<String> array = new ArrayList<String> ();
		for(int i = 0 ; i < 4 ; i ++)
		{
			array.add("0");
		}
		if(date == null)
			filename = fileArray[fileArray.length - 1];
		else
		for(int i = 0 ; i < fileArray.length ; i++)
		{
			if(fileArray[i] .getName().equals(date + ".log.txt" ))
			{
				filename = fileArray[i];
				break;
			}				
		}
		
		Scanner sc  = new Scanner(filename,"UTF-8");
		fileContent = new ArrayList<String>();
		while(sc.hasNext())
		{
			String str = sc.next();
			if (str.equals("//"))
				break;
			
			else fileContent.add(str);
		}					      
		sc.close();
		
		for(int i = 0; i < fileContent.size() - 2; i++)
		{
			if(fileContent.get(i + 1).equals("新增") &&  fileContent.get(i).equals(province))  //判别新增
			{
				String str = fileContent.get(i + 3);
				str = str.substring(0 , str.length() - 1); //截取人数
				if(fileContent.get(i + 2).equals("感染患者"))
				{
					int num = Integer.parseInt(array.get(0)) + Integer.parseInt(str);
					array.set(0, String.valueOf(num));
				}
				
				else if(fileContent.get(i + 2).equals("疑似患者"))
				{
					array.set(1, str);
				}
			}
			
			else if(fileContent.get(i + 2).equals("确诊感染") &&  fileContent.get(i).equals(province)) //判别为确诊感染或排除的情况
			{
				String str = fileContent.get(i + 3);
				str = str.substring(0 , str.length() - 1); //截取人数
				int num = Integer.parseInt(array.get(0)) + Integer.parseInt(str);
				array.set(0, String.valueOf(num));
			}
			
			
			else if(fileContent.get(i + 1).equals("死亡")) //判别为死亡的情况
			{
				if(fileContent.get(i).equals(province))
				{
					String str = fileContent.get(i + 2);
					str = str.substring(0 , str.length() - 1); //截取人数
					array.set(3, str);
				}
			}
			
			else if(fileContent.get(i + 1).equals("治愈")) //判别为治愈的情况
			{
				if(fileContent.get(i).equals(province))
				{
					String str = fileContent.get(i + 2);
					str = str.substring(0 , str.length() - 1); //截取人数
					array.set(2, str);
				}
			}
		}
		
		return array;
	}
	
	public static void main(String args[]) throws FileNotFoundException
	{
		ArrayList<String> a = compare("内蒙古" , null);
		for(String str: a)
			System.out.println(str);
		return;
	}
}
