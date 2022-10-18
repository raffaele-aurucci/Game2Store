package Model;

import java.sql.Date;
import java.util.List;

public class Ordine {

    private Integer id;
    private Double totaleOrdine;
    private Date dataAcquisto;
    private List<Gioco> listaGiochiOrdinati;

    public List<Gioco> getListaGiochiOrdinati() {
        return listaGiochiOrdinati;
    }

    public void setListaGiochiOrdinati(List<Gioco> listaGiochiOrdinati) {
        this.listaGiochiOrdinati = listaGiochiOrdinati;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotaleOrdine() {
        return totaleOrdine;
    }

    public void setTotaleOrdine(Double totaleOrdine) {
        this.totaleOrdine = totaleOrdine;
    }

    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(Date dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }
}
