package ch.specchio.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.annotation.security.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import ch.specchio.constants.UserRoles;
import ch.specchio.factories.SPECCHIOFactoryException;
import ch.specchio.factories.SpecchioCampaignFactory;
import ch.specchio.factories.UncertaintyFactory;
import ch.specchio.jaxb.XmlInteger;
import ch.specchio.types.Campaign;
import ch.specchio.types.InstrumentNode;


/**
 * Uncertainty service.
 */

@Path("/uncertainty")
@DeclareRoles({UserRoles.ADMIN, UserRoles.USER})

public class UncertaintyService extends SPECCHIOService {
	
	
	/**
	 * Insert instrument node.
	 * 
	 * @param an InstrumentNode object
	 * 
	 * @return the id of the new instrument node
	 * 
	 */
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("insertInstrumentNode")
	public XmlInteger insertInstrumentNode(InstrumentNode instrument_node) throws SPECCHIOFactoryException {
		
		System.out.println("Inserting new instrument node");
		
		try
		{
		
			UncertaintyFactory factory = new UncertaintyFactory(getClientUsername(), getClientPassword(), getDataSourceName(), isAdmin());
		factory.insertInstrumentNode(instrument_node);
		factory.dispose();
		
		}
		catch(SPECCHIOFactoryException e)
		{
			System.out.println(e.toString());
			throw(e);
		}
		
		// this might need more of a chain than just 'Factory'
		return new XmlInteger(instrument_node.getId()); 
		
	}
	
	
	
}