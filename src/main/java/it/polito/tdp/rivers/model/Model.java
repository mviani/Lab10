package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
     private Map<Integer, River> idMap;
     private RiversDAO dao;
     private Simulatore s;

	public Model() {
		super();
		idMap = new HashMap<Integer,River>();
		dao = new RiversDAO();
		s = new Simulatore();
	}
	
	public List<River> getAllRivers(){
		return dao.getAllRivers(idMap);
	}
	
	public Statistiche getStatistiche(int id){
		return dao.getStatistiche(id);
	}
	
	public Map<Integer, River> getIdMap() {
		return idMap;
	}

	public Risultati simula(double k,int id,Map<Integer,River> idMap) {
		s.loadQueue(k, id, idMap);
		s.run();
		return s.calcolaStatistiche();
	}
	
	
     
}
