package com.knure.project.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Set;

public class MovieHandler implements SOAPHandler<SOAPMessageContext> {

    private final Logger log = LoggerFactory.getLogger(MovieHandler.class);

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        SOAPMessage msg = context.getMessage();
        boolean isOutbound = (boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
        boolean result = true;
        try {
            if (isOutbound) {
                result = handleOutboundMessge(msg);
            } else {
                result = handleInboundMessge(msg);
            }
        } catch (SOAPException e) {
            log.error("", e);
            result = generateSOAPErrMessage(msg, "Your reason");
        }
        return result;
    }

    private boolean handleInboundMessge(SOAPMessage msg) throws SOAPException {
        log.info("MovieHandler: Inbound message");
        return true;
    }

    private boolean handleOutboundMessge(SOAPMessage msg) throws SOAPException {
        log.info("MovieHandler: Outbound message");
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        log.info("MovieHandler: handleFault()");
        return true;
    }

    @Override
    public void close(MessageContext context) {
        log.info("MovieHandler: close()");
    }

    @Override
    public Set<QName> getHeaders() {
        log.info("getHeaders()");
        return Set.of();
    }


    private boolean generateSOAPErrMessage(SOAPMessage msg, String reason) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultString(reason);
            soapFault.setFaultCode(reason);
            throw new SOAPFaultException(soapFault);
        } catch (SOAPException e) {
            // nothing
        }
        return false;
    }
}
