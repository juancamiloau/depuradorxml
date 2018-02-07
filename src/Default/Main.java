package Default;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import au.com.bytecode.opencsv.CSVReader;

public class Main {

	public static void main(String[] args) throws DocumentException, IOException {

		try {			
			SAXReader reader = new SAXReader();
			Document document = reader.read("C:/Users/jalvarez/Downloads/vsts-agent-win-x64-2.127.0/"
					+ "_work/1/s/target/surefire-reports/TEST-com.choucair.formacion.RunnerFeatures.xml"); //Ruta XML Junit
			Element root;
			CSVReader csvreader = new CSVReader(new FileReader("C:/Users/jalvarez/Downloads/"
					+ "vsts-agent-win-x64-2.127.0/_work/1/s/target/site/serenity/results.csv")); // Ruta Results de los test
			
			String[] nextLine;
			root = document.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getName().equals("testcase")) {
					if (!(element.attributeValue("name").contains("Scenario:"))) {
						System.out.println("Elimina nodo: " + element.attributeValue("name") );
						element.detach();
					}
				}				
			}
			while ((nextLine = csvreader.readNext()) != null) {
				root = document.getRootElement();
				for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
					Element element = it.next();
					if (element.getName().equals("testcase")) {
						if (("Scenario: " + nextLine[1]).equals(element.attributeValue("name"))) {
							element.addAttribute("time", nextLine[5]);
							System.out.println("Agrega atributo time: " + nextLine[5]+" Al nodo: "+ element.attributeValue("name") );
							break;
						} 						
					}
				}
			}
			FileWriter out = new FileWriter("C:/Users/jalvarez/Downloads/vsts-agent-win-x64-2.127.0/_work/1/s/target/surefire-reports/TEST-com.choucair.formacion.RunnerFeatures.xml");
			document.write(out);
			out.close();
			csvreader.close();
			reader = null;
		} catch (Exception e) {

		}

	}

}
