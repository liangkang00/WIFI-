/**
 *
 */
package com.ssv.ssvwifitool.chip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Element;

import android.util.Log;

import com.ssv.ssvwifitool.*;

public class Chip {

	private String name;
	
	private List<ChipModule> modules=new ArrayList<ChipModule>();
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    FileInputStream fin;
    Document document;
    Element root;
    NodeList nodes;

	//private SPIDevice spiDevice;

	
	/**
	 * @return the modules
	 */
	@SuppressWarnings("rawtypes")
	public List<ChipModule> getModules() {
		return modules;
	}

	//@SuppressWarnings("rawtypes")
	//private List modules = new ArrayList();

	public Chip(File configFile, InputStream resConfigFile) {
		try {			     
			        factory=DocumentBuilderFactory.newInstance();
			
			        builder=factory.newDocumentBuilder();
			        if (configFile != null) {
			            fin = new FileInputStream(configFile); 
			            document=builder.parse(fin);  //以樹狀格式存於記憶體中﹐比較耗記憶體
			        }
					else
					{
					    document=builder.parse(resConfigFile);
					}
			        root=document.getDocumentElement();
			
			        nodes=root.getElementsByTagName("Module");
			
			
			        /*for(int i=0;i<nodes.getLength();i++){
			
			            Element moduleElement=(Element)nodes.item(i);
			
			            ChipModule module=new ChipModule();
			
			            module.setName(moduleElement.getAttribute("name"));			           			          			             
			
			            modules.add(module);
			
			        }*/
			        if (document.getChildNodes().getLength() > 0) {
						NodeList list = document.getChildNodes().item(0).getChildNodes();
						for (int i = 0; i < list.getLength(); i++) {
							Node subnode = list.item(i);
							Log.d("angel",subnode.getNodeName());
							if (subnode.getNodeName().equals("Module")) {
								parseModules(subnode, modules);
							}
						}
					}
			        
			        

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void parseModules(Node node, List module) {
		NodeList list = node.getChildNodes();
		ChipModule chipModule = new ChipModule();
		List<String> tempAddressList = new ArrayList<String>();
		for (int i = 0; i < list.getLength(); i++) {
			Node subnode = list.item(i);
			
			if (subnode.getNodeName().equals("List")) {
				NodeList sublist = subnode.getChildNodes();
			
				for (int j = 0; j < sublist.getLength(); j++) {
					if (sublist.item(j).getNodeName().equals("Address")) {
						tempAddressList.add(sublist.item(j).getTextContent());
						//tempAddressList.set(j, sublist.item(j).getTextContent());
					}
					
					} 
			}else if (subnode.getNodeName().equals("Name")) {
						chipModule.setName(subnode.getTextContent());
					}
				}
		   chipModule.setAddress(tempAddressList);;
		   module.add(chipModule);	
			}
			
			/*if (subnode.getNodeName().equals("Module")) {
				ChipModule chipModule = new ChipModule();
				NodeList sublist = subnode.getChildNodes();
			    
				for (int j = 0; j < sublist.getLength(); j++) {
					if (sublist.item(j).getNodeName().equals("List")) {
						List<String> tempAddressList= new ArrayList<String>();
						NodeList adrlist = subnode.getChildNodes();
						for (int k = 0; k < adrlist.getLength(); k++) {
							if (sublist.item(k).getNodeName().equals("Address")) {
								tempAddressList.set(k, adrlist.item(k).getTextContent());
							}
						}
						chipModule.setAddress(tempAddressList);
					
					} else if (sublist.item(j).getNodeName().equals("Name")) {
						chipModule.setName(sublist.item(j).getTextContent());
					}
				}
				module.add(chipModule);
			}*/
		
	
}
