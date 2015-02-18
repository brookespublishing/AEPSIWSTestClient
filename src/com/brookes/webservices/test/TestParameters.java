/**
 * 
 */
package com.brookes.webservices.test;

import java.util.List;

/**
 * TestParameters -- Simpleton class for holding test parameters for the TestWS.
 * <p>depends on Spring injection to set a start and end date as Strings as well as the 
 * User ID, Password, source provider name, output path and file name for the test import file.
 * Dates must be in yyyy-mm-dd format.
 * Also accepts a list of the names of the methods that are to be exercised. See beans.xml for more detailed examples.</p>
 * 
 * <p>Copyright (c) 2008 by Brookes Publishers, Inc.</p>
 * 
 * @author Steve Carton (steve.carton@retrievalsystems.com)
 */

public class TestParameters {
	String startDate;
	String endDate;
	String userId;
	String password;
	String sourceProvider;
	String outputPath;
	String importFile;
	List<String> methods;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSourceProvider() {
		return sourceProvider;
	}
	public void setSourceProvider(String sourceProvider) {
		this.sourceProvider = sourceProvider;
	}
	public String getImportFile() {
		return importFile;
	}
	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	public List<String> getMethods() {
		return methods;
	}
	public void setMethods(List<String> methods) {
		this.methods = methods;
	}

}
