package java_up_learn.xml.dom4j;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class TraverseElements {
	@Test
	public void traversAll() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read("examdata.xml");
		Element root = document.getRootElement();
		travers2(root);
		
	}
	
	public void travers(Element child){
		System.out.println(child.getName());
		Iterator<Element> it = child.elementIterator();
		while(it.hasNext()){
			Element e = it.next();
			if(e.isTextOnly())
				System.out.println(e.getName());
			else
				travers(e);
		}
	}
	
	public void travers2(Element child){
		System.out.println(child.getName());
		List<Element> list = child.elements();
		for(Element e:list){
			if(e.isTextOnly())
				System.out.println(e.getName());
			else
				travers(e);
		}
	}

}
