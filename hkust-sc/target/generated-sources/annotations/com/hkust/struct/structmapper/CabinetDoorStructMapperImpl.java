package com.hkust.struct.structmapper;

import com.hkust.dto.ao.CabinetDoorAO;
import com.hkust.dto.vo.CabinetDetailVO;
import com.hkust.dto.vo.CabinetDoorVO;
import com.hkust.entity.CabinetDoor;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-25T00:14:12+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_391 (Oracle Corporation)"
)
@Component
public class CabinetDoorStructMapperImpl implements CabinetDoorStructMapper {

    @Override
    public CabinetDoor cabinetDoorAOToCabinet(CabinetDoorAO cabinetDoorAO) {
        if ( cabinetDoorAO == null ) {
            return null;
        }

        CabinetDoor cabinetDoor = new CabinetDoor();

        cabinetDoor.setDoorId( cabinetDoorAO.getDoorId() );
        cabinetDoor.setCabinetId( cabinetDoorAO.getCabinetId() );
        cabinetDoor.setName( cabinetDoorAO.getName() );
        cabinetDoor.setType( cabinetDoorAO.getType() );
        cabinetDoor.setRoom( cabinetDoorAO.getRoom() );
        cabinetDoor.setStat( cabinetDoorAO.getStat() );
        cabinetDoor.setReagentsQty( cabinetDoorAO.getReagentsQty() );
        cabinetDoor.setInQty( cabinetDoorAO.getInQty() );
        cabinetDoor.setOutQty( cabinetDoorAO.getOutQty() );
        cabinetDoor.setLocation( cabinetDoorAO.getLocation() );

        return cabinetDoor;
    }

    @Override
    public CabinetDoorVO cabinetDoorToCabinetDoorVO(CabinetDoor cabinetDoor) {
        if ( cabinetDoor == null ) {
            return null;
        }

        String arg0 = null;
        String arg1 = null;
        String arg2 = null;
        String arg3 = null;
        String arg4 = null;
        String arg5 = null;
        int arg6 = 0;
        int arg7 = 0;
        int arg8 = 0;
        String arg9 = null;

        CabinetDoorVO cabinetDoorVO = new CabinetDoorVO( arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9 );

        cabinetDoorVO.setCabinetId( cabinetDoor.getCabinetId() );
        cabinetDoorVO.setType( cabinetDoor.getType() );
        cabinetDoorVO.setName( cabinetDoor.getName() );
        cabinetDoorVO.setRoom( cabinetDoor.getRoom() );
        cabinetDoorVO.setStat( cabinetDoor.getStat() );
        cabinetDoorVO.setReagentsQty( cabinetDoor.getReagentsQty() );
        cabinetDoorVO.setInQty( cabinetDoor.getInQty() );
        cabinetDoorVO.setOutQty( cabinetDoor.getOutQty() );
        cabinetDoorVO.setLocation( cabinetDoor.getLocation() );

        return cabinetDoorVO;
    }

    @Override
    public CabinetDetailVO.CabinetDoorDetailVO doorToDoorVO(CabinetDoor cabinetDoor) {
        if ( cabinetDoor == null ) {
            return null;
        }

        CabinetDetailVO.CabinetDoorDetailVO cabinetDoorDetailVO = new CabinetDetailVO.CabinetDoorDetailVO();

        cabinetDoorDetailVO.setType( cabinetDoor.getType() );
        cabinetDoorDetailVO.setName( cabinetDoor.getName() );
        cabinetDoorDetailVO.setRoom( cabinetDoor.getRoom() );
        cabinetDoorDetailVO.setStat( cabinetDoor.getStat() );
        cabinetDoorDetailVO.setLocation( cabinetDoor.getLocation() );

        return cabinetDoorDetailVO;
    }
}
