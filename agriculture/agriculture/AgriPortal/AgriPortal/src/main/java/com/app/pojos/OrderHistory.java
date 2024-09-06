package com.app.pojos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="order_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderHistory extends BaseEntity {

@Column(name="order_date", nullable = false)
private LocalDate OrderDate;

@Column(name="deliverd_on", nullable = false)
private LocalDate DeliverdOn;

@Column(name="quantity", nullable = false)
private double Quantity;

@ManyToOne
@JoinColumn(name="uid")
private Users user;

@ManyToMany
@JoinColumn(name="pid")
private List<Product> products=new ArrayList<>();

}
