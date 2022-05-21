package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Statistiche;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers(Map<Integer,River> idMap) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(idMap.containsKey(res.getInt("id"))) {
					idMap.put(res.getInt("id"), new River(res.getInt("id"), res.getString("name")));
				}
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public Statistiche getStatistiche(int id) {
		final String sql = "SELECT MIN(day) as primo, MAX(day) as ultimo, AVG(flow) as media, COUNT(*) as misurazioni "
				+ "FROM flow "
				+ "WHERE river=?;";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();
			Statistiche s;

			if (res.next()) {
				s = new Statistiche(res.getDate("primo").toLocalDate(), res.getDate("ultimo").toLocalDate(), res.getFloat("media"), res.getInt("misurazioni"));
			} else {
				conn.close();
				return null;
			}

			conn.close();
			return s;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
	}
	
	
public List<Flow> getFlow(int id, Map<Integer,River> idMap) {
		
		final String sql = "SELECT * FROM flow WHERE river=?";

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				flows.add(new Flow(res.getDate("day").toLocalDate(), res.getFloat("flow"), idMap.get(res.getInt("id"))));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flows;
	}
	
	
}
