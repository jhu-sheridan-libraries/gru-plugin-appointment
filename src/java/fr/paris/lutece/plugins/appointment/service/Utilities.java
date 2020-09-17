/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

/**
 * Class of utilities
 * 
 */
public final class Utilities
{
    private static DateTimeFormatter _formatter;
    private static DateTimeFormatter _customFormatter;

    /**
     * Private constructor - this class does not need to be instantiated
     */
    private Utilities( )
    {
    }

    /**
     * Getter for the formatter
     * 
     * @return the formatter
     */
    public static DateTimeFormatter getFormatter( )
    {
        if( _formatter == null )
        {
            _formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale( AppointmentPlugin.getPluginLocale() );
        }
        return _formatter;
    }

    public static DateTimeFormatter getCustomFormatter(String formatPattern, Locale locale) {
        if( _customFormatter == null ) {
            if ( formatPattern != null ) {
                formatPattern = normalizeFormatString( formatPattern );
                if ( locale != null ) {
                    _customFormatter = DateTimeFormatter.ofPattern(formatPattern, locale);
                } else {
                    _customFormatter = DateTimeFormatter.ofPattern(formatPattern);
                }
            } else {
                _customFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale( AppointmentPlugin.getPluginLocale( ) );
            }
        }
        return _customFormatter;
    }

    /**
     * Setter for the formatter
     * 
     * @param formatter
     *            the formatter to set
     * @deprecated Useless setter
     */
    @Deprecated
    public static void setFormatter( DateTimeFormatter formatter )
    {
        _formatter = formatter;
    }
    
    
    /**
     * Reset formatter scope package to be only used by unit tests
     */
    static void resetFormatter()
    {
        _formatter = null;
    }
   

    /**
     * Return the closest date in past a list of date with the given date
     * 
     * @param listDate
     *            the list of date
     * @param dateToSearch
     *            the date to search
     * @return the closest date in past
     */
    public static LocalDate getClosestDateInPast( List<LocalDate> listDate, LocalDate dateToSearch )
    {
        return listDate.stream( ).filter( x -> x.isBefore( dateToSearch ) || x.isEqual( dateToSearch ) ).max( LocalDate::compareTo ).orElse( null );
    }

    /**
     * Return the closest date time in future in a list of date time and a given date time
     * 
     * @param listDateTime
     *            the list of date time
     * @param dateTimeToSearch
     *            the date time to search
     * @return the closest date time in the list in the future of the given date time
     */
    public static LocalDateTime getClosestDateTimeInFuture( List<LocalDateTime> listDateTime, LocalDateTime dateTimeToSearch )
    {
        return listDateTime.stream( ).filter( x -> x.isAfter( dateTimeToSearch ) || x.isEqual( dateTimeToSearch ) ).min( LocalDateTime::compareTo )
                .orElse( null );
    }

    /**
     * A convenience method to correct format strings from datetimepicker
     * @param originalPattern
     * @return
     */
    private static String normalizeFormatString ( String originalPattern ) {
        String normalized;
        // "h:mm A" needs to be "h:mm a"
        if (originalPattern.contains("h:") && originalPattern.endsWith(" A")) {
            normalized = originalPattern.replace(" A", " a");
        } else {
            normalized = originalPattern;
        }

        return normalized;
    }

}
