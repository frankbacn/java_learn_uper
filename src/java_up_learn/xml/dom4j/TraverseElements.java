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
	
	public void travers(Element child){ //��������dom���ֵ�����
		System.out.println(child.getName());
		for(int i =0;i<child.nodeCount();i++) {
			Node node = child.node(i); //��ȡ�ڵ��е��ӽڵ�
			if(node instanceof Element) //�ж��ӽڵ��Ƿ���Ԫ��
				travers((Element)node);
			else //�������Ԫ�����ӡ�ڵ���ı���Ϣ
				System.out.println(node.getText());
		}
	}
}
