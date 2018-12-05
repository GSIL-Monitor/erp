package com.stosz.tms.chain;

import com.stosz.tms.model.AssignTransportRequest;
import com.stosz.tms.model.AssignTransportResponse;

public interface ITransportHandlerChain {

	public AssignTransportResponse doAssignChain(AssignTransportRequest request);
	
	public boolean isChainComplete();
}
