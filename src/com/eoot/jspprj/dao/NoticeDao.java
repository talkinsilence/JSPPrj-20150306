package com.eoot.jspprj.dao;

import java.util.List;

import com.eoot.jspprj.model.Notice;

public interface NoticeDao {
	
	public Notice getNotice(String code);
	public List<Notice> getNotices(int page, String query, String field);
	public List<Notice> getNotices(int page, String query);
	public List<Notice> getNotices(int page);
	//�����ε�� �������̵�� ���� �ٸ� ���!! �� ����� �ƴ�. �̸��� ����� ���̴�. �����ε�� �ߺ�����. ����ڿ��� ������ �� �ֵ��� �ϴ� ����. 
	//�������� �ִ°��, �˻��� �ɼǵ� �ִ� ���, ���� �ܿ� �˻��� �� �ִ� �ɼǵ� �ִ� ���. 
	//�����ε� - ���� - ����ȭ 
	//���� �� �߿� ��� �� �� �����ϸ� �Ǵµ�, �׷� ������ �ϴ� �� ������? ������ ���ڰ� ���� ���� ���� �����ϸ� ��. 
	public int insert(Notice notice); 
	public int update(Notice notice); 
	public int delete(String code);
	public int getSize(String query, String field);
	public int getSize(String query);
	
	public String lastCode();
	
	public Notice prevNotice(String currentCode);
	public Notice nextNotice(String currentCode);
}
