package lib;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class pdf2swf extends common{
	private static Logger logger = LogManager.getLogger(pdf2swf.class);

	HttpServletRequest request = null;

	public pdf2swf(HttpServletRequest request){
		this.request = request;
	}
	
	
	public pdf2swf(){
		super();
	}

	public String convertToPdf(String doc) {

		String s = "Error converting document, make sure the conversion tool is installed and that correct user permissions are applied to the SWF Path directory" + getDocUrl();

		if (doc.endsWith(".pdf")) {
			long start = new Date().getTime();
			logger.info("Convert " + doc + " to swf start");

			String pdfFilePath = separate(getConfig("path.pdf", "")) + doc;
			String swfFilePath = separate(getConfig("path.swf", "")) + doc + ".swf";

			new File(swfFilePath).getParentFile().mkdirs();

			String command = getConfig("cmd.conversion.singledoc", "");

			command = command.replace("{path.pdf}", separate(getConfig("path.pdf", "")));
			command = command.replace("{path.swf}", separate(getConfig("path.swf", "")));
			command = command.replace("{pdffile}", doc);

			System.out.println(command);
			try {
				if (!isNotConverted(pdfFilePath, swfFilePath)) {
					return "[Converted]";
				}
			} catch (Exception ex) {
				return "Error," + ex.toString();
			}

			boolean return_var = false;

			System.out.println(command);
			return_var = exec(command);
			if (return_var) {
				s = "[Converted]";
			}

			long end = new Date().getTime();
			logger.info("Convert " + doc + " to swf end and cost: " + (end - start) / (60 * 1000) + " mimutes");
		}
		new ConvertThread(false).start();

		return s;

	}

	public String convert(String doc, String page) {
		String pdfFilePath	= separate(getConfig("path.pdf", "")) + doc;
		String swfFilePath	= separate(getConfig("path.swf", "")) + doc + page + ".swf";

		String command		= getConfig("cmd.conversion.singledoc", "");
		if("true".equals(getConfig("splitmode", "")))
			command = getConfig("cmd.conversion.splitpages", "");

		command = command.replace("{path.pdf}", separate(getConfig("path.pdf", "")));
		command = command.replace("{path.swf}", separate(getConfig("path.swf", "")));
		command = command.replace("{pdffile}", doc);

		try {
			if (!isNotConverted(pdfFilePath ,swfFilePath)) {
				return "[Converted]";
			}
		} catch (Exception ex) {
			return "Error," + ex.toString();
		}

		boolean return_var = false;

		if("true".equals(getConfig("splitmode", ""))){
			String pagecmd = command.replace("%", page);
			pagecmd = pagecmd + " -p " + page;

			return_var = exec(pagecmd);
			int hash = getStringHashCode(command);
			HttpSession session = request.getSession(true);
			String constr = "CONVERSION_" + hash;
			String conversion = (String) session.getAttribute(constr);
            if(conversion == null){
                exec(command);
                session.setAttribute(constr, "true");
            }
		}else
			return_var = exec(command);
		String s = "Error converting document, make sure the conversion tool is installed and that correct user permissions are applied to the SWF Path directory" + 
					getDocUrl();
		if(return_var) {
			s="[Converted]";
		}
		return s;
	}

	public boolean isNotConverted(String pdfFilePath,String swfFilePath) throws Exception {
		File f = new File(pdfFilePath);
		if (!f.exists()) {
			throw new Exception("Document does not exist");
		}
		if (swfFilePath == null) {
			throw new Exception("Document output file name not set");
		} else {
			File s = new File(swfFilePath);
			if (!s.exists()) {
				return true;
			} else {
				if(f.lastModified() > s.lastModified()) return true;
			}
		}
		return false;
	}
}