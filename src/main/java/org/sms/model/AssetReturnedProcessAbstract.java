/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.                                     *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net                                                  *
 * or https://github.com/adempiere/adempiere/blob/develop/license.html        *
 *****************************************************************************/

package org.sms.model;

import org.compiere.process.SvrProcess;

/** Generated Process for (Asset Returned Process)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class AssetReturnedProcessAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "SMS_Asset_Returned_Process";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "Asset Returned Process";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 1000001;
	/**	Parameter Name for Fixed Asset	*/
	public static final String A_ASSET_ID = "A_Asset_ID";
	/**	Parameter Name for Employees	*/
	public static final String PST_EMPLOYEES_ID = "PST_Employees_ID";
	/**	Parameter Name for Description	*/
	public static final String DESCRIPTION = "Description";
	/**	Parameter Name for Status	*/
	public static final String STATUS = "A_Asset_Status";
	/**	Parameter Name for User/Contact	*/
	public static final String AD_USER_ID = "AD_User_ID";
	/**	Parameter Value for Fixed Asset	*/
	private int assetId;
	/**	Parameter Value for Employees	*/
	private int employeesId;
	/**	Parameter Value for Description	*/
	private String description;
	/**	Parameter Value for status	*/
	private String status;
	/**	Parameter Value for User/Contact	*/
	private int userId;

	@Override
	protected void prepare() {
		assetId = getParameterAsInt(A_ASSET_ID);
		employeesId = getParameterAsInt(PST_EMPLOYEES_ID);
		description = getParameterAsString(DESCRIPTION);
		status = getParameterAsString(STATUS);
		userId = getParameterAsInt(AD_USER_ID);
	}

	/**	 Getter Parameter Value for Fixed Asset	*/
	protected int getAssetId() {
		return assetId;
	}

	/**	 Setter Parameter Value for Fixed Asset	*/
	protected void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	/**	 Getter Parameter Value for Employees	*/
	protected int getEmployeesId() {
		return employeesId;
	}

	/**	 Setter Parameter Value for Employees	*/
	protected void setEmployeesId(int employeesId) {
		this.employeesId = employeesId;
	}

	/**	 Getter Parameter Value for Description	*/
	protected String getDescription() {
		return description;
	}

	/**	 Setter Parameter Value for Description	*/
	protected void setDescription(String description) {
		this.description = description;
	}

	/**	 Getter Parameter Value for User/Contact	*/
	protected int getUserId() {
		return userId;
	}

	/**	 Setter Parameter Value for User/Contact	*/
	protected void setUserId(int userId) {
		this.userId = userId;
	}

	/**	 Getter Parameter Value for Process ID	*/
	public static final int getProcessId() {
		return ID_FOR_PROCESS;
	}

	/**	 Getter Parameter Value for Process Value	*/
	public static final String getProcessValue() {
		return VALUE_FOR_PROCESS;
	}

	/**	 Getter Parameter Value for Process Name	*/
	public static final String getProcessName() {
		return NAME_FOR_PROCESS;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}