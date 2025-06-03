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

package org.sms.process;

import org.sms.model.AssetRelocationProcessAbstract;
import org.compiere.asset.model.MAsset;
import org.compiere.asset.model.MAssetDelivery;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/** Generated Process for (Asset Relocation Process)
 *  @author Jesús Albujas
 *  @version Release 3.9.4
 */
public class AssetRelocationProcess extends AssetRelocationProcessAbstract
{
	@Override
	protected String doIt() throws Exception
	{
		
		int relocate = 0;
		int errors = 0;
		
		if (getAssetId() <= 0) {
			return "@Error@ @No se seleccionó un activo fijo.@";
		}
		
		try {
			
			var ctx = getCtx();
			var trxName = get_TrxName();
			
			// Obtain asset
			MAsset asset = new MAsset(ctx, getAssetId(), trxName);

            String status = getAssetStatus();
            
            if (status == null || status.isEmpty()) {
            	status = "AC";
            }

            asset.set_ValueOfColumn("A_Asset_Status", status);
            asset.set_ValueOfColumn("PST_Location_ID", getLocatorToId());

            Integer bpartnerId = asset.getC_BPartner_ID();
            Integer assetOrgId = asset.getAD_Org_ID();
            String assetSerNo = asset.getSerNo();

            asset.saveEx();

            // Obtain date of VE
            ZoneId venezuelaZone = ZoneId.of("America/Caracas");
            ZonedDateTime venezuelaNow = ZonedDateTime.now(venezuelaZone);
            Timestamp now = Timestamp.valueOf(venezuelaNow.toLocalDateTime());
			
            // create asset delivery
            MAssetDelivery assetDelivery = new MAssetDelivery(asset, getLocationId(), getUserId(), now);
            
            assetDelivery.setAD_Org_ID(assetOrgId);
            assetDelivery.setSerNo(assetSerNo);
            assetDelivery.setDescription(getDescription());
            assetDelivery.set_ValueOfColumn("A_Asset_Status", status);
            assetDelivery.setMovementDate(now);
            assetDelivery.setAD_User_ID(getUserId());
            assetDelivery.set_ValueOfColumn("PST_IsRelocation", "Y");
            assetDelivery.set_ValueOfColumn("PST_Location_ID", getLocationId());
            assetDelivery.set_ValueOfColumn("M_LocatorTo_ID", getLocatorToId());
            assetDelivery.setC_BPartner_ID(bpartnerId); // Mandatory field

            assetDelivery.saveEx();
            relocate++;
            
		} catch (Exception e){
			errors++;
			log.severe("Error when processing asset id=" + getAssetId() + " -> " + e.getMessage());
		}
		

		return "@Activos movidos@=" + relocate + " - @Errores@=" + errors;
	}
}