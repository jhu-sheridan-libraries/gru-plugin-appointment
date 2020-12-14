package fr.paris.lutece.plugins.appointment.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.test.LuteceTestCase;

public class ClosingDayServiceTest extends LuteceTestCase
{

    /**
     * Find all the closing dates of the form on a given period
     */
    public void testFindListDateOfClosingDayByIdFormAndDateRange( )
    {
        // Build the form
        int nIdForm = FormService.createAppointmentForm( FormServiceTest.buildAppointmentForm( ) );
        List<LocalDate> listClosingDays = new ArrayList<>( );
        listClosingDays.add( Constants.DATE_10 );
        listClosingDays.add( Constants.DATE_11 );
        listClosingDays.add( Constants.DATE_12 );
        listClosingDays.add( Constants.DATE_13 );
        ClosingDayService.saveListClosingDay( nIdForm, listClosingDays );

        List<LocalDate> listClosingDaysFound = ClosingDayService.findListDateOfClosingDayByIdFormAndDateRange( nIdForm, Constants.DATE_1,
                Constants.DATE_9);
        assertEquals( 2, listClosingDaysFound.size( ) );

        FormService.removeForm( nIdForm );
    }

}
