package com.example.demo.entities;

public class Intervention {
    private Long dataBon;
    private String dataGmao;
    private String dataDeman;
    private String dataNum;
    private String dataLocal;
    private String dataType;
    private String dataEtat;
    private String dataRef;
    private int dataQtP;
    private int dataQt;
    private String dataDes;
    private String descTrav;
    private String dataDemont;
    private String dataMont;
    private String dataPos;
    private String dataProb;
    private String dateDebut;
    private String dateFin;
    private String heureDebut;
    private String heureFin;
    private String dataDate;
    private String dataChrono;
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	public String getDataChrono() {
		return dataChrono;
	}
	public void setDataChrono(String dataChrono) {
		this.dataChrono = dataChrono;
	}
	public Long getDataBon() {
		return dataBon;
	}
	public void setDataBon(Long dataBon) {
		this.dataBon = dataBon;
	}
	public String getDataGmao() {
		return dataGmao;
	}
	public void setDataGmao(String dataGmao) {
		this.dataGmao = dataGmao;
	}
	public String getDataDeman() {
		return dataDeman;
	}
	public void setDataDeman(String dataDeman) {
		this.dataDeman = dataDeman;
	}
	public String getDataNum() {
		return dataNum;
	}
	public void setDataNum(String dataNum) {
		this.dataNum = dataNum;
	}
	public String getDataLocal() {
		return dataLocal;
	}
	public void setDataLocal(String dataLocal) {
		this.dataLocal = dataLocal;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataEtat() {
		return dataEtat;
	}
	public void setDataEtat(String dataEtat) {
		this.dataEtat = dataEtat;
	}
	public String getDataRef() {
		return dataRef;
	}
	public void setDataRef(String dataRef) {
		this.dataRef = dataRef;
	}
	public int getDataQtP() {
		return dataQtP;
	}
	public void setDataQtP(int dataQtP) {
		this.dataQtP = dataQtP;
	}
	public int getDataQt() {
		return dataQt;
	}
	public void setDataQt(int dataQt) {
		this.dataQt = dataQt;
	}
	public String getDataDes() {
		return dataDes;
	}
	public void setDataDes(String dataDes) {
		this.dataDes = dataDes;
	}
	public String getDescTrav() {
		return descTrav;
	}
	public void setDescTrav(String descTrav) {
		this.descTrav = descTrav;
	}
	public String getDataDemont() {
		return dataDemont;
	}
	public void setDataDemont(String dataDemont) {
		this.dataDemont = dataDemont;
	}
	public String getDataMont() {
		return dataMont;
	}
	public void setDataMont(String dataMont) {
		this.dataMont = dataMont;
	}
	public String getDataPos() {
		return dataPos;
	}
	public void setDataPos(String dataPos) {
		this.dataPos = dataPos;
	}
	public String getDataProb() {
		return dataProb;
	}
	public void setDataProb(String dataProb) {
		this.dataProb = dataProb;
	}
	public String getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	public String getDateFin() {
		return dateFin;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
	public String getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}
	public String getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}
	public Intervention() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Intervention(Long dataBon, String dataGmao, String dataDeman, String dataNum, String dataLocal,
			String dataType, String dataEtat, String dataRef, int dataQtP, int dataQt, String dataDes, String descTrav,
			String dataDemont, String dataMont, String dataPos, String dataProb, String dateDebut, String dateFin,
			String heureDebut, String heureFin,String dataDate,String dataChrono) {
		super();
		this.dataBon = dataBon;
		this.dataGmao = dataGmao;
		this.dataDeman = dataDeman;
		this.dataNum = dataNum;
		this.dataLocal = dataLocal;
		this.dataType = dataType;
		this.dataEtat = dataEtat;
		this.dataRef = dataRef;
		this.dataQtP = dataQtP;
		this.dataQt = dataQt;
		this.dataDes = dataDes;
		this.descTrav = descTrav;
		this.dataDemont = dataDemont;
		this.dataMont = dataMont;
		this.dataPos = dataPos;
		this.dataProb = dataProb;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.dataChrono = dataChrono;
		this.dataDate = dataDate;
	}
	@Override
	public String toString() {
		return "Intervention [dataBon=" + dataBon + ", dataGmao=" + dataGmao + ", dataDeman=" + dataDeman + ", dataNum="
				+ dataNum + ", dataLocal=" + dataLocal + ", dataType=" + dataType + ", dataEtat=" + dataEtat
				+ ", dataRef=" + dataRef + ", dataQtP=" + dataQtP + ", dataQt=" + dataQt + ", dataDes=" + dataDes
				+ ", descTrav=" + descTrav + ", dataDemont=" + dataDemont + ", dataMont=" + dataMont + ", dataPos="
				+ dataPos + ", dataProb=" + dataProb + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
				+ ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + "]";
	}

    // Getters et setters
}
