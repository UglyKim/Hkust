package com.hkust.struct.structmapper;

import com.hkust.dto.vo.ReagentsOptVO;
import com.hkust.entity.ReagentsRecord;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-25T00:14:21+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_391 (Oracle Corporation)"
)
@Component
public class ReagentsRecordStructMapperImpl implements ReagentsRecordStructMapper {

    @Override
    public ReagentsOptVO toVO(ReagentsRecord reagentsRecord) {
        if ( reagentsRecord == null ) {
            return null;
        }

        String arg0 = null;
        String arg1 = null;
        String arg2 = null;
        String arg3 = null;
        LocalDateTime arg4 = null;
        String arg5 = null;

        ReagentsOptVO reagentsOptVO = new ReagentsOptVO( arg0, arg1, arg2, arg3, arg4, arg5 );

        reagentsOptVO.setCabinetId( reagentsRecord.getCabinetId() );
        reagentsOptVO.setDoorId( reagentsRecord.getDoorId() );

        return reagentsOptVO;
    }
}
