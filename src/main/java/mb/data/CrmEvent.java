package mb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import mb.core.ConnectionPool;

public class CrmEvent extends ConnectionPool {
	
	private long crm_event_id;
	private String slug;
	private String name;
	private String type;
	private String performed_at;
	private String extra_id;
	private String extra_subject;
	private String lead_email;
	private String lead_name;
	private String lead_phones;
	private String status;

	public CrmEvent() {
		
	}

	public CrmEvent(String slug, String name, String type, String extra_id,
			String extra_subject, String lead_email, String lead_name, String lead_phones, String status) {
		super();
		this.slug = slug;
		this.name = name;
		this.type = type;
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		sdf.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
		String date = sdf.format( new Date() );
		this.performed_at = date;
		this.extra_id = extra_id;
		this.extra_subject = extra_subject;
		this.lead_email = lead_email;
		this.lead_name = lead_name;
		this.lead_phones = lead_phones;
		this.status = status;
	}

	public boolean save() {
		boolean result = false;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql_insert = "INSERT INTO mboss_crm_event ("
				+ "slug, "
				+ "name, "
				+ "type, "
				+ "performed_at, "
				+ "extra_id, "
				+ "extra_subject, "
				+ "lead_email, "
				+ "lead_name, "
				+ "lead_phones"
				+ "status"
				+ ") "
				+ "VALUES ("
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
				+ ")"
				;
	    Connection con = null;
		try{
			con = getConnection2();
			PreparedStatement pstmt = con.prepareStatement(sql_insert);

			if(ConnectionPool.getDebug()!=null && ConnectionPool.getDebug().equals("true"))
				System.out.println(format.format(new Date()) + " -> CrmEvent - Bulk Insert: " + pstmt.toString());
			pstmt.setString(1, getSlug());
			pstmt.setString(2, getName());
			pstmt.setString(3, getType());
			pstmt.setString(4, getPerformed_at());
			pstmt.setString(5, getExtra_id());
			pstmt.setString(6, getExtra_subject());
			pstmt.setString(7, getLead_email());
			pstmt.setString(8, getLead_name());
			pstmt.setString(9, getLead_phones());
			pstmt.setString(10, "queue");
		    pstmt.executeUpdate();
			cleanUp(pstmt);
		} catch (SQLException e) {
		   System.out.println(format.format(new Date()) + " -> CrmEvent - BULK INSERT ERROR. Trying solo inserts.");
		   System.out.println(e.getMessage());
		} catch (Exception e) {
	       e.printStackTrace();
	     } finally {
	       freeConnection(con);
	     }
		
		return result;
	}
	
	public void getEventsByStatus(String status, Integer limit)
	{

		String sql = "SELECT * FROM mboss_crm_event WHERE status = ? LIMIT ?";
	    Connection con2 = null;
	    ResultSet rs = null;
		try{
			con2 = getConnection2();
	        PreparedStatement pstmt = con2.prepareStatement(sql);
	        pstmt.setString(1, status);
	        pstmt.setInt(2, limit);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	
	        }
	        cleanUp(pstmt, rs);
		} catch (SQLException e) {
	       e.printStackTrace();
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	       freeConnection(con2);
	    }
		
	}

	public long getcrm_event_id() {
		return crm_event_id;
	}

	public String getSlug() {
		return slug;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getPerformed_at() {
		return performed_at;
	}

	public String getExtra_id() {
		return extra_id;
	}

	public String getExtra_subject() {
		return extra_subject;
	}

	public String getLead_email() {
		return lead_email;
	}

	public String getLead_name() {
		return lead_name;
	}

	public String getLead_phones() {
		return lead_phones;
	}

	public String getStatus() {
		return status;
	}

}
