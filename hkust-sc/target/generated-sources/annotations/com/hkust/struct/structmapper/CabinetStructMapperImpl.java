package com.hkust.struct.structmapper;

import com.hkust.dto.ao.CabinetAO;
import com.hkust.dto.vo.CabinetDetailVO;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.entity.Cabinet;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-25T00:14:12+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_391 (Oracle Corporation)"
)
@Component
public class CabinetStructMapperImpl implements CabinetStructMapper {

    @Override
    public CabinetVO toVO(Cabinet cabinet) {
        if ( cabinet == null ) {
            return null;
        }

        String arg0 = null;
        String arg1 = null;
        String arg2 = null;
        String arg3 = null;
        String arg4 = null;

        CabinetVO cabinetVO = new CabinetVO( arg0, arg1, arg2, arg3, arg4 );

        cabinetVO.setCabinetId( cabinet.getCabinetId() );
        cabinetVO.setName( cabinet.getName() );
        cabinetVO.setLocation( cabinet.getLocation() );
        cabinetVO.setCabinetAddr( cabinet.getCabinetAddr() );
        cabinetVO.setOpenMode( cabinet.getOpenMode() );

        return cabinetVO;
    }

    @Override
    public Cabinet cabinetAOToCabinet(CabinetAO cabinetAO) {
        if ( cabinetAO == null ) {
            return null;
        }

        Cabinet cabinet = new Cabinet();

        cabinet.setCabinetId( cabinetAO.getCabinetId() );
        cabinet.setName( cabinetAO.getName() );
        cabinet.setLocation( cabinetAO.getLocation() );
        cabinet.setMac( cabinetAO.getMac() );
        cabinet.setCabinetAddr( cabinetAO.getCabinetAddr() );
        cabinet.setOpenMode( cabinetAO.getOpenMode() );
        cabinet.setRemark( cabinetAO.getRemark() );

        return cabinet;
    }

    @Override
    public CabinetDetailVO cabinetToCabinetDetailVO(Cabinet cabinet) {
        if ( cabinet == null ) {
            return null;
        }

        CabinetDetailVO cabinetDetailVO = new CabinetDetailVO();

        cabinetDetailVO.setCabinetId( cabinet.getCabinetId() );
        cabinetDetailVO.setName( cabinet.getName() );
        cabinetDetailVO.setLocation( cabinet.getLocation() );
        cabinetDetailVO.setCabinetAddr( cabinet.getCabinetAddr() );
        cabinetDetailVO.setOpenMode( cabinet.getOpenMode() );

        return cabinetDetailVO;
    }
}
