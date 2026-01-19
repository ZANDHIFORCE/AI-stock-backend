package Nemsi.AiStock.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

public class PreStock {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String ticker;
    private String name;
    @NumberFormat(pattern = "#,##0.00")
    private Double curPrice;
    @NumberFormat(pattern = "#,##0.00")
    private Double predictPrice;
    @NumberFormat(pattern = "#,##0.00")
    private Double predictGap;
    private String useYN;

    public PreStock() {}

    public PreStock(Long id, LocalDate date, String ticker, String name, Double curPrice, Double predictPrice, Double predictGap, String useYN) {
        this.id = id;
        this.date = date;
        this.ticker = ticker;
        this.name = name;
        this.curPrice = curPrice;
        this.predictPrice = predictPrice;
        this.predictGap = predictGap;
        this.useYN = useYN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    public Double getPredictPrice() {
        return predictPrice;
    }

    public void setPredictPrice(Double predictPrice) {
        this.predictPrice = predictPrice;
    }

    public Double getPredictGap() {
        return predictGap;
    }

    public void setPredictGap(Double predictGap) {
        this.predictGap = predictGap;
    }

    public String getUseYN() {
        return useYN;
    }

    public void setUseYN(String useYN) {
        this.useYN = useYN;
    }

    @Override
    public String toString() {
        return "PreStock{" +
                "id=" + id +
                ", date=" + date +
                ", ticker='" + ticker + "'" +
                ", name='" + name + "'" +
                ", curPrice=" + curPrice +
                ", predictPrice=" + predictPrice +
                ", predictGap=" + predictGap +
                ", useYN='" + useYN + "'" +
                '}';
    }
}