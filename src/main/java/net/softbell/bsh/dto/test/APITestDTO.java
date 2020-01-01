package net.softbell.bsh.dto.test;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APITestDTO {
	private String strName;
	private Integer intAge;
	private List<ExampleClass> values;
	
	public APITestDTO(String strName, Integer intAge)
	{
		this.strName = strName;
		this.intAge = intAge;
		values = new ArrayList<ExampleClass>();
		for (int i = 1; i <= 3; i++)
			values.add(new ExampleClass("Test", i));
	}
	
	@AllArgsConstructor
	@Getter
	@Setter
	class ExampleClass {
		private String strFirst;
		private Integer intSecond;
	}
}
