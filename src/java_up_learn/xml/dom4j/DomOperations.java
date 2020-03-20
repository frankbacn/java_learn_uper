package java_up_learn.xml.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class DomOperations {
	private SAXReader reader;
	private Document document;
	
	public void beforTest(){
		
	}
	
	@Test
	public void createDocument(){
		Document dom =  DocumentHelper.createDocument();
		//����DOM��
		Element root = dom.addElement("root");
		//��dom������Ӹ��ڵ�
		root.addElement("sub1").addText("test1");
		//����ڵ�������ӽڵ�
		root.addElement("sub2").addText("test2");
		new TraverseElements().travers2(root);
	}

}
