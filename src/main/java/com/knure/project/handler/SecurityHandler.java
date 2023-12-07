package com.knure.project.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.StringWriter;
import java.util.Set;

public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {
    private static final String CLIENT_TOKEN_TAG_NAME = "clientToken";
    private final Logger log = LoggerFactory.getLogger(SecurityHandler.class);
    private static final String SERVICE_TOKEN = "I_am_server";
    private static final String SERVICE_TOKEN_NAME = "serviceToken";
    JAXBContext jaxb;

    public SecurityHandler() throws JAXBException {
        jaxb = JAXBContext.newInstance("com.knure.project.server.service");
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        boolean outbound = (boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        boolean result = true;
        if (outbound) {
            SOAPMessage soapMessage = context.getMessage();
            SOAPHeader soapHeader;
            try {
                soapHeader = soapMessage.getSOAPHeader();
            } catch (SOAPException e) {
                throw new RuntimeException(e);
            }
            log.info("Outbound message");
            System.out.println(soapHeader.getValue());
            result = createSecurityHeader(context.getMessage());
        } else {
            log.info("Inbound message");
            result = checkSecurityHeader(context.getMessage());
        }
        return result;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        log.error("Service handleFault()");
        return true;
    }

    @Override
    public void close(MessageContext context) {
        log.debug("Service close()");
    }

    @Override
    public Set<QName> getHeaders() {
        return Set.of();
    }

    private boolean createSecurityHeader(SOAPMessage message) {
        try {
            SOAPHeader header = message.getSOAPHeader();
            header.extractAllHeaderElements();
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty("jaxb.fragment", true);
            marshaller.marshal(new JAXBElement<String>(
                    new QName("http://service.server.project.knure.com/", SERVICE_TOKEN_NAME), String.class, SERVICE_TOKEN), header);
            message.saveChanges();
            log.info("Service token: {}", toString(header));
        } catch (SOAPException | JAXBException e) {
            String msg = e.getCause() != null ? e.getMessage() + "\nCause: " + e.getCause().getMessage()
                    : e.getMessage();
            log.error(msg);
            return false;
        }
        return true;
    }

    private boolean checkSecurityHeader(SOAPMessage message) {
        try {
            QName clientTokenQName = new QName("http://service.server.project.knure.com/", CLIENT_TOKEN_TAG_NAME);
            SOAPHeader header = message.getSOAPPart().getEnvelope().getHeader();
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            Node childElements = header.getChildElements(clientTokenQName).next();
            JAXBElement<String> sh = unmarshaller.unmarshal(childElements, String.class);
            return isValidToken(sh.getValue());
        } catch (Exception e) {
            String faultString = "Client token not found";
            log.error(faultString);
            throw new SOAPFaultException(createFault(message, faultString));
        }
    }

    private boolean isValidToken(String token) {
        if (token == null || token.isEmpty()) {
            log.error("Client token is incorrect: {}", token);
            return false;
        }
        log.info("Client token: {}", token);
        return true;
    }

    // How to replace/create your own Fault which depends from a protocol
    private SOAPFault createFault(SOAPMessage message, String faultString) {
        SOAPFault fault = null;
        try {
            SOAPEnvelope env = message.getSOAPPart().getEnvelope();
            QName faultCode = null;
            String soapProtocol = SOAPConstants.SOAP_1_1_PROTOCOL;
            String code = "Client";
            String prefix = env.lookupPrefix(SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE);
            if (prefix == null) {
                soapProtocol = SOAPConstants.SOAP_1_2_PROTOCOL;
                code = "Sender";
                prefix = env.lookupPrefix(SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE);
            }
            faultCode = env.createQName(code, prefix);
            fault = SOAPFactory.newInstance(soapProtocol).createFault(faultString, faultCode);
        } catch (SOAPException e) {
            // e.printStackTrace();
        }
        return fault;
    }

    private String toString(Node header) {
        DOMSource source = new DOMSource(header);
        StringWriter stringResult = new StringWriter();
        try {
            TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return stringResult.toString().replaceFirst("<\\?xml.+\\?>", "");
    }
}