package com.straccion.motos_admin.models;

import java.util.ArrayList;

public class PostAKT {
    private String id;
    private int precio;
    private String nombreMoto;
    private String marcaMoto;
    private int prioridad;
    private ArrayList<String> imagenes;
    private ArrayList<String> colores;
    private ArrayList<String> imagenesColores1;
    private ArrayList<String> imagenesColores2;
    private ArrayList<String> imagenesColores3;
    private ArrayList<String> imagenesColores4;
    private ArrayList<String> imagenesColores5;
    private ArrayList<String> imagenesColores6;
    private String motor;
    private String cilindraje;
    private String potencia;
    private String torqueMaximo;
    private String capacidadTanque;
    private String compresion;
    private String arranque;
    private String suspensionDelantera;
    private String suspensionTrasera;
    private String frenoDelantero;
    private String frenoTrasero;
    private String largoTotal;
    private String alturaSillin;
    private String anchoTotal;
    private String encendido;
    private String distanciaEntreEjes;
    private String distanciaMotorAlPiso;
    private String llantaDelantera;
    private String llantaTrasera;
    private String pesoSeco;
    private String cajaVelocidades;
    private String garantia;
    private String revisionesGratis;
    private String descripcion;
    private String modelo;
    private boolean descuento;
    private int nuevoValorDescuento;
    private String carpeta1;
    private String carpeta2;
    private String carpeta3;
    private ArrayList<String> manualesArchivos;
    private ArrayList<String> nombresArchivos;
    private ArrayList<String> caracteristicasTexto;
    private ArrayList<String> caracteristicasImagenes;
    private ArrayList<String> clasificacion;
    private double vistas;
    private double busquedas;
    private int consumoPorGalon;
    private boolean visible;

    public PostAKT() {

    }

