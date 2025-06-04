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

import org.sms.model.UnsubscribeAssetAbstract;
import org.compiere.asset.model.MAsset;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/** Generated Process for (Asset Unsubscribe Process)
 *  @author Jesús Albujas
 *  @version Release 3.9.4
 */
public class UnsubscribeAsset extends UnsubscribeAssetAbstract
{

	@Override
	protected String doIt() throws Exception
	{
	  	
        int disposed = 0;
        int errors = 0;

        if (getAssetId() <= 0) {
            return "@Error@ @No se seleccionó ningún activo fijo.@";
        }

        try {
            var ctx = getCtx();
            var trxName = get_TrxName();

            // Obtain asset
            MAsset asset = new MAsset(ctx, getAssetId(), trxName);

            // Obtain date of VE
            ZoneId venezuelaZone = ZoneId.of("America/Caracas");
            ZonedDateTime venezuelaNow = ZonedDateTime.now(venezuelaZone);
            Timestamp nowVe = Timestamp.valueOf(venezuelaNow.toLocalDateTime());

            String assetStatus = getAssetStatus();
            
            if (assetStatus == null || assetStatus.isEmpty()) {
            	assetStatus = "DI";
            }

            // Asset
            asset.set_ValueOfColumn("PST_Location_ID", null);
            asset.set_ValueOfColumn("A_Asset_Status", assetStatus);
            asset.setIsActive(false);
            asset.setIsDisposed(true);
            asset.setAssetDisposalDate(nowVe);
            
            asset.saveEx();

            disposed++;

        } catch (Exception e) {
            errors++;
            log.severe("Error al procesar activo ID=" + getAssetId() + " -> " + e.getMessage());
        }

        return "@Desechados@=" + disposed + " - @Errores@=" + errors;
	}
}