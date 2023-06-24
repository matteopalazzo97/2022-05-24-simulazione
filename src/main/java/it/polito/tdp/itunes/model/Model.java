package it.polito.tdp.itunes.model;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private Graph<Track, DefaultWeightedEdge> grafo;
	private ItunesDAO dao;
	private Map<Integer, Track> mappaTrack;
	
	public Model() {
		super();
		this.dao = new ItunesDAO();
		this.mappaTrack = this.dao.getMappaTracks();
	}
	
	public List<Genre> getTendina(){
		return this.dao.getAllGenres();
	}
	
	public void creaGrafo(int id) {
		
		grafo = new SimpleWeightedGraph<Track, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.dao.getVertici(id));
		
		List<Arco> archi = this.dao.getArchi(id, mappaTrack);
		
		for(Arco a: archi) {
			this.grafo.addEdge(a.getT1(), a.getT2());
			this.grafo.setEdgeWeight(a.getT1(), a.getT2(), a.getPeso());
		}
		
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Arco> getDelta(int id) {

		int max = 0;
		List<Arco> result = new LinkedList<Arco>();
		
		for(Arco a: this.dao.getArchi(id, mappaTrack)){
			if(a.getPeso()>max) {
				max = a.getPeso();
			}
		}
		
		for(Arco a: this.dao.getArchi(id, mappaTrack)) {
			if(a.getPeso() == max) {
				result.add(a);
			}
		}
		
		return result;
	}
	
	
}
