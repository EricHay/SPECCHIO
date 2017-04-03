package ch.specchio.query_builder;

import java.util.ArrayList;
import java.util.ListIterator;

import ch.specchio.client.SPECCHIOClient;
import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.metadata.MD_CategoryComparator;
import ch.specchio.metadata.MD_FormDescriptor;
import ch.specchio.queries.QueryConditionChangeInterface;
import ch.specchio.queries.QueryCondition;

public class QueryController {
	
	QueryFormFactory form_factory;
	
	QueryForm form;
	SPECCHIOClient specchio_client;

	MD_FormDescriptor form_descriptor;
	
	private ArrayList<QueryConditionChangeInterface> change_listeners = new ArrayList<QueryConditionChangeInterface>();
	

	public QueryController(SPECCHIOClient specchio_client, String form_name, MD_FormDescriptor form_descriptor) throws SPECCHIOClientException {
		
		this.specchio_client = specchio_client;
		this.form_descriptor = form_descriptor;
		form_factory = new QueryFormFactory(specchio_client, new MD_CategoryComparator());
		form = form_factory.getForm(this.specchio_client, form_descriptor);
		form.textReport();
	}

	public void ConditionChange(QueryField field, Object new_value)
	{
		
		String string_value = (new_value != null)? new_value.toString() : "";
		
		System.out.println("Change:" + field.getLabel() + ", value = " + string_value);
		
		field.set_value(string_value);
		
		// inform all registered listeners
		ListIterator<QueryConditionChangeInterface> li = change_listeners.listIterator();
		
		while(li.hasNext())
		{
			QueryConditionChangeInterface listener = li.next();
			
			listener.changed(this);
		}
		
	}
	
	public void addChangeListener(QueryConditionChangeInterface listener)
	{
		this.change_listeners.add(listener);
	}
	
	public QueryForm getForm() {
		
		return form;
		
	}
	
	public ArrayList<QueryCondition> getListOfConditions()
	{		
		return form.getListOfConditions();
	}
	
	public SPECCHIOClient getSpecchio_client() {
		return specchio_client;
	}

	public void setSpecchio_client(SPECCHIOClient specchio_client) {
		this.specchio_client = specchio_client;
	}
	
	
}
