package com.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Organization implements Comparable<Organization> {

	private String orgName;
	private String otherEmail;

	@Override
	public int compareTo(Organization o) {
		return this.orgName.compareTo(o.orgName);
	}
	
	public void sample() {
		System.out.println("hiii");
	}

}
