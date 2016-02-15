	/*
	 * Copyright (c) 2002-2014, Mairie de Paris
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

package fr.paris.lutece.plugins.appointment.Daemon;

	 

	import java.sql.Timestamp;
import java.util.List;

import fr.paris.lutece.plugins.appointment.business.calendar.AppointmentSlotDisponiblity;
import fr.paris.lutece.plugins.appointment.web.AppointmentApp;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.util.AppLogService;

	
		public class AppointmentSlotDisponibilityDaemon extends Daemon 
		{
			public void run( ) 
			{
			        List<AppointmentSlotDisponiblity>  listAppointmentSlotDisponiblity =AppointmentApp.getListAppointmentSlotDisponiblity();
			        
			        for (int i=0; i< listAppointmentSlotDisponiblity.size();i++) {
			        	Timestamp liberateTime = listAppointmentSlotDisponiblity.get(i).getFreeDate();
			        	
			        	if ( liberateTime.before(new Timestamp( new java.util.Date( ).getTime( ))))
			        	{	
			        		AppLogService.info( "release of slot " +listAppointmentSlotDisponiblity.get(i).getIdSlot() );
			        		listAppointmentSlotDisponiblity.remove(listAppointmentSlotDisponiblity.get(i));
			        		AppointmentApp.setListAppointmentSlotDisponiblity(listAppointmentSlotDisponiblity);
			        	}
			        }
 			    }
			}

 