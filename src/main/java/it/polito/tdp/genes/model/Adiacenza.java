package it.polito.tdp.genes.model;

public class Adiacenza {
	private Integer chromosome1;
	private Integer chromosome2;
	private Double peso;
	
	public Adiacenza(Integer chromosome1, Integer chromosome2, Double peso) {
		super();
		this.chromosome1 = chromosome1;
		this.chromosome2 = chromosome2;
		this.peso = peso;
	}

	public Integer getChromosome1() {
		return chromosome1;
	}

	public void setChromosome1(Integer chromosome1) {
		this.chromosome1 = chromosome1;
	}

	public Integer getChromosome2() {
		return chromosome2;
	}

	public void setChromosome2(Integer chromosome2) {
		this.chromosome2 = chromosome2;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}
	

}
