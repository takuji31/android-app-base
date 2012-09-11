package com.github.takuji31.appbase.util;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayMapper<Input,Output> {

	protected abstract Output map(Input item);
	
	public List<Output> execute(List<Input> source) {
		ArrayList<Output> result = new ArrayList<Output>();
		for (Input input : source) {
			result.add(map(input));
		}
		return result;
	}
}
