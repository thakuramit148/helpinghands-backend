package com.helpinghands.organization.model;
import com.helpinghands.queryhelper.annotations.QueryHelperColumnName;
import com.helpinghands.queryhelper.annotations.QueryHelperPrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationCategoryRefrence {
	@QueryHelperPrimaryKey
	private int id;
	@QueryHelperColumnName(name = "org_id")
	private int OrgId;
	@QueryHelperColumnName(name = "org_category_id")	
	private int OrgCatId;	
}
