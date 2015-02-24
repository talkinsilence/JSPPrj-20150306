package com.eoot.jspprj.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.eoot.jspprj.dao.NoticeDao;
import com.eoot.jspprj.model.Notice;

public class JdbcNoticeDao implements NoticeDao {

	@Override
	public Notice getNotice(String code) {
		String sql = "SELECT * FROM NOTICES WHERE CODE ='" + code + "'"; 
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(url, "sist", "newlec");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			rs.next();

			//Model �����ϱ� 
			Notice n = new Notice();
			n.setCode(rs.getString("CODE"));
			n.setTitle(rs.getString("TITLE"));
			n.setWriter(rs.getString("WRITER"));
			n.setRegdate(rs.getDate("REGDATE"));
			n.setContent(rs.getString("CONTENT"));
			n.setHit(rs.getInt("HIT"));
			
			rs.close();
			st.close();
			con.close();
			
			return n;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	@Override
	public List<Notice> getNotices(int page, String query, String field) {
		
		//���������� ��Ģ�� ���̵��� ������ ����Ͽ� Ǯ������ �ʵ��� ����. ����� ���⿡�� �ñ���. 
		int start = 1 + 20*(page - 1);
		int end = 20 + 20*(page - 1);
//		int end = start + 19;
		
		//�׻� �ֽű��� ���� ���� ���;�.
		String sql ="SELECT N.* FROM"
				+ "(SELECT (ROW_NUMBER() OVER (ORDER BY REGDATE DESC)) NUM"
				+ ", NOTICES.* FROM NOTICES WHERE " + field + " LIKE ?) N	"
				+ "WHERE N.NUM BETWEEN ? AND ?";
/*		String sql = "SELECT * FROM ( "
				+ "SELECT ROWNUM NUM, N.* FROM ( "
				+ "SELECT * FROM NOTICES WHERE " + field + " LIKE ? ORDER BY REGDATE DESC) N) "
				+ "NOTICES WHERE NUM BETWEEN ? AND ?"; */
//		String url = "jdbc:oracle:thin:@win.newlecture.com:1521:orcl";
		
		//���Ṯ�ڿ� �ٿ��ֱ� 
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection(url, "sist", "newlec");

		PreparedStatement pst = con.prepareStatement(sql);
//		pst.setString(1, field);��� ���ָ� �ȵ�. �̷��� �ϸ� �������� 'FIELD'��� ��������ǥ�� �پ ����. ���ڿ��̴ϱ�. 
		pst.setString(1, "%" + query + "%"); //%�� ���⼭ ������. ���������� ���� ��������... 
		pst.setInt(2, start);
		pst.setInt(3, end);
		
		ResultSet rs = pst.executeQuery();
//		prepared������ �̹� sql�Ѱ����ϱ� ���� �ȵ�!
	
		List<Notice> list = new ArrayList<Notice>();	
		
		while(rs.next()){
		
			//Model �����ϱ� 
			Notice n = new Notice();
			n.setCode(rs.getString("CODE"));
			n.setTitle(rs.getString("TITLE"));
			n.setWriter(rs.getString("WRITER"));
			n.setRegdate(rs.getDate("REGDATE"));
			n.setContent(rs.getString("CONTENT"));
			n.setHit(rs.getInt("HIT"));
			
			list.add(n); //�̰� ����� ���� �� �����!!! ������� notice�� list�� �������������~
		}
		
		rs.close();
		pst.close();
		con.close();
		
		return list;
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Notice> getNotices(int page, String query) {
		//���� �Լ��� �Ѱܹ޴µ�,
		//�Ѱܹ��� �ʴ� ���ڴ� �⺻������ ��. 
		return getNotices(page, query, "TITLE");
	}
	
	@Override
	public List<Notice> getNotices(int page) { 
		return getNotices(page, "", "TITLE");
	}

	@Override
	public int insert(Notice notice) {
		//������ �ִ� ��ȣ���� �ϳ� �� ū ��ȣ�� �ο��Ǿ��
		String sqlCode = "SELECT ISNULL(MAX(CAST(CODE AS INT)),0)+1 CODE FROM NOTICES";
		String sql = "INSERT INTO NOTICES(CODE, TITLE, WRITER, REGDATE, CONTENT, HIT) VALUES(?,?,?,GetDate(),?,0)"; 
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection(url, "sist", "newlec");
		
		/*--------------�߰�---------------*/
		Statement st = con.createStatement(); 
		ResultSet rs = st.executeQuery(sqlCode);
		
		rs.next();
		
//		String code = rs.getString(0); //��Ī �Ⱥٿ��� ��� 
		String code = rs.getString("CODE"); //��Ī�� CODE��� ���������� ��� 
		
		rs.close();
		st.close();
		/*--------------�߰�---------------*/
		
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, code); //ù��° ����ǥ���� code�� �ִ´�
		pst.setString(2, notice.getTitle());
		pst.setString(3, notice.getWriter());
		pst.setString(4, notice.getContent());
		
		int result = pst.executeUpdate();
		
		pst.close();
		con.close();
		
		return result;
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}

	@Override
	public int update(Notice notice) {
		String sql = "UPDATE NOTICES SET(TITLE=?, CONTENT=?) WHERE CODE =?";  
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		Connection con = DriverManager.getConnection(url, "sist", "newlec");
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, notice.getTitle());
		st.setString(2, notice.getContent());
		st.setString(3, notice.getCode());
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;

	}

