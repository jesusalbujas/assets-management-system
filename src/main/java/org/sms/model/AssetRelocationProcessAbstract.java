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
 * Developer(s): Jesús Albujas, jesusramirez350000@gmail.com   			      *
 *****************************************************************************/

package org.sms.model;

import org.compiere.process.SvrProcess;

/** Generated Process for (Asset Relocation Process)
 *  @author Jesús Albujas
 *  @version Release 3.9.4
 */
public abstract class AssetRelocationProcessAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "SMS_Asset_Relocation_Process";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "Asset Relocation Process";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 1000002;
	/**	Parameter Name for Fixed Asset	*/
	public static final String A_ASSET_ID = "A_Asset_ID";
	/**	Parameter Name for Description	*/
	public static final String DESCRIPTION = "Description";
	/**	Parameter Name for Location	*/
	public static final String PST_LOCATION_ID = "PST_Location_ID";
	/**	Parameter Name for Locator To	*/
	public static final String M_LOCATORTO_ID = "M_LocatorTo_ID";
	/**	Parameter Name for Asset Status	*/
	public static final String A_ASSET_STATUS = "A_Asset_Status";
	/**	Parameter Name for User/Contact	*/
	public static final String AD_USER_ID = "AD_User_ID";
	/**	Parameter Value for Fixed Asset	*/
	private int assetId;
	/**	Parameter Value for Description	*/
	private String description;
	/**	Parameter Name for Status	*/
	public static final String STATUS = "A_Asset_Status";
	/**	Parameter Value for Location	*/
	private int locationId;
	/**	Parameter Value for Locator To	*/
	private int locatorToId;
	/**	Parameter Value for status	*/
	private String status;
	/**	Parameter Value for Asset Status	*/
	private String assetStatus;
	/**	Parameter Value for User/Contact	*/
	private int userId;

	@Override
	protected void prepare() {
		assetId = getParameterAsInt(A_ASSET_ID);
		description = getParameterAsString(DESCRIPTION);
		locationId = getParameterAsInt(PST_LOCATION_ID);
		locatorToId = getParameterAsInt(M_LOCATORTO_ID);
		assetStatus = getParameterAsString(A_ASSET_STATUS);
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

	/**	 Getter Parameter Value for Description	*/
	protected String getDescription() {
		return description;
	}

	/**	 Setter Parameter Value for Description	*/
	protected void setDescription(String description) {
		this.description = description;
	}

	/**	 Getter Parameter Value for Location	*/
	protected int getLocationId() {
		return locationId;
	}

	/**	 Setter Parameter Value for Location	*/
	protected void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	/**	 Getter Parameter Value for Locator To	*/
	protected int getLocatorToId() {
		return locatorToId;
	}

	/**	 Setter Parameter Value for Locator To	*/
	protected void setLocatorToId(int locatorToId) {
		this.locatorToId = locatorToId;
	}

	/**	 Getter Parameter Value for Asset Status	*/
	protected String getAssetStatus() {
		return assetStatus;
	}

	/**	 Setter Parameter Value for Asset Status	*/
	protected void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
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
}