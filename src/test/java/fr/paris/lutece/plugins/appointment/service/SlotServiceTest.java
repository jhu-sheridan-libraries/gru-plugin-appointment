package fr.paris.lutece.plugins.appointment.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import fr.paris.lutece.plugins.appointment.business.SlotTest;
import fr.paris.lutece.plugins.appointment.business.form.Form;
import fr.paris.lutece.plugins.appointment.business.planning.WeekDefinition;
import fr.paris.lutece.plugins.appointment.business.slot.Slot;
import fr.paris.lutece.plugins.appointment.service.FormService;
import fr.paris.lutece.plugins.appointment.service.SlotService;
import fr.paris.lutece.plugins.appointment.service.WeekDefinitionService;
import fr.paris.lutece.plugins.appointment.web.dto.AppointmentFormDTO;
import fr.paris.lutece.test.LuteceTestCase;

public class SlotServiceTest extends LuteceTestCase
{
    private AppointmentFormDTO appointmentForm;
    private int nIdForm;

    // Check that there are 180 open slots from the 3/12/2022 to the 14/12/2022
    // With open days from Monday to Friday
    public void testOpenSlots( )
    {
        // Get all the week definitions
        HashMap<LocalDate, WeekDefinition> mapWeekDefinition = WeekDefinitionService.findAllWeekDefinition( this.nIdForm );
        List<Slot> listSlots = SlotService.buildListSlot( nIdForm, mapWeekDefinition, Constants.DATE_14, Constants.DATE_15 );

        assertEquals( 180, listSlots.stream( ).filter( s -> s.getIsOpen( ) ).collect( Collectors.toList( ) ).size( ) );

    }

    public void testOpenSlotsWithSpecificSlotsClosed( )
    {
        // Get all the week definitions
        HashMap<LocalDate, WeekDefinition> mapWeekDefinition = WeekDefinitionService.findAllWeekDefinition( this.nIdForm );

        Slot slotSpecificClosed1 = SlotTest.buildSlot( this.nIdForm, Constants.STARTING_DATE_12, Constants.ENDING_DATE_12, Constants.NB_REMAINING_PLACES_1, Constants.NB_REMAINING_PLACES_1,
                0, Constants.NB_REMAINING_PLACES_1, Boolean.FALSE, Boolean.TRUE );
        slotSpecificClosed1 = SlotService.saveSlot( slotSpecificClosed1 );

        Slot slotSpecificClosed2 = SlotTest.buildSlot( this.nIdForm, Constants.STARTING_DATE_13, Constants.ENDING_DATE_13, Constants.NB_REMAINING_PLACES_1, Constants.NB_REMAINING_PLACES_1,
                0, Constants.NB_REMAINING_PLACES_1, Boolean.FALSE, Boolean.TRUE );
        slotSpecificClosed2 = SlotService.saveSlot( slotSpecificClosed2 );

        Slot slotSpecificClosed3 = SlotTest.buildSlot( this.nIdForm, Constants.STARTING_DATE_14, Constants.ENDING_DATE_14, Constants.NB_REMAINING_PLACES_1, Constants.NB_REMAINING_PLACES_1,
                0, Constants.NB_REMAINING_PLACES_1, Boolean.FALSE, Boolean.TRUE );
        slotSpecificClosed3 = SlotService.saveSlot( slotSpecificClosed3 );

        List<Slot> listSlots = SlotService.buildListSlot( this.nIdForm, mapWeekDefinition,  Constants.DATE_14, Constants.DATE_15 );

        assertEquals( 177, listSlots.stream( ).filter( s -> s.getIsOpen( ) ).collect( Collectors.toList( ) ).size( ) );

    }

    public void testOpenSlotsWithSpecificLargeSlots( )
    {
        // Get all the week definitions
        HashMap<LocalDate, WeekDefinition> mapWeekDefinition = WeekDefinitionService.findAllWeekDefinition( this.nIdForm );

        Slot slotSpecific1 = SlotTest.buildSlot( this.nIdForm, Constants.STARTING_DATE_12, Constants.STARTING_DATE_1, Constants.NB_REMAINING_PLACES_1, Constants.NB_REMAINING_PLACES_1,
                0, Constants.NB_REMAINING_PLACES_1, Boolean.TRUE, Boolean.TRUE );
        slotSpecific1 = SlotService.saveSlot( slotSpecific1 );

        Slot slotSpecific2 = SlotTest.buildSlot( this.nIdForm, Constants.STARTING_DATE_15, Constants.ENDING_DATE_15, Constants.NB_REMAINING_PLACES_1, Constants.NB_REMAINING_PLACES_1,
                0, Constants.NB_REMAINING_PLACES_1,Boolean.TRUE, Boolean.TRUE );
        slotSpecific2 = SlotService.saveSlot( slotSpecific2 );

        List<Slot> listSlots = SlotService.buildListSlot( this.nIdForm, mapWeekDefinition, Constants.DATE_14, Constants.DATE_15 );

        assertEquals( 177, listSlots.stream( ).filter( s -> s.getIsOpen( ) ).collect( Collectors.toList( ) ).size( ) );

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.appointmentForm = FormServiceTest.buildAppointmentForm( );
        this.appointmentForm.setDateStartValidity( Date.valueOf( Constants.DATE_16 ) );
        this.appointmentForm.setDateStartValidity( Date.valueOf( Constants.DATE_17) );
        this.appointmentForm.setIsOpenMonday( Boolean.TRUE );
        this.appointmentForm.setIsOpenTuesday( Boolean.TRUE );
        this.appointmentForm.setIsOpenWednesday( Boolean.TRUE );
        this.appointmentForm.setIsOpenThursday( Boolean.TRUE );
        this.appointmentForm.setIsOpenFriday( Boolean.TRUE );
        this.appointmentForm.setIsOpenSaturday( Boolean.FALSE );
        this.appointmentForm.setIsOpenSunday( Boolean.FALSE );

        this.nIdForm = FormService.createAppointmentForm( this.appointmentForm );
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //delete all the forms left over from tests
        for (Form f : FormService.findAllForms()) {
            FormService.removeForm(f.getIdForm());
        }
        this.nIdForm = 0;
        this.appointmentForm = null;
    }


}
