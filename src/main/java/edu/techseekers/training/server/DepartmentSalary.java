/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.techseekers.training.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.techseekers.training.jdbc.JDBC;
import edu.techseekers.xml.binding.Salaries;

@Path("/departmentsalary/")
@Produces("text/xml")
public class DepartmentSalary {
	Map<String, Double> salariesMap;
	Salaries salariesXml;

	public DepartmentSalary() {
		System.out.println("coming");
	}

	@GET
	@Path("/salaryofdept/{name}/")
	public Salaries getSalary(@PathParam("name") String name) {
		System.out.println("----invoking getSalary, Salary name is: " + name);
		init();
		Salaries.item item;
		if (salariesMap.size() == 0) {
			item = new Salaries.item("Connection error!", null);
		} else if (!salariesMap.containsKey(name)) {
			item = new Salaries.item(name + " doesn't exist!", null);
		}else {
			item = new Salaries.item(name, salariesMap.get(name));
		}
		salariesXml.getItem().add(item);
		return salariesXml;
	}

	@GET
	@Path("/allsalaries/")
	public Salaries getAllSalaries() {
		System.out.println("----invoking getAllSalaries ");
		init();
		ValueComparator comparator=new ValueComparator();
		List<Map.Entry<String,Double>> list=new ArrayList<Entry<String, Double>>();  
        list.addAll(salariesMap.entrySet());  
        Collections.sort(list,comparator);  
        
        for (Entry<String, Double> pair : list) {
        	Salaries.item salary_item = new Salaries.item(pair.getKey(),pair.getValue());
			salariesXml.getItem().add(salary_item);
		}
//		for (Entry<String,Double> pair : salariesMap.entrySet()) {
//			Salaries.item salary_item = new Salaries.item(pair.getKey(),pair.getValue());
//			salariesXml.getItem().add(salary_item);
//		}
		return salariesXml;
	}

	public void init() {
		salariesMap = JDBC.QuerySalaryData();
		salariesXml = new Salaries();
	}

	private class ValueComparator implements
			Comparator<Map.Entry<String, Double>> {
		public int compare(Map.Entry<String, Double> mp1,
				Map.Entry<String, Double> mp2) {
			return new Double(mp2.getValue() - mp1.getValue()).intValue();
		}
	}
}
