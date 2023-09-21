package mb.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * @author
 * eBusiness4Us
 */
public class ConnectionPool {

	private static String path = "";
	private static String sysUrl = "";
	private static String localUrl = "";
	private static String propertiesFile = "mboss.properties";
//    private static Properties configNews = new Properties();
	private static int maxNews = 0;
	private static int maxEmails = 0;
	private static int maxUsers = 0;
	private static int maxMessagesSMTP = 0;
	private static int maxMessagesCicle = 0;
	private static float speedRate = 0;
	private static int maxThreads = 0;
	private static String driver = "";
	private static String url = "";
	private static String user = "";
	private static String password = "";
	private static String url2 = "";
	private static String user2 = "";
	private static String password2 = "";
	private static String url3 = "";
	private static String user3 = "";
	private static String password3 = "";
	private static String jdbcPool = "true";
	private static String clientDriver = "";
	private static String clientUrl = "";
	private static String clientUser = "";
	private static String clientPassword = "";
	private static String mailhost = "";
	private static String domainFrom = "mailer.mailingboss.net";
	private static String debug = "";
	private static String importer = "local";
	private static String remotePath = "";
	private static String senderID = "default";
	private static String clusterID = "1";
	private static String ignoreDomainsLocks = "";
	private static String smtpTest = "";
	private static Connection connection = null;
	private static Connection connection2 = null;
	private static Connection connection3 = null;
	private static boolean development = false;
	private static String domainsReady = "";
	
	public ConnectionPool() {
	}

