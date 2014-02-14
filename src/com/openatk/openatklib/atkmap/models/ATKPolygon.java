package com.openatk.openatklib.atkmap.models;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class ATKPolygon extends ATKModel {
	public List<LatLng> boundary;
	public String label;
	//TODO add holes
	
	public ATKPolygon(Object id){
		this.id = id;
		this.boundary = new ArrayList<LatLng>();
		this.label = "";
	}
	public ATKPolygon(Object id, String label){
		this.id = id;
		this.boundary = new ArrayList<LatLng>();
		this.label = "";
		if(label != null){
			this.label = label;
		} else {
			this.label = "";
		}
	}
	public ATKPolygon(Object id, List<LatLng> boundary){
		this.id = id;
		this.boundary = boundary;
		this.label = "";
	}
	public ATKPolygon(Object id, List<LatLng> boundary, String label){
		this.id = id;
		this.boundary = boundary;
		if(label != null){
			this.label = label;
		} else {
			this.label = "";
		}
	}
}