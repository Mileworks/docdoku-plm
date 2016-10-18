/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2015 DocDoku SARL
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

package com.docdoku.server.rest.dto.change;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@ApiModel(value="ChangeOrderDTO", description="This class is the representation of a {@link com.docdoku.core.change.ChangeOrder} entity")
public class ChangeOrderDTO extends ChangeItemDTO implements Serializable {

    @ApiModelProperty(value = "Change order addressed request")
    private List<ChangeRequestDTO> addressedChangeRequests;

    @ApiModelProperty(value = "Change order due milestone")
    @XmlElement(nillable = true)
    private int milestoneId;

    public ChangeOrderDTO() {

    }

    public List<ChangeRequestDTO> getAddressedChangeRequests() {
        return addressedChangeRequests;
    }

    public void setAddressedChangeRequests(List<ChangeRequestDTO> addressedChangeRequests) {
        this.addressedChangeRequests = addressedChangeRequests;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }
}
