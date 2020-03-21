package java_up_learn.xml.dom4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class DomOperations {
	private SAXReader reader;
	private Document document;
	
	public void beforTest(){
		
	}
	
	@Test
	public void createDocument() throws IOException{
		Document dom =  DocumentHelper.createDocument();
		//����DOM��
		Element root = dom.addElement("root");
		//��dom������Ӹ��ڵ�
		root.addElement("sub1").addText("test1");
		//����ڵ�������ӽڵ�
		root.addElement("sub2").addText("test2");
		new TraverseElements().travers(root);
		saveXMLPretty(dom);
	}
	
	/**
	 * ���ڴ��е�dom�����浽����
	 * @param dom
	 * @throws IOException 
	 */
	public void saveXML(Document dom) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("test.xml"),"UTF-8");
		dom.write(osw);
		osw.close();
	}
	
	/**
	 * ���ڴ��е�dom���Ա����Ķ��ķ�ʽ����xml�ĵ�
	 * @param dom
	 * @throws IOException 
	 */
	public void saveXMLPretty(Document dom) throws IOException {
		OutputFormat of = OutputFormat.createPrettyPrint();
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("test.xml"),"UTF-8");
		XMLWriter xmlwr = new XMLWriter(osw, of);
		xmlwr.write(dom);
		osw.close();
	}

}
