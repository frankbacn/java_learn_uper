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
		//创建DOM树
		Element root = dom.addElement("root");
		//向dom树中添加根节点
		root.addElement("sub1").addText("test1");
		//向根节点中添加子节点
		root.addElement("sub2").addText("test2");
		new TraverseElements().travers(root);
		saveXMLPretty(dom);
	}
	
	/**
	 * 将内存中的dom树保存到磁盘
	 * @param dom
	 * @throws IOException 
	 */
	public void saveXML(Document dom) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("test.xml"),"UTF-8");
		dom.write(osw);
		osw.close();
	}
	
	/**
	 * 将内存中的dom树以便于阅读的方式保存xml文档
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
