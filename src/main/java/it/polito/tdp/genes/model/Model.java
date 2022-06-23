package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private GenesDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	private List<Integer> best;
	private Double pesoMax;
	
	public Model() {
		super();
		this.dao = new GenesDao();
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiunta vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici());
		
		//Aggiunta archi
		for(Adiacenza a: this.dao.getAdiacenza()) {
			if(this.grafo.containsVertex(a.getChromosome1()) && this.grafo.containsVertex(a.getChromosome2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getChromosome1(), a.getChromosome2(), a.getPeso());
			}
		}

	    System.out.println("Grafo creato!");
		System.out.println("#VERTICI: "+ this.grafo.vertexSet().size());
		System.out.println("#ARCHI: "+ this.grafo.edgeSet().size());
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else
			return true;
	}
	
	public Double getPesoMin() {
		Double pesoMin = 10000.0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) < pesoMin) {
				pesoMin = this.grafo.getEdgeWeight(e);
			}
		}
		return pesoMin;
	}

	public Double getPesoMax() {
		Double pesoMax = 0.0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) > pesoMax) {
				pesoMax = this.grafo.getEdgeWeight(e);
			}
		}
		return pesoMax;
	}
	
	public int contaArchiMaggioriDi(Double soglia) {
		int count = 0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) > soglia) {
				count++;
			}
		}
		return count;
	}
	
	public int contaArchiMinoriDi(Double soglia) {
		int count = 0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) < soglia) {
				count++;
			}
		}
		return count;
	}
	
	//Trovare il piu lungo cammino di vertici composto esclusivamente 
	//da archi con peso > soglia
	public List<Integer> trovaPercorso(double soglia){
			
		this.best = new ArrayList<>();
			
		List<Integer> parziale = new ArrayList<>();
			
		this.pesoMax = 0.0;
			
		for(Integer v: this.grafo.vertexSet()) {
			parziale.clear();
			parziale.add(v);
			cerca(parziale, soglia, 0.0);
		}
		return best;
		
	}

	private void cerca(List<Integer> parziale, double soglia, double peso) {

		if(peso > pesoMax) {
				this.best = new ArrayList<>(parziale);
				pesoMax = peso;
		}
		
		//Cicla sul gruppo di archi con peso maggiore della soglia
		Integer ultimo = parziale.get(parziale.size()-1);
		List<Integer> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		for(Integer v: vicini) {
			DefaultWeightedEdge e = this.grafo.getEdge(ultimo, v);
			if(e!=null && this.grafo.getEdgeWeight(e) > soglia && !parziale.contains(v)) {
				parziale.add(v);
				peso += this.grafo.getEdgeWeight(e);
				cerca(parziale, soglia, peso);
				parziale.remove(parziale.size()-1);
				peso -= this.grafo.getEdgeWeight(e);
			}
		}
	}
}