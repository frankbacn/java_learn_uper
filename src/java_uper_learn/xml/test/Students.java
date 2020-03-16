package java_uper_learn.xml.test;

public class Students {
	private String idcard;
	private String examid;
	private String name;
	private String location;
	private int grade;
	
	public Students() {
		super();
		idcard=null;
		examid=null;
		name=null;
		location=null;
		grade = 0;
	}

	public Students(String idcard, String examid, String name, String location, int grade) {
		super();
		this.idcard = idcard;
		this.examid = examid;
		this.name = name;
		this.location = location;
		this.grade = grade;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getExamid() {
		return examid;
	}

	public void setExamid(String examid) {
		this.examid = examid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	

}
