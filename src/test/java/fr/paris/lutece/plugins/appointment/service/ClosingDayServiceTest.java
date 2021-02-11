/*
 * Copyright (c) 2002-2021, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

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