package lib;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.zcy.util.EcUtil;

public class ConvertThread extends Thread {
	private static Logger logger = LogManager.getLogger(ConvertThread.class);
	public static Queue<String> queue = new LinkedList<String>();

	private boolean firstInit = false;

	public ConvertThread(boolean firstInit) {
		this.firstInit = firstInit;
	}

	public void run() {

		if (firstInit) {

			for (int i = 0; i < 1; i++) {

				if (queue.size() > 0) {
					String doc = queue.poll();
					if (EcUtil.isValid(doc)) {
						new pdf2swf().convertToPdf(doc);
					}
				}
			}
		} else {
			if (queue.size() > 0) {
				new pdf2swf().convertToPdf(queue.poll());
			}
		}

	}
}
