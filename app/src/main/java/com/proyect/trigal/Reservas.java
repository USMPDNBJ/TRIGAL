package com.proyect.trigal;

import android.os.Parcel;
import android.os.Parcelable;

public class Reservas implements Parcelable {
    private String empresa, responsable, sala,personas,fecha,horaEntrada,horaSalida,usuario;
    public Reservas(String empresa, String responsable, String sala, String personas, String fecha, String horaEntrada, String horaSalida) {
        this.empresa = empresa;
        this.responsable = responsable;
        this.sala = sala;
        this.personas = personas;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }
    public Reservas(String empresa, String responsable, String sala, String personas, String fecha, String horaEntrada, String horaSalida,String usuario) {
        this.empresa = empresa;
        this.responsable = responsable;
        this.sala = sala;
        this.personas = personas;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.usuario=usuario;
    }
    public Reservas() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getPersonas() {
        return personas;
    }

    public void setPersonas(String personas) {
        this.personas = personas;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public String getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
    @Override
    public String toString() {
        return "Reserva" +
                "\nEmpresa : '" + empresa + '\'' +
                "\nResponsable : '" + responsable + '\'' +
                "\nSala : '" + sala + '\'' +
                "\nPersonas : '" + personas + '\'' +
                "\nFecha : '" + fecha + '\'' +
                "\nHoraEntrada : '" + horaEntrada + '\'' +
                "\nHoraSalida : '" + horaSalida + '\'';
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.empresa);
        dest.writeString(this.responsable);
        dest.writeString(this.sala);
        dest.writeString(this.personas);
        dest.writeString(this.fecha);
        dest.writeString(this.horaEntrada);
        dest.writeString(this.horaSalida);
    }
    public void readFromParcel(Parcel source) {
        this.empresa = source.readString();
        this.responsable = source.readString();
        this.sala = source.readString();
        this.personas = source.readString();
        this.fecha = source.readString();
        this.horaEntrada = source.readString();
        this.horaSalida = source.readString();
    }

    protected Reservas(Parcel in) {
        this.empresa = in.readString();
        this.responsable = in.readString();
        this.sala = in.readString();
        this.personas = in.readString();
        this.fecha = in.readString();
        this.horaEntrada = in.readString();
        this.horaSalida = in.readString();
    }

    public static final Creator<Reservas> CREATOR = new Creator<Reservas>() {
        @Override
        public Reservas createFromParcel(Parcel source) {
            return new Reservas(source);
        }

        @Override
        public Reservas[] newArray(int size) {
            return new Reservas[size];
        }
    };
}
