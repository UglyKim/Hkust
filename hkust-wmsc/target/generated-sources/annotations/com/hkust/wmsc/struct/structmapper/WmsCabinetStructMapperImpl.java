package com.hkust.wmsc.struct.structmapper;

import com.hkust.entity.wms.WmsCabinet;
import com.hkust.wmsc.dto.vo.CabinetVO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T19:09:40+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_391 (Oracle Corporation)"
)
@Component
public class WmsCabinetStructMapperImpl implements WmsCabinetStructMapper {

    @Override
    public CabinetVO wmsCabinetToCabinetVO(WmsCabinet wmsCabinet) {
        if ( wmsCabinet == null ) {
            return null;
        }

        CabinetVO cabinetVO = new CabinetVO();

        cabinetVO.setId( wmsCabinet.getId() );
        cabinetVO.setState( wmsCabinet.getState() );
        cabinetVO.setType( wmsCabinet.getType() );
        cabinetVO.setName( wmsCabinet.getName() );
        cabinetVO.setBarcode( wmsCabinet.getBarcode() );
        cabinetVO.setReview_state( wmsCabinet.getReview_state() );
        cabinetVO.setStorageRoom( wmsCabinet.getStorageRoom() );
        cabinetVO.setBrand( wmsCabinet.getBrand() );
        cabinetVO.setSpecification( wmsCabinet.getSpecification() );
        cabinetVO.setLayerCount( wmsCabinet.getLayerCount() );
        cabinetVO.setCreator( wmsCabinet.getCreator() );
        cabinetVO.setCreateTime( wmsCabinet.getCreateTime() );
        cabinetVO.setAuditor( wmsCabinet.getAuditor() );
        cabinetVO.setAuditTime( wmsCabinet.getAuditTime() );
        cabinetVO.setMaterial( wmsCabinet.getMaterial() );
        cabinetVO.setThresholdRatio( wmsCabinet.getThresholdRatio() );
        cabinetVO.setTeachingBuilding( wmsCabinet.getTeachingBuilding() );
        cabinetVO.setCapacity( wmsCabinet.getCapacity() );
        cabinetVO.setWasteContainerName( wmsCabinet.getWasteContainerName() );
        cabinetVO.setWasteLiquidsType( wmsCabinet.getWasteLiquidsType() );

        return cabinetVO;
    }
}
