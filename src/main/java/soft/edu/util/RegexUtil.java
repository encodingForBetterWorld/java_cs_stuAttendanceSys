package soft.edu.util;


public class RegexUtil {
	/**
	 * @Description: 取文件名称
	 * @param @param str
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author wangsuqi
	 * @date 2016年6月26日
	 */
	public static String getFileName(String str) {
		System.out.println("reg"+str);
		String[] strs=str.split("/");
		str = strs[strs.length-1];
		System.out.println(str);
		return str;
    }
	public static String getFileExt(String str){
		return str.substring(str.lastIndexOf(".")+1);
	}
}
