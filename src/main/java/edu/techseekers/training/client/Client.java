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

package edu.techseekers.training.client;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

import edu.techseekers.xml.binding.Salaries;

public final class Client {

    private Client() {
    }

    public static void main(String args[]) throws Exception {

        // Sent HTTP GET request to query salary info
        System.out.println("Sent HTTP GET request to query salary info");
        URL url = new URL("http://localhost:9000/departmentsalary/salaryofdept/d002");
        InputStream in = url.openStream();
        //System.out.println(getStringFromInputStream(in));
        readXml(in);
        
       

        // Sent HTTP GET request to query all salary info
        System.out.println("\n");
        System.out.println("Sent HTTP GET request to query all salary info");
        url = new URL("http://localhost:9000/departmentsalary/allsalaries");
        in = url.openStream();
        //System.out.println(getStringFromInputStream(in));
       readXml(in);


        System.out.println("\n");
        System.exit(0);
    }

    private static String getStringFromInputStream(InputStream in) throws Exception {
        CachedOutputStream bos = new CachedOutputStream();
        IOUtils.copy(in, bos);
        in.close();
        bos.close();
        return bos.getOut().toString();
    }

    
    private static void readXml(InputStream in) {
    	JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Salaries.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	        Salaries salaryInfo = (Salaries) jaxbUnmarshaller.unmarshal(in);
	        salaryInfo.print();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
