package com.loyalty.source.lbsourcesvc.utility;

import org.slf4j.MDC;

public final class MDCHandler {
	static public enum constantes{
		trackingToken,
		ip,
		LmNumber,
		canal,
		service,
		hostname,
		senderRequest
	}
	static public final void setContextVariables(String lmNumber, String trackingToken,String service, String hostname,String ip,String canal) {
		MDC.put(constantes.LmNumber.name(), lmNumber);
		MDC.put(constantes.trackingToken.name(), trackingToken);
		MDC.put(constantes.service.name(), service);
		MDC.put(constantes.hostname.name(), hostname);
		MDC.put(constantes.ip.name(),ip);
		MDC.put(constantes.canal.name(), canal);
	}

	static public final void removeContextVariables() {
		MDC.remove(constantes.LmNumber.name());
		MDC.remove(constantes.trackingToken.name());
		MDC.remove(constantes.service.name());
		MDC.remove(constantes.hostname.name());
		MDC.remove(constantes.ip.name());
		MDC.remove(constantes.canal.name());
	}
	static public final boolean isSetSenderRequest()
	{
		if(MDC.get(constantes.senderRequest.name()) != null) {
			if(!"".equals(MDC.get(constantes.senderRequest.name()))){
				return true;
			}

		}
		return false;
	}
	static public final void setSenderRequest(String senderRequest)
	{
		MDC.put(constantes.senderRequest.name(), senderRequest);
	}
	static public final String getSenderRequest() {
		return MDC.get(constantes.senderRequest.name());
	}
	static public final void clearSenderRequest()
	{
		if(MDC.get(constantes.senderRequest.name()) != null)
		{
			MDC.remove(constantes.senderRequest.name());
		}
	}
}
