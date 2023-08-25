package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	private Double pricePerHour;
	private Double pricePerDay;
	private BrazilTaxService taxBr;
	
	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxBr) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxBr = taxBr;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public BrazilTaxService getTaxBr() {
		return taxBr;
	}

	public void setTaxBr(BrazilTaxService taxBr) {
		this.taxBr = taxBr;
	}
	
	public void processInvoice(CarRental carRental) {
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes/60;
		
		double basicPayment;
		if(hours <= 12.0) {
			basicPayment = pricePerHour*Math.ceil(hours);
		}
		else {
			basicPayment = pricePerDay*Math.ceil(hours/24.0);
		}
		
		double tax = taxBr.tax(basicPayment);
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}
