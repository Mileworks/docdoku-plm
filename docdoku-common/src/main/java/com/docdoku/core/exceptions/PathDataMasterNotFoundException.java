/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2017 DocDoku SARL
 *
 * This file is part of DocDokuPLM.
 *
 * DocDokuPLM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDokuPLM is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
 * GNU Affero General Public License for more details.  
 *  
 * You should have received a copy of the GNU Affero General Public License  
 * along with DocDokuPLM.  If not, see <http://www.gnu.org/licenses/>.  
 */

package com.docdoku.core.exceptions;

import java.text.MessageFormat;
import java.util.Locale;

/**
 *
 * @author Morgan Guimard
 */
public class PathDataMasterNotFoundException extends EntityNotFoundException {
    private final Integer mPathDataMasterId;
    private final String mPathDataMasterPath;

    public PathDataMasterNotFoundException(String pMessage) {
        super(pMessage);
        mPathDataMasterId=null;
        mPathDataMasterPath=null;
    }
    public PathDataMasterNotFoundException(Locale pLocale, String pPathDataMasterPath) {
        this(pLocale, pPathDataMasterPath, null);
    }

    public PathDataMasterNotFoundException(Locale pLocale, String pPathDataMasterPath, Throwable pCause) {
        super(pLocale, pCause);
        mPathDataMasterPath=pPathDataMasterPath;
        mPathDataMasterId=null;
    }

    public PathDataMasterNotFoundException(Locale pLocale, Integer pPathDataMasterId) {
        this(pLocale, pPathDataMasterId, null);
    }

    public PathDataMasterNotFoundException(Locale pLocale, Integer pPathDataMasterId, Throwable pCause) {
        super(pLocale, pCause);
        mPathDataMasterId=pPathDataMasterId;
        mPathDataMasterPath=null;
    }

    @Override
    public String getLocalizedMessage() {
        String message = getBundleDefaultMessage();
        if(mPathDataMasterPath!=null)
            return MessageFormat.format(message,mPathDataMasterPath);
        else
            return MessageFormat.format(message,mPathDataMasterId);
    }
}
