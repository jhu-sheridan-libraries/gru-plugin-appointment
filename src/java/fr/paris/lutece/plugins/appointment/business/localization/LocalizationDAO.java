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
package fr.paris.lutece.plugins.appointment.business.localization;

import java.sql.Statement;

import fr.paris.lutece.plugins.appointment.business.UtilDAO;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

public final class LocalizationDAO extends UtilDAO implements ILocalizationDAO
{

    private static final String SQL_QUERY_INSERT = "INSERT INTO appointment_localization (longitude, latitude, address, id_form) VALUES ( ?, ?, ?, ?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE appointment_localization SET longitude = ?, latitude = ?, address = ?, id_form = ? WHERE id_localization = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appointment_localization WHERE id_localization = ?";
    private static final String SQL_QUERY_SELECT_COLUMNS = "SELECT id_localization, longitude, latitude, address, id_form FROM appointment_localization";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECT_COLUMNS + " WHERE id_localization = ?";
    private static final String SQL_QUERY_SELECT_BY_ID_FORM = SQL_QUERY_SELECT_COLUMNS + " WHERE id_form = ?";

    @Override
    public  void insert( Localization localization, Plugin plugin )
    {
        DAOUtil daoUtil = buildDaoUtil( SQL_QUERY_INSERT, localization, plugin, true );
     
        try
        {
            daoUtil.executeUpdate( );       
	        if ( daoUtil.nextGeneratedKey( ) )
	        {
	        	localization.setIdLocalization( daoUtil.getGeneratedKeyInt( 1 ) );
	        }
        }finally
        {
                daoUtil.free();       
        }
    }

    @Override
    public void update( Localization localization, Plugin plugin )
    {
        DAOUtil daoUtil = buildDaoUtil( SQL_QUERY_UPDATE, localization, plugin, false );
        executeUpdate( daoUtil );
    }

    @Override
    public void delete( int nIdLocalization, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nIdLocalization );
        executeUpdate( daoUtil );
    }

    @Override
    public Localization select( int nIdLocalization, Plugin plugin )
    {
        DAOUtil daoUtil = null;
        Localization localization = null;
        try
        {
            daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
            daoUtil.setInt( 1, nIdLocalization );
            daoUtil.executeQuery( );
            if ( daoUtil.next( ) )
            {
                localization = buildLocalization( daoUtil );
            }
        }
        finally
        {
            if ( daoUtil != null )
            {
                daoUtil.free( );
            }
        }
        return localization;
    }

    @Override
    public Localization findByIdForm( int nIdForm, Plugin plugin )
    {
        DAOUtil daoUtil = null;
        Localization localization = null;
        try
        {
            daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_FORM, plugin );
            daoUtil.setInt( 1, nIdForm );
            daoUtil.executeQuery( );
            if ( daoUtil.next( ) )
            {
                localization = buildLocalization( daoUtil );
            }
        }
        finally
        {
            if ( daoUtil != null )
            {
                daoUtil.free( );
            }
        }
        return localization;
    }

    /**
     * Build a Localization business object from the resultset
     * 
     * @param daoUtil
     *            the prepare statement util object
     * @return a new Localization business object with all its attributes assigned
     */
    private Localization buildLocalization( DAOUtil daoUtil )
    {
        int nIndex = 1;
        Localization localization = new Localization( );
        localization.setIdLocalization( daoUtil.getInt( nIndex++ ) );
        Float fLongitude = ( (Float) daoUtil.getObject( nIndex++ ) );
        if ( fLongitude != null )
        {
            localization.setLongitude( fLongitude.doubleValue( ) );
        }
        Float fLatitude = ( (Float) daoUtil.getObject( nIndex++ ) );
        if ( fLatitude != null )
        {
            localization.setLatitude( fLatitude.doubleValue( ) );
        }
        localization.setAddress( daoUtil.getString( nIndex++ ) );
        localization.setIdForm( daoUtil.getInt( nIndex ) );
        return localization;
    }

    /**
     * Build a daoUtil object with the localization business object for insert query
     * 
     * @param query
     *            the query
     * @param localization
     *            the localization
     * @param plugin
     *            the plugin
     * @param isInsert
     *            true if it is an insert query (in this case, need to set the id). If false, it is an update, in this case, there is a where parameter id to
     *            set
     * @return a new daoUtil with all its values assigned
     */
    private DAOUtil buildDaoUtil( String query, Localization localization, Plugin plugin, boolean isInsert )
    {
        int nIndex = 1;
        DAOUtil daoUtil = null;
        if ( isInsert )
        {
        	daoUtil = new DAOUtil( query, Statement.RETURN_GENERATED_KEYS, plugin );
        }else{
        	daoUtil = new DAOUtil( query, plugin );
        }
        if ( localization.getLongitude( ) != null )
        {
            daoUtil.setDouble( nIndex++, localization.getLongitude( ) );
        }
        else
        {
            daoUtil.setDoubleNull( nIndex++ );
        }
        if ( localization.getLatitude( ) != null )
        {
            daoUtil.setDouble( nIndex++, localization.getLatitude( ) );
        }
        else
        {
            daoUtil.setDoubleNull( nIndex++ );
        }
        daoUtil.setString( nIndex++, localization.getAddress( ) );
        daoUtil.setInt( nIndex++, localization.getIdForm( ) );
        if ( !isInsert )
        {
            daoUtil.setInt( nIndex, localization.getIdLocalization( ) );
        }
        return daoUtil;
    }

    /**
     * Execute a safe update (Free the connection in case of error when execute the query)
     * 
     * @param daoUtil
     *            the daoUtil
     */
    private void executeUpdate( DAOUtil daoUtil )
    {
        try
        {
            daoUtil.executeUpdate( );
        }
        finally
        {
            if ( daoUtil != null )
            {
                daoUtil.free( );
            }
        }
    }

}
