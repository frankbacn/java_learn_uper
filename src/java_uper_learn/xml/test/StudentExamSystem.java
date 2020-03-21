package java_uper_learn.xml.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StudentExamSystem {
	/*
	 * 思路：
	 * 1. 程序使用字符界面操作，有与用户的交互因此需要使用到键盘读取流。
	 * 2. 程序根据用户输入的不同命令执行不同的操作。
	 * 3. xml文件中存储了程序所要使用到的数据，因此需要首先获取xml文档并进行解析
	 *    使用dom方式解析。
	 * 4. 程序有三个功能，分别是增加学生考试信息，删除学生考试信息以及查询学生考试信息，
	 *    三个功能需要分别进行操作。
	 * 5. 三部分功能最终都需要将执行结果保存到xml文档中，因此保存也是功能之一。
	 * 6. 为操作方便可定义一个学生对象用于存储相学生相关的信息。
	 * 7. 将xml文档封装成对象以便后期操作。
	 */
	
	private File xml_file; //定义xml文档文件对象
	private Document dom; //定义xml文档dom解析后的对象
	
	private String operation = null;
	private String optlevel = null;
	
	/**
	 * 使用一个xml文档路径对xml文档对象进行初始化
	 * 并对xml文档进行解析获取dom树
	 * @param xml_file_path
	 */
	public StudentExamSystem(String xml_file_path) {
		xml_file = new File(xml_file_path);
		if(!xml_file.exists()) {
			throw new RuntimeException("数据文件不存在，程序无法运行！");
		}
		try {
			dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml_file);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("数据文件解析错误或损坏，程序无法运行！");
		}
	}
	
	/**
	 * 将获取的学生信息添加到dom树并保存到xml文档中
	 * @param student 接收一个学生对象
	 * @throws Exception 
	 */
	public void addStudentInfo(Students student) throws Exception {
		
		if(searchStudent(student.getIdcard())!=null)
			throw new Exception("学生已存在，不能添加重复学生");
		//创建学生节点并设置属性信息
		Element xmlStudent = dom.createElement("student");
		xmlStudent.setAttribute("idcard", student.getIdcard());
		xmlStudent.setAttribute("examid", student.getExamid());
		
		//创建学生节点的子节点并设置信息
		Element studentName = dom.createElement("name");
		studentName.setTextContent(student.getName());
		Element studentLocation = dom.createElement("location");
		studentLocation.setTextContent(student.getLocation());
		Element studentGrade = dom.createElement("grade");
		studentGrade.setTextContent(Integer.toString(student.getGrade()));
		
		//向学生节点中添加元素
		xmlStudent.appendChild(studentName);
		xmlStudent.appendChild(studentLocation);
		xmlStudent.appendChild(studentGrade);
		
		//向根节点中添加学生元素
		dom.getElementsByTagName("exam").item(0).appendChild(xmlStudent);
		
		//保存新增数据至xml文件
		try {
			save();
		} catch (TransformerException | TransformerFactoryConfigurationError e) {
			throw new RuntimeException("数据文件保存一场，程序终止！");
		}
	}
	
	/**
	 * 搜索学生所在的节点并返回，如果学生不存在则返回空
	 * @param student
	 * @return
	 */
	private Element searchStudent(String id) {
		NodeList nodes = dom.getElementsByTagName("student");
		Element e = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			e = (Element) nodes.item(i);
			if(e.getAttribute("idcard").equals(id)) {
				break;
			}
		}
		return e;
	}
	
	/**
	 * 删除学生节点数据，如果删除成功返回真，如果失败返回假
	 * @param student
	 * @return
	 */
	private boolean deleteStudent(String id) {
		Element e = searchStudent(id);
		if(e==null)
			return false;
		e.getParentNode().removeChild(e);
		return true;
	}
	
	/**
	 * 删除学生节点并保存xml文档
	 * 如果删除失败则会排除删除失败异常
	 * @param student
	 * @throws Exception 如果学生信息未被删除则发生异常
	 */
	public void removeStudent(String id) throws Exception {
		if(deleteStudent(id))
			try {
				save();
			} catch (TransformerException | TransformerFactoryConfigurationError e) {
				throw new RuntimeException("数据文件保存一场，程序终止！");
			}
		else
			throw new Exception("学生不存在");
	}

	/**
	 * 将内存中的dom树保存到xml文档中
	 * 在保存过程中如果出错则会抛出异常
	 * @throws TransformerConfigurationException 创建Transformer实例错误
	 * @throws TransformerException dom树保存错误
	 * @throws TransformerFactoryConfigurationError 创建工厂实例错误
	 */
	private void save() throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		TransformerFactory.newInstance().newTransformer().transform(new DOMSource(dom), new StreamResult(xml_file));
	}
	
	/**
	 * 更新学生信息
	 * 根据学生身份证号对指定的学生信息字段内容进行更新
	 * 更新成功后将保存xml文档
	 * 
	 * 如果无学生信息或给定的学生属性错误则会发生异常
	 * @param id
	 * @param key
	 * @param s
	 * @throws Exception 
	 */
	public void updateStudentInfo(String id,String key,String value) throws Exception{
		Element e = searchStudent(id);
		if(e==null)
			throw new Exception("无学生信息，更新失败");
		if(e.getElementsByTagName(key).getLength()==0)
			throw new Exception("给定的学生属性错误，无法更新");
		e.getElementsByTagName(key).item(0).setTextContent(value);
		save();
	}
	
	/**
	 * 根据学生身份证查找并获取学生考试信息
	 * 返回一个学生对象
	 * @param id
	 * @return
	 */
	public Students getStudentInfo(String id) {
		Students s = null;
		Element e = searchStudent(id);
		if(e==null) {
			return s;
		}		
		s = new Students();
		s.setIdcard(e.getAttribute("idcard"));
		s.setExamid(e.getAttribute("examid"));
		s.setName(e.getElementsByTagName("name").item(0).getTextContent());
		s.setLocation(e.getElementsByTagName("location").item(0).getTextContent());
		s.setGrade(Integer.parseInt(e.getElementsByTagName("grade").item(0).getTextContent()));
		return s;
	}

	public static void main(String[] args){
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); //将键盘输入封装为字符流
		StudentExamSystem ses = new StudentExamSystem("examdata.xml");
		
		ses.topMenu(bfr);
	}

	public void topMenu(BufferedReader bfr) {
		startPrint();
		try {
			while((operation = bfr.readLine())!=null){
				if(operation.length()==0)
					continue;
				if(optlevel==null){
					initStart(bfr);
				}
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
		

	public void initStart(BufferedReader bfr) {
		switch (operation) {
		case "A":
			optlevel = "select";
			operation=null;
			selectById(bfr);
			break;
		case "B":
			optlevel = "update";
			operation=null;
			updateById(bfr);
			break;
		case "C":					
			break;
		case "D":					
			break;
		case "E":
			System.exit(0);;
		}
	}

	public void updateById(BufferedReader bfr) {
		updateByIdP();
		try {
			while((operation=bfr.readLine())!=null) {
				if(operation.length()==0)
					continue;
				if(operation.equals("Q")) {
					optlevel=null;
					startPrint();
					return;
				}
				if(searchStudent(operation)==null) {
					System.out.println("学生不存在!");
					updateByIdP();
				}else {
					
					operation=null;
					updateByIdP();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateByIdP() {
		System.out.println("您已进入更新功能。");
		System.out.println("请根据学生的身份证号码对考生信息进行更新或输入Q退回上一级功能。");
		System.out.print("请输入要更新学生的身份证号码: ");
	}

	/**
	 * 考生信息查询交互功能
	 * @param bfr
	 */
	public void selectById(BufferedReader bfr) {
		selectByIdP();
		try {
			while((operation=bfr.readLine())!=null) {
				if(operation.length()==0)
					continue;
				if(operation.equals("Q")) {
					optlevel=null;
					startPrint();
					return;
				}
				if(searchStudent(operation)==null) {
					System.out.println("查询的学生不存在!");
					selectByIdP();
				}else {
					Students s = getStudentInfo(operation);
					System.out.println("您查询的考生姓名是:"+s.getName());
					System.out.println("考试的身份证号码是:"+operation);
					System.out.println("考试的准考证号是:"+s.getExamid());
					System.out.println("考试的籍贯是:"+s.getLocation());
					System.out.println("考生的成绩是:"+s.getGrade());
					operation=null;
					selectByIdP();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void selectByIdP() {
		System.out.println("您已进入查询功能。");
		System.out.println("请根据学生的身份证号码对考生信息进行查询或输入Q退回上一级功能。");
		System.out.print("请输入要查询学生的身份证号码: ");
	}

	public void startPrint() {
		System.out.println("请输入对应的选项进行操作");
		System.out.println("A: 查询\tB:更新\tC:新增\tD:删除\tE:退出");
		System.out.print("选项:");
	}

}
