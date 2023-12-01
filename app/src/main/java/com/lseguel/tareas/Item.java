package com.lseguel.tareas;

public class Item {
    private int id;
    private String texto;
    private String prioridad;
    private boolean estado;

    public Item(int id, String texto, String prioridad, boolean estado) {
        this.id = id;
        this.texto = texto;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public void actualizar(String texto) {
        this.texto = texto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        String estadoTexto = (estado) ? "Finalizada" : "";
        return id + ". " + texto + " (" + prioridad + ") | " + estadoTexto;
    }
}