	public static Connection getConnection() throws SQLException {
        try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT 1");
			ResultSet rs = pstmt.executeQuery();
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
        	connection = getConnectionPool();
		}
		return connection;
	}

	public static Connection getConnection2() throws SQLException {
        try {
			PreparedStatement pstmt = connection2.prepareStatement("SELECT 1");
			ResultSet rs = pstmt.executeQuery();
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
        	connection2 = getConnectionPool2();
		}
		return connection2;
	}

	public static Connection getConnection3() throws SQLException {
        try {
			PreparedStatement pstmt = connection3.prepareStatement("SELECT 1");
			ResultSet rs = pstmt.executeQuery();
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
        	connection3 = getConnectionPool3();
		}
		return connection3;
	}

	public static Connection getConnectionPool() throws SQLException {
		try {
	        Connection conRet = null;
        	for(int xx=0; xx < 10; xx++)
        	{	
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e) { e.printStackTrace(); } catch (Exception e1) { e1.printStackTrace(); }
				try {
					conRet = DriverManager.getConnection(url, user, password);
					break;
				} catch (SQLException e1) { if(e1 !=null && e1.getMessage() !=null) System.out.println(e1.getMessage()); } catch (Exception geral) { System.out.println(geral.getMessage()); }
				Thread.sleep(5000);
        	}
        	if(conRet == null)
        		System.out.println("Connection failed after 10 retries. Aborting...");
			return conRet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnectionPool2() throws SQLException {
		try {
	        Connection conRet2 = null;
        	for(int xx=0; xx < 10; xx++)
        	{	
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e) { e.printStackTrace(); } catch (Exception e1) { e1.printStackTrace(); }
				try {
					conRet2 = DriverManager.getConnection(url2, user2, password2);
					break;
				} catch (SQLException e1) { if(e1 !=null && e1.getMessage() !=null) System.out.println(e1.getMessage()); } catch (Exception geral) { System.out.println(geral.getMessage()); }
				Thread.sleep(5000);
        	}
        	if(conRet2 == null)
        		System.out.println("Connection2 failed after 10 retries. Aborting...");
			return conRet2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnectionPool3() throws SQLException {
		try {
	        Connection conRet3 = null;
        	for(int xx=0; xx < 10; xx++)
        	{	
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e) { e.printStackTrace(); } catch (Exception e1) { e1.printStackTrace(); }
				try {
					conRet3 = DriverManager.getConnection(url3, user3, password3);
					break;
				} catch (SQLException e1) { if(e1 !=null && e1.getMessage() !=null) System.out.println(e1.getMessage()); } catch (Exception geral) { System.out.println(geral.getMessage()); }
				Thread.sleep(5000);
        	}
        	if(conRet3 == null)
        		System.out.println("Connection3 failed after 10 retries. Aborting...");
			return conRet3;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void initVars()
    {
    	path = "/var/www/mailboss/";
    	sysUrl = "http://app.mailingboss.com/";
    	localUrl = "http://app.mailingboss.com/";
    	maxNews = 1;
    	maxEmails = 0;
    	maxUsers = 0;
    	maxMessagesSMTP = 100000;
    	maxMessagesCicle = 100;
    	speedRate = 1;
    	maxThreads = 10;
    	driver = "com.mysql.jdbc.Driver";
    	url = "jdbc:mariadb://64.251.1.126/mailingboss?useUnicode=true&characterEncoding=utf8&sessionVariables=character_set_client=utf8mb4,character_set_results=utf8mb4,character_set_connection=utf8mb4,collation_connection=utf8mb4_unicode_ci";
    	user = "mailingboss";
    	password = "mailingboss";
    	url2 = "jdbc:mariadb://64.251.1.126/mboss_service?useUnicode=true&characterEncoding=utf8&sessionVariables=character_set_client=utf8mb4,character_set_results=utf8mb4,character_set_connection=utf8mb4,collation_connection=utf8mb4_unicode_ci";
    	user2 = "mailingboss";
    	password2 = "mailingboss";
    	url3 = "jdbc:mariadb://64.251.1.126/mboss_stats?useUnicode=true&characterEncoding=utf8&sessionVariables=character_set_client=utf8mb4,character_set_results=utf8mb4,character_set_connection=utf8mb4,collation_connection=utf8mb4_unicode_ci";
    	user3 = "mailingboss";
    	password3 = "mailingboss";
    	clientDriver = "";
    	clientUrl = "";
    	clientUser = "";
    	clientPassword = "";
    	jdbcPool = "false";
    	mailhost = "smtp001.mailingboss.net";
    	domainFrom = "mailer.mailingboss.net";
    	debug = "false";
    	importer = "local";
    	remotePath = "";
    	senderID = "smtp001.mailingboss.net";
    	clusterID = "1";
    	ignoreDomainsLocks = "";
    	smtpTest = "none";
        try{
		    FileReader fr = new FileReader("/srv/newmailingboss/conf/mboss.cf");
		    if(isDevelopment())
		    {
		    	fr.close();
		    	fr = new FileReader("/srv/newmailingboss/conf/mbossdev.cf");
		    	System.out.println("Reading configurations from /srv/newmailingboss/conf/mbossdev.cf");
		    }
			BufferedReader br = new BufferedReader(fr);		 
		    String line = "";
			while ((line = br.readLine()) != null) 
		    {
				if(line.indexOf("=") > 0)
				{	
					String prop = line.substring(0, line.indexOf("=") - 1).trim();
					String value = line.substring(line.indexOf("=") + 1).trim();
					if(prop.equalsIgnoreCase("path"))
					{
						path = value;
					} else if(prop.equalsIgnoreCase("sysurl")) {
						sysUrl = value;
					} else if(prop.equalsIgnoreCase("localurl")) {
						localUrl = value;
					} else if(prop.equalsIgnoreCase("maxnews")) {
						maxNews = Integer.parseInt(value);
					} else if(prop.equalsIgnoreCase("maxemails")) {
						maxEmails = Integer.parseInt(value);
					} else if(prop.equalsIgnoreCase("maxusers")) {
						maxUsers = Integer.parseInt(value);
					} else if(prop.equalsIgnoreCase("maxmessagessmtp")) {
						maxMessagesSMTP = Integer.parseInt(value);
					} else if(prop.equalsIgnoreCase("maxmessagescicle")) {
						maxMessagesCicle = Integer.parseInt(value);
					} else if(prop.equalsIgnoreCase("speedrate")) {
						speedRate = Float.parseFloat(value);
					} else if(prop.equalsIgnoreCase("maxthreads")) {
						maxThreads = Integer.parseInt(value);
					} else if(prop.equalsIgnoreCase("driver")) {
						driver = value;
					} else if(prop.equalsIgnoreCase("url")) {
						url = value;
					} else if(prop.equalsIgnoreCase("user")) {
						user = value;
					} else if(prop.equalsIgnoreCase("password")) {
						password = value;
					} else if(prop.equalsIgnoreCase("url2")) {
						url2 = value;
					} else if(prop.equalsIgnoreCase("user2")) {
						user2 = value;
					} else if(prop.equalsIgnoreCase("password2")) {
						password2 = value;
					} else if(prop.equalsIgnoreCase("url3")) {
						url3 = value;
					} else if(prop.equalsIgnoreCase("user3")) {
						user3 = value;
					} else if(prop.equalsIgnoreCase("password3")) {
						password3 = value;
					} else if(prop.equalsIgnoreCase("clientdriver")) {
						clientDriver = value;
					} else if(prop.equalsIgnoreCase("clienturl")) {
						clientUrl = value;
					} else if(prop.equalsIgnoreCase("clientuser")) {
						clientUser = value;
					} else if(prop.equalsIgnoreCase("clientpassword")) {
						clientPassword = value;
					} else if(prop.equalsIgnoreCase("jdbcpool")) {
						jdbcPool = value.toLowerCase();
					} else if(prop.equalsIgnoreCase("mailhost")) {
						mailhost = value;
					} else if(prop.equalsIgnoreCase("domainfrom")) {
						domainFrom = value;
					} else if(prop.equalsIgnoreCase("importer")) {
						importer = value;
					} else if(prop.equalsIgnoreCase("remotepath")) {
						remotePath = value;
					} else if(prop.equalsIgnoreCase("senderid")) {
						senderID = value;
					} else if(prop.equalsIgnoreCase("clusterid")) {
						clusterID = value;
					} else if(prop.equalsIgnoreCase("ignoredomainslocks")) {
						ignoreDomainsLocks = value.toLowerCase();
					} else if(prop.equalsIgnoreCase("smtptest")) {
						smtpTest = value.toLowerCase();
					} else if(prop.equalsIgnoreCase("debug")) {
						debug = value.toLowerCase();
					}
				}
		    }
			br.close();	
			startConnections();
        }
        catch(FileNotFoundException e2){
            e2.printStackTrace(); 
        	System.out.println("MailingBoss - [ConnectionPool] file not found: /srv/newmailingboss/mboss.cf"); }
        catch(IOException e2) {
            e2.printStackTrace(); 
    		System.out.println("MailingBoss - [ConnectionPool] IO error reading file: /srv/newmailingboss/mboss.cf"); }
        catch(Exception e3){
            e3.printStackTrace();  
            System.out.println("MailingBoss - [ConnectionPool] error parsing file: /srv/newmailingboss/mboss.cf");
        }
    	
    }

	public static void startConnections()
	{
		try {
			if(connection == null)
			{	
				connection = getConnectionPool();
			}
			if(connection2 == null)
			{	
				connection2 = getConnectionPool2();
			}
			if(connection3 == null)
			{	
				connection3 = getConnectionPool3();
			}
		} catch (SQLException e) {}
	}
	
	public static void closeAllConnections()
	{
		closeConnection();
		closeConnection2();
		closeConnection3();
	}

	public static void closeConnection()
	{
		try {
			connection.close();
		} catch (SQLException e) {}
	}

	public static void closeConnection2()
	{
		try {
			connection2.close();
		} catch (SQLException e) {}
	}

	public static void closeConnection3()
	{
		try {
			connection3.close();
		} catch (SQLException e) {}
	}

	public static void freeConnection(Connection c)
	{
		//do nothing. leave the connection open. let the closeConnection be called at the end of the app running.
	}

	public static void cleanUp(Connection con) throws SQLException {
		cleanUp(con, null, null);
	}

	public static void cleanUp(Connection con, Statement stmt) throws SQLException {
		cleanUp(con, stmt, null);
	}

	public static void cleanUp(Statement stmt, ResultSet rs) throws SQLException {
		cleanUp(null, stmt, rs);
	}

	public static void cleanUp(Statement stmt) throws SQLException {
		cleanUp(null, stmt, null);
	}

	public static void cleanUp(ResultSet rs) throws SQLException {
		cleanUp(null, null, rs);
	}

	public static void cleanUp(Connection con, Statement stmt, ResultSet rs) throws SQLException {
		if ( rs != null) {
			rs.close();
		}
		if ( stmt != null) {
			stmt.close();
		}
		if ( con != null && !con.isClosed()) {
			con.close();
		}
	}

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		ConnectionPool.path = path;
	}

	public static String getSysUrl() {
		return sysUrl;
	}

	public static void setSysUrl(String sysUrl) {
		ConnectionPool.sysUrl = sysUrl;
	}

	public static String getLocalUrl() {
		return localUrl;
	}

	public static void setLocalUrl(String localUrl) {
		ConnectionPool.localUrl = localUrl;
	}

	public static String getPropertiesFile() {
		return propertiesFile;
	}

	public static void setPropertiesFile(String propertiesFile) {
		ConnectionPool.propertiesFile = propertiesFile;
	}

	public static int getMaxEmails() {
		return maxEmails;
	}

	public static void setMaxEmails(int maxEmails) {
		ConnectionPool.maxEmails = maxEmails;
	}

	public static int getMaxNews() {
		return maxNews;
	}

	public static void setMaxNews(int maxNews) {
		ConnectionPool.maxNews = maxNews;
	}

	public static int getMaxUsers() {
		return maxUsers;
	}

	public static void setMaxUsers(int maxUsers) {
		ConnectionPool.maxUsers = maxUsers;
	}

	public static int getMaxMessagesSMTP() {
		return maxMessagesSMTP;
	}

	public static void setMaxMessagesSMTP(int maxMessagesSMTP) {
		ConnectionPool.maxMessagesSMTP = maxMessagesSMTP;
	}

	public static int getMaxMessagesCicle() {
		return maxMessagesCicle;
	}

	public static void setMaxMessagesCicle(int maxMessagesCicle) {
		ConnectionPool.maxMessagesCicle = maxMessagesCicle;
	}

	public static float getSpeedRate() {
		return speedRate;
	}

	public static void setSpeedRate(float speedRate) {
		ConnectionPool.speedRate = speedRate;
	}

	public static int getMaxThreads() {
		return maxThreads;
	}

	public static void setMaxThreads(int maxThreads) {
		ConnectionPool.maxThreads = maxThreads;
	}

	public static String getClientDriver() {
		return clientDriver;
	}

	public static void setClientDriver(String clientDriver) {
		ConnectionPool.clientDriver = clientDriver;
	}

	public static String getClientUrl() {
		return clientUrl;
	}

	public static void setClientUrl(String clientUrl) {
		ConnectionPool.clientUrl = clientUrl;
	}

	public static String getClientUser() {
		return clientUser;
	}

	public static void setClientUser(String clientUser) {
		ConnectionPool.clientUser = clientUser;
	}

	public static String getClientPassword() {
		return clientPassword;
	}

	public static void setClientPassword(String clientPassword) {
		ConnectionPool.clientPassword = clientPassword;
	}

	public static String getMailhost() {
		return mailhost;
	}

	public static String getDebug() {
		return debug;
	}

	public static String getImporter() {
		return importer;
	}

	public static String getRemotePath() {
		return remotePath;
	}

	public static String getSenderID() {
		return senderID;
	}

	public static String getIgnoreDomainsLocks() {
		return ignoreDomainsLocks;
	}

	public static String getDriver() {
		return driver;
	}

	public static void setDriver(String driver) {
		ConnectionPool.driver = driver;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		ConnectionPool.url = url;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		ConnectionPool.user = user;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ConnectionPool.password = password;
	}

	public static String getJdbcPool() {
		return jdbcPool;
	}

	public static void setJdbcPool(String jdbcPool) {
		ConnectionPool.jdbcPool = jdbcPool;
	}

	public static void setMailhost(String mailhost) {
		ConnectionPool.mailhost = mailhost;
	}

	public static void setDebug(String debug) {
		ConnectionPool.debug = debug;
	}

	public static void setImporter(String importer) {
		ConnectionPool.importer = importer;
	}

	public static void setRemotePath(String remotePath) {
		ConnectionPool.remotePath = remotePath;
	}

	public static void setSenderID(String senderID) {
		ConnectionPool.senderID = senderID;
	}

	public static void setIgnoreDomainsLocks(String ignoreDomainsLocks) {
		ConnectionPool.ignoreDomainsLocks = ignoreDomainsLocks;
	}

	public static String getClusterID() {
		return clusterID;
	}

	public static void setClusterID(String clusterID) {
		ConnectionPool.clusterID = clusterID;
	}

	public static String getSmtpTest() {
		return smtpTest;
	}

	public static void setSmtpTest(String smtpTest) {
		ConnectionPool.smtpTest = smtpTest;
	}

	public static String getDomainFrom() {
		return domainFrom;
	}

	public static void setDomainFrom(String domainFrom) {
		ConnectionPool.domainFrom = domainFrom;
	}

	public static boolean isDevelopment() {
		return development;
	}

	public static void setDevelopment(boolean development) {
		ConnectionPool.development = development;
	}

	public static String getUrl2() {
		return url2;
	}

	public static void setUrl2(String url2) {
		ConnectionPool.url2 = url2;
	}

	public static String getUser2() {
		return user2;
	}

	public static void setUser2(String user2) {
		ConnectionPool.user2 = user2;
	}

	public static String getPassword2() {
		return password2;
	}

	public static void setPassword2(String password2) {
		ConnectionPool.password2 = password2;
	}

	public static String getUrl3() {
		return url3;
	}

	public static void setUrl3(String url3) {
		ConnectionPool.url3 = url3;
	}

	public static String getUser3() {
		return user3;
	}

	public static void setUser3(String user3) {
		ConnectionPool.user3 = user3;
	}

	public static String getPassword3() {
		return password3;
	}

	public static void setPassword3(String password3) {
		ConnectionPool.password3 = password3;
	}

	public static String getDomainsReady() {
		return domainsReady;
	}

	public static void setDomainsReady(String domainsReady) {
		ConnectionPool.domainsReady = domainsReady;
	}

	public static void setConnection(Connection connection) {
		ConnectionPool.connection = connection;
	}

	public static void setConnection2(Connection connection2) {
		ConnectionPool.connection2 = connection2;
	}

	public static void setConnection3(Connection connection3) {
		ConnectionPool.connection3 = connection3;
	}

}

