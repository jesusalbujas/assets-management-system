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

import org.sms.model.AssetDeliveryProcessAbstract;
import org.compiere.asset.model.MAsset;
import org.compiere.asset.model.MAssetDelivery;
import org.compiere.util.DB;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/** Generated Process for (Asset Delivery Process)
 *  @author Jesús Albujas
 *  @version Release 3.9.4
 */
public class AssetDeliveryProcess extends AssetDeliveryProcessAbstract {

    @Override
    protected String doIt() throws Exception {
    	
        int delivered = 0;
        int errors = 0;

        if (getAssetId() <= 0) {
            return "@Error@ @No se seleccionó ningún activo fijo.@";
        }

        try {
            var ctx = getCtx();
            var trxName = get_TrxName();

            // Obtain asset
            MAsset asset = new MAsset(ctx, getAssetId(), trxName);

            // Asset
            String isinPosession = "UPDATE A_Asset SET IsInPosession = 'N' WHERE A_Asset_ID = ?";
            Object[] params = new Object[]{asset.getA_Asset_ID()};
            DB.executeUpdateEx(isinPosession, params, trxName);
                        
            asset.set_ValueOfColumn("PST_Location_ID", null);
            asset.set_ValueOfColumn("A_Asset_Status", "IU");
            asset.set_ValueOfColumn("PST_Employees_ID", getEmployeesId());
            
            Integer bpartnerId = asset.getC_BPartner_ID();
            Integer assetOrgId = asset.getAD_Org_ID();
            
            asset.saveEx();

            // Obtain date of VE
            ZoneId venezuelaZone = ZoneId.of("America/Caracas");
            ZonedDateTime venezuelaNow = ZonedDateTime.now(venezuelaZone);
            Timestamp now = Timestamp.valueOf(venezuelaNow.toLocalDateTime());
            
            // create asset delivery
            MAssetDelivery assetDelivery = new MAssetDelivery(asset, getEmployeesId(), getUserId(), now);
            assetDelivery.setDescription(getDescription());
            assetDelivery.set_ValueOfColumn("A_Asset_Status", "IU");
            assetDelivery.setMovementDate(now);
            assetDelivery.setAD_User_ID(getUserId());
            assetDelivery.set_ValueOfColumn("PST_Employees_ID", getEmployeesId());
            assetDelivery.setC_BPartner_ID(bpartnerId);
            assetDelivery.setAD_Org_ID(assetOrgId);
            assetDelivery.set_ValueOfColumn("PST_IsAsigned", "Y");
            assetDelivery.saveEx();

            String sql = "UPDATE PST_Employees SET PST_AssetAsigned = COALESCE(PST_AssetAsigned, 0) + 1 WHERE PST_Employees_ID = ?";
            DB.executeUpdateEx(sql, new Object[] { getEmployeesId() }, trxName);
            delivered++;

        } catch (Exception e) {
            errors++;
            log.severe("Error al procesar activo ID=" + getAssetId() + " -> " + e.getMessage());
        }

        return "@Entregados@=" + delivered + " - @Errores@=" + errors;
    }
}