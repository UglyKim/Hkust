package com.hkust.struct.structmapper;

import com.hkust.dto.ao.OperationAO;
import com.hkust.dto.vo.EventVO;
import com.hkust.entity.Event;
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
public class EventStructMapperImpl implements EventStructMapper {

    private final DatatypeFactory datatypeFactory;

    public EventStructMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public EventVO toVO(Event event) {
        if ( event == null ) {
            return null;
        }

        String arg0 = null;
        String arg1 = null;
        String arg2 = null;
        String arg3 = null;
        String arg4 = null;
        String arg5 = null;
        LocalDate arg6 = null;

        EventVO eventVO = new EventVO( arg0, arg1, arg2, arg3, arg4, arg5, arg6 );

        eventVO.setEventId( event.getEventId() );
        eventVO.setCabinetId( event.getCabinetId() );
        eventVO.setEventType( event.getEventType() );
        eventVO.setContent( event.getContent() );
        eventVO.setOptDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( event.getOptDate() ) ) );

        return eventVO;
    }

    @Override
    public Event eventAOToEvent(OperationAO operationAO) {
        if ( operationAO == null ) {
            return null;
        }

        Event event = new Event();

        event.setCabinetId( operationAO.getCabinetId() );
        event.setDoorId( operationAO.getDoorId() );
        event.setEventType( operationAO.getEventType() );
        event.setContent( operationAO.getContent() );

        return event;
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
