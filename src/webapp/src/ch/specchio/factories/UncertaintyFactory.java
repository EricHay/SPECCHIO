package ch.specchio.factories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ch.specchio.eav_db.SQL_StatementBuilder;
import ch.specchio.types.Campaign;
import ch.specchio.types.InstrumentNode;
import ch.specchio.types.ResearchGroup;

public class UncertaintyFactory extends SPECCHIOFactory {
	
	/**
	 * Constructor.
	 * 
	 * @param db_user		database account user name
	 * @param db_password	database account password
	 * @param ds_name		data source name
	 * @param is_admin		is the user an administrator? 
	 * 
	 * @throws SPECCHIOFactoryException	database error
	 */
	public UncertaintyFactory(String db_user, String db_password, String ds_name, boolean is_admin) throws SPECCHIOFactoryException {
		
		super(db_user, db_password, ds_name, is_admin);
		
	}
	
	/**
	 * Copy constructor. Construct a new factory that uses the same database connection
	 * as an existing factory.
	 * 
	 * @param factory	the existing factory
	 * 
	 * @throws SPECCHIOFactoryException	database error
	 */
	public UncertaintyFactory(SPECCHIOFactory factory) throws SPECCHIOFactoryException {
		
		super(factory);
		
	}
	
	//insertInstrumentNode
	// void because it assigns an id attribute to the object which can then be found using 'getId' in uncertainty service
	
	/**
	 * Insert a new instrument node into the database.
	 * 
	 * @param instrument_node	the instrument_node to insert
	 * 
	 * @throws SPECCHIOFactoryException	the instrument_node could not be inserted
	 */
	public void insertInstrumentNode(InstrumentNode instrument_node) throws SPECCHIOFactoryException {
		
		try {
			
			// create SQL-building objects
			SQL_StatementBuilder SQL = getStatementBuilder();
			
		
			// insert the instrument node into the database
			String query;
			
			query = "INSERT INTO instrument_node(node_type, confidence_level, abs_rel) VALUES (" +
						SQL.quote_string(instrument_node.getNodeType()) + "," +
						instrument_node.getConfidenceLevel() + "," +
						SQL.quote_string(instrument_node.getAbsRel()) +
						")";
			
			
			Statement stmt = SQL.createStatement();
			stmt.executeUpdate(query);
		
			// get the instrument node id
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			while (rs.next())
				instrument_node.setId(rs.getInt(1));
			stmt.close();
			
			
		}
		catch (SQLException ex) {
			// bad SQL
			throw new SPECCHIOFactoryException(ex);
		}
	
	}
	
	
	
}