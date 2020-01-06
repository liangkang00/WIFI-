/**
 *
 */
package com.ssv.ssvwifitool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
//import java.io.FileWriter;
import java.util.ArrayList;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;

//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;


import java.util.List;

import com.ssv.ssvwifitool.chip.Chip;
import com.ssv.ssvwifitool.chip.ChipModule;

import android.os.Environment;

/**
 * @author Administrator
 *
 */
public class DumpInfo {

	private String dumpFile = "ssv6200_dump.xml";
	
	private List<ChipModule> list;

	public DumpInfo(String File) {
		//interfaceDataMap = new HashMap<String, Map<String, String>>();
		this.dumpFile = File;
	}
	

	public void loadInfo() {
		loadInfo(dumpFile);
	}

	public void loadInfo(String dumpFile) {
		try {
		/*	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			//File SDCardpath = Environment.getExternalStorageDirectory();
			
			//File myDataPath = new File( SDCardpath.getAbsolutePath() + "/test.txt");
			File myDataPath = new File( dumpFile);
			Document doc = db.parse(myDataPath.getAbsolutePath());

			if (doc.getChildNodes().getLength() > 0) {
				NodeList list = doc.getChildNodes().item(0).getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					Node subnode = list.item(i);

					if (subnode.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}

					if (subnode.getNodeName().trim().equals("Address")) {
						if (subnode.getNodeName().trim().equals("Address")) {
							
							List.add(subnode.getTextContent());
							i=i+2;	
						}
					} else if (subnode.getNodeName().trim().length() != 0) {
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/	
			
		   File SDCardpath = Environment.getExternalStorageDirectory();
		   
		   boolean can_write = SDCardpath.canWrite(); 
		   boolean can_read = SDCardpath.canRead(); 
		   String path = SDCardpath.getAbsolutePath() +"/"+ dumpFile; 
           File myDataPath = new File( SDCardpath.getAbsolutePath() +"/"+ dumpFile);
           FileReader fr = new FileReader(myDataPath);
           if( myDataPath.exists())
           {
        	   /*BufferedReader br = new BufferedReader(fr);
        	   String temp = br.readLine();
        	   while(temp !=null)
        	   {
        		   List.add(temp);
        		   temp = br.readLine();
        	   }*/
        	   Chip chip= new Chip(myDataPath, null);
        	   list= chip.getModules();
        	   
        	   
        	   
           }
           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
           
	}

	public List<ChipModule> getDumpList() {
		return list;
	}
	

	
}
