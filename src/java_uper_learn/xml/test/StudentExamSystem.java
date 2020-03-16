package java_uper_learn.xml.test;

import java.io.File;
import java.io.IOException;

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
	 * ˼·��
	 * 1. ����ʹ���ַ���������������û��Ľ��������Ҫʹ�õ����̶�ȡ����
	 * 2. ��������û�����Ĳ�ͬ����ִ�в�ͬ�Ĳ�����
	 * 3. xml�ļ��д洢�˳�����Ҫʹ�õ������ݣ������Ҫ���Ȼ�ȡxml�ĵ������н���
	 *    ʹ��dom��ʽ������
	 * 4. �������������ܣ��ֱ�������ѧ��������Ϣ��ɾ��ѧ��������Ϣ�Լ���ѯѧ��������Ϣ��
	 *    ����������Ҫ�ֱ���в�����
	 * 5. �����ֹ������ն���Ҫ��ִ�н�����浽xml�ĵ��У���˱���Ҳ�ǹ���֮һ��
	 * 6. Ϊ��������ɶ���һ��ѧ���������ڴ洢��ѧ����ص���Ϣ��
	 * 7. ��xml�ĵ���װ�ɶ����Ա���ڲ�����
	 */
	
	private File xml_file; //����xml�ĵ��ļ�����
	private Document dom; //����xml�ĵ�dom������Ķ���
	
	/**
	 * ʹ��һ��xml�ĵ�·����xml�ĵ�������г�ʼ��
	 * ����xml�ĵ����н�����ȡdom��
	 * @param xml_file_path
	 */
	public StudentExamSystem(String xml_file_path) {
		xml_file = new File(xml_file_path);
		if(!xml_file.exists()) {
			throw new RuntimeException("�����ļ������ڣ������޷����У�");
		}
		try {
			dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml_file);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("�����ļ�����������𻵣������޷����У�");
		}
	}
	
	/**
	 * ����ȡ��ѧ����Ϣ��ӵ�dom�������浽xml�ĵ���
	 * @param student ����һ��ѧ������
	 */
	public void addStudentInfo(Students student) {
		//����ѧ���ڵ㲢����������Ϣ
		Element xmlStudent = dom.createElement("student");
		xmlStudent.setAttribute("idcard", student.getIdcard());
		xmlStudent.setAttribute("examid", student.getExamid());
		
		//����ѧ���ڵ���ӽڵ㲢������Ϣ
		Element studentName = dom.createElement("name");
		studentName.setTextContent(student.getName());
		Element studentLocation = dom.createElement("location");
		studentLocation.setTextContent(student.getLocation());
		Element studentGrade = dom.createElement("grade");
		studentGrade.setTextContent(Integer.toString(student.getGrade()));
		
		//��ѧ���ڵ������Ԫ��
		xmlStudent.appendChild(studentName);
		xmlStudent.appendChild(studentLocation);
		xmlStudent.appendChild(studentGrade);
		
		//����ڵ������ѧ��Ԫ��
		dom.getElementsByTagName("exam").item(0).appendChild(xmlStudent);
		
		//��������������xml�ļ�
		try {
			save();
		} catch (TransformerException | TransformerFactoryConfigurationError e) {
			throw new RuntimeException("�����ļ�����һ����������ֹ��");
		}
	}
	
	/**
	 * ����ѧ�����ڵĽڵ㲢���أ����ѧ���������򷵻ؿ�
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
	 * ɾ��ѧ���ڵ����ݣ����ɾ���ɹ������棬���ʧ�ܷ��ؼ�
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
	 * ɾ��ѧ���ڵ㲢����xml�ĵ�
	 * ���ɾ��ʧ������ų�ɾ��ʧ���쳣
	 * @param student
	 * @throws Exception ���ѧ����Ϣδ��ɾ�������쳣
	 */
	public void removeStudent(String id) throws Exception {
		if(deleteStudent(id))
			try {
				save();
			} catch (TransformerException | TransformerFactoryConfigurationError e) {
				throw new RuntimeException("�����ļ�����һ����������ֹ��");
			}
		else
			throw new Exception("ѧ��������");
	}

	/**
	 * ���ڴ��е�dom�����浽xml�ĵ���
	 * �ڱ�������������������׳��쳣
	 * @throws TransformerConfigurationException ����Transformerʵ������
	 * @throws TransformerException dom���������
	 * @throws TransformerFactoryConfigurationError ��������ʵ������
	 */
	private void save() throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		TransformerFactory.newInstance().newTransformer().transform(new DOMSource(dom), new StreamResult(xml_file));
	}
	/**
	 * ����ѧ�����֤���Ҳ���ȡѧ��������Ϣ
	 * ����һ��ѧ������
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

	public static void main(String[] args) {

	}

}
