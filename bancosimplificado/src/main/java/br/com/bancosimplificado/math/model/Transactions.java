package br.com.bancosimplificado.math.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.bancosimplificado.math.useful.UserDeserializer;
import jakarta.persistence.*;

@Entity
@Table
public class Transactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String descrpition;

	@Column(nullable = false)
	private BigDecimal value;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(nullable = false)
	private LocalDateTime paymentDate;

	@JsonDeserialize(using = UserDeserializer.class)
	@ManyToOne
	@JoinColumn(name = "payer_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "payer_fk"))
	private Users payer;

	@JsonDeserialize(using = UserDeserializer.class)
	@ManyToOne
	@JoinColumn(name = "payee_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "payee_fk"))
	private Users payee;
	
	@PrePersist
	private void prePersist() {
		this.setPaymentDate(LocalDateTime.now());
	}
	
	//...Getters, Setters, Equals and Hash Code
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescrpition() {
		return descrpition;
	}

	public void setDescrpition(String descrpition) {
		this.descrpition = descrpition;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Users getPayer() {
		return payer;
	}

	public void setPayer(Users payer) {
		this.payer = payer;
	}

	public Users getPayee() {
		return payee;
	}

	public void setPayee(Users payee) {
		this.payee = payee;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transactions other = (Transactions) obj;
		return Objects.equals(id, other.id);
	}
	
}
