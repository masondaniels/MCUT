package com.github.masondaniels.argumentparser;

public class ArgumentParserStorage {

	private String argumentValues;
	private ArgumentRegistry reg;

	public ArgumentParserStorage(ArgumentRegistry reg, String argumentValues) {
		this.reg = reg;
		this.argumentValues = argumentValues;
	}

	public ArgumentRegistry getReg() {
		return reg;
	}

	public void setReg(ArgumentRegistry reg) {
		this.reg = reg;
	}

	public String getArgumentValues() {
		return argumentValues;
	}

	public void setArgumentValues(String argumentValues) {
		this.argumentValues = argumentValues;
	}

}
