package com.example.demo.entities;

import jakarta.persistence.Id;

public class Secteur {

	@Id
	    private String refSecteur;
	  
		private String usine;
	    private String etage;
		public Secteur(String refSecteur, String usine, String etage) {
			super();
			this.refSecteur = refSecteur;
			this.usine = usine;
			this.etage = etage;
		}
		  public Secteur() {
				super();
				// TODO Auto-generated constructor stub
			}
		  public String getRefSecteur() {
				return refSecteur;
			}
			public void setRefSecteur(String refSecteur) {
				this.refSecteur = refSecteur;
			}
			public String getUsine() {
				return usine;
			}
			public void setUsine(String usine) {
				this.usine = usine;
			}
			public String getEtage() {
				return etage;
			}
			public void setEtage(String etage) {
				this.etage = etage;
			}

}
