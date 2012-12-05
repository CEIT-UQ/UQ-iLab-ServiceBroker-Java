/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.ilabs.library.batchservicebroker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import uq.ilabs.batchservicebroker.ObjectFactory;
import uq.ilabs.batchservicebroker.SbAuthHeader;
import uq.ilabs.library.lab.utilities.Logfile;

/**
 *
 * @author uqlpayne
 */
public class AuthenticateToBatchServiceBroker implements SOAPHandler<SOAPMessageContext> {

    //<editor-fold defaultstate="collapsed" desc="Constants">
    /*
     * String constants
     */
    private static final String STR_CouponId = "couponID";
    private static final String STR_CouponPasskey = "couponPassKey";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables">
    private static boolean initialised = false;
    private static SOAPFactory soapFactory;
    private static ObjectFactory objectFactory;
    private static QName qNameSbAuthHeader;
    //</editor-fold>

    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {
        boolean success = false;

        /*
         * Process the SOAP header for an outbound message
         */
        if ((Boolean) messageContext.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY) == true) {
            try {
                /*
                 * Check if local static variables have been initialised
                 */
                if (initialised == false) {
                    soapFactory = SOAPFactory.newInstance();
                    objectFactory = new ObjectFactory();
                    JAXBElement<SbAuthHeader> jaxbElementSbAuthHeader = objectFactory.createSbAuthHeader(new SbAuthHeader());
                    qNameSbAuthHeader = jaxbElementSbAuthHeader.getName();

                    initialised = true;
                }

                /*
                 * Process the SOAP header to add the authentication information
                 */
                this.ProcessSoapHeader(messageContext);

                /*
                 * Write the finished SOAP message to system output
                 */
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                messageContext.getMessage().writeTo(outputStream);
//                System.out.println(outputStream.toString());

                success = true;
            } catch (SOAPException | IOException ex) {
                Logfile.WriteError(ex.toString());
            }
        }

        return success;
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    /**
     *
     * @param messageContext
     * @throws SOAPException
     */
    private void ProcessSoapHeader(SOAPMessageContext messageContext) throws SOAPException {
        /*
         * Get the SOAP envelope and add a SOAP header
         */
        SOAPMessage soapMessage = messageContext.getMessage();
        SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
        SOAPHeader soapHeader = soapEnvelope.addHeader();

        /*
         * Get the authentication header information
         */
        Object object = messageContext.get(qNameSbAuthHeader.getLocalPart());
        if (object != null && object instanceof SbAuthHeader) {
            /*
             * SbAuthHeader
             */
            this.ProcessSbAuthHeader((SbAuthHeader) object, qNameSbAuthHeader, soapHeader);
        }
    }

    /**
     *
     * @param sbAuthHeader
     * @param qnameAuthHeader
     * @param soapHeader
     * @return SOAPHeaderElement
     */
    private SOAPHeaderElement ProcessSbAuthHeader(SbAuthHeader sbAuthHeader, QName qnameAuthHeader, SOAPHeader soapHeader) {

        SOAPHeaderElement headerElement;

        try {
            /*
             * Create the authentication header element
             */
            headerElement = soapHeader.addHeaderElement(qnameAuthHeader);

            /*
             * Create couponId element and add to the coupon element
             */
            QName qName = new QName(qnameAuthHeader.getNamespaceURI(), STR_CouponId, qnameAuthHeader.getPrefix());
            SOAPElement element = soapFactory.createElement(qName);
            element.addTextNode(Long.toString(sbAuthHeader.getCouponID()));
            headerElement.addChildElement(element);

            /*
             * Check if coupon passkey is specified
             */
            if (sbAuthHeader.getCouponPassKey() != null) {
                /*
                 * Create the coupon passkey element and add its value
                 */
                qName = new QName(qnameAuthHeader.getNamespaceURI(), STR_CouponPasskey, qnameAuthHeader.getPrefix());
                element = soapFactory.createElement(qName);
                element.addTextNode(sbAuthHeader.getCouponPassKey());
                headerElement.addChildElement(element);
            }
        } catch (SOAPException ex) {
            headerElement = null;
        }

        return headerElement;
    }
}
