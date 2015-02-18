package com.brookes.aepsi.webservices.reference;

import javax.jws.WebService;

import com.brookes.aepsi.exception.AEPSIException;

/**
 * IDataExchange -- Interface defining the web services to implement data exchange. 
 * 
 * <p>Exposes five methods to validate, get a count of children, 
 * get a list of child guids, get exchange-format XML for each child in a list, and update child data from the exchange format. 
 * This is a copy of the interface as defined in the AEPSI source code.</p>  
 * <p>Copyright (c) 2008 by Brookes Publishers, Inc.</p>
 * 
 * @author Steve Carton (steve.carton@retrievalsystems.com)
 *
 */
public interface IDataExchange {
	/**
	 * getCredentials - given a valid AEPSI user ID and password, validates this user and returns a set of credentials to be used in subsequent requests
	 * <p></p>
	 * @param userId - the user's ID
	 * @param passwd - the user's password
	 * @param source - the valid name of the data source.
	 * @return - a Base64 encoded String containing the user credentials to be used in subsequent calls.
	 * @throws AEPSIException
	 */
	String getCredentials(String userId, String passwd, String source) throws AEPSIException;
	/**
	 * getChildCount - Returns a count of the children with GUIDs in this AEPSI account
	 * @param credentials - the credentials returned from a call to getCredentials.
	 * @return - an integer count of the number of children in the program.
	 * @throws AEPSIException
	 */
	String getChildCount(String credentials) throws AEPSIException;
	/**
	 * getChildList - Returns an XML document as a String that has a list of the GUIDs for all the children in this account.
	 * <p>The XML is in the form:<br> 
	 * &lt;aepsi&gt;&lt;guid&gt;147&lt;/guid&gt;&lt;guid&gt;139&lt;/guid&gt;&lt;guid&gt;55&lt;/guid&gt;&lt;/aepsi&gt;</p>
	 * @param credentials - the credentials returned from a call to getCredentials.
	 * @return - the XML list of GUIDS
	 * @throws AEPSIException
	 */
	String getChildList(String credentials) throws AEPSIException;
	/**
	 * getChildren - Returns the export XML for each child identified by a guid element in the list.
	 * <p>If the list of GUIDS is null or empty, then all children (with GUIDS) for this program are exported.</p>
	 * <p>The list is a String of xml in the same form as above (getChildList).<br>
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
	 * @param credentials - the credentials returned from a call to getCredentials.
	 * @param list - the list of GUIDS in the above format.
	 * @return - the XML of the retrieved child records as a String.
	 * @throws AEPSIException
	 */
	String getChildren(String credentials, String list) throws AEPSIException;
	/**
	 * putChildren - updates the AEPSI database with the children in the "children" XML string. 
	 * <p>Source must be a valid XML source (see the DTD). </p>
	 * @param credentials - the credentials returned from a call to getCredentials.
	 * @param children - the valid XML stream of children
	 * @return - a report in XML format of the results of the import.
	 * @throws AEPSIException
	 */
	String putChildren(String credentials, String children) throws AEPSIException;
	/**
	 * getOsepEntryData - Returns an XML stream representing the raw OSEP near-entry data for this list of children
	 * <p></p>
	 * @param credentials - the credentials returned from a call to getCredentials.
	 * @param list - list of GUIDS for this export
	 * @param beginDate - the starting date range for the report in the form yyyy-MM-dd
	 * @param endDate - the ending date range for the report in the form yyyy-MM-dd
	 * @return - The XML of the report
	 * @throws AEPSIException
	 */
	String getOsepEntryData (String credentials, String list, String beginDate, String endDate) throws AEPSIException;
	/**
	 * getOsepExitData - Returns an XML stream representing the raw OSEP near-exit data for this list of children
	 * <p></p>
	 * @param credentials - the credentials returned from a call to getCredentials.
	 * @param list - list of GUIDS for this export
	 * @param beginDate - the starting date range for the report in the form yyyy-MM-dd
	 * @param endDate - the ending date range for the report in the form yyyy-MM-dd
	 * @return - The XML of the report
	 * @throws AEPSIException
	 */
	String getOsepExitData (String credentials, String list, String beginDate, String endDate) throws AEPSIException;
	/**
	 * getCosfExitData - Returns an XML stream representing the raw COSF Ratings near-exit data for this list of children
	 * <p></p>
	 * @param credentials - the credentials returned from a call to getCredentials.
	 * @param list - list of GUIDS for this export
	 * @param beginDate - the starting date range for the report in the form yyyy-MM-dd
	 * @param endDate - the ending date range for the report in the form yyyy-MM-dd
	 * @return - The XML of the report
	 * @throws AEPSIException
	 */
	String getCosfExitData (String credentials, String list, String beginDate, String endDate) throws AEPSIException;
}
