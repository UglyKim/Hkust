package com.hkust.struct.structmapper;

import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.vo.ReagentsVO;
import com.hkust.entity.Reagents;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import javax.annotation.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-25T00:14:21+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_391 (Oracle Corporation)"
)
@Component
public class ReagentsStructMapperImpl implements ReagentsStructMapper {

    private final DatatypeFactory datatypeFactory;

    public ReagentsStructMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public ReagentsVO toReagentsVO(Reagents reagents) {
        if ( reagents == null ) {
            return null;
        }

        String arg0 = null;
        String arg1 = null;
        String arg2 = null;
        String arg3 = null;
        String arg4 = null;
        String arg5 = null;
        String arg6 = null;
        LocalDate arg7 = null;
        String arg8 = null;
        String arg9 = null;
        LocalDate arg10 = null;

        ReagentsVO reagentsVO = new ReagentsVO( arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10 );

        reagentsVO.setReagentsId( reagents.getReagentsId() );
        reagentsVO.setBarCode( reagents.getBarCode() );
        reagentsVO.setName( reagents.getName() );
        reagentsVO.setQty( reagents.getQty() );
        reagentsVO.setBottleWeight( reagents.getBottleWeight() );
        reagentsVO.setReagentWeight( reagents.getReagentWeight() );
        reagentsVO.setExpireDate( reagents.getExpireDate() );
        if ( reagents.getIsExpire() != null ) {
            reagentsVO.setIsExpire( String.valueOf( reagents.getIsExpire() ) );
        }
        reagentsVO.setOperator( reagents.getOperator() );
        reagentsVO.setAddDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( reagents.getAddDate() ) ) );

        return reagentsVO;
    }

    @Override
    public Reagents toReagents(ReagentsAO reagentsAO) {
        if ( reagentsAO == null ) {
            return null;
        }

        Reagents reagents = new Reagents();

        reagents.setDoorId( reagentsAO.getDoorId() );
        reagents.setBarCode( reagentsAO.getBarCode() );
        reagents.setCabinetId( reagentsAO.getCabinetId() );
        reagents.setName( reagentsAO.getName() );
        reagents.setQty( reagentsAO.getQty() );
        reagents.setBottleWeight( reagentsAO.getBottleWeight() );
        reagents.setReagentWeight( reagentsAO.getReagentWeight() );
        reagents.setExpireDate( reagentsAO.getExpireDate() );
        reagents.setRemark( reagentsAO.getRemark() );

        return reagents;
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }
}
