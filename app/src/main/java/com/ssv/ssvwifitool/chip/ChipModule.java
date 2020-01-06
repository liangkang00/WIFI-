/**
 *
 */
package com.ssv.ssvwifitool.chip;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class ChipModule {	
	private String name;
    private List<String> addressList;
	/**
	 * @return the address
	 */
	public List<String> getAddress() {
		return addressList;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(List<String> addressLst) {
		this.addressList = addressLst;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
