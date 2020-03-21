package java_up_learn.xml.dom4j;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class TraverseElements {
	@Test
	public void traversAll() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read("examdata.xml");
		Element root = document.getRootElement();
		travers(root);
		
	}
	
	public void travers(Element child){ //遍历整个dom树种的内容
		System.out.println(child.getName());
		for(int i =0;i<child.nodeCount();i++) {
			Node node = child.node(i); //获取节点中的子节点
			if(node instanceof Element) //判断子节点是否是元素
				travers((Element)node);
			else //如果不是元素则打印节点的文本信息
				System.out.println(node.getText());
		}
	}
}
