package com.brookes.webservices.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.brookes.aepsi.webservices.IDataExchange;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * TestWS -- Tests the data exchange web services. 
 * <p>Accepts a single, optional command-line argument specifying the path and file name to an import file. If not present, the import isn't tested.
 * Tests the following WS methods:
 * <ul>
 * <li>getCredentials -- Validates this user in AEPSI and returns a credentials string to be used in subsequent calls.</li>
 * <li>getChildCount -- Returns a count of the number of children that are in this account.</li>
 * <li>getChildList -- returns an XML stream of the GUIDs of all the children in this account. The XML is in the form:<br> 
 * &lt;aepsi&gt;&lt;guid&gt;147&lt;/guid&gt;&lt;guid&gt;139&lt;/guid&gt;&lt;guid&gt;55&lt;/guid&gt;&lt;/aepsi&gt;</li>
 * <li>getChildren -- returns the XML for the requested children. A list of GUIDS is passed in the above format. 
 * The resulting XML is in this format:<br>
 * &lt;children&gt;&lt;child&gt;
	&lt;family_name&gt;Villegas Rodriguez&lt;/family_name&gt;
	&lt;given_name&gt;Gregorio&lt;/given_name&gt;
	&lt;address1&gt;2415 N. Huston Ave.&lt;/address1&gt;
	&lt;city&gt;Grand Island&lt;/city&gt;
	&lt;state&gt;NE&lt;/state&gt;
	&lt;zip&gt;68803&lt;/zip&gt;
	&lt;classroom&gt;Partc&lt;/classroom&gt;
	&lt;local_id1&gt;3954342596&lt;/local_id1&gt;
	&lt;guid&gt;147&lt;/guid&gt;
	&lt;dob&gt;2003-05-05&lt;/dob&gt;
	&lt;pre_weeks&gt;0&lt;/pre_weeks&gt;
	&lt;gender&gt;M&lt;/gender&gt;
	&lt;level&gt;1&lt;/level&gt;
	&lt;dev_status&gt;Dev. Delay or Disability&lt;/dev_status&gt;
	&lt;/children&gt;	
</li>

 * <li>putChildren -- Adds or updates child records in AEPSI. The import file is in the above format and must conform to the DTD provided.</li>
 * </ul>
 * </p>
 * 
 * <p>Copyright (c) 2008 by Brookes Publishers, Inc.</p>
 * 
 * @author Steve Carton (steve.carton@retrievalsystems.com)
 */
public class TestWS {
  private static Logger LOG_ = Logger.getLogger(TestWS.class);

	public static void main(String args[]) throws Exception {

		ClassPathResource cpr = new ClassPathResource("log4j.properties");
  	PropertyConfigurator.configure(cpr.getFile().getAbsolutePath());
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
		
		IDataExchange ws = (IDataExchange) context.getBean("webService");
		TestParameters tp = (TestParameters) context.getBean("TestParameters");
		
		LOG_.debug("Starting AEPSI Test Harness");
		LOG_.debug("Output Path: "+tp.getOutputPath());
		
		String creds = ws.getCredentials(tp.getUserId(), tp.getPassword(), tp.getSourceProvider());
		File path = new File(tp.getOutputPath());
		LOG_.debug("Credentials: " + creds);

		if (tp.methods.contains("getChildCount")) {
			String count = ws.getChildCount(creds);
			LOG_.debug("Child count: " + count);
		}

		String list = null; 
		if (tp.methods.contains("getChildList")) {
			list = ws.getChildList(creds);
			LOG_.debug("Child GUID list: " + list);
			string2File(path, "guids.txt", list);
		}

		if (tp.methods.contains("getChildren")) {
			String export = ws.getChildren(creds, list);
			LOG_.debug("Child XML Export: " + export);
			string2File(path, "export.txt", export);
		}

		if (tp.methods.contains("putChildren")) {
			if (tp.getImportFile()!=null && tp.getImportFile().length() > 0) {
				String children = new String(file2bytes(new File(tp.getImportFile())));
				String stats = ws.putChildren(creds, children);
				LOG_.debug("Child XML Import results: " + stats);
				string2File(path, "importresults.txt", "Child XML Import results: " + stats);
			}
			else {
				LOG_.debug("Import not tested -- need to specify import file in beans.xml.");
			}
		}

		if (tp.methods.contains("getOSEPProgressCategories")) {
			String osepExit = ws.getOSEPProgressCategories(creds, list, tp.getStartDate(), tp.getEndDate());
			LOG_.debug("getOSEPProgressCategories Export: " + osepExit);
			string2File(path, "categories.txt", osepExit);
		}

		if (tp.methods.contains("getECOProgressRatings")) {
			String cosfExit = ws.getECOProgressRatings(creds, list, tp.getStartDate(), tp.getEndDate());
			LOG_.debug("getECOProgressRatings Export: " + cosfExit);
			string2File(path, "ratings.txt", cosfExit);
		}
		System.exit(0);
	}

	/**
	 * file2bytes - reads the entire contents of a file into a byte array.
	 * @param f
	 * @return byte array of the read contents.
	 * @throws IOException
	 */
	public static byte[] file2bytes(File f) throws IOException {
		byte[] b = null;
		try {
			long L = f.length();
			if (L > Integer.MAX_VALUE) throw new IOException("File too large for a byte array");
			int l = (int) L;
			b = new byte[l];
			int p = 0, r = 0;
			FileInputStream in = new FileInputStream(f);
			while ((r = in.read(b, p, l - p)) > 0)
				p += r;
			in.close();
		}
		catch (FileNotFoundException ex) {
			throw new IOException(ex.getMessage());
		}
		return b;
	}
	/**
	 * string2File - Creates a file and writes the contents of the String to it.
	 * @param p - the Path into which to write the file.
	 * @param file - name of file.
	 * @param content -- the contents to write.
	 * @throws IOException
	 */
	public static void string2File(File p, String file, String content) throws IOException {
		File f = new File(p.getAbsoluteFile()+System.getProperty("file.separator")+file);
		FileOutputStream fw = new FileOutputStream(f);
		fw.write(content.getBytes());
		fw.flush();
		fw.close();
	}
}
