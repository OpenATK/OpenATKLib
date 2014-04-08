package com.openatk.openatklib.atkmap.views;



import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openatk.openatklib.R;
import com.openatk.openatklib.atkmap.listeners.ATKPointClickListener;
import com.openatk.openatklib.atkmap.listeners.ATKPointDragListener;
import com.openatk.openatklib.atkmap.models.ATKPoint;

public class ATKPointView {
	private ATKPoint point;
	private GoogleMap map;
	private ATKPointClickListener clickListener;
	private ATKPointDragListener dragListener;
	
	private Marker marker;
	private MarkerOptions markerOptions;
	private boolean disabled = false;
	private boolean cluster = false;
	private boolean draggable = false;
	
	private int iconWidth;
	private int iconHeight;
	
	private Object userData;


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
		if(this.marker != null) this.marker.setVisible(true);
	}
	
	public void setIcon(BitmapDescriptor icon, int width, int height){
		this.iconHeight = height;
		this.iconWidth = width;
		this.markerOptions.icon(icon);
		if(marker != null) marker.setIcon(icon);
	}
	
	public void setIcon(Bitmap bitmapIcon){
		this.iconHeight = bitmapIcon.getHeight();
		this.iconWidth = bitmapIcon.getWidth();
		BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmapIcon);
		this.markerOptions.icon(icon);
		if(this.marker != null) marker.setIcon(icon);
	}
	
	public int getIconWidth(){
		return this.iconWidth;
	}
	
	public int getIconHeight(){
		return this.iconHeight;
	}
	
	public void setAnchor(float horizontal, float vertical){
		this.markerOptions.anchor(horizontal, vertical);
		if(marker != null) marker.setAnchor(horizontal, vertical);
	}
	
	public float getAnchorU(){
		return this.markerOptions.getAnchorU();
	}
	
	public float getAnchorV(){
		return this.markerOptions.getAnchorV();
	}
	
	public void setOnClickListener(ATKPointClickListener listener){
		this.clickListener = listener;
	} 
	
	public void setOnDragListener(ATKPointDragListener listener){
		this.dragListener = listener;
	}
	
	public Boolean wasClicked(Marker clickedMarker){  //TODO protected?
		//Returns null if wasn't clicked, false if clicked and not consumed, true if clicked and consumed
		Boolean consumed = null;
		if(this.marker.equals(clickedMarker)){
			consumed = false;
			//Check if we have a click listener
			if(this.clickListener != null){
				consumed = this.clickListener.onPointClick(this);
			}
		}
		return consumed;
	}
	
	public Boolean dragStart(){
		Boolean ret = null;
		if(this.dragListener != null) {
			ret = this.dragListener.onPointDragStart(this);
		}
		return ret;
	}
	
	public Boolean dragEnd(){
		Boolean ret = null;
		if(this.dragListener != null) {
			ret = this.dragListener.onPointDragEnd(this);
		}
		return ret;
	}
	
	public Boolean drag(){
		Boolean ret = null;
		if(this.dragListener != null) {
			ret = this.dragListener.onPointDrag(this);
		}
		return ret;
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
	
	public void setSuperDraggable(boolean draggable){
		this.draggable = draggable;
	}
	public boolean getSuperDraggable(){
		return this.draggable;
	}
	
	public void setData(Object data){
		this.userData = data;
	}
	
	public Object getData(){
		return this.userData;
	}
}
