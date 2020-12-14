package fr.paris.lutece.plugins.appointment.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.appointment.business.form.Form;
import fr.paris.lutece.plugins.appointment.business.form.FormHome;
import fr.paris.lutece.plugins.appointment.business.planning.WeekDefinition;
import fr.paris.lutece.plugins.appointment.business.planning.WorkingDay;
import fr.paris.lutece.plugins.appointment.web.dto.AppointmentFormDTO;
import fr.paris.lutece.test.LuteceTestCase;

public class WorkingDayServiceTest extends LuteceTestCase
{
    public final static String TIME_1 = "18:00";
    public final static String TIME_3 = "20:00";
    public final static String TIME_4 = "09:00";
    public final static String TIME_6 = "10:00";
    private AppointmentFormDTO appointmentForm;
    private int nIdForm;
    /**
     * Get the max ending time of a list of working days
     */
    public void testGetMaxEndingTimeOfAListOfWorkingDay( )
    {
        // Build the form
        this.appointmentForm.setTimeEnd( TIME_1 );

        AppointmentFormDTO appointmentForm2 = FormServiceTest.buildAppointmentForm( );
        appointmentForm2.setIdForm( this.nIdForm );
        appointmentForm2.setTimeEnd( TIME_3 );
        LocalDate dateOfModification = Constants.DATE_4;
        FormService.updateAdvancedParameters( appointmentForm2, dateOfModification );

        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( this.nIdForm );
        List<WorkingDay> listWorkingDay = new ArrayList<>( );
        for ( WeekDefinition weekDefinition : listWeekDefinition )
        {
            listWorkingDay.addAll( weekDefinition.getListWorkingDay( ) );
        }

        assertEquals( Constants.TIME_9, WorkingDayService.getMaxEndingTimeOfAListOfWorkingDay( listWorkingDay ) );
    }

    /**
     * Get the max ending time of a working day
     */
    public void testGetMaxEndingTimeOfAWorkingDay( )
    {
        // Build the form
        this.appointmentForm.setTimeEnd( TIME_1 );

        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( this.nIdForm );
        List<WorkingDay> listWorkingDay = WorkingDayService.findListWorkingDayByWeekDefinition( listWeekDefinition.get( 0 ).getIdWeekDefinition( ) );

        WorkingDay workingDayMonday = listWorkingDay.stream( ).filter( w -> w.getDayOfWeek( ) == DayOfWeek.MONDAY.getValue( ) ).findFirst( ).get( );

        assertEquals( Constants.TIME_8, WorkingDayService.getMaxEndingTimeOfAWorkingDay( workingDayMonday ) );
    }

    /**
     * Get the min duration slot of a list of working days
     */
    public void testGetMinDurationTimeSlotOfAListOfWorkingDay( )
    {
        // Build the form
        this.appointmentForm.setDurationAppointments( 30 );

        AppointmentFormDTO appointmentForm2 = FormServiceTest.buildAppointmentForm( );
        appointmentForm2.setIdForm( this.nIdForm );
        appointmentForm2.setDurationAppointments( 10 );
        LocalDate dateOfModification = Constants.DATE_7;
        FormService.updateAdvancedParameters( appointmentForm2, dateOfModification );

        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( this.nIdForm );
        List<WorkingDay> listWorkingDay = new ArrayList<>( );
        for ( WeekDefinition weekDefinition : listWeekDefinition )
        {
            listWorkingDay.addAll( weekDefinition.getListWorkingDay( ) );
        }

        assertEquals( 10, WorkingDayService.getMinDurationTimeSlotOfAListOfWorkingDay( listWorkingDay ) );
    }

    /**
     * Get the min duration slot of a working day
     */
    public void testGetMinDurationTimeSlotOfAWorkingDay( )
    {
        // Build the form
        this.appointmentForm.setDurationAppointments( 30 );
        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( this.nIdForm );
        List<WorkingDay> listWorkingDay = WorkingDayService.findListWorkingDayByWeekDefinition( listWeekDefinition.get( 0 ).getIdWeekDefinition( ) );

        WorkingDay workingDayMonday = listWorkingDay.stream( ).filter( w -> w.getDayOfWeek( ) == DayOfWeek.MONDAY.getValue( ) ).findFirst( ).get( );

        assertEquals( 30, WorkingDayService.getMinDurationTimeSlotOfAWorkingDay( workingDayMonday ) );
    }

    /**
     * Get the min starting time of a list of working days
     */
    public void testGetMinStartingTimeOfAListOfWorkingDay( )
    {
        // Build the form
        this.appointmentForm.setTimeStart( TIME_4 );

        AppointmentFormDTO appointmentForm2 = FormServiceTest.buildAppointmentForm( );
        appointmentForm2.setIdForm( this.nIdForm );
        appointmentForm2.setTimeStart( TIME_6);
        LocalDate dateOfModification = Constants.DATE_4;
        FormService.updateAdvancedParameters( appointmentForm2, dateOfModification );

        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( this.nIdForm );
        List<WorkingDay> listWorkingDay = new ArrayList<>( );
        for ( WeekDefinition weekDefinition : listWeekDefinition )
        {
            listWorkingDay.addAll( weekDefinition.getListWorkingDay( ) );
        }

        assertEquals( Constants.STARTING_TIME_3, WorkingDayService.getMinStartingTimeOfAListOfWorkingDay( listWorkingDay ) );
    }

    /**
     * Get the min starting time of a working day
     */
    public void testGetMinStartingTimeOfAWorkingDay( )
    {
        // Build the form
        this.appointmentForm.setTimeStart( TIME_4 );
        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( this.nIdForm );
        List<WorkingDay> listWorkingDay = WorkingDayService.findListWorkingDayByWeekDefinition( listWeekDefinition.get( 0 ).getIdWeekDefinition( ) );

        WorkingDay workingDayMonday = listWorkingDay.stream( ).filter( w -> w.getDayOfWeek( ) == DayOfWeek.MONDAY.getValue( ) ).findFirst( ).get( );

        assertEquals( Constants.STARTING_TIME_3, WorkingDayService.getMinStartingTimeOfAWorkingDay( workingDayMonday ) );
    }

    /**
     * Get the open days of an appointmentForm DTO
     */
    public void testGetOpenDays( )
    {
        // Build the form
        this.appointmentForm.setIsOpenMonday( Boolean.TRUE );
        this.appointmentForm.setIsOpenTuesday( Boolean.TRUE );
        this.appointmentForm.setIsOpenWednesday( Boolean.TRUE );
        this.appointmentForm.setIsOpenThursday( Boolean.TRUE );
        this.appointmentForm.setIsOpenFriday( Boolean.TRUE );
        this.appointmentForm.setIsOpenSaturday( Boolean.FALSE );
        this.appointmentForm.setIsOpenSunday( Boolean.FALSE );
        assertEquals( 5, WorkingDayService.getOpenDays( this.appointmentForm ).size( ) );
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.appointmentForm = FormServiceTest.buildAppointmentForm( );
        this.nIdForm = FormService.createAppointmentForm( appointmentForm );
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //delete all the forms left over from tests
        for (Form f : FormService.findAllForms()) {
            FormService.removeForm(f.getIdForm());
        }
        this.appointmentForm = null;
        this.nIdForm = 0;
    }
}
