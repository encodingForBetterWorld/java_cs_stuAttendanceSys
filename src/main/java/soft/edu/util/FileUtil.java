package soft.edu.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ClassName: FileUtil 
 * @Description: 截图和拷贝
 * @author wangsuqi
 * @date 2016年6月11日
 * @see 
 */
public class FileUtil {
	public static void FileCopy(String oldFile, String newfile) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(oldFile);
			fos = new FileOutputStream(newfile);
			int l = -1;
			byte[] b = new byte[1024];
			while ((l = fis.read(b)) != -1) {
				fos.write(b, 0, l);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				;
			}
		}
	}
}
