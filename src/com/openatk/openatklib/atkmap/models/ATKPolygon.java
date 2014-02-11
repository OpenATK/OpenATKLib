package com.openatk.openatklib.atkmap.models;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class ATKPolygon extends ATKModel {
	public List<LatLng> boundary;
	//TODO add holes
	
	public ATKPolygon(Object id){
		this.id = id;
		this.boundary = new ArrayList<LatLng>();
	}
	public ATKPolygon(Object id, List<LatLng> boundary){
		this.id = id;
		this.boundary = boundary;
	}
}
