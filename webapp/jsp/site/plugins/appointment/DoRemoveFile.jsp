<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="appointmentApp" scope="request" class="fr.paris.lutece.plugins.appointment.web.AppointmentApp" />

<%= appointmentApp.doRemoveAsynchronousUploadedFile( request ) %>