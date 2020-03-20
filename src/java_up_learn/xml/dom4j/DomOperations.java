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
		//创建DOM树
		Element root = dom.addElement("root");
		//向dom树中添加根节点
		root.addElement("sub1").addText("test1");
		//向根节点中添加子节点
		root.addElement("sub2").addText("test2");
		new TraverseElements().travers2(root);
	}

}
