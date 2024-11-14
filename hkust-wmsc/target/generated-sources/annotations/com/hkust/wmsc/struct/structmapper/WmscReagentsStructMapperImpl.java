package com.hkust.wmsc.struct.structmapper;

import com.hkust.entity.wms.WmsReagents;
import com.hkust.wmsc.dto.ao.InReagentsAO;
import com.hkust.wmsc.dto.vo.ReagentsVO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-14T14:18:43+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_391 (Oracle Corporation)"
)
@Component
public class WmscReagentsStructMapperImpl implements WmscReagentsStructMapper {

    @Override
    public WmsReagents InReagentsAOToReagents(InReagentsAO inReagentsAO) {
        if ( inReagentsAO == null ) {
            return null;
        }

        WmsReagents wmsReagents = new WmsReagents();

        wmsReagents.setCasNo( inReagentsAO.getCasNo() );
        wmsReagents.setName( inReagentsAO.getName() );
        wmsReagents.setEnName( inReagentsAO.getEnName() );
        wmsReagents.setType( inReagentsAO.getType() );
        wmsReagents.setBarcode( inReagentsAO.getBarcode() );
        wmsReagents.setBrand( inReagentsAO.getBrand() );
        wmsReagents.setPrice( inReagentsAO.getPrice() );
        wmsReagents.setStorageLocation( inReagentsAO.getStorageLocation() );
        wmsReagents.setGhs( inReagentsAO.getGhs() );
        wmsReagents.setResearchGroup( inReagentsAO.getResearchGroup() );
        wmsReagents.setState( inReagentsAO.getState() );
        wmsReagents.setSpecification( inReagentsAO.getSpecification() );
        wmsReagents.setPhysicalState( inReagentsAO.getPhysicalState() );
        wmsReagents.setSpecialStorageConditions( inReagentsAO.getSpecialStorageConditions() );
        wmsReagents.setHazardClassification( inReagentsAO.getHazardClassification() );
        wmsReagents.setExpirationDate( inReagentsAO.getExpirationDate() );
        wmsReagents.setCabinetId( inReagentsAO.getCabinetId() );
        wmsReagents.setHazardProps( inReagentsAO.getHazardProps() );

        return wmsReagents;
    }

    @Override
    public ReagentsVO ReagentsToReagentsVO(WmsReagents wmsReagents) {
        if ( wmsReagents == null ) {
            return null;
        }

        ReagentsVO reagentsVO = new ReagentsVO();

        reagentsVO.setReagentsId( wmsReagents.getId() );
        reagentsVO.setCasNo( wmsReagents.getCasNo() );
        reagentsVO.setName( wmsReagents.getName() );
        reagentsVO.setEnName( wmsReagents.getEnName() );
        reagentsVO.setType( wmsReagents.getType() );
        reagentsVO.setBarcode( wmsReagents.getBarcode() );
        reagentsVO.setBrand( wmsReagents.getBrand() );
        reagentsVO.setPrice( wmsReagents.getPrice() );
        reagentsVO.setStorageLocation( wmsReagents.getStorageLocation() );
        reagentsVO.setGhs( wmsReagents.getGhs() );
        reagentsVO.setResearchGroup( wmsReagents.getResearchGroup() );
        reagentsVO.setState( wmsReagents.getState() );
        reagentsVO.setSpecification( wmsReagents.getSpecification() );
        reagentsVO.setPhysicalState( wmsReagents.getPhysicalState() );
        reagentsVO.setSpecialStorageConditions( wmsReagents.getSpecialStorageConditions() );
        reagentsVO.setHazardClassification( wmsReagents.getHazardClassification() );
        reagentsVO.setExpirationDate( wmsReagents.getExpirationDate() );

        return reagentsVO;
    }
}
