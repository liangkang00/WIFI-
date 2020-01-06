/**
 *
 */
package com.ssv.ssvwifitool.chip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class ChipSubModule {
	@SuppressWarnings("rawtypes")
	private List modules = new ArrayList();
	private String name;

	/**
	 * @return the modules
	 */
	@SuppressWarnings("rawtypes")
	public List getModules() {
		return modules;
	}

	public ChipSubModule(String name) {
		this.name = name;
	}

	/**
	 * @param modules the modules to set
	 */
	@SuppressWarnings("rawtypes")
	public void setModules(List modules) {
		this.modules = modules;
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
