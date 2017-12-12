package br.com.rr.ocorrencias.model;

public class Ocorrencia {
	
	private Integer quantity;
	private Long duration;
	
	public Ocorrencia(Integer quantity, Long duration) {
		this.quantity = quantity;
		this.duration = duration;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	
}
