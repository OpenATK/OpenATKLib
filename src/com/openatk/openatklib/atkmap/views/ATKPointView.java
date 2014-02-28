package com.openatk.openatklib.atkmap.views;



import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openatk.openatklib.atkmap.listeners.ATKPointClickListener;
import com.openatk.openatklib.atkmap.models.ATKPoint;

public class ATKPointView {
	private ATKPoint point;
	private GoogleMap map;
	private ATKPointClickListener clickListener;
	
	private Marker marker;
	private MarkerOptions markerOptions;
	private boolean disabled = false;
	private boolean cluster = false;


	public ATKPointView(GoogleMap map, ATKPoint point){
		this.map = map;
		this.point = point;
		markerOptions = new MarkerOptions().position(point.position);
		this.drawPoint();
	}
	
	public ATKPoint getAtkPoint(){
		return point;
	}
	public void setAtkPoint(ATKPoint point){
		this.point = point;
		this.drawPoint();
	}
	
	public void update(){
		this.drawPoint();
	}
	
	public void remove(){
		if(marker != null) marker.remove();
		marker = null;
	}
	
	public void hide(){
		this.markerOptions.visible(false);
		if(this.marker != null) this.marker.setVisible(false);
	}
	
	public void show(){
		this.markerOptions.visible(true);
		if(this.marker != null) this.marker.setVisible(false);
	}
	
	public void setIcon(BitmapDescriptor icon){
		this.markerOptions.icon(icon);
		if(marker != null) marker.setIcon(icon);
	}
	
	public void setAnchor(float horizontal, float vertical){
		this.markerOptions.anchor(horizontal, vertical);
		if(marker != null) marker.setAnchor(horizontal, vertical);
	}
	
	public void setOnClickListener(ATKPointClickListener listener){
		this.clickListener = listener;
	} 
	
	public Boolean wasClicked(Marker clickedMarker){  //TODO protected?
		//Returns null if wasn't clicked, false if clicked and consumed, true if clicked and not consumed
		Boolean consumed = null;
		if(this.marker.equals(clickedMarker)){
			consumed = false;
			//Check if we have a click listener
			if(this.clickListener != null){
				consumed = this.clickListener.onClick(this);
			}
		}
		return consumed;
	}
	
	public void cluster(boolean cluster){
		this.cluster = cluster;
	}
	
	public void disableDrawing(boolean disabled){
		//Used by ATKPointClusterer
		this.disabled = disabled;
		if(disabled == true) this.remove();
	}
	
	private void drawPoint(){
		//Draw the point on the map
		if(point.position != null && disabled == false) {
			markerOptions.position(point.position);
			if(marker == null) {
				marker = this.map.addMarker(markerOptions);
			} else {
				marker.setPosition(point.position);
			}
		}
	}
}
