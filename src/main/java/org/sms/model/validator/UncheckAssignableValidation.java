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

/*
* Model validator for fixed assets.
*
* This class is responsible for validating that the "Is Assignable?" field (IsAssignable)
* is not unchecked in the following cases:
*
* - If the asset is assigned to an employee.
* - If the asset is marked as damaged or stolen.
* - If the asset is in a depreciated status (IsDepreciated = 'Y').
*
* Under any of these conditions, unchecking the "Is Assignable?" box is not allowed.
*/

package org.sms.model.validator;

import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.CLogger;
import org.compiere.util.Msg;
import org.compiere.asset.model.MAsset;

public class UncheckAssignableValidation implements ModelValidator {

    private int m_AD_Client_ID = -1;
    private static final CLogger log = CLogger.getCLogger(UncheckAssignableValidation.class);

    @Override
    public void initialize(ModelValidationEngine engine, MClient client) {
        if (client != null) {
            m_AD_Client_ID = client.getAD_Client_ID();
            engine.addModelChange(MAsset.Table_Name, this);
        } else {
            log.warning("ModelValidator initialized with null client");
        }
    }
    @Override
    public int getAD_Client_ID() {
        return m_AD_Client_ID;
    }

    @Override
    public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
        return null; // No se requiere lógica en login
    }

    @Override
    public String modelChange(PO po, int type) throws Exception {
        if (!(po instanceof MAsset)) {
            return null;
        }

        if (type == TYPE_BEFORE_CHANGE) {
            MAsset asset = (MAsset) po;

            if (asset.is_ValueChanged("PST_IsAssignable")) {
                boolean newValue = asset.get_ValueAsBoolean("PST_IsAssignable");

                if (!newValue) {
                    boolean isDepreciated = "Y".equals(asset.get_ValueAsString("IsDepreciated"));
                    String status = asset.get_ValueAsString("A_Asset_Status");
                    boolean isDamaged = "DM".equals(status);
                    boolean isStolen  = "ST".equals(status);
                    Integer employeeId = asset.get_ValueAsInt("PST_Employees_ID");
                    boolean isAssigned = false;
                    
                    if (employeeId > 0) {
                    	isAssigned = true;
                    }

                    if (isDepreciated || isDamaged || isStolen || isAssigned) {
                        return Msg.getMsg(po.getCtx(), "UnassignNotAllowedDueToState");
                    }
                }
            }
        }

        return null;
    }

    @Override
    public String docValidate(PO po, int timing) {
        return null; // No usamos validación de documentos
    }

    public void reset() {
        // No se necesita limpieza
    }
}