	@Override
	public int delete(String code) {
		String sql = "DELETE NOTICES WHERE CODE = ?"; 
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		Connection con = DriverManager.getConnection(url, "sist", "newlec");
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, code);
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;

	}


	@Override
	public int getSize(String query, String field) {

		String sql = "SELECT COUNT(*) CNT FROM NOTICES WHERE " + field + " LIKE ?";
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		
		try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection(url, "sist", "newlec");

		PreparedStatement pst = con.prepareStatement(sql); 
		pst.setString(1, "%" + query + "%");
		ResultSet rs = pst.executeQuery();
//	                                	prepared������ �̹� sql�Ѱ����ϱ� ���� �ȵ�!
		
		rs.next();
		
		int size = rs.getInt("CNT");
		
		rs.close();
		pst.close();
		con.close();
		
		return size;
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	
	@Override
	public int getSize(String query) {
		return getSize(query, "TITLE");
	}


	@Override
	public String lastCode() {
		
		String sql = "SELECT ISNULL(MAX(CAST(CODE AS INT)),0) CODE FROM NOTICES";
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			Connection con = DriverManager.getConnection(url, "sist", "newlec");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			rs.next();
 
			String code = rs.getString("CODE");
			
			rs.close();
			st.close();
			con.close();
			
			return code;
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}


	@Override
	public Notice prevNotice(String currentCode) {
		String sql = "SELECT TOP 1 * FROM NOTICES "
				+ "WHERE REGDATE > (SELECT REGDATE FROM NOTICES WHERE CODE = ?) "
				+ "ORDER BY REGDATE ASC";
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		
		try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection(url, "sist", "newlec");

		PreparedStatement pst = con.prepareStatement(sql); 
		pst.setString(1, currentCode);
		ResultSet rs = pst.executeQuery();
//	                                	prepared������ �̹� sql�Ѱ����ϱ� ���� �ȵ�!
		
		rs.next();
		//���, ���ǰ˻� �ʿ� ! next()�ؼ� ���� ���� ��� ������...  
		
		Notice n = new Notice();
		n.setCode(rs.getString("CODE"));
		n.setTitle(rs.getString("TITLE"));
		n.setWriter(rs.getString("WRITER"));
		n.setRegdate(rs.getDate("REGDATE"));
		n.setContent(rs.getString("CONTENT"));
		n.setHit(rs.getInt("HIT"));
		
		rs.close();
		pst.close();
		con.close();
		
		return n;
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Notice nextNotice(String currentCode) {
		String sql = "SELECT TOP 1 * FROM NOTICES "
				+ "WHERE REGDATE < (SELECT REGDATE FROM NOTICES WHERE CODE = ?) "
				+ "ORDER BY REGDATE DESC";
		String url = "jdbc:sqlserver://win.newlecture.com:1433;databaseName=newlecdb";
		
		try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection(url, "sist", "newlec");

		PreparedStatement pst = con.prepareStatement(sql); 
		pst.setString(1, currentCode);
		ResultSet rs = pst.executeQuery();
//	                                	prepared������ �̹� sql�Ѱ����ϱ� ���� �ȵ�!
		
		rs.next();
		
		Notice n = new Notice();
		n.setCode(rs.getString("CODE"));
		n.setTitle(rs.getString("TITLE"));
		n.setWriter(rs.getString("WRITER"));
		n.setRegdate(rs.getDate("REGDATE"));
		n.setContent(rs.getString("CONTENT"));
		n.setHit(rs.getInt("HIT"));
		
		rs.close();
		pst.close();
		con.close();
		
		return n;
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}