    public PostAKT(String id, int precio, String nombreMoto, String marcaMoto, int prioridad, ArrayList<String> imagenes, ArrayList<String> colores, ArrayList<String> imagenesColores1, ArrayList<String> imagenesColores2, ArrayList<String> imagenesColores3, ArrayList<String> imagenesColores4, ArrayList<String> imagenesColores5, ArrayList<String> imagenesColores6, String motor, String cilindraje, String potencia, String torqueMaximo, String capacidadTanque, String compresion, String arranque, String suspensionDelantera, String suspensionTrasera, String frenoDelantero, String frenoTrasero, String largoTotal, String alturaSillin, String anchoTotal, String encendido, String distanciaEntreEjes, String distanciaMotorAlPiso, String llantaDelantera, String llantaTrasera, String pesoSeco, String cajaVelocidades, String garantia, String revisionesGratis, String descripcion, String modelo, boolean descuento, int nuevoValorDescuento, String carpeta1, String carpeta2, String carpeta3, ArrayList<String> manualesArchivos, ArrayList<String> nombresArchivos, ArrayList<String> caracteristicasTexto, ArrayList<String> caracteristicasImagenes, ArrayList<String> clasificacion, double vistas, double busquedas, int consumoPorGalon, boolean visible) {
        this.id = id;
        this.precio = precio;
        this.nombreMoto = nombreMoto;
        this.marcaMoto = marcaMoto;
        this.prioridad = prioridad;
        this.imagenes = imagenes;
        this.colores = colores;
        this.imagenesColores1 = imagenesColores1;
        this.imagenesColores2 = imagenesColores2;
        this.imagenesColores3 = imagenesColores3;
        this.imagenesColores4 = imagenesColores4;
        this.imagenesColores5 = imagenesColores5;
        this.imagenesColores6 = imagenesColores6;
        this.motor = motor;
        this.cilindraje = cilindraje;
        this.potencia = potencia;
        this.torqueMaximo = torqueMaximo;
        this.capacidadTanque = capacidadTanque;
        this.compresion = compresion;
        this.arranque = arranque;
        this.suspensionDelantera = suspensionDelantera;
        this.suspensionTrasera = suspensionTrasera;
        this.frenoDelantero = frenoDelantero;
        this.frenoTrasero = frenoTrasero;
        this.largoTotal = largoTotal;
        this.alturaSillin = alturaSillin;
        this.anchoTotal = anchoTotal;
        this.encendido = encendido;
        this.distanciaEntreEjes = distanciaEntreEjes;
        this.distanciaMotorAlPiso = distanciaMotorAlPiso;
        this.llantaDelantera = llantaDelantera;
        this.llantaTrasera = llantaTrasera;
        this.pesoSeco = pesoSeco;
        this.cajaVelocidades = cajaVelocidades;
        this.garantia = garantia;
        this.revisionesGratis = revisionesGratis;
        this.descripcion = descripcion;
        this.modelo = modelo;
        this.descuento = descuento;
        this.nuevoValorDescuento = nuevoValorDescuento;
        this.carpeta1 = carpeta1;
        this.carpeta2 = carpeta2;
        this.carpeta3 = carpeta3;
        this.manualesArchivos = manualesArchivos;
        this.nombresArchivos = nombresArchivos;
        this.caracteristicasTexto = caracteristicasTexto;
        this.caracteristicasImagenes = caracteristicasImagenes;
        this.clasificacion = clasificacion;
        this.vistas = vistas;
        this.busquedas = busquedas;
        this.consumoPorGalon = consumoPorGalon;
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getNombreMoto() {
        return nombreMoto;
    }

    public void setNombreMoto(String nombreMoto) {
        this.nombreMoto = nombreMoto;
    }

    public String getMarcaMoto() {
        return marcaMoto;
    }

    public void setMarcaMoto(String marcaMoto) {
        this.marcaMoto = marcaMoto;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public ArrayList<String> getColores() {
        return colores;
    }

    public void setColores(ArrayList<String> colores) {
        this.colores = colores;
    }

    public ArrayList<String> getImagenesColores1() {
        return imagenesColores1;
    }

    public void setImagenesColores1(ArrayList<String> imagenesColores1) {
        this.imagenesColores1 = imagenesColores1;
    }

    public ArrayList<String> getImagenesColores2() {
        return imagenesColores2;
    }

    public void setImagenesColores2(ArrayList<String> imagenesColores2) {
        this.imagenesColores2 = imagenesColores2;
    }

    public ArrayList<String> getImagenesColores3() {
        return imagenesColores3;
    }

    public void setImagenesColores3(ArrayList<String> imagenesColores3) {
        this.imagenesColores3 = imagenesColores3;
    }

    public ArrayList<String> getImagenesColores4() {
        return imagenesColores4;
    }

    public void setImagenesColores4(ArrayList<String> imagenesColores4) {
        this.imagenesColores4 = imagenesColores4;
    }

    public ArrayList<String> getImagenesColores5() {
        return imagenesColores5;
    }

    public void setImagenesColores5(ArrayList<String> imagenesColores5) {
        this.imagenesColores5 = imagenesColores5;
    }

    public ArrayList<String> getImagenesColores6() {
        return imagenesColores6;
    }

    public void setImagenesColores6(ArrayList<String> imagenesColores6) {
        this.imagenesColores6 = imagenesColores6;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getTorqueMaximo() {
        return torqueMaximo;
    }

    public void setTorqueMaximo(String torqueMaximo) {
        this.torqueMaximo = torqueMaximo;
    }

    public String getCapacidadTanque() {
        return capacidadTanque;
    }

    public void setCapacidadTanque(String capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }

    public String getCompresion() {
        return compresion;
    }

    public void setCompresion(String compresion) {
        this.compresion = compresion;
    }

    public String getArranque() {
        return arranque;
    }

    public void setArranque(String arranque) {
        this.arranque = arranque;
    }

    public String getSuspensionDelantera() {
        return suspensionDelantera;
    }

    public void setSuspensionDelantera(String suspensionDelantera) {
        this.suspensionDelantera = suspensionDelantera;
    }

    public String getSuspensionTrasera() {
        return suspensionTrasera;
    }

    public void setSuspensionTrasera(String suspensionTrasera) {
        this.suspensionTrasera = suspensionTrasera;
    }

    public String getFrenoDelantero() {
        return frenoDelantero;
    }

    public void setFrenoDelantero(String frenoDelantero) {
        this.frenoDelantero = frenoDelantero;
    }

    public String getFrenoTrasero() {
        return frenoTrasero;
    }

    public void setFrenoTrasero(String frenoTrasero) {
        this.frenoTrasero = frenoTrasero;
    }

    public String getLargoTotal() {
        return largoTotal;
    }

    public void setLargoTotal(String largoTotal) {
        this.largoTotal = largoTotal;
    }

    public String getAlturaSillin() {
        return alturaSillin;
    }

    public void setAlturaSillin(String alturaSillin) {
        this.alturaSillin = alturaSillin;
    }

    public String getAnchoTotal() {
        return anchoTotal;
    }

    public void setAnchoTotal(String anchoTotal) {
        this.anchoTotal = anchoTotal;
    }

    public String getEncendido() {
        return encendido;
    }

    public void setEncendido(String encendido) {
        this.encendido = encendido;
    }

    public String getDistanciaEntreEjes() {
        return distanciaEntreEjes;
    }

    public void setDistanciaEntreEjes(String distanciaEntreEjes) {
        this.distanciaEntreEjes = distanciaEntreEjes;
    }

    public String getDistanciaMotorAlPiso() {
        return distanciaMotorAlPiso;
    }

    public void setDistanciaMotorAlPiso(String distanciaMotorAlPiso) {
        this.distanciaMotorAlPiso = distanciaMotorAlPiso;
    }

    public String getLlantaDelantera() {
        return llantaDelantera;
    }

    public void setLlantaDelantera(String llantaDelantera) {
        this.llantaDelantera = llantaDelantera;
    }

    public String getLlantaTrasera() {
        return llantaTrasera;
    }

    public void setLlantaTrasera(String llantaTrasera) {
        this.llantaTrasera = llantaTrasera;
    }

    public String getPesoSeco() {
        return pesoSeco;
    }

    public void setPesoSeco(String pesoSeco) {
        this.pesoSeco = pesoSeco;
    }

    public String getCajaVelocidades() {
        return cajaVelocidades;
    }

    public void setCajaVelocidades(String cajaVelocidades) {
        this.cajaVelocidades = cajaVelocidades;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getRevisionesGratis() {
        return revisionesGratis;
    }

    public void setRevisionesGratis(String revisionesGratis) {
        this.revisionesGratis = revisionesGratis;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }

    public int getNuevoValorDescuento() {
        return nuevoValorDescuento;
    }

    public void setNuevoValorDescuento(int nuevoValorDescuento) {
        this.nuevoValorDescuento = nuevoValorDescuento;
    }

    public String getCarpeta1() {
        return carpeta1;
    }

    public void setCarpeta1(String carpeta1) {
        this.carpeta1 = carpeta1;
    }

    public String getCarpeta2() {
        return carpeta2;
    }

    public void setCarpeta2(String carpeta2) {
        this.carpeta2 = carpeta2;
    }

    public String getCarpeta3() {
        return carpeta3;
    }

    public void setCarpeta3(String carpeta3) {
        this.carpeta3 = carpeta3;
    }

    public ArrayList<String> getManualesArchivos() {
        return manualesArchivos;
    }

    public void setManualesArchivos(ArrayList<String> manualesArchivos) {
        this.manualesArchivos = manualesArchivos;
    }

    public ArrayList<String> getNombresArchivos() {
        return nombresArchivos;
    }

    public void setNombresArchivos(ArrayList<String> nombresArchivos) {
        this.nombresArchivos = nombresArchivos;
    }

    public ArrayList<String> getCaracteristicasTexto() {
        return caracteristicasTexto;
    }

    public void setCaracteristicasTexto(ArrayList<String> caracteristicasTexto) {
        this.caracteristicasTexto = caracteristicasTexto;
    }

    public ArrayList<String> getCaracteristicasImagenes() {
        return caracteristicasImagenes;
    }

    public void setCaracteristicasImagenes(ArrayList<String> caracteristicasImagenes) {
        this.caracteristicasImagenes = caracteristicasImagenes;
    }

    public ArrayList<String> getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ArrayList<String> clasificacion) {
        this.clasificacion = clasificacion;
    }

    public double getVistas() {
        return vistas;
    }

    public void setVistas(double vistas) {
        this.vistas = vistas;
    }

    public double getBusquedas() {
        return busquedas;
    }

    public void setBusquedas(double busquedas) {
        this.busquedas = busquedas;
    }

    public int getConsumoPorGalon() {
        return consumoPorGalon;
    }

    public void setConsumoPorGalon(int consumoPorGalon) {
        this.consumoPorGalon = consumoPorGalon;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
