package fr.paris.lutece.plugins.appointment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.test.LuteceTestCase;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.junit.Test;

public class UtilitiesTest extends LuteceTestCase
{
    public final static String DATE_EQUAL_TO_CONST_DATE_TIME_5 = "2022-06-25";
    public final static String TIME_EQUAL_TO_CONST_DATE_TIME_5 = "00:00:00";
    public final static String GET_FORMATTER = "getFormatter";
    /**
     * Return the closest date in past a list of date with the given date
     */
    @Test
    public void testGetClosestDateInPast( )
    {
        LocalDate localDate1 = Constants.DATE_18;
        LocalDate localDate2 = Constants.DATE_19;
        LocalDate localDate3 = Constants.DATE_20;

        List<LocalDate> listDates = new ArrayList<>( );
        listDates.add( localDate1 );
        listDates.add( localDate2 );
        listDates.add( localDate3 );
        assertEquals( localDate2, Utilities.getClosestDateInPast( listDates, Constants.DATE_21 ) );
    }

    /**
     * Return the closest date time in future in a list of date time and a given date time
     */
    @Test
    public void testGetClosestDateTimeInFuture( )
    {
        LocalDateTime localDateTime1 = Constants.DATE_TIME_1;
        LocalDateTime localDateTime2 = Constants.DATE_TIME_2;
        LocalDateTime localDateTime3 = Constants.DATE_TIME_3;

        List<LocalDateTime> listDateTime = new ArrayList<>( );
        listDateTime.add( localDateTime1 );
        listDateTime.add( localDateTime2 );
        listDateTime.add( localDateTime3 );
        assertEquals( localDateTime3, Utilities.getClosestDateTimeInFuture( listDateTime, Constants.DATE_TIME_4 ) );
    }

    /**
     * Test of getDateFormatter method, of class Utilities.
     * Change was made to use ISO formatter - should be the same for any locale
     */
    @Test
    public void testGetDateFormatter() {
        System.out.println(GET_FORMATTER);
        
        AppointmentPlugin.setPluginLocale( Locale.ENGLISH );
        Utilities.resetDateFormatter();
        DateTimeFormatter formatterEn = Utilities.getDateFormatter();
        LocalDateTime localDateTimeEn = Constants.DATE_TIME_5;
        String strDateEn = localDateTimeEn.format(formatterEn);
        assertEquals( DATE_EQUAL_TO_CONST_DATE_TIME_5 , strDateEn );

        AppointmentPlugin.setPluginLocale( Locale.FRENCH );
        Utilities.resetDateFormatter();
        DateTimeFormatter formatterFr = Utilities.getDateFormatter();
        LocalDateTime localDateTimeFr = Constants.DATE_TIME_5;
        String strDateFr = localDateTimeFr.format(formatterFr);
        assertEquals( DATE_EQUAL_TO_CONST_DATE_TIME_5 , strDateFr );

    }

    /**
     * Test of getRimeFormatter method, of class Utilities.
     * Change was made to use ISO formatter - should be the same for any locale
     */
    @Test
    public void testGetTimeFormatter() {
        System.out.println(GET_FORMATTER);

        AppointmentPlugin.setPluginLocale( Locale.ENGLISH );
        Utilities.resetTimeFormatter();
        DateTimeFormatter formatterEn = Utilities.getTimeFormatter();
        LocalDateTime localDateTimeEn = Constants.DATE_TIME_5;
        String strDateEn = localDateTimeEn.format(formatterEn);
        assertEquals( TIME_EQUAL_TO_CONST_DATE_TIME_5, strDateEn );

        AppointmentPlugin.setPluginLocale( Locale.FRENCH );
        Utilities.resetTimeFormatter();
        DateTimeFormatter formatterFr = Utilities.getTimeFormatter();
        LocalDateTime localDateTimeFr = Constants.DATE_TIME_5;
        String strDateFr = localDateTimeFr.format(formatterFr);
        assertEquals( TIME_EQUAL_TO_CONST_DATE_TIME_5, strDateFr );

    }


}
