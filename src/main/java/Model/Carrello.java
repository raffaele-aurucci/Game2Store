package Model;

import java.util.List;

public class Carrello {

    private int id;
    private double totale;
    private List<String> listaTitoliGiochiCarrello;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public List<String> getListaTitoliGiochiCarrello() {
        return listaTitoliGiochiCarrello;
    }

    public void setListaTitoliGiochiCarrello(List<String> listaTitoliGiochiCarrello) {
        this.listaTitoliGiochiCarrello = listaTitoliGiochiCarrello;
    }

    @Override
    public String toString() {
        return "Carrello{" +
                "id=" + id +
                ", totale=" + totale +
                ", listaGiochiCarrello=" + listaTitoliGiochiCarrello +
                '}';
    }
}
